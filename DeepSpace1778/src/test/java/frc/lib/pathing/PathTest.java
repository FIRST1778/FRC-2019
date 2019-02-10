package frc.lib.pathing;

import static org.assertj.core.api.Assertions.assertThat;

import frc.robot.Constants;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PathTest {

  @Test
  @DisplayName("The path's duration should be calculated based on acceleration and velocity inputs")
  public void testPathDuration() {
    Path tenFeetForwards =
        new Path(
            0,
            Constants.SWERVE_MAX_ACCELERATION,
            Constants.SWERVE_MAX_VELOCITY,
            0.0,
            new PathSegment.Line(120, 0));
    assertThat(tenFeetForwards.getDuration()).isEqualTo(1.348, Offset.offset(0.001));
  }

  @Test
  @DisplayName("getAngle should scale evenly along each segment of the path")
  public void getAngleShouldScaleEvenly() {
    Path path =
        new Path(
            0,
            Constants.SWERVE_MAX_ACCELERATION,
            Constants.SWERVE_MAX_VELOCITY,
            45.0,
            new PathSegment.Line(60, 45),
            new PathSegment.RadialArc(60, 48, 270));

    Path flippedPath = path.getFlipped();

    assertThat(path.getAngleAtDistance(0.0)).isEqualTo(45.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(30.0)).isEqualTo(45.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(60.0)).isEqualTo(45.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(90.0)).isEqualTo(337.5, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(120.0)).isEqualTo(270.0, Offset.offset(0.001));

    assertThat(flippedPath.getAngleAtDistance(0.0)).isEqualTo(315.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(30.0)).isEqualTo(315.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(60.0)).isEqualTo(315.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(90.0)).isEqualTo(22.5, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(120.0)).isEqualTo(90.0, Offset.offset(0.001));
  }
}
