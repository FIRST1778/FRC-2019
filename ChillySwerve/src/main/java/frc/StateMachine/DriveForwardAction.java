package frc.StateMachine;

import frc.ChillySwerve.ChillySwerve;

public class DriveForwardAction extends Action {

  private String name;
  private double targetPosInches = 0.0;
  private double angleDeg = 0.0;
  private int speedRpm = 0;
  private int accelRpm = 0;

  public DriveForwardAction(
      double targetPosInches, double angleDeg, int speedRpm, int accelRpm) {
    this.name = "<Drive Forward Action>";
    this.targetPosInches = targetPosInches;
    this.angleDeg = angleDeg;
    this.speedRpm = speedRpm;
    this.accelRpm = accelRpm;

    ChillySwerve.initialize();
  }

  public DriveForwardAction(
      String name,
      double targetPosInches,
      double angleDeg,
      int speedRpm,
      int accelRpm) {
    this.name = name;
    this.targetPosInches = targetPosInches;
    this.angleDeg = angleDeg;
    this.speedRpm = speedRpm;
    this.accelRpm = accelRpm;

    ChillySwerve.initialize();
  }

  // action entry
  public void initialize() {

    ChillySwerve.resetAllDriveEnc();
    ChillySwerve.autoStraight(targetPosInches, angleDeg, speedRpm, accelRpm);

    super.initialize();
  }

  // called periodically
  public void process() {

    // do some drivey stuff

    super.process();
  }

  // state cleanup and exit
  public void cleanup() {
    // do some drivey cleanup

    ChillySwerve.stopDrive();

    // cleanup base class
    super.cleanup();
  }
}
