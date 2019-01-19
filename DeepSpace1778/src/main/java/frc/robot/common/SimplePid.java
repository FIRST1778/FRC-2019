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

  public SimplePid(double kp, double ki, double kd) {
    this.kp = kp;
    this.ki = ki;
    this.kd = kd;
    lastTime = System.currentTimeMillis();
  }

  public SimplePid(PidConstants pid) {
    this.kp = pid.getKp();
    this.ki = pid.getKi();
    this.kd = pid.getKd();
    lastTime = System.currentTimeMillis();
  }

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

  public void invertGains() {
    kp = -kp;
    ki = -ki;
    kd = -kd;
  }

  public static class PidConstants {
    private double kp;
    private double ki;
    private double kd;

    public PidConstants(double kp, double ki, double kd) {
      this.kp = kp;
      this.ki = ki;
      this.kd = kd;
    }

    public void setKp(double kp) {
      this.kp = kp;
    }

    public void setKi(double ki) {
      this.ki = ki;
    }

    public void setKd(double kd) {
      this.kd = kd;
    }

    public double getKp() {
      return kp;
    }

    public double getKi() {
      return ki;
    }

    public double getKd() {
      return kd;
    }
  }
}
