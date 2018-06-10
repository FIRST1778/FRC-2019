package frc.team1778.lib.util;

/** A drivetrain command consisting of the left, right motor settings. */
public class DriveSignal {
  protected double mLeftMotor;
  protected double mRightMotor;

  public DriveSignal(double left, double right) {
    mLeftMotor = left;
    mRightMotor = right;
  }

  public double getLeft() {
    return mLeftMotor;
  }

  public double getRight() {
    return mRightMotor;
  }

  @Override
  public String toString() {
    return "L: " + mLeftMotor + ", R: " + mRightMotor;
  }
}
