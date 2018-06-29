package frc.team1778.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.team1778.lib.DriveSignal;
import frc.team1778.lib.driver.NavX;
import frc.team1778.lib.driver.TalonSRXFactory;
import frc.team1778.robot.Constants;
import frc.team1778.robot.Ports;
import frc.team1778.robot.common.communication.NetworkTableWrapper;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * This is the robot's drivetrain. This class handles the four {@link TalonSRX} motor controllers
 * attached to the ganged left and right motors, as well as a {@link DoubleSolenoid} to shift
 * between gears.
 *
 * <p>The drivetrain consists of four (4) TalonSRX motor controllers four (4) CIM motors, two (2)
 * encoders, and two (2) double solenoids to trigger the two (2) shifting cylinders.
 *
 * @author FRC 1778 Chill Out
 */
public class Drive extends Subsystem {
  private static Drive instance = new Drive();

  private TalonSRX leftMaster, rightMaster, leftSlave, rightSlave;

  private DoubleSolenoid leftShifter;
  private DoubleSolenoid rightShifter;

  private NetworkTableWrapper networkTable = new NetworkTableWrapper("Drive");

  private EncoderFollower leftFollower;
  private EncoderFollower rightFollower;

  // private NavX navX;

  public enum SystemMode {
    OPEN_LOOP_PERCENTAGE,
    OPEN_LOOP_CURRENT,
    CLOSED_LOOP_POSITION
  };

  private static TalonSRXFactory.Configuration driveConfiguration;

  private SystemMode currentMode;
  private boolean isInHighGear;
  private boolean isInBrakeMode;
  private boolean pathDone;
  private boolean pathRunning;

  /**
   * Returns a static instance of Drive, to be used instead of instantiating new objects of Drive.
   *
   * @return an instance of Drive to avoid multiple objects of the same hardware devices
   */
  public static Drive getinstance() {
    return instance;
  }

  private Drive() {
    driveConfiguration = new TalonSRXFactory.Configuration();
    driveConfiguration.FEEDBACK_DEVICE = FeedbackDevice.QuadEncoder;
    driveConfiguration.STATUS_FRAME = StatusFrameEnhanced.Status_13_Base_PIDF0;
    driveConfiguration.PID_KP = 1.0;
    driveConfiguration.PID_KI = 0.0;
    driveConfiguration.PID_KD = 0.0;
    driveConfiguration.PID_KF = 0.0;
    driveConfiguration.MOTION_CRUISE_VELOCITY = 1000;
    driveConfiguration.MOTION_ACCELERATION = 300;
    driveConfiguration.CONTINUOUS_CURRENT_LIMIT = 25;
    driveConfiguration.PEAK_CURRENT_LIMIT = 25;
    driveConfiguration.PEAK_CURRENT_LIMIT_DURATION = 100;
    driveConfiguration.ENABLE_CURRENT_LIMIT = true;
    driveConfiguration.OPEN_LOOP_RAMP_TIME_SECONDS = 0.25;
    driveConfiguration.CLOSED_LOOP_RAMP_TIME_SECONDS = 0.25;

    leftMaster = TalonSRXFactory.createTalon(Ports.LEFT_DRIVE_MASTER_ID, driveConfiguration);
    rightMaster = TalonSRXFactory.createTalon(Ports.RIGHT_DRIVE_MASTER_ID, driveConfiguration);

    leftSlave = TalonSRXFactory.createSlaveTalon(Ports.LEFT_DRIVE_SLAVE_ID, leftMaster);
    rightSlave = TalonSRXFactory.createSlaveTalon(Ports.RIGHT_DRIVE_SLAVE_ID, rightMaster);

    leftShifter =
        new DoubleSolenoid(Ports.PCM_ID, Ports.LEFT_SHIFTER_FORWARD, Ports.LEFT_SHIFTER_REVERSE);
    rightShifter =
        new DoubleSolenoid(Ports.PCM_ID, Ports.RIGHT_SHIFTER_FORWARD, Ports.RIGHT_SHIFTER_REVERSE);

    // navX = new NavX(Ports.NAVX_SPI);

    leftMaster.setInverted(true);
    rightMaster.setInverted(false);
    leftSlave.setInverted(true);
    rightSlave.setInverted(false);
    leftMaster.setSensorPhase(true);
    rightMaster.setSensorPhase(true);

    setDriveMode(SystemMode.OPEN_LOOP_PERCENTAGE);
    enableBrake(true);
    setGear(true);
    prepareForPath();
  }

  @Override
  public void sendTelemetry() {
    networkTable.putBoolean("High Gear", isHighGear());
    networkTable.putBoolean("Brake Mode", isBraking());
    networkTable.putString("Drive Mode", currentMode.toString());
    networkTable.putNumber("Left Encoder", getLeftEncoderPosition());
    networkTable.putNumber("Right Encoder", getRightEncoderPosition());
    networkTable.putBoolean("Path Done", isPathDone());
    networkTable.putBoolean("Path Running", pathRunning);
  }

  @Override
  public void resetEncoders() {
    leftMaster.setSelectedSensorPosition(0, 0, driveConfiguration.TIMEOUT_IN_MS);
    rightMaster.setSelectedSensorPosition(0, 0, driveConfiguration.TIMEOUT_IN_MS);
  }

  @Override
  public void zeroSensors() {
    // navX.zeroYaw();
  }

  /**
   * Returns the drivebase's NavX IMU. Use this instead of reinstantiating the NavX, which will
   * result in no response from the sensor.
   *
   * @return the drivebase's NavX IMU
   */
  public NavX getNavX() {
    return null; // navX;
  }

  /**
   * Returns the current encoder position of the left motor in encoder ticks.
   *
   * @return the current encoder position of the left motor
   */
  public long getLeftEncoderPosition() {
    return leftMaster.getSelectedSensorPosition(driveConfiguration.PROFILE_SLOT_ID);
  }

  /**
   * Returns the current encoder position of the right motor in encoder ticks.
   *
   * @return the current encoder position of the right motor
   */
  public long getRightEncoderPosition() {
    return rightMaster.getSelectedSensorPosition(driveConfiguration.PROFILE_SLOT_ID);
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
   * @param newState the wanted state to set the system to use
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

  /* Path Following Below */
  public void prepareForPath() {
    pathDone = false;
    pathRunning = false;
    
    if (leftFollower != null) {
      leftFollower.reset();
    }

    if (rightFollower != null) {
      rightFollower.reset();
    }

    resetEncoders();
    zeroSensors();
  }

  public Trajectory generatePath(Waypoint[] path) {
    Trajectory.Config config =
        new Trajectory.Config(
            Trajectory.FitMethod.HERMITE_QUINTIC,
            Trajectory.Config.SAMPLES_FAST,
            Constants.Path.DELTA_TIME,
            Constants.Path.MAX_VELOCITY,
            Constants.Path.MAX_ACCELERATION,
            Constants.Path.MAX_JERK);

    return Pathfinder.generate(path, config);
  }

  public void setupFollowersForTrajectory(Trajectory trajectory) {
    TankModifier modifier = new TankModifier(trajectory).modify(Constants.Path.TRACK_WIDTH);

    leftFollower = new EncoderFollower(modifier.getLeftTrajectory());
    rightFollower = new EncoderFollower(modifier.getRightTrajectory());

    leftFollower.configureEncoder(
        leftMaster.getSelectedSensorPosition(0),
        Constants.Drive.ENCODER_TICKS_PER_ROTATION,
        Constants.Drive.WHEEL_DIAMETER);
    rightFollower.configureEncoder(
        rightMaster.getSelectedSensorPosition(0),
        Constants.Drive.ENCODER_TICKS_PER_ROTATION,
        Constants.Drive.WHEEL_DIAMETER);

    leftFollower.configurePIDVA(
        Constants.Path.PRIMARY_PID.getkP(),
        Constants.Path.PRIMARY_PID.getkI(),
        Constants.Path.PRIMARY_PID.getkD(),
        Constants.Path.KV,
        Constants.Path.KA);
    rightFollower.configurePIDVA(
        Constants.Path.PRIMARY_PID.getkP(),
        Constants.Path.PRIMARY_PID.getkI(),
        Constants.Path.PRIMARY_PID.getkD(),
        Constants.Path.KV,
        Constants.Path.KA);
  }

  public void followPath(boolean reversePath) {
    pathRunning = true;

    double left;
    double right;

    if (reversePath) {
      left = leftFollower.calculate(-leftMaster.getSelectedSensorPosition(0));
      right = rightFollower.calculate(-rightMaster.getSelectedSensorPosition(0));
    } else {
      left = leftFollower.calculate(leftMaster.getSelectedSensorPosition(0));
      right = rightFollower.calculate(rightMaster.getSelectedSensorPosition(0));
    }

    networkTable.putNumber("Left Power", left);
    networkTable.putNumber("Right Power", right);

    if (reversePath) {
      setPowers(-left, -right);
    } else {
      setPowers(left, right);
    }

    if (leftFollower.isFinished() && rightFollower.isFinished()) {
      pathDone = true;
      pathRunning = false;
    }
  }

  public boolean isPathDone() {
    return pathDone;
  }
}
