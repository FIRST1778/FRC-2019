package frc.lib.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleUtilTest {

  @Test
  @DisplayName("The limit method should constrain all inputs between the min and max")
  public void testLimit() {
    assertThat(SimpleUtil.limit(2.0, -1.0, 1.0), is(1.0));
    assertThat(SimpleUtil.limit(1.0, -1.0, 1.0), is(1.0));
    assertThat(SimpleUtil.limit(0.5, -1.0, 1.0), is(0.5));
    assertThat(SimpleUtil.limit(0.0, -1.0, 1.0), is(0.0));
    assertThat(SimpleUtil.limit(-0.5, -1.0, 1.0), is(-0.5));
    assertThat(SimpleUtil.limit(-1.0, -1.0, 1.0), is(-1.0));
    assertThat(SimpleUtil.limit(-2.0, -1.0, 1.0), is(-1.0));
  }

  @Test
  @DisplayName("The handleDeadband method should zero the input if it is within the deadband")
  public void testHandlingDeadband() {
    assertThat(SimpleUtil.handleDeadband(1.0, 0.1), is(1.0));
    assertThat(SimpleUtil.handleDeadband(0.1, 0.1), is(0.0));
    assertThat(SimpleUtil.handleDeadband(0.05, 0.1), is(0.0));
    assertThat(SimpleUtil.handleDeadband(0.0, 0.1), is(0.0));
    assertThat(SimpleUtil.handleDeadband(-0.05, 0.1), is(0.0));
    assertThat(SimpleUtil.handleDeadband(-0.1, 0.1), is(0.0));
    assertThat(SimpleUtil.handleDeadband(-1.0, 0.1), is(-1.0));
  }

  @Test
  @DisplayName(
      "The meanWithoutLowestOutliers method should average an array, ignoring the lowest outliers")
  public void testMeanWithoutLowestOutliers() {
    double[] a = {3, 2, 1, 4};
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, -1), is(2.0));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 0), is(2.5));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 1), is(3.0));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 2), is(3.5));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 3), is(4.0));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 4), is(Double.NaN));
    assertThat(SimpleUtil.meanWithoutLowestOutliers(a, 5), is(-0.0));
  }
}
