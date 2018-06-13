package frc.team1778.robot.components;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import frc.team1778.lib.util.driver.NavX;
import frc.team1778.lib.util.driver.SimpleTalonSRX;
import frc.team1778.lib.util.driver.TalonSRXFactory;
import frc.team1778.robot.Ports;

/**
 * This is the robot's drivetrain. This class handles the four TalonSRX motor controllers attached
 * to the ganged left and right motors, as well as the solenoids to shift between gears.
 *
 * <p>The drivetrain consists of four (8) TalonSRX motor controllers and four (4) CIM motors.
 *
 * @author FRC 1778 Chill Out
 */
public class Drive extends Subsystem {
  private static Drive instance = new Drive();

  private final SimpleTalonSRX leftMaster, rightMaster, leftSlave, rightSlave;

  private final NavX navX;

  public enum SystemState {
    UNINITIALIZED, // Default
    ZEROING, // Zeroing sensors
    RUNNING_OPEN_LOOP, // Driving with no feedback
    RUNNING_VELOCITY_CLOSED_LOOP // Driving controlling the velocity with PID
  }

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
    
    leftMaster.setInverted(false);
    rightMaster.setInverted(true);
    leftSlave.setInverted(false);
    rightSlave.setInverted(true);

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
