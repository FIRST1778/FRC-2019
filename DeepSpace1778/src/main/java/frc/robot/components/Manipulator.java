package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.lib.driver.TalonSrxFactory;
import frc.robot.Constants;
import frc.robot.Ports;

/**
 * Handles control over a Talon SRX connected to a BAG motor as a pivot for the manipulator, a Talon
 * SRX connected to a 775Pro as a vacuum pump, and a Talon SRX connected to a BAG motor to collect
 * the cargo. The pivot motor is connected with an absolute encoder for angular feedback.
 *
 * @author FRC 1778 Chill Out
 */
public class Manipulator extends Subsystem {

  private static Manipulator instance;

  private static boolean initialized;

  private static final double ZERO_ANGLE_OFFSET = 0.0; // TODO: Measure for robot

  private TalonSRX manipulatorPivot;
  private TalonSRX cargoCollector;
  private TalonSRX hatchPanelCollector;

  private static TalonSrxFactory.Configuration pivotConfiguration;
  private static TalonSrxFactory.Configuration cargoConfiguration;
  private static TalonSrxFactory.Configuration hatchPanelConfiguration;

  private boolean shuffleboardInitialized;

  private NetworkTableEntry manipulatorAngleEntry;
  private NetworkTableEntry hasHatchPanelEntry;
  private NetworkTableEntry hasCargoEntry;

  public static Manipulator getInstance() {
    return getInstance(true);
  }

  public static Manipulator getInstance(boolean hardware) {
    if (!initialized) {
      initialized = true;
      instance = new Manipulator(hardware);
    }

    return instance;
  }

  private Manipulator(boolean hardware) {
    if (hardware) {
      pivotConfiguration = new TalonSrxFactory.Configuration();
      pivotConfiguration.feedbackDevice = FeedbackDevice.Analog;
      pivotConfiguration.invert = false;
      pivotConfiguration.invertSensorPhase = true;
      pivotConfiguration.pidKp = 5.0;
      pivotConfiguration.pidKi = 0.0001;
      pivotConfiguration.pidKd = 0.0;
      pivotConfiguration.motionCruiseVelocity = (int) (180.0 / 10.0);
      pivotConfiguration.motionAcceleration = (int) (720.0 / 10.0);
      pivotConfiguration.continuousCurrentLimit = 20;
      pivotConfiguration.peakCurrentLimit = 15;
      pivotConfiguration.peakCurrentLimitDuration = 10;
      pivotConfiguration.enableCurrentLimit = true;

      cargoConfiguration = new TalonSrxFactory.Configuration();
      cargoConfiguration.forwardLimitSwitch = LimitSwitchSource.Deactivated;
      cargoConfiguration.reverseLimitSwitch = LimitSwitchSource.Deactivated;
      cargoConfiguration.invert = false;
      cargoConfiguration.continuousCurrentLimit = 20;
      cargoConfiguration.peakCurrentLimit = 25;
      cargoConfiguration.peakCurrentLimitDuration = 10;
      cargoConfiguration.enableCurrentLimit = true;

      hatchPanelConfiguration = new TalonSrxFactory.Configuration();
      hatchPanelConfiguration.forwardLimitSwitch = LimitSwitchSource.FeedbackConnector;
      hatchPanelConfiguration.forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      hatchPanelConfiguration.reverseLimitSwitch = LimitSwitchSource.FeedbackConnector;
      hatchPanelConfiguration.reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      hatchPanelConfiguration.invert = true;
      hatchPanelConfiguration.continuousCurrentLimit = 6;
      hatchPanelConfiguration.peakCurrentLimit = 0;
      hatchPanelConfiguration.peakCurrentLimitDuration = 10;
      hatchPanelConfiguration.enableCurrentLimit = true;

      manipulatorPivot = TalonSrxFactory.createTalon(Ports.ARTICULATOR_ID, pivotConfiguration);
      cargoCollector = TalonSrxFactory.createTalon(Ports.CARGO_COLLECTOR_ID, cargoConfiguration);
      hatchPanelCollector =
          TalonSrxFactory.createTalon(Ports.HATCH_PANEL_PICKUP_ID, hatchPanelConfiguration);
    }
  }

  @Override
  public void sendTelemetry(boolean debug) {
    if (shuffleboardInitialized) {
      hasCargoEntry.setBoolean(hasCargo());
      hasHatchPanelEntry.setBoolean(hasHatchPanel());

      if (debug) {
        manipulatorAngleEntry.setDouble(getPivotAngle());
      }
    } else {
      hasCargoEntry =
          Constants.teleopTab
              .add("Cargo", false)
              .withWidget(BuiltInWidgets.kBooleanBox)
              .withPosition(0, 0)
              .withSize(1, 1)
              .getEntry();
      hasHatchPanelEntry =
          Constants.teleopTab
              .add("Hatch Panel", false)
              .withWidget(BuiltInWidgets.kBooleanBox)
              .withPosition(0, 1)
              .withSize(1, 1)
              .getEntry();

      if (debug) {
        manipulatorAngleEntry =
            Constants.debugTab
                .add("Manipulator Angle", 0)
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(0, 2)
                .withSize(1, 1)
                .getEntry();
      }
      shuffleboardInitialized = true;
    }
  }

  @Override
  public void resetEncoders() {}

  @Override
  public void zeroSensors() {}

  public void setCargoIntake(double percentage) {
    cargoCollector.set(
        ControlMode.PercentOutput,
        percentage > 0.0
            ? percentage
            : (hasCargo() ? -0.1 : (percentage < 0.0 ? percentage : -0.2)));
  }

  public void openHatchCollector(boolean open) {
    hatchPanelCollector.set(ControlMode.PercentOutput, open ? 1.0 : -1.0);
  }

  public boolean hasHatchPanel() {
    return hatchPanelCollector.getSensorCollection().isFwdLimitSwitchClosed();
  }

  public boolean hasReleasedHatchPanel() {
    return hatchPanelCollector.getSensorCollection().isRevLimitSwitchClosed();
  }

  public boolean hasCargo() {
    return !cargoCollector.getSensorCollection().isRevLimitSwitchClosed();
  }

  public void setManipulatorPosition(double angle) {
    angle %= 360;
    angle += ZERO_ANGLE_OFFSET;
    angle *= 1024.0 / 360.0;

    manipulatorPivot.set(ControlMode.MotionMagic, angle);
  }

  public void setManipulatorPower(double power) {
    manipulatorPivot.set(ControlMode.PercentOutput, power);
  }

  public double getPivotAngle() {
    return ((double) manipulatorPivot.getSelectedSensorPosition(0) * (360.0 / 1024.0))
        - ZERO_ANGLE_OFFSET;
  }
}
