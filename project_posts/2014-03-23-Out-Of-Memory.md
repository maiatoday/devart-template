###### Out of Memory
Testing on an old device made the app crash. With larger images I got an out of memory. Also there were some usability issues. You could turn the blackbox handle but because of threading the ding-your-image-is-ready worked for some test scenarios but not all. Fixing this up made an endless loop which I had to track down. That is testing for you. The code could do with some optimising too but there are a few other things I'd like to do before I spend time on minimising memory or speeding things up. I did put in a progress bar so you can see that the app hasn't hung but is busy with something.

After doing these changes I could easily re-apply the glitchy-dotty code to an image in succession.
![2014_03_23_22_22_57](../project_images/2014_03_23_22_22_57.gif?raw=true "2014_03_23_22_22_57")
![2014_03_23_22_27_32](../project_images/2014_03_23_22_27_32.gif?raw=true "2014_03_23_22_27_32")

###### Things to do for Friday
At a minimum I need to do the following
 * New project image and update description
 * The app needs some fixing for the small old phones.
 * Upload a new version to play


