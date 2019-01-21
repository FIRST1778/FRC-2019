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

  private static Manipulator instance = new Manipulator();

  private static final double ENCODER_TICKS_PER_INCH = 1024.0; // TODO: Measure for robot
  private static final double ZERO_ANGLE_OFFSET = 0.0; // TODO: Measure for robot

  private TalonSRX manipulatorPivot;
  private TalonSRX cargoCollector;
  private TalonSRX vacuum;

  private static TalonSrxFactory.Configuration pivotConfiguration;
  private static TalonSrxFactory.Configuration intakeConfiguration;

  private boolean shuffleboardInitialized;

  public static Manipulator getInstance() {
    return instance;
  }

  private Manipulator() {
    pivotConfiguration = new TalonSrxFactory.Configuration();
    pivotConfiguration.feedbackDevice = FeedbackDevice.Analog;
    pivotConfiguration.invertSensorPhase = false;
    pivotConfiguration.forwardLimitSwitch = LimitSwitchSource.FeedbackConnector;
    pivotConfiguration.forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
    pivotConfiguration.reverseLimitSwitch = LimitSwitchSource.FeedbackConnector;
    pivotConfiguration.reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
    pivotConfiguration.pidKp = 0.0;
    pivotConfiguration.pidKi = 0.0;
    pivotConfiguration.pidKd = 0.0;
    pivotConfiguration.pidKf = 0.0;
    pivotConfiguration.continuousCurrentLimit = 20;
    pivotConfiguration.peakCurrentLimit = 15;
    pivotConfiguration.peakCurrentLimitDuration = 100;
    pivotConfiguration.enableCurrentLimit = true;

    intakeConfiguration = new TalonSrxFactory.Configuration();
    intakeConfiguration.continuousCurrentLimit = 20;
    intakeConfiguration.peakCurrentLimit = 25;
    intakeConfiguration.peakCurrentLimitDuration = 100;
    intakeConfiguration.enableCurrentLimit = true;

    manipulatorPivot = TalonSrxFactory.createTalon(Ports.MANIPULATOR_PIVOT_ID, pivotConfiguration);
    cargoCollector = TalonSrxFactory.createTalon(Ports.MANIPULATOR_PIVOT_ID, intakeConfiguration);
    vacuum = TalonSrxFactory.createTalon(Ports.MANIPULATOR_PIVOT_ID, intakeConfiguration);
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
    cargoCollector.set(ControlMode.PercentOutput, percentage);
  }

  public void setVacuumPower(double percentage) {
    vacuum.set(ControlMode.PercentOutput, percentage);
  }

  public void setManipulatorPosition(double angle) {
    double target = angle % 360.0;
    target += ZERO_ANGLE_OFFSET;
    target *= 1024.0 / 360.0;

    manipulatorPivot.set(ControlMode.Position, target);
  }

  public double getPivotAngle() {
    return (double) manipulatorPivot.getSelectedSensorPosition(0) * (360.0 / 1024.0)
        - ZERO_ANGLE_OFFSET;
  }
}
