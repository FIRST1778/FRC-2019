package frc.lib.util;

/**
 * A swerve module command consisting of both the turn angle and drive power for a swerve module.
 *
 * @author FRC 1778 Chill Out
 */
public class ModuleSignal {

  private double drivePower;
  private double turnAngle;

  public ModuleSignal(double power, double angle) {
    drivePower = power;
    turnAngle = angle;
  }

  public double getDrivePower() {
    return drivePower;
  }

  public double getAngle() {
    return turnAngle;
  }
}
