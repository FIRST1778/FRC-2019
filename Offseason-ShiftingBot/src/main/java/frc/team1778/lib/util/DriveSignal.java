package frc.team1778.lib.util;

/**
 * A drivetrain command consisting of the left and right motor signals.
 *
 * @author FRC 1778 Chill Out
 */
public class DriveSignal {
  private double leftPower;
  private double rightPower;

  /**
   * Holds the left and right signals for the drivetrain.
   *
   * @param left the signal for the left side of the drivetrain
   * @param right the signal for the right side of the drivetrain
   */
  public DriveSignal(double left, double right) {
    leftPower = left;
    rightPower = right;
  }

  /**
   * Returns the left signal level of the signal.
   *
   * @return the left signal
   */
  public double getLeft() {
    return leftPower;
  }

  /**
   * Returns the right signal level of the signal.
   *
   * @return the right signal
   */
  public double getRight() {
    return rightPower;
  }
}
