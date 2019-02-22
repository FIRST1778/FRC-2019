package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.lib.driver.TalonSrxFactory;
import frc.lib.driver.VictorSpxFactory;
import frc.robot.Constants;
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

  public enum ControlState {
    OPEN_LOOP,
    MOTION_MAGIC,
    CLOSED_LOOP_POSITION
  }

  private ControlState controlState = ControlState.OPEN_LOOP;

  private static final double INCHES_PER_ENCODER_PULSE = 1.0 / 1024.0; // TODO: Measure for robot

  private TalonSRX linearPistonMaster;
  private VictorSPX linearPistonSlave;

  private static TalonSrxFactory.Configuration masterConfiguration;

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
      masterConfiguration = new TalonSrxFactory.Configuration();
      masterConfiguration.feedbackDevice = FeedbackDevice.CTRE_MagEncoder_Relative;
      masterConfiguration.invertSensorPhase = false;
      masterConfiguration.forwardLimitSwitch = LimitSwitchSource.FeedbackConnector;
      masterConfiguration.forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      masterConfiguration.reverseLimitSwitch = LimitSwitchSource.FeedbackConnector;
      masterConfiguration.reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      masterConfiguration.pidKp = 0.0;
      masterConfiguration.pidKi = 0.0;
      masterConfiguration.pidKd = 0.0;
      masterConfiguration.pidKf = 0.0;
      masterConfiguration.continuousCurrentLimit = 20;
      masterConfiguration.peakCurrentLimit = 15;
      masterConfiguration.peakCurrentLimitDuration = 10;
      masterConfiguration.enableCurrentLimit = true;

      linearPistonMaster =
          TalonSrxFactory.createTalon(Ports.CLIMBER_MASTER_ID, masterConfiguration);
      linearPistonSlave =
          VictorSpxFactory.createSlaveVictor(Ports.CLIMBER_SLAVE_ID, linearPistonMaster);
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
    linearPistonMaster.setSelectedSensorPosition(0, 0, 0);
  }

  @Override
  public void zeroSensors() {}

  public void setControlType(ControlState controlType) {
    controlState = controlType;
  }

  public void setTarget(double target) {
    linearPistonMaster.set(ControlMode.PercentOutput, 0);
    switch (controlState) {
      case CLOSED_LOOP_POSITION:
        linearPistonMaster.set(ControlMode.Position, target);
        break;
      case MOTION_MAGIC:
        linearPistonMaster.set(ControlMode.MotionMagic, target);
        break;
      case OPEN_LOOP:
      default:
        linearPistonMaster.set(ControlMode.PercentOutput, target);
        break;
    }
  }

  public void setTargetHeight(double heightInches) {
    if (controlState != ControlState.OPEN_LOOP) {
      setTarget(getEncoderPositionFromHeight(heightInches));
    }
  }

  public void setTargetHeight(boolean extended) {
    if (controlState != ControlState.OPEN_LOOP) {
      setTarget(extended ? getEncoderPositionFromHeight(Constants.EXTENDED_LIMIT) : 0.0);
    }
  }

  public double getHeightFromEncoderPosition(double encoderPosition) {
    return encoderPosition * INCHES_PER_ENCODER_PULSE;
  }

  public double getEncoderPositionFromHeight(double height) {
    return height / INCHES_PER_ENCODER_PULSE;
  }
}
