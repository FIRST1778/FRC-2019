package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.lib.driver.NavX;
import frc.lib.driver.TalonSrxFactory;
import frc.lib.util.DriveSignal;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.common.SimplePid;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * This is the robot's drivetrain. This class handles the four TalonSRX motor controllers attached
 * to the ganged left and right motors, as well as a DoubleSolenoid to shift between gears.
 *
 * <p>The drivetrain consists of four (4) TalonSRX motor controllers four (4) CIM motors, two (2)
 * encoders, and two (2) double solenoids to trigger the two (2) shifting cylinders.
 *
 * @author FRC 1778 Chill Out
 */
public class Drive extends Subsystem {
  private static Drive instance = new Drive();

  private TalonSRX leftMaster;
  private TalonSRX rightMaster;
  private TalonSRX leftSlave;
  private TalonSRX rightSlave;

  private DoubleSolenoid leftShifter;
  private DoubleSolenoid rightShifter;

  private SimplePid gyroPathPid;

  private NavX navX;

  public enum SystemMode {
    OPEN_LOOP_PERCENTAGE,
    OPEN_LOOP_CURRENT,
    CLOSED_LOOP_POSITION
  }

  private static TalonSrxFactory.Configuration driveConfiguration;

  private SystemMode currentMode;
  private boolean isInHighGear;
  private boolean isInBrakeMode;
  private boolean pathDone;
  private boolean reversePath;

  /**
   * Returns a static instance of Drive, to be used instead of instantiating new objects of Drive.
   *
   * @return an instance of Drive to avoid multiple objects of the same hardware devices
   */
  public static Drive getinstance() {
    return instance;
  }

  private Drive() {
    driveConfiguration = new TalonSrxFactory.Configuration();
    driveConfiguration.feedbackDevice = FeedbackDevice.QuadEncoder;
    driveConfiguration.statusFrame = StatusFrameEnhanced.Status_13_Base_PIDF0;
    driveConfiguration.pidKp = 1.0;
    driveConfiguration.pidKi = 0.0;
    driveConfiguration.pidKd = 0.0;
    driveConfiguration.pidKf = 0.0;
    driveConfiguration.motionCruiseVelocity = 1000;
    driveConfiguration.motionAcceleration = 300;
    driveConfiguration.continuousCurrentLimit = 25;
    driveConfiguration.peakCurrentLimit = 35;
    driveConfiguration.peakCurrentLimitDuration = 100;
    driveConfiguration.enableCurrentLimit = true;
    driveConfiguration.openLoopRampTimeSeconds = 0.25;
    driveConfiguration.closedLoopRampTimeSeconds = 0.25;

    leftMaster = TalonSrxFactory.createTalon(Ports.LEFT_DRIVE_MASTER_ID, driveConfiguration);
    rightMaster = TalonSrxFactory.createTalon(Ports.RIGHT_DRIVE_MASTER_ID, driveConfiguration);

    leftSlave = TalonSrxFactory.createSlaveTalon(Ports.LEFT_DRIVE_SLAVE_ID, leftMaster);
    rightSlave = TalonSrxFactory.createSlaveTalon(Ports.RIGHT_DRIVE_SLAVE_ID, rightMaster);

    leftShifter =
        new DoubleSolenoid(Ports.PCM_ID, Ports.LEFT_SHIFTER_FORWARD, Ports.LEFT_SHIFTER_REVERSE);
    rightShifter =
        new DoubleSolenoid(Ports.PCM_ID, Ports.RIGHT_SHIFTER_FORWARD, Ports.RIGHT_SHIFTER_REVERSE);

    navX = new NavX(Ports.NAVX_SPI);

    leftMaster.setInverted(true);
    rightMaster.setInverted(false);
    leftSlave.setInverted(true);
    rightSlave.setInverted(false);
    leftMaster.setSensorPhase(true);
    rightMaster.setSensorPhase(true);

    setDriveMode(SystemMode.OPEN_LOOP_PERCENTAGE);
    enableBrake(true);
    setGear(false);
  }

  @Override
  public void sendTelemetry() {
    /*networkTable.putBoolean("High Gear", isHighGear());
    networkTable.putBoolean("Brake Mode", isBraking());
    networkTable.putNumber("Left Encoder", convertEncoderTicksToInches(getLeftEncoderPosition()));
    networkTable.putNumber("Right Encoder", convertEncoderTicksToInches(getRightEncoderPosition()));
    networkTable.putNumber("Yaw", navX.getYaw());
    networkTable.putBoolean("Path Done", isPathDone());

    debugTable.putString("Drive Mode", currentMode.toString());
    debugTable.putNumber("Left Current", leftMaster.getOutputCurrent());
    debugTable.putNumber("Right Current", rightMaster.getOutputCurrent());
    debugTable.putNumber("Left2 Current", leftSlave.getOutputCurrent());
    debugTable.putNumber("Right2 Current", rightSlave.getOutputCurrent());*/
  }

  @Override
  public void resetEncoders() {
    leftMaster.setSelectedSensorPosition(0, 0, driveConfiguration.timeoutInMs);
    rightMaster.setSelectedSensorPosition(0, 0, driveConfiguration.timeoutInMs);
  }

  @Override
  public void zeroSensors() {
    navX.zeroYaw();
  }

  /**
   * Returns the drivebase's NavX IMU. Use this instead of reinstantiating the NavX, which will
   * result in no response from the sensor.
   *
   * @return the drivebase's NavX IMU
   */
  public NavX getNavX() {
    return navX;
  }

  /**
   * Returns the current encoder position of the left motor in encoder ticks.
   *
   * @return the current encoder position of the left motor
   */
  public int getLeftEncoderPosition() {
    return leftMaster.getSelectedSensorPosition(driveConfiguration.profileSlotId);
  }

  /**
   * Returns the current encoder position of the right motor in encoder ticks.
   *
   * @return the current encoder position of the right motor
   */
  public int getRightEncoderPosition() {
    return rightMaster.getSelectedSensorPosition(driveConfiguration.profileSlotId);
  }

  /**
   * Converts a number of encoder ticks into distance based on the size of the wheel.
   *
   * @param encoderTicks the number of encoder ticks to convert
   * @return the converted inches from the encoder ticks
   */
  public double convertEncoderTicksToInches(int encoderTicks) {
    return ((Math.PI * Constants.DRIVE_WHEEL_DIAMETER) / Constants.DRIVE_ENCODER_TICKS_PER_ROTATION)
        * encoderTicks;
  }

  /**
   * Shifts to either high or low gear by activating or deactivating the solenoids.
   *
   * @param setHighGear shifts to high gear if true, or low gear if false
   */
  public void setGear(boolean setHighGear) {
    leftShifter.set(setHighGear ? Value.kForward : Value.kReverse);
    rightShifter.set(setHighGear ? Value.kForward : Value.kReverse);

    isInHighGear = setHighGear;
  }

  /**
   * Returns the current state of the shifter.
   *
   * @return the state of the gear shifting solenoid, true if in high gear, or false if in low gear
   */
  public boolean isHighGear() {
    return isInHighGear;
  }

  /**
   * Enables or disables the brake on the motors when resting.
   *
   * @param brake sets the Talons to brake if true, or coast if false
   */
  public void enableBrake(boolean brake) {
    leftMaster.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
    rightMaster.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);

    isInBrakeMode = brake;
  }

  /**
   * Returns the current state of the drive motors' brake mode.
   *
   * @return whether or not the drive motors are set to brake when neutral or coast
   */
  public boolean isBraking() {
    return isInBrakeMode;
  }

  /**
   * Sets the drive control mode/state to operate the TalonSRX's with.
   *
   * @param mode the wanted mode to set the system to use
   */
  public void setDriveMode(SystemMode mode) {
    currentMode = mode;
  }

  /**
   * Returns the current mode in which the drive is operating with.
   *
   * @return the mode the drivebase is using at the time
   * @see SystemState
   */
  public SystemMode getDriveMode() {
    return currentMode;
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
    switch (currentMode) {
      case OPEN_LOOP_PERCENTAGE:
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
        break;
      case OPEN_LOOP_CURRENT:
        leftMaster.set(ControlMode.Current, left);
        rightMaster.set(ControlMode.Current, right);
        break;
      case CLOSED_LOOP_POSITION:
        leftMaster.set(ControlMode.MotionMagic, left);
        rightMaster.set(ControlMode.MotionMagic, right);
        break;
      default:
        break;
    }
  }

  /**
   * Prepares the drivebase by resetting encoders and sensors. This also resets the EncoderFollowers
   * to the beginning of their trajectories.
   *
   * @param followers EncoderFollowers for the left and right motors
   */
  public void prepareForPath(EncoderFollower[] followers) {
    if (followers[0] != null) {
      followers[0].reset();
    }

    if (followers[1] != null) {
      followers[1].reset();
    }

    prepareForPath();
  }

  /** Prepares the drivebase by resetting encoders and sensors. */
  public void prepareForPath() {
    pathDone = false;
    resetEncoders();
    zeroSensors();
  }

  /**
   * Generates EncoderFollowers for the left and right motors to follow the given path.
   *
   * @param path the path made up of Waypoints
   * @param reverse reverses the path causing the robot to drive backwards if true
   * @return an array containing EncoderFollower EncoderFollowers for the left and right TalonSRXs
   */
  public EncoderFollower[] generatePath(Waypoint[] path, boolean reverse) {
    Trajectory.Config config =
        new Trajectory.Config(
            Trajectory.FitMethod.HERMITE_QUINTIC,
            Trajectory.Config.SAMPLES_FAST,
            Constants.PATH_DELTA_TIME,
            Constants.PATH_MAX_VELOCITY,
            Constants.PATH_MAX_ACCELERATION,
            Constants.PATH_MAX_JERK);

    Trajectory trajectory = Pathfinder.generate(path, config);
    TankModifier modifier = new TankModifier(trajectory).modify(Constants.DRIVE_TRACK_WIDTH);

    EncoderFollower leftFollower = new EncoderFollower(modifier.getLeftTrajectory());
    EncoderFollower rightFollower = new EncoderFollower(modifier.getRightTrajectory());

    leftFollower.configureEncoder(
        leftMaster.getSelectedSensorPosition(0),
        Constants.DRIVE_ENCODER_TICKS_PER_ROTATION,
        Constants.DRIVE_WHEEL_DIAMETER);
    rightFollower.configureEncoder(
        rightMaster.getSelectedSensorPosition(0),
        Constants.DRIVE_ENCODER_TICKS_PER_ROTATION,
        Constants.DRIVE_WHEEL_DIAMETER);

    leftFollower.configurePIDVA(
        Constants.PATH_PRIMARY_PID.getKp(),
        Constants.PATH_PRIMARY_PID.getKi(),
        Constants.PATH_PRIMARY_PID.getKd(),
        Constants.PATH_KV,
        Constants.PATH_KA);
    rightFollower.configurePIDVA(
        Constants.PATH_PRIMARY_PID.getKp(),
        Constants.PATH_PRIMARY_PID.getKi(),
        Constants.PATH_PRIMARY_PID.getKd(),
        Constants.PATH_KV,
        Constants.PATH_KA);

    reversePath = reverse;
    gyroPathPid = new SimplePid(Constants.PATH_GYRO_PID);
    if (reverse) {
      gyroPathPid.invertGains();
    }
    return new EncoderFollower[] {leftFollower, rightFollower};
  }

  /**
   * Follows the path indicated by the generated EncoderFollowers.
   *
   * @param followers an array of EncoderFollowers for the left and right motors to follow
   */
  public void followPath(EncoderFollower[] followers) {
    double left;
    double right;

    if (reversePath) {
      left = followers[0].calculate(-getLeftEncoderPosition());
      right = followers[1].calculate(-getRightEncoderPosition());
    } else {
      left = followers[0].calculate(getLeftEncoderPosition());
      right = followers[1].calculate(getRightEncoderPosition());
    }

    double headingSetpoint = Pathfinder.r2d(followers[0].getHeading());
    double heading = reversePath ? -navX.getYaw() : navX.getYaw();

    double turn = gyroPathPid.calculate(heading, headingSetpoint);

    if (reversePath) {
      setPowers(-left - turn, -right + turn);
    } else {
      setPowers(left - turn, right + turn);
    }

    if (followers[0].isFinished() && followers[1].isFinished()) {
      pathDone = true;
    }
  }

  public boolean isPathDone() {
    return pathDone;
  }
}
