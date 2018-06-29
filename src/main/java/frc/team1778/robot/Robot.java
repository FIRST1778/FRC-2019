package frc.team1778.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team1778.robot.autonomous.AutoPaths;
import frc.team1778.robot.common.FreezyDrive;
import frc.team1778.robot.components.Drive;
import jaci.pathfinder.Trajectory;

/**
 * This is the main hub for all other classes. Each of the overrided methods are synced with FMS and
 * driverstation packets. Periodic methods loop throughverything inside of them and then return to
 * the top. Init methods are run once, and then pass on to their respective periodic method.
 *
 * @author FRC 1778 Chill Out
 */
public class Robot extends IterativeRobot {
  private Drive drive = Drive.getinstance();
  private FreezyDrive freezyDriver = new FreezyDrive();
  private Controls controlInterpreter = Controls.getInstance();

  @Override
  public void robotInit() {
    Trajectory path = drive.generatePath(AutoPaths.testPath);
    drive.setupFollowersForTrajectory(path, false);
    drive.sendTelemetry();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void autonomousInit() {
    drive.prepareForPath();
  }

  @Override
  public void teleopInit() {}

  @Override
  public void testInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousPeriodic() {
    drive.followPath();

    drive.sendTelemetry();
  }

  @Override
  public void teleopPeriodic() {
    drive.setGear(
        controlInterpreter.getHighGearShift()
            ? true
            : (controlInterpreter.getLowGearShift() ? false : drive.isHighGear()));

    drive.setPowers(
        freezyDriver.freezyDrive(
            controlInterpreter.getThrottle(),
            controlInterpreter.getWheelX(),
            controlInterpreter.getWheelY(),
            controlInterpreter.getQuickTurn(),
            drive.isHighGear()));

    drive.sendTelemetry();
  }

  @Override
  public void testPeriodic() {}
}
