package frc.team1778.robot;

import frc.team1778.robot.common.SimplePID.PIDConstants;

/**
 * Holds constant properties. For example, robot dimensions, encoder ratios, etc.
 *
 * @author FRC 1778 Chill Out
 */
public class Constants {
  public static class Path {
    public static final PIDConstants PRIMARY_PID = new PIDConstants(0.05, 0.0, 0.0);
    public static final PIDConstants GYRO_PID = new PIDConstants(0.01, 0.0, 0.0);

    public static final double MAX_VELOCITY = 60.0;
    public static final double KV = 0.5 / MAX_VELOCITY;
    public static final double MAX_ACCELERATION = 45;
    public static final double KA = 0.05;
    public static final double MAX_JERK = 120.0;
    public static final double TRACK_WIDTH = 22.25;
    public static final double DELTA_TIME = 0.05;

    public static double ANGLE_OFFSET;
  }

  public static class Drive {
    public static final PIDConstants TURN_PID = new PIDConstants(0.0175, 0.0000025, 2.0);

    public static final double WHEEL_DIAMETER = 4.0;
    public static final int ENCODER_TICKS_PER_ROTATION = 10800;
  }
}
