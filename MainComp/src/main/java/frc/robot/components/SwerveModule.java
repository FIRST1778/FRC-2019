package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.lib.driver.TalonSrxFactory;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/** SwerveModule */
public class SwerveModule {
  private TalonSRX turnMotor;
  private TalonSRX driveMotor;

  private boolean invertDriveMotor;

  private double zeroAngleOffset;
  private double targetAngle;
  private double lastDrivePower;
  private double lastTurnPower;

  private static final double ENCODER_PULSES_PER_REV = 20 * 4; // am-3314a (CIM)encoders
  private static final double INCHES_PER_REV = (5.9 * 3.14159); // 5.9-in diameter wheel (worn)

  public static final double INCHES_PER_ENCODER_PULSE = INCHES_PER_REV / ENCODER_PULSES_PER_REV;
  public static final double RPM_TO_UNIT_PER_100MS = ENCODER_PULSES_PER_REV / (60 * 10);

  private static TalonSrxFactory.Configuration driveConfiguration;
  private static TalonSrxFactory.Configuration turnConfiguration;

  public SwerveModule(int driveTalonID, int turnTalonID, double angleOffset) {
    driveConfiguration = new TalonSrxFactory.Configuration();
    driveConfiguration.feedbackDevice = FeedbackDevice.QuadEncoder;
    driveConfiguration.invertSensorPhase = false;
    driveConfiguration.pidKp = 1.0;
    driveConfiguration.pidKi = 0.001;
    driveConfiguration.pidKd = 0.0;
    driveConfiguration.pidKf = 0.0;
    driveConfiguration.pidIntegralZone = 18;
    driveConfiguration.continuousCurrentLimit = 25;
    driveConfiguration.peakCurrentLimit = 35;
    driveConfiguration.peakCurrentLimitDuration = 100;
    driveConfiguration.enableCurrentLimit = true;

    turnConfiguration = new TalonSrxFactory.Configuration();
    turnConfiguration.feedbackDevice = FeedbackDevice.Analog;
    turnConfiguration.invertSensorPhase = true;
    turnConfiguration.neutralPowerMode = NeutralMode.Brake;
    turnConfiguration.pidKp = 4.2;
    turnConfiguration.pidKi = 0.01;
    turnConfiguration.pidKd = 0.0;
    turnConfiguration.pidKf = 0.0;
    turnConfiguration.pidIntegralZone = 200;
    turnConfiguration.continuousCurrentLimit = 25;
    turnConfiguration.peakCurrentLimit = 35;
    turnConfiguration.peakCurrentLimitDuration = 100;
    turnConfiguration.enableCurrentLimit = true;

    driveMotor = TalonSrxFactory.createTalon(driveTalonID, driveConfiguration);
    turnMotor = TalonSrxFactory.createTalon(turnTalonID, turnConfiguration);

    zeroAngleOffset = angleOffset;
    setTargetAngle(0);
  }

  public void stopMotors() {
    setDrivePower(0);
    setTurnPower(0);
  }

  public TalonSRX getDriveMotor() {
    return driveMotor;
  }

  public TalonSRX getTurnMotor() {
    return turnMotor;
  }

  public double getDrivePower() {
    return lastDrivePower;
  }

  public double getTurnPower() {
    return lastTurnPower;
  }

  public double getTurnOffset() {
    return zeroAngleOffset;
  }

  public double getTargetAngle() {
    return targetAngle;
  }

  public double getRawAbsAngle() {
    return (double) turnMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
  }

  public double getTurnEnc() {
    return (double) turnMotor.getSelectedSensorPosition(0);
  }

  public double getAbsAngle() {
    return ((double) turnMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0)) - zeroAngleOffset;
  }

  public double getDistanceInches() {
    return (double) (driveMotor.getSelectedSensorPosition(0)) * INCHES_PER_ENCODER_PULSE;
  }

  public void setDrivePower(double percent) {
    lastDrivePower = percent;
    driveMotor.set(ControlMode.PercentOutput, percent);
  }

  public void setTurnPower(double percent) {
    turnMotor.set(ControlMode.PercentOutput, percent);
  }

  /**
   * Get the current angle of the swerve module
   *
   * @return An angle in the range [0, 360)
   */
  public double getCurrentAngle() {
    double angle = turnMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
    angle -= zeroAngleOffset;
    angle %= 360;
    if (angle < 0) angle += 360;

    return angle;
  }

  public void setTargetAngle(double angle) {
    targetAngle = angle;

    targetAngle %= 360;
    targetAngle += zeroAngleOffset;

    double currentAngle = getRawAbsAngle();
    double currentAngleMod = currentAngle % 360;
    if (currentAngleMod < 0) currentAngleMod += 360;

    double delta = currentAngleMod - targetAngle;

    if (delta > 180) {
      targetAngle += 360;
    } else if (delta < -180) {
      targetAngle -= 360;
    }

    delta = currentAngleMod - targetAngle;
    if (delta > 90 || delta < -90) {
      if (delta > 90) targetAngle += 180;
      else if (delta < -90) targetAngle -= 180;
      driveMotor.setInverted(false);
    } else {
      driveMotor.setInverted(true);
    }

    targetAngle += currentAngle - currentAngleMod;

    targetAngle *= 1024.0 / 360.0;

    turnMotor.set(ControlMode.Position, targetAngle);
  }

  public void drivePath(EncoderFollower follower) {

    if (!follower.isFinished()) {

      Trajectory.Segment seg = follower.getSegment();
      double fakeDistance = seg.position;
      double speed = follower.calculate((int) fakeDistance);
      double heading = follower.getHeading();

      setDrivePower(speed);

      double gyroAngle = SwerveDrive.getinstance().getNavX().getAngle();
      gyroAngle = Math.IEEEremainder(gyroAngle, 360);

      setTargetAngle(Pathfinder.r2d(heading) + gyroAngle);
    }
  }

  public void stop() {
    setDrivePower(0);
    setTurnPower(0);
  }

  public void setBrakeMode(boolean brake) {
    driveMotor.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
  }

  public void setDriveMotorForward(boolean forward) {
    invertDriveMotor = !forward;

    driveMotor.setInverted(invertDriveMotor);
    driveMotor.setSensorPhase(forward);
  }

  public void resetDriveEncoder() {
    driveMotor.setSelectedSensorPosition(0);
  }
}
