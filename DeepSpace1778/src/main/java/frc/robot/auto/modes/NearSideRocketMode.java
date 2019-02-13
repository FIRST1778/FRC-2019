package frc.robot.auto.modes;

import frc.robot.AutoModeSelector.StartingPosition;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.AutoPaths;
import frc.robot.auto.actions.AlignWithTargetAction;
import frc.robot.auto.actions.FollowPathAction;
import frc.robot.auto.actions.RunOnceAction;
import frc.robot.components.SwerveDrive;

/**
 * An auto mode to score two hatch panels on the near side of the rocket.
 *
 * @author FRC 1778 Chill Out
 */
public class NearSideRocketMode extends AutoModeBase {

  StartingPosition startingPosition;

  public NearSideRocketMode(StartingPosition position) {
    startingPosition = position;
  }

  @Override
  public void routine() throws AutoModeEndedException {
    runAction(
        new RunOnceAction() {
          @Override
          public void runOnce() {
            SwerveDrive.getInstance().zeroSensors();
          }
        });
    switch (startingPosition) {
      case LEFT:
        runAction(new FollowPathAction(AutoPaths.START_LEFT_TO_LEFT_ROCKET_NEAR_SIDE));
        runAction(new AlignWithTargetAction(30.0));
        break;
      default:
      case RIGHT:
        runAction(new FollowPathAction(AutoPaths.START_RIGHT_TO_RIGHT_ROCKET_NEAR_SIDE));
        runAction(new AlignWithTargetAction(330.0));
        break;
    }
  }
}
