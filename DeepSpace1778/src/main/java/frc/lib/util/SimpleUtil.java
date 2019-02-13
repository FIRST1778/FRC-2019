package frc.lib.util;

import java.util.Arrays;

/**
 * Contains simple methods to handle basic utility operations.
 *
 * @author FRC 1778 Chill Out
 */
public class SimpleUtil {

  public static double limit(double value, double min, double max) {
    return min(max, max(min, value));
  }

  public static double handleDeadband(double value, double deadband) {
    return (Math.abs(value) > Math.abs(deadband)) ? value : 0.0;
  }

  public static double min(double... values) {
    double lowest = Double.POSITIVE_INFINITY;
    for (double value : values) {
      lowest = lowest >= value ? value : lowest;
    }
    return lowest;
  }

  public static double max(double... values) {
    double highest = Double.NEGATIVE_INFINITY;
    for (double value : values) {
      highest = highest <= value ? value : highest;
    }
    return highest;
  }

  public static double meanWithoutLowestOutliers(double[] values, int numberOfLowestOutliers) {
    Arrays.sort(values);

    double sum = 0;
    for (int i = values.length; i > max(0, numberOfLowestOutliers); i--) {
      sum += values[i - 1];
    }

    return sum / (values.length - numberOfLowestOutliers);
  }

  public static double getContinuousInRange(double input, double maxRange) {
    input %= maxRange;
    if (Math.abs(input) > maxRange / 2) {
      if (input > 0) {
        return input - maxRange;
      } else {
        return input + maxRange;
      }
    }
    return input;
  }

  public static double mod(double a, double n) {
    return a < 0 ? (a % n + n) % n : a % n;
  }

  public static double euclideanModulo(double a, double n) {
    return n < 0 ? euclideanModulo(a, -n) : mod(a, n);
  }

  public static double flooredModulo(double a, double n) {
    return n < 0 ? -flooredModulo(-a, -n) : mod(a, n);
  }
}
