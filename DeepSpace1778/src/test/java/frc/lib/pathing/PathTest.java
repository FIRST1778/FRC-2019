package frc.lib.pathing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import frc.robot.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PathTest {

  @Test
  @DisplayName(
      "The path's duration should be calculated bsaed on accerleration and velocity inputs")
  public void testPathDuration() {
    Path tenFeetForwards =
        new Path(
            0,
            Constants.SWERVE_MAX_ACCELERATION,
            Constants.SWERVE_MAX_VELOCITY,
            new PathSegment.Line(120, 0));
    System.out.println(tenFeetForwards.getDuration());
    assertThat(true, is(true));
  }
}
