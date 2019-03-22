package frc.robot.components;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
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

  private static final double INCHES_PER_ENCODER_PULSE = 1 / 12.4285714286;

  private CANSparkMax linearPistonMaster;
  private CANPIDController pidController;
  private CANEncoder encoder;

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
      linearPistonMaster = new CANSparkMax(Ports.CLIMBER_MASTER_ID, MotorType.kBrushless);
      linearPistonMaster.restoreFactoryDefaults();
      pidController = linearPistonMaster.getPIDController();
      encoder = linearPistonMaster.getEncoder();

      pidController.setP(0.00004);
      pidController.setI(0.0);
      pidController.setD(0.0);
      pidController.setIZone(0.0);
      pidController.setFF(0.0);
      pidController.setOutputRange(-1, 1);

      pidController.setSmartMotionMaxVelocity(24 / INCHES_PER_ENCODER_PULSE * 60, 0);
      pidController.setSmartMotionMaxAccel(96 / INCHES_PER_ENCODER_PULSE * 60, 0);
      pidController.setSmartMotionAllowedClosedLoopError(0, 0);
    }
  }

  @Override
  public void sendTelemetry(boolean debug) {
    if (shuffleboardInitialized) {
      // entry.setType(data);
      // if (debug) {}
    } else {
      // entry =
      // Constants.teleopTab
      // .add("Name", 0)
      // .withWidget(BuiltInWidgets.kWidgetType)
      // .withPosition(x, y)
      // .withSize(width, height)
      // .getEntry();
      // if (debug) {}
      shuffleboardInitialized = true;
    }
  }

  @Override
  public void resetEncoders() {
    encoder.setPosition(0);
  }

  @Override
  public void zeroSensors() {}

  public void setControlType(ControlState controlType) {
    controlState = controlType;
  }

  public void setTarget(double target) {
    switch (controlState) {
      case CLOSED_LOOP_POSITION:
        pidController.setReference(target, ControlType.kPosition);
        break;
      case MOTION_MAGIC:
        pidController.setReference(target, ControlType.kSmartMotion);
        break;
      case OPEN_LOOP:
      default:
        pidController.setReference(target, ControlType.kDutyCycle);
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

  public double getCurrentHeightEncoder() {
    // return encoder.getPosition();
    return encoder.getVelocity();
  }

  public double getHeightFromEncoderPosition(double encoderPosition) {
    return encoderPosition * INCHES_PER_ENCODER_PULSE;
  }

  public double getEncoderPositionFromHeight(double height) {
    return height / INCHES_PER_ENCODER_PULSE;
  }
}
