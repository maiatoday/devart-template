Time to think about how the installation could work.
![workbookscan002.resized](../project_images/workbookscan002.resized.png?raw=true "workbookscan002.resized")
##### Baudot-Ben-Day-FIFO-ribbon
Build a ribbon of translucent material that is suspended in the centre of the space. It can be viewed from both sides. It is at eye height but you can see the feet of the people on the other side. It is a floating [FIFO](http://en.wikipedia.org/wiki/FIFO) buffer where images come in from the one side and shift. It is a image memory word with bits/images [shifted right](http://en.wikipedia.org/wiki/Logical_right_shift) when a new image comes online.

You stand infront of the ribbon looking at your mediated self or someone else. On the other side of the ribbon is another person looking at the same image but you cannot see each other's face. You can add an image to the FIFO with the app.

It is a person-sized strip that reminds me of a strip of paper with [Baudot code](http://en.wikipedia.org/wiki/Baudot_code) dots.

##### Intimate - Public
Before it is posted, taking a selfie with your phone is intimate, like looking in a hand mirror. I am revisiting the idea that the user has/does not have a choice about the sharing of data. In the app, before the Google+ share screen is presented the image can be submitted to a server. The transition from phone to cloud has already happened. To make this work the images are collected and displayed on the ribbon FIFO screen.

##### How to do it
Images from the app are transferred to a server. In the physical space four computers running a simple client application, using openframeworks and the [Most-Pixels-Ever](https://github.com/shiffman/Most-Pixels-Ever-Processing/tree/master/Most-Pixels-Ever-Server) library, will slide the images over the ribbon. Each computer will connect to a projector which projects its part of the ribbon.

##### Missing software bits
* Add call in app to transfer the image before share - HTTP post
* Write the Most-Pixels-Ever client code using example on the wiki. Simple 8 image strip with new images sliding in from the one side. Adjust images to match portrait or landscape.
* Server collecting and timestamping images

##### Hardware and material
* 8 projectors
* 8 Pcs running ubuntu with internet access and TCP/IP connection to each other
* strip white translucent vinyl (1m x 10m)
* gut and clips to suspend





