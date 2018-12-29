package frc.StateMachine;

import edu.wpi.first.wpilibj.RobotController;
import frc.ChillySwerve.ChillySwerve;

// event triggered when closed loop position control gets to within an error range of target for a
// time period
public class ClosedLoopPositionEvent extends Event {

  private String name;
  private double targetPosInches;
  private double errorThresholdInches;
  private double durationSec;
  private long startTimeUs;

  public ClosedLoopPositionEvent() {
    this.name = "<Closed-Loop Position Event>";
    this.durationSec = 0.0;
    this.errorThresholdInches = 0.0;
    this.targetPosInches = 0.0;
    ChillySwerve.initialize();
  }

  public ClosedLoopPositionEvent(
      double targetPosInches, double errorThreshInches, double durationSec) {
    this.name = "<Closed-Loop Position Event>";
    this.durationSec = durationSec;
    this.errorThresholdInches = errorThreshInches;
    this.targetPosInches = targetPosInches;
    ChillySwerve.initialize();
  }

  // overloaded initialize method
  public void initialize() {
    // System.out.println("ClosedLoopPositionEvent initialized!");
    // startTimeUs = Utility.getFPGATime();   // deprecated
    startTimeUs = RobotController.getFPGATime();

    super.initialize();
  }

  // overloaded trigger method
  public boolean isTriggered() {
    // measure current position error
    double actualPosInches = ChillySwerve.getDistanceInches();
    double errorPosInches = Math.abs(targetPosInches - actualPosInches);
    if (errorPosInches > errorThresholdInches) {
      // outside error range...
      // reset timer
      // startTimeUs = Utility.getFPGATime();    // deprecated
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

    // within error range for enough time
    System.out.println("ClosedLoopPositionEvent triggered!");
    return true;
  }
}
