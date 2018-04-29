# 2018 Offseason Code

This is a compilation of all of the code developed over the 2018 summer/offseason. This is the first time migrating to [GradleRIO](https://github.com/Open-RIO/GradleRIO), which makes developing code smoother,more open, and removes a lot of frustration with building from ANT.

## Prerequisites

As this is using [GradleRIO](https://github.com/Open-RIO/GradleRIO), there are technically no prerequisites. It is recommended to use [VS Code](https://code.visualstudio.com/), but if you wish, you can use any text editor as long as you understand the losses.

You will need [Git](https://git-scm.com/) if you want to keep your code up to date.

### Cloning with [Git](https://git-scm.com/)

The best way to work with this code is by cloning it from GitHub through [Git](https://git-scm.com/).

Once Git is installed, [set up Git](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup) with your user data.

The following commands work best from Git Bash, which is installed with Git, but Powershell and CMD will work as well.

- First create a new directory to store all of the code if you do not already have one. For this example, it will be '~/Documents/code'
- Clone this repository into that directory:
```sh
~ $ cd ~/Documents/code
~/Documents/code $ git clone https://github.com/HillelCoates/2018-offseason.git
```

### Download the zip package

If you prefer not to or can't use Git, the alternative is to just download the most recent ZIP file. Simply click on "Clone of download", select "Download ZIP", and the download should begin.

## Deployment

To deploy to the RoboRIO, GradleRIO makes this very easy. For this, it is recommended to use Powershell on Windows. In VS Code, you can set this as the Terminal application.
- `./gradlew build` will build your code
- `./gradlew deploy` will build and deploy your code
- `./gradlew riolog` will display the RoboRIO's console output
    - use `-Pfakeds` if there is not a driverstation connected

- use `--offline` with all commands when at a competition or when tethered

For more information, read the [GradleRIO README](https://github.com/Open-RIO/GradleRIO/blob/master/README.md).

## Key features

Feature and in-depth description. Make sure that each line of code displayed is documented with inline comments.
```java
// The constructor for Thing does not take any arguments
private mThing = new Thing();

mThing.doTheThing(); // This does the thing

mThing.doTheOherThing(); // This is the other thing
```