package frc.team1778.robot.common;

/**
 * Handles a basic PID loop by calculating the output over time based on an input and setpoint.
 *
 * @author FRC 1778 Chill Out
 */
public class SimplePID {
  private double kP, kI, kD;
  private double integral;
  private double lastError;
  private long lastTime;

  /**
   * Initializes the system by setting the kP, kI, and kD gains for the system to use in the future.
   *
   * @param kP the kP gain
   * @param kI the kP gain
   * @param kD the kP gain
   */
  public SimplePID(double kP, double kI, double kD) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    lastTime = System.currentTimeMillis();
  }

  /**
   * Initializes the system by setting the kP, kI, and kD gains for the system to use in the future.
   *
   * @param pid the pid gains to use
   */
  public SimplePID(PIDConstants pid) {
    this.kP = pid.getkP();
    this.kI = pid.getkI();
    this.kD = pid.getkD();
    lastTime = System.currentTimeMillis();
  }

  /**
   * Calculates PID based off of the input and set point values.
   *
   * @param input the current value of the sensor to be used as feedback
   * @param setPoint the goal value or point that the system is set to
   * @return the calculated PID output value
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

    double output = kP * error + kI * integral + kD * derivative;
    lastError = error;
    lastTime = System.currentTimeMillis();

    return output;
  }

  /** Inverts all of the PID gains, so if kP is set to 0.8, it will change to -0.8. */
  public void invertGains() {
    kP = -kP;
    kI = -kI;
    kD = -kD;
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
