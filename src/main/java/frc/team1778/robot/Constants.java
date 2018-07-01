package frc.team1778.robot;

/**
 * Holds constant properties. For example, robot dimensions, encoder ratios, etc.
 *
 * @author FRC 1778 Chill Out
 */
public class Constants {
  public static class Path {
    public static final PIDConstants PRIMARY_PID = new PIDConstants(0.05, 0.0, 0.0);
    public static final PIDConstants GYRO_PID = new PIDConstants(0.015, 0.0, 0.0);

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
    public static final double WHEEL_DIAMETER = 4.0;
    public static final int ENCODER_TICKS_PER_ROTATION = 10800;
  }

  public static class PIDConstants {
    private double kP;
    private double kI;
    private double kD;

    /**
     * Holds the three PID gain constants requred for a PID loop.
     *
     * @param kP the kP to set
     * @param kI the kI to set
     * @param kD the kD to set
     */
    public PIDConstants(double kP, double kI, double kD) {
      this.kP = kP;
      this.kI = kI;
      this.kD = kD;
    }

    /**
     * Sets the new kP.
     *
     * @param kP the kP to set
     */
    public void setkP(double kP) {
      this.kP = kP;
    }

    /**
     * Sets the new kI.
     *
     * @param kI the kI to set
     */
    public void setkI(double kI) {
      this.kI = kI;
    }

    /**
     * Sets the new kD.
     *
     * @param kD the kD to set
     */
    public void setkD(double kD) {
      this.kD = kD;
    }

    /**
     * Returns the current kP gain
     *
     * @return the kP gain
     */
    public double getkP() {
      return kP;
    }

    /**
     * Returns the current kI gain
     *
     * @return the kI gain
     */
    public double getkI() {
      return kI;
    }

    /**
     * Returns the current kD gain
     *
     * @return the kD gain
     */
    public double getkD() {
      return kD;
    }
  }
}
