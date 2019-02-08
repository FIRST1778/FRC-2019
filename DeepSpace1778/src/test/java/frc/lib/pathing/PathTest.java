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
            new PathSegment.Line(120, 0));
    assertThat(tenFeetForwards.getDuration()).isEqualTo(1.34, Offset.offset(0.01));
  }
}
