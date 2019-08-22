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
import frc.robot.components.Elevator;
import frc.robot.components.Manipulator;
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

  private long lastTelemetryTime;

  private AutoModeSelector autoModeSelector = new AutoModeSelector();
  private AutoModeExecutor autoModeExecutor;

  private SwerveDrive swerve = SwerveDrive.getInstance();
  private Controls controls = Controls.getInstance();
  private Elevator elevator = Elevator.getInstance();
  private Manipulator manipulator = Manipulator.getInstance();

  public static NetworkTable limelightTable =
      NetworkTableInstance.getDefault().getTable("/limelight");

  private NetworkTableEntry totalPdpVoltage;

  boolean shuffleboardInitialized;

  private boolean previousOpenHatchPanel;
  private boolean isHatchPanelOpen = true;

  public Robot() {
    DebugLog.logRobotConstruction();
  }

  @Override
  public void robotInit() {
    try {
      DebugLog.logRobotInit();

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

      limelightTable.getEntry("pipeline").setDouble(0.0);

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

      limelightTable.getEntry("pipeline").setDouble(1.0);

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

      limelightTable.getEntry("pipeline").setDouble(0.0);

      // elevator.setControlType(Elevator.ControlState.MOTION_MAGIC);
      // elevator.setTarget(elevator.getCurrentHeightEncoder());

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
      // teleopPeriodic();

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

      /*elevator.setControlType(Elevator.ControlState.MOTION_MAGIC);
      if (controls.getLiftToCargoHigh()) {
        elevator.setTargetHeight(HeightSetPoints.CARGO_HIGH);
      } else if (controls.getLiftToCargoMedium()) {
        elevator.setTargetHeight(HeightSetPoints.CARGO_MED);
      } else if (controls.getLiftToCargoLow()) {
        elevator.setTargetHeight(HeightSetPoints.CARGO_LOW);
      } else if (controls.getLiftToCargoShipCargo()) {
        elevator.setTargetHeight(HeightSetPoints.CARGO_SHIP_CARGO);
      } else if (controls.getLiftToHatchHigh()) {
        elevator.setTargetHeight(HeightSetPoints.HATCH_HIGH);
      } else if (controls.getLiftToHatchMedium()) {
        elevator.setTargetHeight(HeightSetPoints.HATCH_MED);
      } else if (controls.getLiftToHatchLow()) {
        elevator.setTargetHeight(HeightSetPoints.HATCH_LOW);
      } else if (controls.getLiftToCargoPickup()) {
        elevator.setTargetHeight(HeightSetPoints.CARGO_PICKUP);
      }*/

      elevator.setControlType(Elevator.ControlState.OPEN_LOOP);
      elevator.setTarget(
          controls.getLiftManualControl() < 0
              ? controls.getLiftManualControl() * 0.25
              : controls.getLiftManualControl());

      // manipulator.setManipulatorPosition(controls.getExtendArticulator() ? -90.0 : 0.0);
      manipulator.setManipulatorPower(controls.getManualArticulator() * 0.3);
      // System.out.println(manipulator.getPivotAngle());

      manipulator.setCargoIntake(controls.getCargoIntake());

      if (controls.getOpenHatchPanel() && !previousOpenHatchPanel) {
        isHatchPanelOpen = !isHatchPanelOpen;
      }

      manipulator.openHatchCollector(isHatchPanelOpen);

      previousOpenHatchPanel = controls.getOpenHatchPanel();
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  @Override
  public void testPeriodic() {
    try {
      sendTelemetry();
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
    }
  }

  private void sendTelemetry() {
    long waitTime = (long) (Constants.TELEMETRY_RATE * 1000.0);
    long currentTime = System.currentTimeMillis();

    if ((currentTime - lastTelemetryTime) > waitTime) {
      swerve.sendTelemetry(Constants.DEBUG);
      manipulator.sendTelemetry(Constants.DEBUG);
      elevator.sendTelemetry(Constants.DEBUG);
      if (shuffleboardInitialized) {
        totalPdpVoltage.setDouble(RobotController.getBatteryVoltage());
      } else {
        totalPdpVoltage =
            Constants.debugTab
                .add("Battery Voltage", 0)
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(0, 2)
                .withSize(1, 1)
                .getEntry();
        shuffleboardInitialized = true;
      }
      lastTelemetryTime = currentTime;
    }
  }
}
