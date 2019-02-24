package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.lib.driver.TalonSrxFactory;
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

  private static final double ZERO_ANGLE_OFFSET = 6.67; // TODO: Measure for robot

  private TalonSRX manipulatorPivot;
  private TalonSRX cargoCollector;
  private TalonSRX hatchPanelCollector;

  private static TalonSrxFactory.Configuration pivotConfiguration;
  private static TalonSrxFactory.Configuration cargoConfiguration;
  private static TalonSrxFactory.Configuration hatchPanelConfiguration;

  private boolean shuffleboardInitialized;

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
      pivotConfiguration.pidKp = 3.0;
      pivotConfiguration.pidKi = 0.0;
      pivotConfiguration.pidKd = 0.0;
      pivotConfiguration.motionCruiseVelocity = (int) (3.0 / 10.0);
      pivotConfiguration.motionAcceleration = (int) (5.0 / 10.0);
      pivotConfiguration.continuousCurrentLimit = 20;
      pivotConfiguration.peakCurrentLimit = 15;
      pivotConfiguration.peakCurrentLimitDuration = 10;
      pivotConfiguration.enableCurrentLimit = true;

      cargoConfiguration = new TalonSrxFactory.Configuration();
      cargoConfiguration.forwardLimitSwitch = LimitSwitchSource.FeedbackConnector;
      cargoConfiguration.forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      cargoConfiguration.reverseLimitSwitch = LimitSwitchSource.Deactivated;
      cargoConfiguration.invert = true;
      cargoConfiguration.continuousCurrentLimit = 20;
      cargoConfiguration.peakCurrentLimit = 25;
      cargoConfiguration.peakCurrentLimitDuration = 10;
      cargoConfiguration.enableCurrentLimit = true;

      hatchPanelConfiguration = new TalonSrxFactory.Configuration();
      hatchPanelConfiguration.forwardLimitSwitch = LimitSwitchSource.FeedbackConnector;
      hatchPanelConfiguration.forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      hatchPanelConfiguration.reverseLimitSwitch = LimitSwitchSource.Deactivated;
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
  public void sendTelemetry() {
    if (shuffleboardInitialized) {
      // entry.setType(data);
    } else {
      // entry =
      // Constants.teleopTab
      // .add("Name", 0)
      // .withWidget(BuiltInWidgets.kWidgetType)
      // .withPosition(x, y)
      // .withSize(width, height)
      // .getEntry();
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
        percentage <= 0.0
            ? percentage
            : (!cargoCollector.getSensorCollection().isRevLimitSwitchClosed() ? percentage : 0.0));
  }

  public boolean openHatchCollector(boolean open) {
    boolean isOpened = hatchPanelCollector.getSensorCollection().isFwdLimitSwitchClosed();
    hatchPanelCollector.set(
        ControlMode.PercentOutput,
        open
            ? (!isOpened ? 1.0 : 0.0)
            : (!hatchPanelCollector.getSensorCollection().isRevLimitSwitchClosed() ? -1.0 : 0.0));

    return isOpened;
  }

  public void setManipulatorPosition(double angle) {
    angle %= 360;
    angle += ZERO_ANGLE_OFFSET;
    angle *= 1024.0 / 360.0;

    manipulatorPivot.set(ControlMode.MotionMagic, angle);
  }

  public double getPivotAngle() {
    return ((double) manipulatorPivot.getSelectedSensorPosition(0) * (360.0 / 1024.0))
        - ZERO_ANGLE_OFFSET;
  }
}
