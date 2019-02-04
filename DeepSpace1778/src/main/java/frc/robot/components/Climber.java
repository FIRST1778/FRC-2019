package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.lib.driver.TalonSrxFactory;
import frc.robot.Ports;

/**
 * Handles control over one (1) Talon SRX connected to a 775Pro motor as a piston, and one (1) Talon
 * SRX connected to a BAG motor as a powered roller. The piston has encoder feedback and limit
 * switches at the min and max positions.
 *
 * @author FRC 1778 Chill Out
 */
public class Climber extends Subsystem {

  private static Climber instance;

  private static boolean initialized;

  private static final double INCHES_PER_ENCODER_PULSE = 1.0 / 1024.0; // TODO: Measure for robot

  private TalonSRX linearPiston;
  private TalonSRX poweredRoller;

  private static TalonSrxFactory.Configuration pistonConfiguration;
  private static TalonSrxFactory.Configuration rollerConfiguration;

  private boolean shuffleboardInitialized;

  public static Climber getInstance() {
    return getInstance(true);
  }

  public static Climber getInstance(boolean hardware) {
    if (!initialized) {
      initialized = true;
      instance = new Climber(hardware);
    }

    return instance;
  }

  private Climber(boolean hardware) {
    if (hardware) {
      pistonConfiguration = new TalonSrxFactory.Configuration();
      pistonConfiguration.feedbackDevice = FeedbackDevice.QuadEncoder;
      pistonConfiguration.invertSensorPhase = false;
      pistonConfiguration.forwardLimitSwitch = LimitSwitchSource.FeedbackConnector;
      pistonConfiguration.forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      pistonConfiguration.reverseLimitSwitch = LimitSwitchSource.FeedbackConnector;
      pistonConfiguration.reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      pistonConfiguration.pidKp = 0.0;
      pistonConfiguration.pidKi = 0.0;
      pistonConfiguration.pidKd = 0.0;
      pistonConfiguration.pidKf = 0.0;
      pistonConfiguration.continuousCurrentLimit = 20;
      pistonConfiguration.peakCurrentLimit = 15;
      pistonConfiguration.peakCurrentLimitDuration = 100;
      pistonConfiguration.enableCurrentLimit = true;

      rollerConfiguration = new TalonSrxFactory.Configuration();
      rollerConfiguration.continuousCurrentLimit = 20;
      rollerConfiguration.peakCurrentLimit = 25;
      rollerConfiguration.peakCurrentLimitDuration = 100;
      rollerConfiguration.enableCurrentLimit = true;

      linearPiston = TalonSrxFactory.createTalon(Ports.CLIMBER_PISTON_ID, pistonConfiguration);
      poweredRoller = TalonSrxFactory.createTalon(Ports.CLIMBER_ROLLER_ID, rollerConfiguration);
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
  public void resetEncoders() {
    linearPiston.setSelectedSensorPosition(0, 0, 0);
  }

  @Override
  public void zeroSensors() {}

  public void setExtendedHeight(double heightInches) {
    linearPiston.set(ControlMode.Position, heightInches);
  }

  public void setRollerPower(double percentage) {
    poweredRoller.set(ControlMode.PercentOutput, percentage);
  }

  public double getHeightFromEncoderPosition(int encoderPosition) {
    return encoderPosition * INCHES_PER_ENCODER_PULSE;
  }

  public double getEncoderPositionFromHeight(double height) {
    return height / INCHES_PER_ENCODER_PULSE;
  }
}
