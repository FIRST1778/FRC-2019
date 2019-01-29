package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.lib.driver.TalonSrxFactory;
import frc.robot.Constants;
import frc.robot.Ports;

/**
 * Handles control over the two (2) Talon SRXs, each hooked up to a 775Pro. Feedback is provided by
 * both limit switches and a Grayhill 63R for position control.
 *
 * @author FRC 1778 Chill Out
 */
public class Elevator extends Subsystem {

  private static Elevator instance;

  private static boolean initialized;

  public enum HeightSetPoints {
    ROCKET_CARGO_HIGH(80.0),
    ROCKET_CARGO_MED(60.0),
    ROCKET_HATCH_HIGH(70.0),
    ROCKET_HATCH_MED(50.0),
    ROCKET_HATCH_LOW(30.0),
    CARGO_SHIP_CARGO(50.0),
    CARGO_SHIP_HATCH(30.0),
    FEEDER_STATION(30.0),
    LIFT_LEVEL_NEAR_FLOOR(10.0),
    LIFT_LEVEL_FLOOR(0.0);

    private HeightSetPoints(double heightInches) {
      this.heightInches = heightInches;
    }

    private final double heightInches;

    public double getHeightInches() {
      return heightInches;
    }

    public double getHeightEncoderTicks() {
      return Elevator.getInstance().getEncoderPositionFromHeight(heightInches);
    }
  }

  public enum GamePiece {
    CARGO(0.0),
    HATCH_PANEL(0.0),
    NONE(0.0);

    private GamePiece(double feedForward) {
      this.feedForward = feedForward;
    }

    public final double feedForward;
  }

  public enum ControlState {
    OPEN_LOOP,
    MOTION_MAGIC,
    CLOSED_LOOP_POSITION
  }

  private ControlState controlState = ControlState.OPEN_LOOP;

  private GamePiece gamePieceTransported = GamePiece.NONE;

  private static final double ENCODER_TICKS_PER_INCH = 29.85; // TODO: Measure for robot

  private TalonSRX masterElevator;
  private TalonSRX slaveElevator;

  private static TalonSrxFactory.Configuration masterConfiguration;

  private NetworkTableEntry liftHeightEntry;

  private boolean shuffleboardInitialized;

  public static Elevator getInstance() {
    return getInstance(true);
  }

  public static Elevator getInstance(boolean hardware) {
    if (!initialized) {
      initialized = true;
      instance = new Elevator(hardware);
    }

    return instance;
  }

  private Elevator(boolean hardware) {
    if (hardware) {
      masterConfiguration = new TalonSrxFactory.Configuration();
      masterConfiguration.feedbackDevice = FeedbackDevice.QuadEncoder;
      masterConfiguration.invert = false;
      masterConfiguration.invertSensorPhase = true;
      masterConfiguration.forwardLimitSwitch = LimitSwitchSource.FeedbackConnector;
      masterConfiguration.forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      masterConfiguration.reverseLimitSwitch = LimitSwitchSource.FeedbackConnector;
      masterConfiguration.reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      masterConfiguration.pidKp = 2.0;
      masterConfiguration.pidKi = 0.0;
      masterConfiguration.pidKd = 0.0;
      masterConfiguration.pidKf = 0.0;
      masterConfiguration.motionCruiseVelocity = (int) (ENCODER_TICKS_PER_INCH * 20);
      masterConfiguration.motionAcceleration = (int) (ENCODER_TICKS_PER_INCH * 40);
      masterConfiguration.continuousCurrentLimit = 15;
      masterConfiguration.peakCurrentLimit = 20;
      masterConfiguration.peakCurrentLimitDuration = 100;
      masterConfiguration.enableCurrentLimit = true;

      masterElevator = TalonSrxFactory.createTalon(Ports.ELEVATOR_MASTER_ID, masterConfiguration);
      slaveElevator = TalonSrxFactory.createSlaveTalon(Ports.ELEVATOR_SLAVE_ID, masterElevator);
    }
  }

  @Override
  public void sendTelemetry() {
    if (shuffleboardInitialized) {
      liftHeightEntry.setDouble(
          getHeightFromEncoderPosition(masterElevator.getSelectedSensorPosition()));
    } else {
      liftHeightEntry =
          Constants.autoTab
              .add("Elevator Height (in)", 0)
              .withWidget(BuiltInWidgets.kTextView)
              .withPosition(4, 4)
              .withSize(1, 1)
              .getEntry();

      shuffleboardInitialized = true;
    }
  }

  @Override
  public void resetEncoders() {
    masterElevator.setSelectedSensorPosition(0, 0, masterConfiguration.timeoutInMs);
  }

  @Override
  public void zeroSensors() {}

  public boolean resetEncoderIfLimitSwitchReached() {
    boolean limitSwitchTriggered = masterElevator.getSensorCollection().isRevLimitSwitchClosed();
    if (limitSwitchTriggered) {
      resetEncoders();
    }

    return limitSwitchTriggered;
  }

  public void setBrakeMode(boolean brake) {
    masterElevator.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
    slaveElevator.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
  }

  public void setGamePiece(GamePiece gamePiece) {
    gamePieceTransported = gamePiece;
  }

  public void setControlType(ControlState controlType) {
    controlState = controlType;
  }

  public void setTarget(double target) {
    masterElevator.set(ControlMode.PercentOutput, 0);
    switch (controlState) {
      case CLOSED_LOOP_POSITION:
        masterElevator.set(
            ControlMode.Position,
            target,
            DemandType.ArbitraryFeedForward,
            gamePieceTransported.feedForward);
        break;
      case MOTION_MAGIC:
        masterElevator.set(
            ControlMode.MotionMagic,
            target,
            DemandType.ArbitraryFeedForward,
            gamePieceTransported.feedForward);
        break;
      default: // Intended fall-through
      case OPEN_LOOP:
        masterElevator.set(
            ControlMode.PercentOutput,
            target,
            DemandType.ArbitraryFeedForward,
            gamePieceTransported.feedForward);
        break;
    }
  }

  public void setTargetHeight(double heightInches) {
    if (controlState != ControlState.OPEN_LOOP) {
      setTarget(getEncoderPositionFromHeight(heightInches));
    }
  }

  public void setTargetHeight(HeightSetPoints targetGoal) {
    if (controlState != ControlState.OPEN_LOOP) {
      setTarget(targetGoal.getHeightEncoderTicks());
    }
  }

  public double getHeightFromEncoderPosition(double encoderPosition) {
    return encoderPosition / ENCODER_TICKS_PER_INCH;
  }

  public double getEncoderPositionFromHeight(double height) {
    return height * ENCODER_TICKS_PER_INCH;
  }
}
