package frc.lib.pathing;

import static org.assertj.core.api.Assertions.assertThat;

import frc.robot.Constants;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class PathTest {

  @Test
  @Tag("robot-dependent")
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
            0,
            0,
            0.0,
            new PathSegment.Line(60, 0),
            new PathSegment.Line(60, 0),
            new PathSegment.Line(60, 330),
            new PathSegment.Line(60, 330));

    Path flippedPath = path.getFlipped();

    assertThat(path.getAngle(0.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngle(0.125)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngle(0.25)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngle(0.375)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngle(0.5)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngle(0.625)).isEqualTo(345.0, Offset.offset(0.001));
    assertThat(path.getAngle(0.75)).isEqualTo(330.0, Offset.offset(0.001));
    assertThat(path.getAngle(0.875)).isEqualTo(330.0, Offset.offset(0.001));
    assertThat(path.getAngle(1.0)).isEqualTo(330.0, Offset.offset(0.001));

    assertThat(flippedPath.getAngle(0.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngle(0.125)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngle(0.25)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngle(0.375)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngle(0.5)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngle(0.625)).isEqualTo(15.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngle(0.75)).isEqualTo(30.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngle(0.875)).isEqualTo(30.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngle(1.0)).isEqualTo(30.0, Offset.offset(0.001));
  }
}
