package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
 * both limit switches and a VersaPlanetary Integrated Encoder.
 *
 * @author FRC 1778 Chill Out
 */
public class Elevator extends Subsystem {

  private static Elevator instance;

  private static boolean initialized;

  public enum HeightSetPoints {
    CARGO_HIGH(70, -75.0),
    CARGO_MED(55.5, -75.0),
    CARGO_SHIP_CARGO(39.625, -75.0),
    CARGO_LOW(27.5, -75.0),
    HATCH_HIGH(65.0, 0.0),
    HATCH_MED(47.0, 0.0),
    HATCH_LOW(0.0, 0.0);

    private HeightSetPoints(double heightInches, double manipulatorAngle) {
      this.heightInches = heightInches;
      this.manipulatorAngle = manipulatorAngle;
      heightEncoderTicks = Elevator.getInstance().getEncoderPositionFromHeight(heightInches);
    }

    public final double heightInches;
    public final double manipulatorAngle;
    public final double heightEncoderTicks;
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

  private static final double INCHES_PER_ENCODER_PULSE = 0.00036292747;

  private double wantedHeight;

  private TalonSRX masterElevator;
  private TalonSRX slaveElevator;

  private static TalonSrxFactory.Configuration masterConfiguration;

  private NetworkTableEntry liftHeightEntry;
  private NetworkTableEntry liftSpeedEntry;

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
      masterConfiguration.feedbackDevice = FeedbackDevice.CTRE_MagEncoder_Relative;
      masterConfiguration.invert = false;
      masterConfiguration.invertSensorPhase = true;
      masterConfiguration.forwardLimitSwitch = LimitSwitchSource.Deactivated;
      masterConfiguration.reverseLimitSwitch = LimitSwitchSource.Deactivated;
      // masterConfiguration.reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
      masterConfiguration.pidKp = 1.0;
      masterConfiguration.pidKi = 0.0;
      masterConfiguration.pidKd = 0.0;
      masterConfiguration.pidKf = 0.0;
      masterConfiguration.motionCruiseVelocity = (int) (20.0 / INCHES_PER_ENCODER_PULSE / 10.0);
      masterConfiguration.motionAcceleration = (int) (30.0 / INCHES_PER_ENCODER_PULSE / 10.0);
      masterConfiguration.continuousCurrentLimit = 20;
      masterConfiguration.peakCurrentLimit = 0;
      masterConfiguration.peakCurrentLimitDuration = 10;
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

      liftSpeedEntry.setDouble(
          getHeightFromEncoderPosition(masterElevator.getSelectedSensorVelocity() * 10.0));
    } else {
      liftHeightEntry =
          Constants.teleopTab
              .add("Elevator Height (in)", 0)
              .withWidget(BuiltInWidgets.kTextView)
              .withPosition(0, 0)
              .withSize(1, 1)
              .getEntry();

      liftSpeedEntry =
          Constants.teleopTab
              .add("Elevator Speed", 0)
              .withWidget(BuiltInWidgets.kTextView)
              .withPosition(1, 0)
              .withSize(1, 1)
              .getEntry();

      shuffleboardInitialized = true;
    }
  }

  @Override
  public void resetEncoders() {
    masterElevator.setSelectedSensorPosition(0, 0, 0);
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
        wantedHeight = target;
        masterElevator.set(
            ControlMode.Position,
            target,
            DemandType.ArbitraryFeedForward,
            gamePieceTransported.feedForward);
        break;
      case MOTION_MAGIC:
        wantedHeight = target;
        masterElevator.set(
            ControlMode.MotionMagic,
            target,
            DemandType.ArbitraryFeedForward,
            gamePieceTransported.feedForward);
        break;
      case OPEN_LOOP:
      default:
        masterElevator.set(
            ControlMode.PercentOutput,
            target < 0
                ? target
                : (masterElevator.getSensorCollection().isRevLimitSwitchClosed() ? 0.0 : target),
            DemandType.ArbitraryFeedForward,
            gamePieceTransported.feedForward);
        System.out.println(
            "Elevator Lower Limit: "
                + masterElevator.getSensorCollection().isRevLimitSwitchClosed());
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
      setTarget(targetGoal.heightEncoderTicks);
    }
  }

  public int getCurrentHeightEncoder() {
    return masterElevator.getSelectedSensorPosition();
  }

  public boolean isCloseToTarget(double allowableError) {
    return Math.abs(wantedHeight - getCurrentHeightEncoder()) < allowableError;
  }

  public double getHeightFromEncoderPosition(double encoderPosition) {
    return encoderPosition * INCHES_PER_ENCODER_PULSE;
  }

  public double getEncoderPositionFromHeight(double height) {
    return height / INCHES_PER_ENCODER_PULSE;
  }
}
