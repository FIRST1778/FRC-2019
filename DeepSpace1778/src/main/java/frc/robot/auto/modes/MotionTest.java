package frc.robot.auto.modes;

import frc.lib.pathing.Path;
import frc.lib.pathing.PathSegment;
import frc.robot.Constants;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.actions.FollowPathAction;

/**
 * A simple auto mode to test that auto selection works.
 *
 * @author FRC 1778 Chill Out
 */
public class MotionTest extends AutoModeBase {

  private int teamSelectedPosition;

  private Path path =
      new Path(
          0,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          new PathSegment.ArcedTranslation(48.0, 36.0),
          new PathSegment.Line(48.0));

  public MotionTest(int driverStationPosition) {
    teamSelectedPosition = driverStationPosition;
  }

  @Override
  protected void routine() throws AutoModeEndedException {
    runAction(new FollowPathAction(path, 180));
  }
}
