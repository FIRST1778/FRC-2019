package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.lib.util.DebugLog;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeExecutor;
import frc.robot.components.SwerveDrive;
import java.util.Optional;

/**
 * The main hub for all other classes. Each of the overrided methods are synced with FMS and
 * driverstation packets. Periodic methods loop throughverything inside of them and then return to
 * the top. Init methods are run once, and then pass on to their respective periodic method.
 *
 * @author FRC 1778 Chill Out
 */
public class Robot extends TimedRobot {

  private AutoModeSelector autoModeSelector = new AutoModeSelector();
  private AutoModeExecutor autoModeExecutor;

  private SwerveDrive swerve = SwerveDrive.getInstance();
  private Controls controls = Controls.getInstance();
  // private Elevator elevator = Elevator.getInstance();
  // private Manipulator manipulator = Manipulator.getInstance();

  public static NetworkTable limelightTable =
      NetworkTableInstance.getDefault().getTable("/limelight");

  private NetworkTableEntry totalPdpVoltage;

  public Robot() {
    DebugLog.logRobotConstruction();
  }

  @Override
  public void robotInit() {
    try {
      DebugLog.logRobotInit();
      limelightTable.getEntry("camMode").setDouble(1.0);
      limelightTable.getEntry("ledMode").setDouble(1.0);

      autoModeSelector.updateModeCreator();
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  @Override
  public void disabledInit() {
    try {
      DebugLog.logDisabledInit();
      Shuffleboard.selectTab("Autonomous");
      // elevator.setControlType(ControlState.OPEN_LOOP);

      if (autoModeExecutor != null) {
        autoModeExecutor.stop();
      }

      autoModeSelector.reset();
      autoModeSelector.updateModeCreator();
      autoModeExecutor = new AutoModeExecutor();
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  @Override
  public void autonomousInit() {
    try {
      DebugLog.logAutoInit();
      Shuffleboard.selectTab("Autonomous");
      // elevator.setControlType(ControlState.MOTION_MAGIC);

      autoModeExecutor.start();
      DebugLog.logNote(
          String.format(
              "Auto mode %s selected and running.",
              autoModeExecutor.getAutoMode().getClass().getSimpleName()));
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  @Override
  public void teleopInit() {
    try {
      DebugLog.logTeleopInit();
      Shuffleboard.selectTab("TeleOp");
      swerve.zeroSensors();
      // elevator.setControlType(ControlState.MOTION_MAGIC);

      if (autoModeExecutor != null) {
        autoModeExecutor.stop();
      }
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  @Override
  public void testInit() {
    try {
      Shuffleboard.selectTab("Debug");
      DebugLog.logTestInit();
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  @Override
  public void disabledPeriodic() {
    try {
      sendTelemetry();

      autoModeSelector.updateModeCreator();

      Optional<AutoModeBase> autoMode = autoModeSelector.getAutoMode();
      if (autoMode.isPresent() && autoMode.get() != autoModeExecutor.getAutoMode()) {
        autoModeExecutor.setAutoMode(autoMode.get());
      }
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  @Override
  public void autonomousPeriodic() {
    try {
      sendTelemetry();

      // elevator.resetEncoderIfLimitSwitchReached();
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  @Override
  public void teleopPeriodic() {
    try {
      sendTelemetry();

      if (controls.getResetFieldCentric()) {
        swerve.getNavX().zeroYaw();
        swerve.resetEncoders();
      }

      if (controls.getTranslationX() != 0
          || controls.getTranslationY() != 0
          || controls.getRotation() != 0) {

        if (controls.getFieldCentricToggle()) {
          double angle = Math.toRadians(swerve.getNavX().getAngle());

          double forward = controls.getTranslationY();
          double strafe = controls.getTranslationX();

          double temp = (forward * Math.cos(angle)) + (strafe * Math.sin(angle));
          strafe = (-forward * Math.sin(angle)) + (strafe * Math.cos(angle));
          forward = temp;

          swerve.setSignals(swerve.calculateModuleSignals(forward, strafe, controls.getRotation()));
        } else {
          swerve.setSignals(
              swerve.calculateModuleSignals(
                  controls.getTranslationY(), controls.getTranslationX(), controls.getRotation()));
        }
      } else {
        swerve.stop();
      }

      /*if (controls.getLiftToHome()) {
        elevator.setTargetHeight(HeightSetPoints.LIFT_LEVEL_FLOOR);
      } else if (controls.getLiftToRocketCargoHigh()) {
        elevator.setTargetHeight(HeightSetPoints.ROCKET_CARGO_HIGH);
      } else if (controls.getLiftToRocketCargoMedium()) {
        elevator.setTargetHeight(HeightSetPoints.ROCKET_CARGO_MED);
      } else if (controls.getLiftToFeederStation()) {
        elevator.setTargetHeight(HeightSetPoints.FEEDER_STATION);
      } else if (controls.getLiftToCargoShipCargo()) {
        elevator.setTargetHeight(HeightSetPoints.CARGO_SHIP_CARGO);
      }

      manipulator.setCargoIntake(controls.getCargoIntake());

      elevator.resetEncoderIfLimitSwitchReached();
      */
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  @Override
  public void testPeriodic() {
    try {
      sendTelemetry();

      // elevator.setControlType(ControlState.MOTION_MAGIC);
      // elevator.setTargetHeight(10);

      // swerve.setAllTurnPowers(1);
      swerve.setAllToAngle(0);

      // elevator.resetEncoderIfLimitSwitchReached();
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  boolean shuffleboardInitialized;

  private void sendTelemetry() {
    swerve.sendTelemetry();
    // elevator.sendTelemetry();
    if (shuffleboardInitialized) {
      totalPdpVoltage.setDouble(RobotController.getBatteryVoltage());
    } else {
      totalPdpVoltage =
          Constants.debugTab
              .add("Battery Voltage", 0)
              .withWidget(BuiltInWidgets.kGraph)
              .withPosition(0, 2)
              .withSize(4, 2)
              .getEntry();
      shuffleboardInitialized = true;
    }
  }
}
