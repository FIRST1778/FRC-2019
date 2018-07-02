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

## Writing code

Make sure that all code you write follows the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html). This makes everything much easier to read.

In addition, use Google's Java formatter, which is in this directory. To do this run the following, changing the file path:

```sh
java -jar .\google-java-format-1.6-all-deps.jar -i .\src\main\java\frc\team1778\path\to\file.java
```

### Javadoc

Javadocs are declared using all of the rules of block comments, but begin with `/**`, having a second asterisk.

Any public class and every public or protected method is required to have a Javadoc. This is already required by the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html#s7-javadoc).

### Commit and push to GitHub

Commit often, you should be able to see the progress of your code by looking at commits. Try to only commit if the code is runnable and has been built without errors. If it is not runnable, but you have to commit anyways, indicate as such in the commit message. To do this, first run the following:

```sh
git add -A # Stages all changed files
git status # Shows exactly what will be staged, make sure it is what you changed

# If everything looks fine, skip this, if not run:
git reset

git commit -m "Commit message" # Commits staged changes and documents it with the commit message
```

At the end of every work day, or when a milestone has been reached, push all commits.

```sh
git push origin master # Pushes commits from the origin to the master branch
```

## Key features

Feature and in-depth description. Make sure that each line of code displayed is documented with inline comments.

```java
// The constructor for Thing does not take any arguments
private thing = new Thing();

mThing.doTheThing(); // This does the thing

mThing.doTheOherThing(); // This is the other thing
```