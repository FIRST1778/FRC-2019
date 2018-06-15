package frc.team1778.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import edu.wpi.first.wpilibj.Solenoid;
import frc.team1778.lib.util.DriveSignal;
import frc.team1778.lib.util.driver.NavX;
import frc.team1778.lib.util.driver.SimpleTalonSRX;
import frc.team1778.lib.util.driver.TalonSRXFactory;
import frc.team1778.robot.Ports;

/**
 * This is the robot's drivetrain. This class handles the four TalonSRX motor controllers attached
 * to the ganged left and right motors, as well as the solenoids to shift between gears.
 *
 * <p>The drivetrain consists of four (4) TalonSRX motor controllers four (4) CIM motors, and one
 * (1) solenoid to trigger the two (2) pistons.
 *
 * @author FRC 1778 Chill Out
 */
public class Drive extends Subsystem {
  private static Drive instance = new Drive();

  private final SimpleTalonSRX leftMaster, rightMaster, leftSlave, rightSlave;

  private final Solenoid shifter;

  private final NavX navX;

  public enum SystemState {
    OPEN_LOOP_PERCENTAGE,
    OPEN_LOOP_CURRENT,
    CLOSED_LOOP_VELOCITY
  };

  public static final long SHIFT_DEBOUNCE_TIME = 250;

  private static TalonSRXFactory.Configuration driveConfiguration =
      new TalonSRXFactory.Configuration();

  static {
    // Drive Config
    driveConfiguration.FEEDBACK_DEVICE = FeedbackDevice.QuadEncoder;
    driveConfiguration.STATUS_FRAME_PERIOD = 10;
    driveConfiguration.STATUS_FRAME = StatusFrameEnhanced.Status_13_Base_PIDF0;
    driveConfiguration.PID_KP = 15;
    driveConfiguration.PID_KI = 0.01;
    driveConfiguration.PID_KD = 0.1;
    driveConfiguration.PID_KF = 0.2;
    driveConfiguration.MOTION_CRUISE_VELOCITY = 640;
    driveConfiguration.MOTION_ACCELERATION = 200;
    driveConfiguration.NEUTRAL_POWER_MODE = NeutralMode.Brake;
    driveConfiguration.CONTINUOUS_CURRENT_LIMIT = 25;
    driveConfiguration.PEAK_CURRENT_LIMIT = 25;
    driveConfiguration.PEAK_CURRENT_LIMIT_DURATION = 100;
    driveConfiguration.ENABLE_CURRENT_LIMIT = true;
  }

  private SystemState currentState;
  private boolean isInHighGear;
  private long lastShiftTime;

  /**
   * Returns a static instance of Drive, to be used instead of instantiating new objects of Drive.
   *
   * @return An instance of Drive
   */
  public static Drive getinstance() {
    return instance;
  }

  private Drive() {
    leftMaster = TalonSRXFactory.createTalon(Ports.LEFT_DRIVE_MASTER_ID, driveConfiguration);
    rightMaster = TalonSRXFactory.createTalon(Ports.RIGHT_DRIVE_MASTER_ID, driveConfiguration);
    leftSlave = TalonSRXFactory.createSlaveTalon(Ports.LEFT_DRIVE_SLAVE_ID, leftMaster);
    rightSlave = TalonSRXFactory.createSlaveTalon(Ports.RIGHT_DRIVE_SLAVE_ID, rightMaster);

    shifter = new Solenoid(Ports.DRIVE_SHIFTER);

    navX = new NavX(Ports.NAVX_SPI);

    leftMaster.setInverted(false);
    rightMaster.setInverted(true);
    leftSlave.setInverted(false);
    rightSlave.setInverted(true);

    currentState = SystemState.OPEN_LOOP_PERCENTAGE;
    isInHighGear = false;
    lastShiftTime = System.currentTimeMillis();
  }

  /**
   * Returns the drivebase's NavX IMU. Use this instead of reinstantiating the NavX, which will
   * result in no response from the sensor.
   *
   * @return The drivebase's NavX
   */
  public NavX getNavX() {
    return navX;
  }

  @Override
  public void sendTelemetry() {}

  @Override
  public void resetEncoders() {
    leftMaster.setSelectedSensorPosition(0, 0, driveConfiguration.TIMEOUT_IN_MS);
    rightMaster.setSelectedSensorPosition(0, 0, driveConfiguration.TIMEOUT_IN_MS);
  }

  @Override
  public void zeroSensors() {
    navX.zeroYaw();
  }

  /**
   * Returns the current state of the shifter.
   *
   * @return The state of the shifting solenoid.
   */
  public boolean isHighGear() {
    return isInHighGear;
  }

  /**
   * Sets the shifter position.
   *
   * @param setToHighGear The wanted state of the gear shifter.
   */
  public void setHighGear(boolean setToHighGear) {
    if (setToHighGear != isInHighGear) {
      isInHighGear = setToHighGear;
      shifter.set(!setToHighGear);
    }
  }

  /**
   * Sets the shifter position.
   *
   * @param setToHighGear The wanted state of the gear shifter.
   * @param checkDebounce If true, it first checks if the shifter has been triggered directly
   *     before.
   */
  public void setHighGear(boolean setToHighGear, boolean checkDebounce) {
    if (checkDebounce
        ? (System.currentTimeMillis() - lastShiftTime >= SHIFT_DEBOUNCE_TIME)
        : true) {
      setHighGear(setToHighGear);
    }
  }

  /**
   * Sets the drive control mode/state to operate the TalonSRX's with.
   *
   * @param newState The state to set the system to use.
   */
  public void setDriveState(SystemState newState) {
    currentState = newState;
  }

  /**
   * Sets the values to be sent to the motors, which can change depending on the state of the
   * system.
   *
   * @param signals The signals to send each of the motors.
   */
  public void setPowers(DriveSignal signals) {
    setPowers(signals.getLeft(), signals.getRight());
  }

  /**
   * Sets the values to be sent to the motors, which can change depending on the state of the
   * system.
   *
   * @param left The left signal to send to the left motor.
   * @param right The right signal to send to the right motor.
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
