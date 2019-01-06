package frc.lib.util;

/**
 * A drivetrain command consisting of both the turn angle and drive power for a swerve module.
 *
 * @author FRC 1778 Chill Out
 */
public class ModuleSignal {
  private double drivePower;
  private double turnAngle;

  /**
   * Holds the speed and angle signals for the swerve module.
   *
   * @param power the signal sent to the drive power motors
   * @param angle the target angle for the module to rotate to
   */
  public ModuleSignal(double power, double angle) {
    drivePower = power;
    turnAngle = angle;
  }

  /**
   * Returns the drive power of the signal.
   *
   * @return the dive power signal
   */
  public double getDrivePower() {
    return drivePower;
  }

  /**
   * Returns the angle signal.
   *
   * @return the angle signal
   */
  public double getAngle() {
    return turnAngle;
  }
}
