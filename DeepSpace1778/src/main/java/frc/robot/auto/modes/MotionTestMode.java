package frc.robot.auto.modes;

import frc.robot.Robot;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.actions.LiftToHeightAction;
import frc.robot.auto.actions.RunOnceAction;
import frc.robot.components.Elevator.HeightSetPoints;
import frc.robot.components.SwerveDrive;

/**
 * A simple auto mode to test that auto selection works.
 *
 * @author FRC 1778 Chill Out
 */
public class MotionTestMode extends AutoModeBase {

  @Override
  public void routine() throws AutoModeEndedException {
    runAction(
        new RunOnceAction() {
          @Override
          public void runOnce() {
            SwerveDrive.getInstance().zeroSensors();
            Robot.limelightTable.getEntry("camMode").setDouble(1.0);
            Robot.limelightTable.getEntry("ledMode").setDouble(1.0);
          }
        });
    runAction(new LiftToHeightAction(HeightSetPoints.CARGO_MED));
    runAction(new LiftToHeightAction(HeightSetPoints.CARGO_PICKUP));
  }
}
