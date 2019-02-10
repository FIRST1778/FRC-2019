package frc.lib.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleUtilTest {

  @Test
  @DisplayName("The limit method should constrain all inputs between the min and max")
  public void testLimit() {
    assertThat(SimpleUtil.limit(2.0, -1.0, 1.0)).isEqualTo(1.0, Offset.offset(0.001));
    assertThat(SimpleUtil.limit(1.0, -1.0, 1.0)).isEqualTo(1.0, Offset.offset(0.001));
    assertThat(SimpleUtil.limit(0.5, -1.0, 1.0)).isEqualTo(0.5, Offset.offset(0.001));
    assertThat(SimpleUtil.limit(0.0, -1.0, 1.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(SimpleUtil.limit(-0.5, -1.0, 1.0)).isEqualTo(-0.5, Offset.offset(0.001));
    assertThat(SimpleUtil.limit(-1.0, -1.0, 1.0)).isEqualTo(-1.0, Offset.offset(0.001));
    assertThat(SimpleUtil.limit(-2.0, -1.0, 1.0)).isEqualTo(-1.0, Offset.offset(0.001));
  }

  @Test
  @DisplayName("The handleDeadband method should zero the input if it is within the deadband")
  public void testHandlingDeadband() {
    assertThat(SimpleUtil.handleDeadband(1.0, 0.1)).isEqualTo(1.0, Offset.offset(0.001));
    assertThat(SimpleUtil.handleDeadband(0.1, 0.1)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(SimpleUtil.handleDeadband(0.05, 0.1)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(SimpleUtil.handleDeadband(0.0, 0.1)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(SimpleUtil.handleDeadband(-0.05, 0.1)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(SimpleUtil.handleDeadband(-0.1, 0.1)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(SimpleUtil.handleDeadband(-1.0, 0.1)).isEqualTo(-1.0, Offset.offset(0.001));
  }

  @Test
  @DisplayName(
      "The meanWithoutLowestOutliers method should average an array, ignoring the lowest outliers")
  public void testMeanWithoutLowestOutliers() {
    double[] a = {3, 2, 1, 4};
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, -1)).isEqualTo(2.0, Offset.offset(0.001));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 0)).isEqualTo(2.5, Offset.offset(0.001));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 1)).isEqualTo(3.0, Offset.offset(0.001));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 2)).isEqualTo(3.5, Offset.offset(0.001));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 3)).isEqualTo(4.0, Offset.offset(0.001));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 4))
        .isEqualTo(Double.NaN, Offset.offset(0.001));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 5)).isEqualTo(-0.0, Offset.offset(0.001));
  }

  @Test
  @DisplayName("getContinuousRange should constrain between zero and the max range")
  public void testGetContinuousInRange() {
    assertThat(SimpleUtil.getContinuousInRange(0, 360.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(SimpleUtil.getContinuousInRange(360.0, 360.0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(SimpleUtil.getContinuousInRange(90, 360.0)).isEqualTo(90.0, Offset.offset(0.001));
    assertThat(SimpleUtil.getContinuousInRange(180.0, 360.0))
        .isEqualTo(180.0, Offset.offset(0.001));
    assertThat(SimpleUtil.getContinuousInRange(270, 360.0)).isEqualTo(-90.0, Offset.offset(0.001));
  }
}
