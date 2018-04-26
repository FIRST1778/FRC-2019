package frc.team4077.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team4077.robot.common.RobotMap;

/**
 * This is the robot's drivetrain. This class handles the four TalonSRX motor
 * controllers attached to the ganged left and right motors.
 * <p>
 * The drivetrain consists of four (4) TalonSRX motor controllers, four (4) CIM
 * motors, one (1) solenoid, two (2) shifting pistons, and one (1) NavX IMU.
 *
 * @author FRC 4077 MASH, Hillel Coates
 * @see Subsystem.java
 */
public class Drive extends Subsystem {
  private static Drive mInstance = new Drive();

  private final TalonSRX mLeftMaster, mRightMaster, mLeftSlave, mRightSlave;

  /**
   * Returns a static instance of Drive, to be used instead of instantiating
   * new objects of Drive.
   */
  public static Drive getInstance() {
    return mInstance;
  }

  private Drive() {
    mLeftMaster = new TalonSRX(RobotMap.LEFT_SLAVE_TALON_ID);
    mRightMaster = new TalonSRX(RobotMap.RIGHT_MASTER_TALON_ID);
    mLeftSlave = new TalonSRX(RobotMap.LEFT_SLAVE_TALON_ID);
    mRightSlave = new TalonSRX(RobotMap.RIGHT_SLAVE_TALON_ID);

    mLeftSlave.set(ControlMode.Follower, RobotMap.LEFT_MASTER_TALON_ID);
    mRightSlave.set(ControlMode.Follower, RobotMap.RIGHT_MASTER_TALON_ID);
  }

  @Override
  public void sendTelemetry() {}

  @Override
  public void zeroAndReset() {}
}