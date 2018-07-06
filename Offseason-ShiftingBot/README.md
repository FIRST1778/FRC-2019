# 2018 Offseason Code

This is a compilation of all of the code developed over the 2018 summer/offseason. This is the first time migrating to [GradleRIO](https://github.com/Open-RIO/GradleRIO), which makes developing code smoother, more open, and removes a lot of frustration caused by Eclipse and Ant.

## Prerequisites

As this is using [GradleRIO](https://github.com/Open-RIO/GradleRIO), there are technically no prerequisites. It is recommended to use [VS Code](https://code.visualstudio.com/), but if you wish, you can use any text editor.

You will need [Git](https://git-scm.com/) if you want to keep your code up to date.

## Deployment

GradleRIO makes deploying your code very easy. It is recommended to use Powershell on Windows. In VS Code, you can set this by setting `"terminal.integrated.shell.windows"` to the path to Powershell in the `settings.json` file.

The following commands can be used to deploy, build, or monitor your code:

- `./gradlew build` will build your code
- `./gradlew deploy` will build and deploy your code
- `./gradlew riolog` will display the RoboRIO's console output
  - use `-Pfakeds` if there is not a driverstation connected
- amend `--offline` with all of these commands when at a competition or tethered

For more information, read through the documentation on the [GradleRIO repo](https://github.com/Open-RIO/GradleRIO).

## Writing code

Make sure that all code you write follows the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html). This makes everything much easier to read.

In addition, use [Google's Java formatter](https://github.com/google/google-java-format), which is already included as a jar in this project. To do this run the following command on any changed files:

```sh
java -jar .\google-java-format-1.6-all-deps.jar -i .\src\main\java\frc\team1778\path\to\file.java
```

### Javadoc

Javadocs are declared using all of the rules of block comments, but begin with `/**`, having a second asterisk.

Any public class and every public or protected method is required to have a Javadoc. This is already required by the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html#s7-javadoc).

## Key features

Feature and in-depth description. Make sure that each line of code displayed is documented with inline comments.

```java
// The constructor for Thing does not take any arguments
private thing = new Thing();

mThing.doTheThing(); // This does the thing

mThing.doTheOherThing(); // This is the other thing
```