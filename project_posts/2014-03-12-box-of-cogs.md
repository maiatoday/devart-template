###### Box of Cogs
![wb2014-03-11-001](../project_images/wb2014-03-11-001.png?raw=true "wb2014-03-11-001")
I refactored the architecture. I now have a box of cogs and I can add more cogs and just drop them in and spin each one. For now the previous cogs still work.

![Screenshot from 2014-03-11 20-55-09](../project_images/Screenshot_from_2014-03-11_20-55-09.png?raw=true "Screenshot from 2014-03-11 20-55-09")

I have some threading issues. The cogs should not spin on the UI thread of course. The face detect still works and there is a version of a [MedianCutQuantizer](https://github.com/biometrics/imagingbook/blob/master/src/color/MedianCutQuantizer.java) that can chew through an image and spit out some colors. Also some google+ bugs are fixed and some UI flow issues are fixed.

###### Some thoughts on process
Make more cogs. I want to build a version of the Benday dots I had before. To imagine a how an image mutating algorithm could work sometimes I like to draw and then  transpose the physical actions into code. Sometimes its just lots of tweaking on found code collaged together. Since I threw out the opencv for now because it complicated the install, I don't have edges to play with unless I find another bit of code that will do the edge detect for me.

