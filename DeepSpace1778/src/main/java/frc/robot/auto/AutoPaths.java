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
          0.0,
          new PathSegment.Line(24, 0),
          new PathSegment.ArcedTranslation(24, 24, 0),
          new PathSegment.Line(60, 330),
          new PathSegment.Line(54, 330));

  public static final Path START_LEFT_TO_LEFT_ROCKET_NEAR_SIDE =
      START_RIGHT_TO_RIGHT_ROCKET_NEAR_SIDE.getFlipped();

  public static final Path RIGHT_ROCKET_NEAR_SIDE_TO_RIGHT_FEEDER_STATION =
      new Path(
          180,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          330.0,
          new PathSegment.Line(24, 330),
          new PathSegment.Line(96, 180));

  public static final Path LEFT_ROCKET_NEAR_SIDE_TO_LEFT_FEEDER_STATION =
      RIGHT_ROCKET_NEAR_SIDE_TO_RIGHT_FEEDER_STATION.getFlipped();

  public static final Path RIGHT_FEEDER_STATION_TO_RIGHT_ROCKET_NEAR_SIDE =
      new Path(
          0,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          180.0,
          new PathSegment.ArcedTranslation(-24, -12, 180));
  // new PathSegment.Line(96, 330));

  public static final Path LEFT_FEEDER_STATION_TO_LEFT_ROCKET_NEAR_SIDE =
      RIGHT_FEEDER_STATION_TO_RIGHT_ROCKET_NEAR_SIDE.getFlipped();

  public static final Path RIGHT_FEEDER_STATION_TO_RIGHT_ROCKET_FAR_SIDE =
      new Path(
          0,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          180.0,
          new PathSegment.Line(24, 180),
          new PathSegment.ArcedTranslation(96, 24, 210),
          new PathSegment.ArcedTranslation(-24, 24, 210));

  public static final Path LEFT_FEEDER_STATION_TO_LEFT_ROCKET_FAR_SIDE =
      RIGHT_FEEDER_STATION_TO_RIGHT_ROCKET_FAR_SIDE.getFlipped();

  public static final Path RIGHT_FEEDER_STATION_TO_RIGHT_CARGO_BAY_NEAR =
      new Path(
          0,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          180.0,
          new PathSegment.Line(24, 180),
          new PathSegment.Line(24, 90),
          new PathSegment.ArcedTranslation(96, 96, 90));

  public static final Path LEFT_FEEDER_STATION_TO_LEFT_CARGO_BAY_NEAR =
      RIGHT_FEEDER_STATION_TO_RIGHT_CARGO_BAY_NEAR.getFlipped();

  public static final Path RIGHT_FEEDER_STATION_TO_RIGHT_CARGO_BAY_CENTER =
      new Path(
          0,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          180.0,
          new PathSegment.Line(24, 180),
          new PathSegment.Line(60, 90));

  public static final Path LEFT_FEEDER_STATION_TO_LEFT_CARGO_BAY_CENTER =
      RIGHT_FEEDER_STATION_TO_RIGHT_CARGO_BAY_CENTER.getFlipped();

  public static final Path RIGHT_FEEDER_STATION_TO_RIGHT_CARGO_BAY_FAR =
      new Path(
          0,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          180.0,
          new PathSegment.Line(24, 180),
          new PathSegment.Line(72, 90));

  public static final Path LEFT_FEEDER_STATION_TO_LEFT_CARGO_BAY_FAR =
      RIGHT_FEEDER_STATION_TO_RIGHT_CARGO_BAY_FAR.getFlipped();
}
