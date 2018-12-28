package frc.robot;

import frc.robot.common.SimplePid.PidConstants;

/**
 * Holds constant properties. For example, robot dimensions, encoder ratios, etc.
 *
 * @author FRC 1778 Chill Out
 */
public class Constants {

  public static final PidConstants PATH_PRIMARY_PID = new PidConstants(0.05, 0.0, 0.0);
  public static final PidConstants PATH_GYRO_PID = new PidConstants(0.01, 0.0, 0.0);

  public static final double PATH_MAX_VELOCITY = 60.0;
  public static final double PATH_KV = 0.5 / PATH_MAX_VELOCITY;
  public static final double PATH_MAX_ACCELERATION = 45;
  public static final double PATH_KA = 0.05;
  public static final double PATH_MAX_JERK = 120.0;
  public static final double DRIVE_TRACK_WIDTH = 22.25;
  public static final double PATH_DELTA_TIME = 0.05;

  public static double PATH_ANGLE_OFFSET;

  public static final double DRIVE_WHEEL_DIAMETER = 4.0;
  public static final int DRIVE_ENCODER_TICKS_PER_ROTATION = 10800;
}
