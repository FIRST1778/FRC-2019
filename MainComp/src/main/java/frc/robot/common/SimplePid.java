package frc.robot.common;

/**
 * Handles a basic PID loop by calculating the output over time based on an input and setpoint.
 *
 * @author FRC 1778 Chill Out
 */
public class SimplePid {
  private double kp;
  private double ki;
  private double kd;
  private double integral;
  private double lastError;
  private long lastTime;

  /**
   * Initializes the system by setting the kp, ki, and kd gains for the system to use in the future.
   *
   * @param kp the kp gain
   * @param ki the kp gain
   * @param kd the kp gain
   */
  public SimplePid(double kp, double ki, double kd) {
    this.kp = kp;
    this.ki = ki;
    this.kd = kd;
    lastTime = System.currentTimeMillis();
  }

  /**
   * Initializes the system by setting the kp, ki, and kd gains for the system to use in the future.
   *
   * @param pid the pid gains to use
   */
  public SimplePid(PidConstants pid) {
    this.kp = pid.getKp();
    this.ki = pid.getKi();
    this.kd = pid.getKd();
    lastTime = System.currentTimeMillis();
  }

  /**
   * Calculates PID based off of the input and set point values.
   *
   * @param input the current value of the sensor to be used as feedback
   * @param setPoint the goal value or point that the system is set to
   * @return the calculated PID output value - yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet
   *     yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet
   *     yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet
   *     yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet
   *     yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet yeet
   */
  public double calculate(double input, double setPoint) {
    long deltaTime = System.currentTimeMillis() - lastTime;

    if (deltaTime >= 500) {
      deltaTime = 0;
      integral = 0;
      lastError = 0;
    }

    double error = setPoint - input;

    integral += error * deltaTime;
    double derivative = (error - lastError) / deltaTime;

    double output = kp * error + ki * integral + kd * derivative;
    lastError = error;
    lastTime = System.currentTimeMillis();

    return output;
  }

  /** Inverts all of the PID gains, so if kp is set to 0.8, it will change to -0.8. */
  public void invertGains() {
    kp = -kp;
    ki = -ki;
    kd = -kd;
  }

  public static class PidConstants {
    private double kp;
    private double ki;
    private double kd;

    /**
     * Holds the three PID gain constants requred for a PID loop.
     *
     * @param kp the kp to set
     * @param ki the ki to set
     * @param kd the kd to set
     */
    public PidConstants(double kp, double ki, double kd) {
      this.kp = kp;
      this.ki = ki;
      this.kd = kd;
    }

    /**
     * Sets the new kp.
     *
     * @param kp the kp to set
     */
    public void setKp(double kp) {
      this.kp = kp;
    }

    /**
     * Sets the new ki.
     *
     * @param ki the ki to set
     */
    public void setKi(double ki) {
      this.ki = ki;
    }

    /**
     * Sets the new kd.
     *
     * @param kd the kd to set
     */
    public void setKd(double kd) {
      this.kd = kd;
    }

    /**
     * Returns the current kp gain.
     *
     * @return the kp gain
     */
    public double getKp() {
      return kp;
    }

    /**
     * Returns the current ki gain.
     *
     * @return the ki gain
     */
    public double getKi() {
      return ki;
    }

    /**
     * Returns the current kd gain.
     *
     * @return the kd gain
     */
    public double getKd() {
      return kd;
    }
  }
}
