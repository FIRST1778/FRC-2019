package frc.robot.auto.modes;

import frc.lib.pathing.Path;
import frc.lib.pathing.PathSegment;
import frc.robot.Constants;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.actions.AlignWithTargetAction;
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
    runAction(new AlignWithTargetAction());
    runAction(
        new FollowPathAction(
            new Path(
                0.0,
                Constants.SWERVE_MAX_ACCELERATION,
                Constants.SWERVE_MAX_VELOCITY,
                0.0,
                new PathSegment.Line(12.0, 0.0))));
  }
}
