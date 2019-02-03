package frc.lib.util;

/**
 * Contains simple methods to handle basic utility operations.
 *
 * @author FRC 1778 Chill Out
 */
public class SimpleUtil {

  public static double limit(double value, double max) {
    return limit(value, -max, max);
  }

  public static double limit(double value, double min, double max) {
    return Math.min(max, Math.max(min, value));
  }

  public static double handleDeadband(double value, double deadband) {
    return (Math.abs(value) > Math.abs(deadband)) ? value : 0.0;
  }

  public static double min(double... values) {
    double lowest = Double.POSITIVE_INFINITY;
    for (double value : values) {
      lowest = lowest > value ? value : lowest;
    }
    return lowest;
  }
}
