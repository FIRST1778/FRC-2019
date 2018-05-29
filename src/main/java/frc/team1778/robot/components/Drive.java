package frc.team1778.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team1778.robot.Ports;
import frc.team1778.robot.common.NavX;
import frc.team1778.robot.common.TalonSRXFactory;

/**
 * This is the robot's drivetrain. This class handles the four TalonSRX motor controllers attached
 * to the ganged left and right motors.
 *
 * <p>The drivetrain consists of four (8) TalonSRX motor controllers, four (4) CIM motors, two (2)
 * BAG motors, and one (1) NavX IMU. The CIM motors are used to drive each swerve module, and the
 * BAG motors are used for rotation.
 *
 * @author FRC 1778 Chill Out, Hillel Coates
 */
public class Drive extends Subsystem {
  private static Drive instance = new Drive();

  private final TalonSRX leftFrontDrive, leftRearDrive, rightRearDrive, rightFrontDrive;
  private final TalonSRX leftFrontRotate, leftRearRotate, rightRearRotate, rightFrontRotate;
  private final NavX navX;

  public enum SystemState {
    UNINITIALIZED, // Default
    ZEROING, // Zeroing sensors
    RUNNING_OPEN_LOOP, // Driving with no feedback
    RUNNING_VELOCITY_CLOSED_LOOP // Driving controlling the velocity with PID
  }

  private static TalonSRXFactory.Configuration driveConfiguration =
      new TalonSRXFactory.Configuration();
  private static TalonSRXFactory.Configuration rotationConfiguration =
      new TalonSRXFactory.Configuration();

  static {
    // Drive Config
    driveConfiguration.FEEDBACK_DEVICE = FeedbackDevice.CTRE_MagEncoder_Relative;
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

    // Rotation Config
    rotationConfiguration.FEEDBACK_DEVICE = FeedbackDevice.Analog;
    rotationConfiguration.INVERT_SENSOR_PHASE = true;
    rotationConfiguration.PID_KP = 30;
    rotationConfiguration.PID_KI = 0.001;
    rotationConfiguration.PID_KD = 200;
    rotationConfiguration.NEUTRAL_POWER_MODE = NeutralMode.Brake;
    rotationConfiguration.CONTINUOUS_CURRENT_LIMIT = 30;
    rotationConfiguration.PEAK_CURRENT_LIMIT = 30;
    rotationConfiguration.PEAK_CURRENT_LIMIT_DURATION = 100;
    rotationConfiguration.ENABLE_CURRENT_LIMIT = true;
  }

  /**
   * Returns a static instance of Drive, to be used instead of instantiating new objects of Drive.
   *
   * @return An instance of Drive
   */
  public static Drive getinstance() {
    return instance;
  }

  private Drive() {
    leftFrontDrive = TalonSRXFactory.createTalon(1, driveConfiguration);
    leftRearDrive = TalonSRXFactory.createTalon(3, driveConfiguration);
    rightRearDrive = TalonSRXFactory.createTalon(5, driveConfiguration);
    rightFrontDrive = TalonSRXFactory.createTalon(7, driveConfiguration);

    leftFrontRotate = TalonSRXFactory.createTalon(2, rotationConfiguration);
    leftRearRotate = TalonSRXFactory.createTalon(3, rotationConfiguration);
    rightRearRotate = TalonSRXFactory.createTalon(6, rotationConfiguration);
    rightFrontRotate = TalonSRXFactory.createTalon(8, rotationConfiguration);

    leftFrontRotate.set(ControlMode.Position, 0);
    leftRearRotate.set(ControlMode.Position, 0);
    rightRearRotate.set(ControlMode.Position, 0);
    rightFrontRotate.set(ControlMode.Position, 0);

    navX = new NavX(Ports.NAVX_SPI);
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
  public void resetEncoders() {}

  @Override
  public void zeroSensors() {
    navX.zeroYaw();
  }
}
