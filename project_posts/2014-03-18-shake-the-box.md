![strip.resized](../project_images/strip.resized.png?raw=true "strip.resized")
###### blackbox 
The cogs have changed but there still isn't nearly enough variation. I now have a [headless BlackBoxFragment](https://github.com/maiatoday/devart-template/blob/master/project_code/DevArt/app/src/main/java/za/co/maiatoday/devart/ui/BlackBoxFragment.java) which does all of the mutating on a separate thread. You give it a picture, shake it and turn the handle and out pops a mutated image. You can shake the phone to choose the mutation cogs inside the BlackBoxFragment. Also maybe I will show if a face is still recognised after the mutation and mutate again until no faces can be recognised. 

###### apk
Just to make sure I made the build make a signed version and loaded the app onto play. It is far from finished but in the spirit of [release early release often](http://en.wikipedia.org/wiki/Release_early,_release_often) the app is in the wild. Just as well I did because I couldn't upload any files to play for 3 days. 
[![Android app on Google Play][1]][2]

###### a portrait from g+
At the moment it just pulls the g+ avatar when it signs in but this could be a g+ data enriched portrait.

###### testing testing
I have a g+ [alterego](https://plus.google.com/109493599093504543719/posts) to test the sharing. This is so I don't pollute my feed with test images. She is also the user on my genymotion emulator. Her images come from the webcam not the phone camera.

###### solve these problems
Google+: What else can I get from the api? How can I stir the info into the mix? What else, search history, ...

Installation: Can this intimate/internet piece be made to work in a gallery space? How?

###### some more cog ideas
 * mess with the saturation of the dots
 * use the rule of thirds
 * change the glitch zones from just using bands, maybe bring the code back that put the glitches on user paths
 * what about a little L-system overlay that seeds from age and number of circles
 * how to integrate the info I get from Google+

[1]: https://developer.android.com/images/brand/en_app_rgb_wo_45.png
[2]: https://play.google.com/store/apps/details?id=za.co.maiatoday.devart



