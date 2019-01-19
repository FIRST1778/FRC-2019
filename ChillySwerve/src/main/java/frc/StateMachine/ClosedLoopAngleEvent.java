package frc.StateMachine;

import edu.wpi.first.wpilibj.RobotController;
import frc.NetworkComm.InputOutputComm;
import frc.Systems.NavXSensor;

// event triggered when closed-loop gyro gets to a certain predetermined angle
public class ClosedLoopAngleEvent extends Event {

  private String name;

  private double targetAngleDeg = 0.0;
  private double errorDeg = 5.0;
  private double durationSec = 0.0;

  private long startTimeUs = 0;

  public ClosedLoopAngleEvent(double targetAngleDeg, double errorDeg, double durationSec) {
    this.name = "<Gyro Angle Event>";

    this.targetAngleDeg = targetAngleDeg;
    this.errorDeg = errorDeg;
    this.durationSec = durationSec;

    NavXSensor.initialize();
    InputOutputComm.initialize();
  }

  // overloaded initialize method
  public void initialize() {

    startTimeUs = RobotController.getFPGATime();

    super.initialize();
  }

  private double getGyroAngle() {

    double gyroAngle = NavXSensor.getAngle(); // continuous angle (can be larger than 360 deg)

    // System.out.println("gyroAngle = " + gyroAngle);
    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "Auto/GyroAngle", gyroAngle);

    return gyroAngle;
  }

  // overloaded trigger method
  public boolean isTriggered() {
    double gyroAngle = getGyroAngle();

    if (Math.abs(gyroAngle - targetAngleDeg) > errorDeg) {

      // outside error range...
      // reset timer and return false
      startTimeUs = RobotController.getFPGATime();
      return false;
    }

    long currentTimeUs = RobotController.getFPGATime();
    double delta = (currentTimeUs - startTimeUs) / 1e6;
    // System.out.println("delta = " + delta + " duration = " + durationSec);

    if (delta < durationSec) {
      // within error range, but not for enough time
      return false;
    }

    System.out.println("ClosedLoopAngleEvent triggered!");
    return true;
  }
}
