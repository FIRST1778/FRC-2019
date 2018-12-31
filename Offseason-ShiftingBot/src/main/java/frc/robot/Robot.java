package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import frc.lib.util.CrashTracker;
import frc.robot.auto.AutoFieldState;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.AutoModeExecutor;
import frc.robot.auto.creators.TestAutoModeCreator;

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
    try {
      CrashTracker.logRobotInit();
      // testPathFollowers = drive.generatePath(AutoPaths.testPath, false);
      autoFieldState.setSides(DriverStation.getInstance().getGameSpecificMessage());
      // drive.sendTelemetry();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  @Override
  public void disabledInit() {
    try {
      CrashTracker.logDisabledInit();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
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
  }

  @Override
  public void teleopInit() {
    try {
      CrashTracker.logTeleopInit();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  @Override
  public void testInit() {
    try {
      CrashTracker.logTestInit();
      throw new AutoModeEndedException();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  @Override
  public void disabledPeriodic() {
    try {
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  @Override
  public void autonomousPeriodic() {
    try {
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  @Override
  public void teleopPeriodic() {
    try {
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  @Override
  public void testPeriodic() {
    try {
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }
}
