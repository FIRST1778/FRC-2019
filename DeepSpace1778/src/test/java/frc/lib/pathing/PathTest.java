package frc.lib.pathing;

import static org.assertj.core.api.Assertions.assertThat;

import frc.robot.Constants;
import frc.robot.auto.AutoPaths;
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
    Path path = AutoPaths.START_RIGHT_TO_RIGHT_ROCKET_NEAR_SIDE;

    Path flippedPath = AutoPaths.START_LEFT_TO_LEFT_ROCKET_NEAR_SIDE;

    double curveLength = 37.69911184307752;

    assertThat(path.getAngleAtDistance(0.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(6.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(12.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(18.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(24.0)).isEqualTo(0.0, Offset.offset(0.001));

    assertThat(path.getAngleAtDistance((24.0 + curveLength) * 0.25))
        .isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance((24.0 + curveLength) * 0.5))
        .isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance((24.0 + curveLength) * 0.75))
        .isEqualTo(0.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(24.0 + curveLength)).isEqualTo(0.0, Offset.offset(0.001));

    assertThat(path.getAngleAtDistance(39.0 + curveLength)).isEqualTo(352.5, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(54.0 + curveLength)).isEqualTo(345.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(69.0 + curveLength)).isEqualTo(337.5, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(84.0 + curveLength)).isEqualTo(330.0, Offset.offset(0.001));

    assertThat(path.getAngleAtDistance(97.5 + curveLength)).isEqualTo(330.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(111.0 + curveLength)).isEqualTo(330.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(124.5 + curveLength)).isEqualTo(330.0, Offset.offset(0.001));
    assertThat(path.getAngleAtDistance(138.0 + curveLength)).isEqualTo(330.0, Offset.offset(0.001));

    assertThat(flippedPath.getAngleAtDistance(0.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(6.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(12.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(18.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(24.0)).isEqualTo(0.0, Offset.offset(0.001));

    assertThat(flippedPath.getAngleAtDistance((24.0 + curveLength) * 0.25))
        .isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance((24.0 + curveLength) * 0.5))
        .isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance((24.0 + curveLength) * 0.75))
        .isEqualTo(0.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(24.0 + curveLength))
        .isEqualTo(0.0, Offset.offset(0.001));

    assertThat(flippedPath.getAngleAtDistance(39.0 + curveLength))
        .isEqualTo(7.5, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(54.0 + curveLength))
        .isEqualTo(15.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(69.0 + curveLength))
        .isEqualTo(22.5, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(84.0 + curveLength))
        .isEqualTo(30.0, Offset.offset(0.001));

    assertThat(flippedPath.getAngleAtDistance(97.5 + curveLength))
        .isEqualTo(30.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(111.0 + curveLength))
        .isEqualTo(30.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(124.5 + curveLength))
        .isEqualTo(30.0, Offset.offset(0.001));
    assertThat(flippedPath.getAngleAtDistance(138.0 + curveLength))
        .isEqualTo(30.0, Offset.offset(0.001));
  }
}
