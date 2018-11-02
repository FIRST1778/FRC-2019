package frc.team1778.lib.util;

/**
 * Contains simple methods to handle basic utility operations.
 *
 * @author FRC 1778 Chill Out
 */
public class SimpleUtil {
  /**
   * Limits the input value to the specified max value in either direction.
   *
   * @param value the input value to limit
   * @param max the max magnitude to limit the input value to
   */
  public static double limit(double value, double max) {
    return limit(value, -max, max);
  }

  /**
   * Limits the input value to the specified min and max values.
   *
   * @param value the input value to limit
   * @param min the minimum to limit the input value to
   * @param max the maximum to limit the input value to
   */
  public static double limit(double value, double min, double max) {
    return Math.min(max, Math.max(min, value));
  }

  /**
   * Handles the deadband on the input value. If the value is within the deadband, it will be cut to
   * zero.
   *
   * @param value the input value to handle
   * @param deadband the amount of deadband that is applied to the input value
   */
  public static double handleDeadband(double value, double deadband) {
    return (Math.abs(value) > Math.abs(deadband)) ? value : 0.0;
  }
}
