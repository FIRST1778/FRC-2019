package frc.robot.auto.modes;

import frc.robot.AutoModeSelector.StartingPosition;
import frc.robot.Robot;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.AutoPaths;
import frc.robot.auto.actions.FollowPathAction;
import frc.robot.auto.actions.HatchAction;
import frc.robot.auto.actions.RunOnceAction;
import frc.robot.components.SwerveDrive;

/**
 * An auto mode to score two hatch panels on the near side of the rocket.
 *
 * @author FRC 1778 Chill Out
 */
public class DualNearSideRocketMode extends AutoModeBase {

  StartingPosition startingPosition;

  public DualNearSideRocketMode(StartingPosition position) {
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
        runAction(new FollowPathAction(AutoPaths.START_LEFT_TO_LEFT_ROCKET_NEAR_SIDE));
        // runAction(new AlignWithTargetAction(28.77));
        runAction(new HatchAction(false));
        runAction(new FollowPathAction(AutoPaths.LEFT_ROCKET_NEAR_SIDE_TO_LEFT_FEEDER_STATION));
        // runAction(new AlignWithTargetAction(180));
        runAction(new HatchAction(true));
        runAction(new FollowPathAction(AutoPaths.LEFT_FEEDER_STATION_TO_LEFT_ROCKET_NEAR_SIDE));
        // runAction(new AlignWithTargetAction(28.77));
        runAction(new HatchAction(false));
        break;
      default:
      case RIGHT:
        runAction(new FollowPathAction(AutoPaths.START_RIGHT_TO_RIGHT_ROCKET_NEAR_SIDE));
        // runAction(new AlignWithTargetAction(331.23));
        runAction(new HatchAction(false));
        runAction(new FollowPathAction(AutoPaths.RIGHT_ROCKET_NEAR_SIDE_TO_RIGHT_FEEDER_STATION));
        // runAction(new AlignWithTargetAction(180));
        runAction(new HatchAction(true));
        runAction(new FollowPathAction(AutoPaths.RIGHT_FEEDER_STATION_TO_RIGHT_ROCKET_NEAR_SIDE));
        // runAction(new AlignWithTargetAction(331.23));
        runAction(new HatchAction(false));
        break;
    }
  }
}
