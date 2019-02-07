package frc.robot.auto.modes;

import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.AutoPaths;
import frc.robot.auto.actions.FollowPathAction;
import frc.robot.auto.actions.RunOnceAction;
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
          }
        });
    runAction(new FollowPathAction(AutoPaths.START_RIGHT_TO_RIGHT_ROCKET_NEAR_SIDE));
    runAction(new FollowPathAction(AutoPaths.RIGHT_ROCKET_NEAR_SIDE_TO_RIGHT_FEEDER_STATION));
    runAction(new FollowPathAction(AutoPaths.RIGHT_FEEDER_STATION_TO_RIGHT_ROCKET_NEAR_SIDE));
  }
}
