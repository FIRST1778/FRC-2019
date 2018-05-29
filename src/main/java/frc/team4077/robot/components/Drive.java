package frc.team4077.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team4077.robot.Ports;
import frc.team4077.robot.common.NavX;
import frc.team4077.robot.common.TalonSRXFactory;

/**
 * This is the robot's drivetrain. This class handles the four TalonSRX motor controllers attached
 * to the ganged left and right motors.
 *
 * <p>The drivetrain consists of four (4) TalonSRX motor controllers, four (4) CIM motors, one (1)
 * solenoid, two (2) shifting pistons, and one (1) NavX IMU.
 *
 * @author FRC 4077 MASH, Hillel Coates
 */
public class Drive extends Subsystem {
  private static Drive instance = new Drive();

  private final TalonSRX leftMaster, rightMaster, leftSlave, rightSlave;
  private final NavX navX;

  public enum SystemState {
    UNINITIALIZED, // Default
    ZEROING, // Zeroing sensors
    RUNNING_OPEN_LOOP, // Driving with no feedback
    RUNNING_VELOCITY_CLOSED_LOOP // Driving controlling the velocity with PID
  }

  private static TalonSRXFactory.Configuration masterConfiguration =
      new TalonSRXFactory.Configuration();

  static {
    masterConfiguration.FEEDBACK_DEVICE = FeedbackDevice.CTRE_MagEncoder_Relative;
    masterConfiguration.STATUS_FRAME_PERIOD = 10;
    masterConfiguration.STATUS_FRAME = StatusFrameEnhanced.Status_13_Base_PIDF0;
    masterConfiguration.PID_KP = 15;
    masterConfiguration.PID_KI = 0.01;
    masterConfiguration.PID_KD = 0.1;
    masterConfiguration.PID_KF = 0.2;
    masterConfiguration.MOTION_CRUISE_VELOCITY = 640;
    masterConfiguration.MOTION_ACCELERATION = 200;

    masterConfiguration.NEUTRAL_POWER_MODE = NeutralMode.Brake;
    masterConfiguration.CONTINUOUS_CURRENT_LIMIT = 25;
    masterConfiguration.PEAK_CURRENT_LIMIT = 25;
    masterConfiguration.PEAK_CURRENT_LIMIT_DURATION = 100;
    masterConfiguration.ENABLE_CURRENT_LIMIT = true;
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
    leftMaster = new TalonSRX(Ports.LEFT_SLAVE_TALON_ID);
    rightMaster = new TalonSRX(Ports.RIGHT_MASTER_TALON_ID);
    leftSlave = new TalonSRX(Ports.LEFT_SLAVE_TALON_ID);
    rightSlave = new TalonSRX(Ports.RIGHT_SLAVE_TALON_ID);

    leftMaster.setInverted(false);
    rightMaster.setInverted(true);
    leftSlave.setInverted(false);
    rightSlave.setInverted(true);

    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    leftMaster.setSensorPhase(false);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    rightMaster.setSensorPhase(false);

    leftSlave.set(ControlMode.Follower, Ports.LEFT_MASTER_TALON_ID);
    rightSlave.set(ControlMode.Follower, Ports.RIGHT_MASTER_TALON_ID);

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
