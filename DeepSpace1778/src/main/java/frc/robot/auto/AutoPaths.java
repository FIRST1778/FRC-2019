package frc.robot.auto;

import frc.lib.pathing.Path;
import frc.lib.pathing.PathSegment;
import frc.robot.Constants;

public class AutoPaths {

  public static final Path RIGHT_START_FORWARD_TO_RIGHT_SCALE_FRONT =
      new Path(
          -3.9499,
          Constants.SWERVE_MAX_ACCELERATION,
          Constants.SWERVE_MAX_VELOCITY,
          new PathSegment.Line(140),
          new PathSegment.RadialArc(105.14, 40.1621),
          new PathSegment.Line(39.93));
}
