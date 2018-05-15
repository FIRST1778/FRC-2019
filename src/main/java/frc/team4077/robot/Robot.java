package frc.team4077.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * This is the main hub for all other classes. Each of the overrided methods are synced with FMS and
 * driverstation packets. Periodic methods loop throughverything inside of them and then return to
 * the top. Init methods are run once, and then pass on to their respective periodic method.
 *
 * @author FRC 4077 MASH, Hillel Coates
 */
public class Robot extends IterativeRobot {
  @Override
  public void robotInit() {}

  @Override
  public void disabledInit() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void teleopInit() {}

  @Override
  public void testInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testPeriodic() {}

  /**
   * Puts all subsystem data on the SmartDashboard.
   *
   * @param debug If true, more feedback data will be logged.
   */
  public void updateSmartDashboard(boolean debug) {
    // Add general SmartDashboard values here

    if (debug) {
      // Add specialized SmartDashboard values here
    }
  }
}
