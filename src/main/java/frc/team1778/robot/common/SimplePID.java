package frc.team1778.robot.common;

import frc.team1778.robot.Constants;

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
  public SimplePID(Constants.PIDConstants pid) {
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
}
