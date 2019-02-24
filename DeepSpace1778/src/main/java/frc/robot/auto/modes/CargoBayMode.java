package frc.robot.auto.modes;

import frc.robot.AutoModeSelector.StartingPosition;
import frc.robot.Robot;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.AutoPaths;
import frc.robot.auto.actions.FollowPathAction;
import frc.robot.auto.actions.RunOnceAction;
import frc.robot.components.SwerveDrive;

/**
 * An auto mode to score a single hatch panel on the cargo bay.
 *
 * @author FRC 1778 Chill Out
 */
public class CargoBayMode extends AutoModeBase {

  StartingPosition startingPosition;

  public CargoBayMode(StartingPosition position) {
    startingPosition = position;
  }

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
    switch (startingPosition) {
      case LEFT:
        runAction(new FollowPathAction(AutoPaths.START_LEFT_TO_LEFT_CARGO_BAY_NEAR));
        break;
      case CENTER:
        runAction(new FollowPathAction(AutoPaths.START_CENTER_TO_LEFT_FRONT_CARGO_BAY));
        runAction(new FollowPathAction(AutoPaths.LEFT_FRONT_CARGO_BAY_TO_LEFT_DEPOT));
        runAction(new FollowPathAction(AutoPaths.LEFT_DEPOT_TO_ROCKET_CARGO));
        break;
      default:
      case RIGHT:
        runAction(new FollowPathAction(AutoPaths.START_RIGHT_TO_RIGHT_CARGO_BAY_NEAR));
        break;
    }
  }
}
