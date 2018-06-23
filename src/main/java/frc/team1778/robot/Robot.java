package frc.team1778.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team1778.robot.common.FreezyDrive;
import frc.team1778.robot.components.Drive;

/**
 * This is the main hub for all other classes. Each of the overrided methods are synced with FMS and
 * driverstation packets. Periodic methods loop throughverything inside of them and then return to
 * the top. Init methods are run once, and then pass on to their respective periodic method.
 *
 * @author FRC 1778 Chill Out
 */
public class Robot extends IterativeRobot {
  private Drive drive = Drive.getinstance();
  private FreezyDrive freezyDriver;
  private Controls controlInterpreter = Controls.getInstance();

  @Override
  public void robotInit() {
    drive.zeroSensors();
  }

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
  public void teleopPeriodic() {
    if (controlInterpreter.getHighGearShift()) {
      drive.setHighGear(true);
    } else if (controlInterpreter.getLowGearShift()) {
      drive.setHighGear(false);
    }
    freezyDriver.freezyDrive(
        controlInterpreter.getThrottle(),
        controlInterpreter.getWheelX(),
        controlInterpreter.getWheelY(),
        controlInterpreter.getQuickTurn(),
        drive.isHighGear());
  }

  @Override
  public void testPeriodic() {}
}
