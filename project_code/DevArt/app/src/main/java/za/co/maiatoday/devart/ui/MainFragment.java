package za.co.maiatoday.devart.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusShare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import za.co.maiatoday.devart.R;
import za.co.maiatoday.devart.util.ImageUtils;

/**
 * Created by maia on 2013/09/01.
 */
public class MainFragment extends Fragment implements View.OnTouchListener, PlusFragment.PlusStatusChangeListener {   // Update status button

    private static final int REQUEST_IMAGE = 2;
    Button mShareButton;
    EditText mUpdateText;
    private Uri shareUri;
    private ImageView mSelfieImage;
    Button mSnapButton;

    private Path path;
    private boolean debugHide = true;
    private boolean debugSaveFile = true;
    private Bitmap bitmap;
    private Matrix inverseMatrix = new Matrix();
    private MainNavigation mainActivity;

    private LinearLayout mChipStrip;
    private SignInButton mSignInButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getArguments().getParcelable("intent");

        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                shareUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);

        mSelfieImage = (ImageView) view.findViewById(R.id.result);
        populateImageFromUri(shareUri);
        mSnapButton = (Button) view.findViewById(R.id.btnSnap);
        // All UI elements
        mSignInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        mShareButton = (Button) view.findViewById(R.id.share_button);
        mUpdateText = (EditText) view.findViewById(R.id.txtUpdateStatus);
        PlusFragment plusFragment = PlusFragment.getInstance(getActivity());

        mChipStrip = (LinearLayout) view.findViewById(R.id.colorStrip);

        showPlusButtons(plusFragment.isConnected());

        BlackBoxFragment bbFragment = BlackBoxFragment.getInstance(getActivity());
        bbFragment.setImageView(mSelfieImage);
        bbFragment.setTextView(mUpdateText);

        mShareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PlusFragment plusFragment = PlusFragment.getInstance(getActivity());
                if (plusFragment.isConnected()) {
                    processImageToShare(true);
                } else {
                    mainActivity.switchToInfoFragment();
                }
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusFragment plusFragment = PlusFragment.getInstance(getActivity());
                plusFragment.signIn();
                mainActivity.switchToInfoFragment();
            }
        });

        mSnapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // debug so I don't have to get another pic but can try another algorithm
                if (bitmap != null) {
                    BlackBoxFragment bbFragment = BlackBoxFragment.getInstance(getActivity());
                    bbFragment.shake();
                }
                // get a new image
                openImageIntent();
            }
        });

        if (savedInstanceState != null) {
            processedImageUri = savedInstanceState.getParcelable("lastUri");
            populateImageFromUri(processedImageUri);
        }

        mSelfieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlackBoxFragment bbFragment = BlackBoxFragment.getInstance(getActivity());
                bbFragment.turnHandle();
            }
        });

        mSelfieImage.setOnTouchListener(this);
        return view;
    }

    /**
     * Update the views from the blackbox
     */
    private void updateViews() {
        BlackBoxFragment bbFragment = BlackBoxFragment.getInstance(getActivity());
        mSelfieImage.setImageBitmap(bbFragment.getBitmap());
        mUpdateText.setText(bbFragment.getStatus());
    }

    private void showPlusButtons(boolean isConnected) {
        mSignInButton.setVisibility(isConnected?View.GONE:View.VISIBLE);
        mShareButton.setVisibility(isConnected?View.VISIBLE:View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("lastUri", processedImageUri);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mainActivity = (MainNavigation) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement MainNavigation");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        populateImageFromUri(processedImageUri);
        PlusFragment plusFragment = PlusFragment.getInstance(getActivity());
        plusFragment.register(this);
        showPlusButtons(plusFragment.isConnected());
    }

    @Override
    public void onPause() {
        super.onPause();
        PlusFragment plusFragment = PlusFragment.getInstance(getActivity());
        plusFragment.unRegister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
            case REQUEST_IMAGE:
                processImage(data);
                updateViews();
                break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
        case R.id.action_share:
            processImageToShare(false);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private Uri outputFileUri;

    /**
     * Open and intent to get an image, either from camera or gallery
     */
    private void openImageIntent() {

// Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "autoSelfie" + File.separator);
        root.mkdirs();
        final String fname = ImageUtils.getUniqueImageFilename(".jpg");
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

        startActivityForResult(chooserIntent, REQUEST_IMAGE);
    }

    /**
     * Process the intent data and get the image
     *
     * @param data
     */
    private void processImage(Intent data) {
        final boolean isCamera;
        if (data == null) {
            isCamera = true;
        } else {
            final String action = data.getAction();
            if (action == null) {
                isCamera = false;
            } else {
                isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
            }
        }

        Uri selectedImageUri;
        if (isCamera) {
            selectedImageUri = outputFileUri;
        } else {
            selectedImageUri = data == null ? null : data.getData();
        }
        processedImageUri = selectedImageUri;
        populateImageFromUri(selectedImageUri);
    }

    private void populateImageFromUri(Uri selectedImageUri) {
        if (selectedImageUri == null) {
            return;
        }
        try {
            bitmap = ImageUtils.getSizedBitmap(getActivity(), selectedImageUri, mSelfieImage.getHeight());
        } catch (Exception e) {
            Toast.makeText(getActivity(),
                "Problem loading file", Toast.LENGTH_LONG).show();
            Log.d("autoselfie", "can't load image", e);
        }

        if (bitmap != null) {

            BlackBoxFragment bbFragment = BlackBoxFragment.getInstance(getActivity());
            bbFragment.setBitmap(bitmap);
            bbFragment.shake();
            mSelfieImage.setImageBitmap(bitmap);
            Matrix matrix = mSelfieImage.getImageMatrix();
            matrix.invert(inverseMatrix);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (bitmap != null) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(event.getX(), event.getY());
//                Log.d("DOWN", "DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
//                Log.d("MOVE", "MOVE");
                path.lineTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_UP:
//                Log.d("UP", "UP");
                path.lineTo(event.getX(), event.getY());
                RectF bounds = new RectF();
                path.computeBounds(bounds, false);
//                selfie.glitchImage(convertFromViewToImage(bounds), 0);
//                bitmap = drawPath(selfie.getBmpToPost(), path, pathColor); //The path is in the wrong place
//                mSelfieImage.setImageBitmap(selfie.getBmpToPost());
                break;
            }
        }
        return false;
    }

    private RectF convertFromViewToImage(RectF bounds) {
        RectF transBounds = new RectF();
        inverseMatrix.mapRect(transBounds, bounds);
        return transBounds;
    }

    private Bitmap drawPath(Bitmap in, Path path, int pathColor) {
        Bitmap out = in.copy(in.getConfig(), true);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setColor(pathColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        Canvas canvas = new Canvas();
        canvas.setBitmap(out);
        canvas.drawPath(path, paint);
        return out;

    }

    private Uri processedImageUri;

    private void processImageToShare(boolean plus) {
        BlackBoxFragment bbFragment = BlackBoxFragment.getInstance(getActivity());
        if (bbFragment.isDone()) {
            // Launch the Google+ share dialog with attribution to your app.
            processedImageUri = ImageUtils.saveBitmapToFile(bbFragment.getBitmap(), getActivity());
            ContentResolver cr = getActivity().getContentResolver();
            String mime = cr.getType(processedImageUri);
            Intent shareIntent;
            if (plus) {
                shareIntent = new PlusShare.Builder(getActivity())
                    .setType("text/plain")
                    .setText(mUpdateText.getText().toString())
//                        .setContentUrl(Uri.parse("http://www.maiatoday.co.za"))
                    .addStream(processedImageUri)
                    .setType(mime)
                    .getIntent();
            } else {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareIntent.setType(mime);
                shareIntent.putExtra(Intent.EXTRA_STREAM, processedImageUri);
            }
            startActivityForResult(shareIntent, 0);
            final Bitmap orig = bbFragment.getOriginalBitmap();
            if (debugHide) {
                Runnable r = new Runnable() {
                    public void run() {
                        mSelfieImage.setImageBitmap(orig);
                    }
                };
                mSelfieImage.postDelayed(r, 2000);
            }
        }

    }

    @Override
    public void onPlusStatusChange(boolean isConnected, String status) {
        showPlusButtons(isConnected);
        BlackBoxFragment b = BlackBoxFragment.getInstance(getActivity());
        b.makeDefaultImage();
    }
}
