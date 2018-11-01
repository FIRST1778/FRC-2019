package frc.team1778.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team1778.lib.util.CrashTracker;
import frc.team1778.robot.auto.AutoFieldState;
import frc.team1778.robot.auto.AutoModeExecutor;
import frc.team1778.robot.auto.creators.TestAutoModeCreator;

/**
 * This is the main hub for all other classes. Each of the overrided methods are synced with FMS and
 * driverstation packets. Periodic methods loop throughverything inside of them and then return to
 * the top. Init methods are run once, and then pass on to their respective periodic method.
 *
 * @author FRC 1778 Chill Out
 */
public class Robot extends IterativeRobot {
  // private Drive drive = Drive.getinstance();
  // private FreezyDrive freezyDriver = new FreezyDrive();
  // private Controls controlInterpreter = Controls.getInstance();

  private AutoFieldState autoFieldState = AutoFieldState.getInstance();

  private AutoModeExecutor autoModeExecutor = null;

  // EncoderFollower[] testPathFollowers;

  public Robot() {
    CrashTracker.logRobotConstruction();
  }

  @Override
  public void robotInit() {
    CrashTracker.logRobotInit();
    // testPathFollowers = drive.generatePath(AutoPaths.testPath, false);
    autoFieldState.setSides(DriverStation.getInstance().getGameSpecificMessage());
    // drive.sendTelemetry();
  }

  @Override
  public void disabledInit() {
    CrashTracker.logDisabledInit();
  }

  @Override
  public void autonomousInit() {
    try {
      CrashTracker.logAutoInit();
      if (autoModeExecutor != null) {
        autoModeExecutor.stop();
      }

      autoModeExecutor = new AutoModeExecutor();
      autoModeExecutor.setAutoMode(
          new TestAutoModeCreator().getStateDependentAutoMode(autoFieldState));
      autoModeExecutor.start();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
    // drive.prepareForPath(testPathFollowers);
  }

  @Override
  public void teleopInit() {
    CrashTracker.logTeleopInit();
  }

  @Override
  public void testInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousPeriodic() {
    // drive.followPath(testPathFollowers);

    // drive.sendTelemetry();
  }

  @Override
  public void teleopPeriodic() {
    /*drive.setGear(
        controlInterpreter.getHighGearShift()
            ? true
            : (controlInterpreter.getLowGearShift() ? false : drive.isHighGear()));

    drive.setPowers(
        freezyDriver.freezyDrive(
            controlInterpreter.getThrottle(),
            controlInterpreter.getWheelX(),
            controlInterpreter.getQuickTurn(),
            drive.isHighGear()));

    drive.sendTelemetry();*/
  }

  @Override
  public void testPeriodic() {}
}
