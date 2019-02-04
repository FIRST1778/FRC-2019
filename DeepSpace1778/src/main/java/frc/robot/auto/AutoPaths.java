package frc.robot.auto;

import frc.lib.pathing.Path;
import frc.lib.pathing.PathSegment;
import frc.robot.Constants;

/**
 * Stores reusable paths for use during the autonomous period. For example, a path that starts on
 * the left side of the HAB platform and goes to the near side of the left rocket can be used in
 * multiple auto modes, and such should be in this class.
 *
 * @author FRC 1778 Chill Out
 */
public class AutoPaths {
  public static final Path START_RIGHT_TO_RIGHT_ROCKET_NEAR_SIDE =
      new Path(
          -90,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          new PathSegment.Line(24, 0),
          new PathSegment.ArcedTranslation(24, 24, 0),
          new PathSegment.Line(60, 330),
          new PathSegment.Line(54, 330));

  public static final Path RIGHT_ROCKET_NEAR_SIDE_TO_RIGHT_FEEDER_STATION =
      new Path(
          180,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          new PathSegment.Line(24, 330),
          new PathSegment.Line(96, 180));

  public static final Path RIGHT_FEEDER_STATION_TO_RIGHT_ROCKET_NEAR_SIDE =
      new Path(
          0,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          new PathSegment.Line(24, 180),
          new PathSegment.Line(96, 330));
}
