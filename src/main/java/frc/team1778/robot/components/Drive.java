package frc.team1778.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import frc.team1778.lib.DriveSignal;
import frc.team1778.lib.driver.TalonSRXFactory;
import frc.team1778.robot.Ports;
import frc.team1778.robot.common.communication.NetworkTableWrapper;

/**
 * This is the robot's drivetrain. This class handles the four {@link TalonSRX} motor controllers
 * attached to the ganged left and right motors, as well as a {@link Solenoid} to shift between
 * gears.
 *
 * <p>The drivetrain consists of four (4) TalonSRX motor controllers four (4) CIM motors, and one
 * (1) solenoid to trigger the two (2) pistons.
 *
 * @author FRC 1778 Chill Out
 */
public class Drive extends Subsystem {
  private static Drive instance = new Drive();

  private Compressor compressor = new Compressor(Ports.PCM_ID);

  private TalonSRX leftMaster, rightMaster, leftSlave, rightSlave;

  private DoubleSolenoid leftShifter;
  private DoubleSolenoid rightShifter;

  private NetworkTableWrapper networkTable = new NetworkTableWrapper("Drive");

  // private NavX navX;

  public enum SystemState {
    OPEN_LOOP_PERCENTAGE,
    OPEN_LOOP_CURRENT,
    CLOSED_LOOP_VELOCITY
  };

  public static final long SHIFT_DEBOUNCE_TIME = 250;

  private static TalonSRXFactory.Configuration driveConfiguration =
      new TalonSRXFactory.Configuration();

  // Drive Config
  static {
    driveConfiguration.FEEDBACK_DEVICE = FeedbackDevice.QuadEncoder;
    driveConfiguration.STATUS_FRAME = StatusFrameEnhanced.Status_13_Base_PIDF0;
    driveConfiguration.PID_KP = 15;
    driveConfiguration.PID_KI = 0.01;
    driveConfiguration.PID_KD = 0.1;
    driveConfiguration.PID_KF = 0.2;
    driveConfiguration.MOTION_CRUISE_VELOCITY = 640;
    driveConfiguration.MOTION_ACCELERATION = 200;
    driveConfiguration.CONTINUOUS_CURRENT_LIMIT = 25;
    driveConfiguration.PEAK_CURRENT_LIMIT = 25;
    driveConfiguration.PEAK_CURRENT_LIMIT_DURATION = 100;
    driveConfiguration.ENABLE_CURRENT_LIMIT = true;
  }

  private SystemState currentState;
  private boolean isInHighGear;

  /**
   * Returns a static instance of Drive, to be used instead of instantiating new objects of Drive.
   *
   * @return an instance of Drive to avoid multiple objects of the same hardware devices
   */
  public static Drive getinstance() {
    return instance;
  }

  private Drive() {
    leftMaster = TalonSRXFactory.createDefaultTalon(Ports.LEFT_DRIVE_MASTER_ID);
    rightMaster = TalonSRXFactory.createDefaultTalon(Ports.RIGHT_DRIVE_MASTER_ID);
    // TalonSRXFactory.configureTalon(leftMaster, driveConfiguration);
    // TalonSRXFactory.configureTalon(rightMaster, driveConfiguration);

    leftSlave = TalonSRXFactory.createSlaveTalon(Ports.LEFT_DRIVE_SLAVE_ID, leftMaster);
    rightSlave = TalonSRXFactory.createSlaveTalon(Ports.RIGHT_DRIVE_SLAVE_ID, rightMaster);

    leftShifter =
        new DoubleSolenoid(Ports.PCM_ID, Ports.LEFT_SHIFTER_FORWARD, Ports.LEFT_SHIFTER_REVERSE);
    rightShifter =
        new DoubleSolenoid(Ports.PCM_ID, Ports.RIGHT_SHIFTER_FORWARD, Ports.RIGHT_SHIFTER_REVERSE);

    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

    leftMaster.setInverted(true);
    rightMaster.setInverted(false);
    leftSlave.setInverted(true);
    rightSlave.setInverted(false);
    leftMaster.setSensorPhase(false);
    rightMaster.setSensorPhase(true);

    currentState = SystemState.OPEN_LOOP_PERCENTAGE;
    setLowGear();
  }

  @Override
  public void sendTelemetry() {
    networkTable.putBoolean("In High Gear", isInHighGear);
    networkTable.putNumber("Left Encoder", getLeftEncoderPosition());
    networkTable.putNumber("Right Encoder", getRightEncoderPosition());
  }

  @Override
  public void resetEncoders() {
    leftMaster.setSelectedSensorPosition(0, 0, driveConfiguration.TIMEOUT_IN_MS);
    rightMaster.setSelectedSensorPosition(0, 0, driveConfiguration.TIMEOUT_IN_MS);
  }

  @Override
  public void zeroSensors() {}

  /**
   * Returns the drivebase's NavX IMU. Use this instead of reinstantiating the NavX, which will
   * result in no response from the sensor.
   *
   * @return the drivebase's NavX IMU
   */
  /*public NavX getNavX() {
    return navX;
  }*/

  /**
   * Returns the current encoder position of the left motor.
   *
   * @return the current encoder position of the left motor
   */
  public long getLeftEncoderPosition() {
    return leftMaster.getSelectedSensorPosition(driveConfiguration.PROFILE_SLOT_ID);
  }

  /**
   * Returns the current encoder position of the right motor.
   *
   * @return the current encoder position of the right motor
   */
  public long getRightEncoderPosition() {
    return rightMaster.getSelectedSensorPosition(driveConfiguration.PROFILE_SLOT_ID);
  }

  /**
   * Returns the current state of the shifter.
   *
   * @return the state of the gear shifting solenoid
   */
  public boolean isHighGear() {
    return isInHighGear;
  }

  /**
   * Shifts to high gear by activating the solenoids.\
   *
   * @param setHighGear shifts to highGear if true, low gear if false
   */
  public void setGear(boolean setHighGear) {
    if (setHighGear) {
      setHighGear();
    } else {
      setLowGear();
    }
  }

  /** Shifts to high gear by activating the solenoids. */
  public void setHighGear() {
    leftShifter.set(Value.kForward);
    rightShifter.set(Value.kForward);
    isInHighGear = false;
  }

  /** Shifts to low gear by deactivating the solenoids. */
  public void setLowGear() {
    leftShifter.set(Value.kReverse);
    rightShifter.set(Value.kReverse);
    isInHighGear = true;
  }

  /**
   * Sets the drive control mode/state to operate the TalonSRX's with.
   *
   * @param newState the wanted state to set the system to use
   */
  public void setDriveState(SystemState newState) {
    currentState = newState;
  }

  /**
   * Sets the values to be sent to the motors, which can change depending on the state of the
   * system.
   *
   * @param signals the signals to send both sides of the drivetrain
   */
  public void setPowers(DriveSignal signals) {
    setPowers(signals.getLeft(), signals.getRight());
  }

  /**
   * Sets the values to be sent to the motors, which can change depending on the state of the
   * system.
   *
   * @param left the left signal to send to the left side of the drivetrain
   * @param right the right signal to send to the right side of the drivetrain
   */
  public void setPowers(double left, double right) {
    switch (currentState) {
      case OPEN_LOOP_PERCENTAGE:
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
        break;
      case OPEN_LOOP_CURRENT:
        leftMaster.set(ControlMode.Current, left);
        rightMaster.set(ControlMode.Current, right);
        break;
      case CLOSED_LOOP_VELOCITY:
        leftMaster.set(ControlMode.Velocity, left);
        rightMaster.set(ControlMode.Velocity, right);
        break;
      default:
        break;
    }
  }
}
