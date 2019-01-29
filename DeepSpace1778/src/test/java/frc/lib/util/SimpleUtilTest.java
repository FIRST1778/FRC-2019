package frc.lib.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class SimpleUtilTest {

  @Test
  public void testLimit() {
    assertThat(
        "Value should be capped when value is greater than limit",
        SimpleUtil.limit(2.0, 1.0),
        is(1.0));
    assertThat(
        "Value should be capped when value is equal to limit", SimpleUtil.limit(1.0, 1.0), is(1.0));
    assertThat(
        "Value should not be capped when value is less than limit",
        SimpleUtil.limit(0.5, 1.0),
        is(0.5));
    assertThat(
        "Value should not be capped when value is zero", SimpleUtil.limit(0.0, 1.0), is(0.0));
    assertThat(
        "Value should not be capped when absolute value is less than limit",
        SimpleUtil.limit(-0.5, 1.0),
        is(-0.5));
    assertThat(
        "Value should be capped when absolute value is equal to limit",
        SimpleUtil.limit(-1.0, 1.0),
        is(-1.0));
    assertThat(
        "Value should be capped when absolute value is greater than limit",
        SimpleUtil.limit(-2.0, 1.0),
        is(-1.0));
  }

  @Test
  public void testHandlingDeadband() {
    assertThat(
        "Deadband should not be applied when value is greater than deadband",
        SimpleUtil.handleDeadband(1.0, 0.1),
        is(1.0));
    assertThat(
        "Deadband should be applied when value is equal to deadband",
        SimpleUtil.handleDeadband(0.1, 0.1),
        is(0.0));
    assertThat(
        "Deadband should be applied when value is less than deadband",
        SimpleUtil.handleDeadband(0.05, 0.1),
        is(0.0));
    assertThat(
        "Deadband should be applied when value is zero",
        SimpleUtil.handleDeadband(0.0, 0.1),
        is(0.0));
    assertThat(
        "Deadband should be applied when absolute value is less than deadband",
        SimpleUtil.handleDeadband(-0.05, 0.1),
        is(0.0));
    assertThat(
        "Deadband should be applied when absolute value is equal to deadband",
        SimpleUtil.handleDeadband(-0.1, 0.1),
        is(0.0));
    assertThat(
        "Deadband should not be applied when absolute value is greater than deadband",
        SimpleUtil.handleDeadband(-1.0, 0.1),
        is(-1.0));
  }
}
