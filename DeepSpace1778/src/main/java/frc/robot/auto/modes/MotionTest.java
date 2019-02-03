package frc.robot.auto.modes;

import frc.lib.pathing.Path;
import frc.lib.pathing.PathSegment;
import frc.robot.Constants;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.actions.FollowPathAction;
import frc.robot.auto.actions.RunOnceAction;
import frc.robot.components.SwerveDrive;

/**
 * A simple auto mode to test that auto selection works.
 *
 * @author FRC 1778 Chill Out
 */
public class MotionTest extends AutoModeBase {

  private int teamSelectedPosition;

  private Path pathToRocketNearSide =
      new Path(
          -90,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          new PathSegment.Line(24, 0),
          new PathSegment.ArcedTranslation(24, 24, 0),
          new PathSegment.Line(60, 330),
          new PathSegment.Line(54, 330));

  private Path pathToFeederStation =
      new Path(
          180,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          new PathSegment.Line(24, 330),
          new PathSegment.Line(96, 180));
  private Path feederStationToRocketNearSide =
      new Path(
          0,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          new PathSegment.Line(24, 180));

  public MotionTest(int driverStationPosition) {
    teamSelectedPosition = driverStationPosition;
  }

  @Override
  protected void routine() throws AutoModeEndedException {
    runAction(
        new RunOnceAction() {
          @Override
          public void runOnce() {
            SwerveDrive.getInstance().zeroSensors();
          }
        });
    runAction(new FollowPathAction(pathToRocketNearSide));
    runAction(new FollowPathAction(pathToFeederStation));
    runAction(new FollowPathAction(feederStationToRocketNearSide));
  }
}
