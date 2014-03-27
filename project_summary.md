# FIFO ribbon
previously known as IllegalArgumentException 
```throw new IllegalArgumentException("Problem decoding into existing bitmap");```

## Maia Grotepass
- [Maia Grotepass](https://github.com/maiatoday)

## Description
As often happens this project is a remix of older ideas and older projects. I am interested in making the layers of software that surround us a bit more visible. Every word and image we share in a mediated way goes through software. We don't even think about it anymore. There are people and machines who make decisions in these software layers. User experience design tries to give a sense of control to the user.

There are more ideas: My phone can recognises me. Can I make it alter images I post of myself so that it no longer recognises me? Does this mean the code in the Internet which comes across my face will no longer recognise me? Will my friends recognise me then.

The project has an Android application and an installation component. 

### App
![device-2014-03-27-105703](project_images/device-2014-03-27-105703.png?raw=true "device-2014-03-27-105703") 

The app lets you take an image and modifies it. This image is uploaded to a server or can be shared on Google+. The app creates the visual elements of the installation. The app uses Median Cut Quantisation to pick out colours that occur often. It then uses this information to influence the dot placement. It does face recognition to determine where the eyes are. It then applies bit shift glitches to areas in the image. It also uses face recognition to see if the resulting image has a face in it.

http://youtu.be/CCLwW-z-zSk

### Installation
![strip01.resized](project_images/strip01.resized.gif?raw=true "strip01.resized")
The installation consists of a ribbon of translucent material that is suspended in the centre of the space. It can be viewed from both sides. It is at eye height but you can see the feet of the people on the other side. It is a floating [FIFO](http://en.wikipedia.org/wiki/FIFO) buffer where images come in from the one side and shift. It is a image memory-word with bits/images [shifted right](http://en.wikipedia.org/wiki/Logical_right_shift) at a continous tempo looping through a cache of images from previous posts. When a new image is created by the app, the image is added to the end of the image queue.

![strip02.resized](project_images/strip02.resized.gif?raw=true "strip02.resized")
You stand infront of the ribbon looking at your mediated self or someone else. On the other side of the ribbon is another person looking at the same image, mirrored, but you cannot see each other's face. You see the bottom half of the person on the otherside with faces shifting over it. You can add an image to the FIFO ribbon with the app.

![IMG_0944](project_images/IMG_0944.JPG?raw=true "IMG_0944")

#### Installation equipment
* 8 projectors
* 8 Pcs running ubuntu with internet access and TCP/IP connection to each other
* strip white translucent vinyl (1m x 10m)
* gut and clips to suspend

## Concepts and choices
![s4.resized](project_images/s4.resized.gif?raw=true "s4.resized")
### Themes
Portraits, self-portraits, autoportrait, selfie because it's old and new and personal. Because something/someone else changing/interpreting an image of me could be flattering and/or unnerving. Because it changes how other people see me. Because it reveals and hides something at the same time. Is there a likeness in the portrait? Who can see the likeness, the subject, the algorithm, the people in my circles, the machines on the way? 

### Colour choices
A portrait painter looks at the subject and chooses colours possibly based on what she sees. My code looks at the image and chooses colours based on the pixels captured by the CCD and an algorithm. Sometimes the pixels are glitched around in the same way memory and data structures are handled. The colour shifts are caused by bits in memory moving around making the process visible.

### Dots
The dots remind me of the [Baudot code](http://en.wikipedia.org/wiki/Baudot_code) which was used to record data on paper ribbons. The translucent ribbon in space references the paper code ribbons used in the past. The dots also connect to the [Ben-Day dots](http://en.wikipedia.org/wiki/Ben-Day_dots) used by Roy Lichtenstein. It refers to the use of consumer image making techniques used to make this artwork: a phone camera, an app and the internet. 

### Spacial arrangement
The ribbon is placed in the space, among the viewers. At the same time it prevents viewers on either side of the ribbon from seeing each other. It is a physical representation of the mediated, codified, connected space which we use to see representations of ourselves as well as representations of other people.

### FIFO and bit shifting
The arrangement of 8 shifting images on the ribbon mirrors the way the bits in the image are shifted to create the glitch blocks in the app. It is a low level activity that is happening inside the computers around us all the time. The FIFO queue also surrounds us in various guises in the technology we use. In this way these machine level activities are made visible in a physical installation.

### Mirrors
Using an phone app for a self portrait feels similar to looking in a small mirror. The ribbon is at eye height and if you see your own image in it, it works as a mediated mirror. Data collected on the the Internet becomes another kind of mirror showing a representation of you. This data is mirrored from server to server as it persist on the Internet. The Internet mirrors become the mirrors we all use, conciously or not, to create our mediated self portraits.


## Link to Prototype

[app on github](https://github.com/maiatoday/devart-template/tree/master/project_code/DevArt)

[Android app on Google Play](https://play.google.com/store/apps/details?id=za.co.maiatoday.devart)
[![Android app on Google Play][1]][2]

[![Build Status](https://travis-ci.org/maiatoday/devart-template.png?branch=master)](https://travis-ci.org/maiatoday/devart-template)

[1]: https://developer.android.com/images/brand/en_app_rgb_wo_45.png
[2]: https://play.google.com/store/apps/details?id=za.co.maiatoday.devart
