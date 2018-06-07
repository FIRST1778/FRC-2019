package frc.team1778.robot.components.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team1778.robot.Constants;
import frc.team1778.robot.common.TalonSRXFactory;
import frc.team1778.robot.components.Subsystem;

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
public class SwerveModule extends Subsystem {
  private static SwerveModule instance = new SwerveModule();

  private final TalonSRX driveMotor;
  private final TalonSRX rotationMotor;

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
    driveConfiguration.MOTION_CRUISE_VELOCITY = (int) (Constants.SWERVE_DRIVE_MAX_SPEED * 0.9);
    driveConfiguration.MOTION_ACCELERATION = (int) (Constants.SWERVE_DRIVE_MAX_SPEED);
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
  public static SwerveModule getinstance() {
    return instance;
  }

  private SwerveModule() {
    driveMotor = TalonSRXFactory.createTalon(1, driveConfiguration);
    rotationMotor = TalonSRXFactory.createTalon(2, rotationConfiguration);
    driveMotor.set(ControlMode.PercentOutput, 0);
    rotationMotor.set(ControlMode.Position, 0);
  }

  @Override
  public void sendTelemetry() {}

  @Override
  public void resetEncoders() {}

  @Override
  public void zeroSensors() {}
}
