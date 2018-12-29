package frc.StateMachine;

import frc.ChillySwerve.ChillySwerve;
import frc.NetworkComm.InputOutputComm;
import frc.Systems.FreezyPath;
import frc.Systems.NavXSensor;

public class FollowPathAction extends Action {

  private String name;
  private int pathToFollow;
  private boolean fwdPolarity;
  private boolean resetGyro;

  public FollowPathAction(int pathToFollow, boolean polarity, boolean resetGyro) {
    this.name = "<Follow Path Action>";
    this.pathToFollow = pathToFollow;
    this.fwdPolarity = polarity;
    this.resetGyro = resetGyro;

    ChillySwerve.initialize();
    NavXSensor.initialize();
    InputOutputComm.initialize();
    FreezyPath.initialize();
  }

  public FollowPathAction(String name, int pathToFollow, boolean polarity, boolean resetGyro) {
    this.name = name;
    this.pathToFollow = pathToFollow;
    this.fwdPolarity = polarity;
    this.resetGyro = resetGyro;

    ChillySwerve.initialize();
    NavXSensor.initialize();
    InputOutputComm.initialize();
    FreezyPath.initialize();
  }

  // action entry
  public void initialize() {
    if (resetGyro)
      NavXSensor.reset();

    // reset the drive encoders
    ChillySwerve.resetAllDriveEnc();

		// set up path and followers
		FreezyPath.reset(pathToFollow);

		// start the pathfinder thread
    FreezyPath.start(fwdPolarity);

    super.initialize();
  }

  // called periodically
  public void process() {

    // no action needed (Path Following is self-running)

    super.process();
  }

  // state cleanup and exit
  public void cleanup() {
    // do some drivey cleanup

    FreezyPath.stop();
    ChillySwerve.stopDrive();

    // cleanup base class
    super.cleanup();
  }
}
