package frc.robot.auto.modes;

import frc.lib.pathing.Path;
import frc.lib.pathing.PathSegment;
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
      new Path(0, new PathSegment.RadialArc(48.0, 90.0), new PathSegment.RadialArc(48.0, -90.0));

  public MotionTest(int driverStationPosition) {
    teamSelectedPosition = driverStationPosition;
  }

  @Override
  protected void routine() throws AutoModeEndedException {
    runAction(new FollowPathAction(path));
  }
}
