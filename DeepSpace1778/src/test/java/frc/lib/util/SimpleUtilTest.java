package frc.lib.util;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class SimpleUtilTest {

  @Test
  public void testLimit() {
    Assert.assertThat(
        "Value should be capped when value is greater than limit",
        SimpleUtil.limit(2.0, 1.0),
        Is.is(1.0));
    Assert.assertThat(
        "Value should be capped when value is equal to limit",
        SimpleUtil.limit(1.0, 1.0),
        Is.is(1.0));
    Assert.assertThat(
        "Value should not be capped when value is less than limit",
        SimpleUtil.limit(0.5, 1.0),
        Is.is(0.5));
    Assert.assertThat(
        "Value should not be capped when value is zero", SimpleUtil.limit(0.0, 1.0), Is.is(0.0));
    Assert.assertThat(
        "Value should not be capped when absolute value is less than limit",
        SimpleUtil.limit(-0.5, 1.0),
        Is.is(-0.5));
    Assert.assertThat(
        "Value should be capped when absolute value is equal to limit",
        SimpleUtil.limit(-1.0, 1.0),
        Is.is(-1.0));
    Assert.assertThat(
        "Value should be capped when absolute value is greater than limit",
        SimpleUtil.limit(-2.0, 1.0),
        Is.is(-1.0));
  }

  @Test
  public void testHandlingDeadband() {
    Assert.assertThat(
        "Deadband should not be applied when value is greater than deadband",
        SimpleUtil.handleDeadband(1.0, 0.1),
        Is.is(1.0));
    Assert.assertThat(
        "Deadband should be applied when value is equal to deadband",
        SimpleUtil.handleDeadband(0.1, 0.1),
        Is.is(0.0));
    Assert.assertThat(
        "Deadband should be applied when value is less than deadband",
        SimpleUtil.handleDeadband(0.05, 0.1),
        Is.is(0.0));
    Assert.assertThat(
        "Deadband should be applied when value is zero",
        SimpleUtil.handleDeadband(0.0, 0.1),
        Is.is(0.0));
    Assert.assertThat(
        "Deadband should be applied when absolute value is less than deadband",
        SimpleUtil.handleDeadband(-0.05, 0.1),
        Is.is(0.0));
    Assert.assertThat(
        "Deadband should be applied when absolute value is equal to deadband",
        SimpleUtil.handleDeadband(-0.1, 0.1),
        Is.is(0.0));
    Assert.assertThat(
        "Deadband should not be applied when absolute value is greater than deadband",
        SimpleUtil.handleDeadband(-1.0, 0.1),
        Is.is(-1.0));
  }
}
