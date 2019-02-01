package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.lib.driver.TalonSrxFactory;
import frc.robot.Constants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * Each instance of SwerveModule handles the two (2) Talon SRX controllers used to drive one (1) CIM
 * motor with a quadrature CIMCoder and one (1) BAG motor with an absolute MA3 encoder connected to
 * each swerve module. This simplifies the control structure for the swerve drive subsystem by
 * breaking up each module as a separate controllable appendage in the drivebase.
 *
 * @author FRC 1778 Chill Out
 */
public class SwerveModule {

  private TalonSRX turnMotor;
  private TalonSRX driveMotor;

  private boolean invertDriveMotor;

  private double zeroAngleOffset;
  private double targetAngle;
  private double lastDrivePower;

  private static final double ENCODER_PULSES_PER_REV = 20 * 8 * (60 / 8); // am-3314a (CIM)encoders
  private static final double INCHES_PER_REV = 5.9 * Math.PI; // 5.9-in diameter wheel (worn)

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
    driveConfiguration.continuousCurrentLimit = 10;
    driveConfiguration.peakCurrentLimit = 0;
    driveConfiguration.peakCurrentLimitDuration = 10;
    driveConfiguration.enableCurrentLimit = true;
    driveConfiguration.openLoopRampTimeSeconds = 0.25;
    driveConfiguration.motionAcceleration =
        (int) (Constants.SWERVE_MAX_ACCELERATION * INCHES_PER_ENCODER_PULSE);
    driveConfiguration.motionCruiseVelocity =
        (int) (Constants.SWERVE_MAX_VELOCITY * INCHES_PER_ENCODER_PULSE);

    turnConfiguration = new TalonSrxFactory.Configuration();
    turnConfiguration.feedbackDevice = FeedbackDevice.Analog;
    turnConfiguration.invert = false;
    turnConfiguration.invertSensorPhase = true;
    turnConfiguration.neutralPowerMode = NeutralMode.Brake;
    turnConfiguration.pidKp = 5.0; // 10.0;
    turnConfiguration.pidKi = 0.01; // 0.0;
    turnConfiguration.pidKd = 0.0; // 50.0;
    turnConfiguration.pidKf = 0.0;
    turnConfiguration.pidIntegralZone = 200;
    turnConfiguration.continuousCurrentLimit = 10;
    turnConfiguration.peakCurrentLimit = 0;
    turnConfiguration.peakCurrentLimitDuration = 10;
    turnConfiguration.enableCurrentLimit = true;
    turnConfiguration.openLoopRampTimeSeconds = 0.25;

    driveMotor = TalonSrxFactory.createTalon(driveTalonID, driveConfiguration);
    turnMotor = TalonSrxFactory.createTalon(turnTalonID, turnConfiguration);

    zeroAngleOffset = angleOffset;
    setTargetAngle(0);
  }

  public void stopMotors() {
    setDrivePower(0);
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
    return (invertDriveMotor ? -1 : 1)
        * (double) driveMotor.getSelectedSensorPosition(0)
        * INCHES_PER_ENCODER_PULSE;
  }

  public void setDrivePower(double percent) {
    lastDrivePower = percent;
    driveMotor.set(ControlMode.PercentOutput, percent);
  }

  public void setTurnPower(double percent) {
    turnMotor.set(ControlMode.PercentOutput, percent);
  }

  public double getCurrentAngle() {
    double angle = turnMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
    angle -= zeroAngleOffset;
    angle %= 360;
    if (angle < 0) {
      angle += 360;
    }

    return angle;
  }

  public void setTargetAngle(double angle) {
    targetAngle = angle;

    targetAngle %= 360;
    targetAngle += zeroAngleOffset;

    double currentAngle = getRawAbsAngle();
    double currentAngleMod = currentAngle % 360;
    if (currentAngleMod < 0) {
      currentAngleMod += 360;
    }

    double delta = currentAngleMod - targetAngle;

    if (delta > 180) {
      targetAngle += 360;
    } else if (delta < -180) {
      targetAngle -= 360;
    }

    delta = currentAngleMod - targetAngle;
    if (delta > 90 || delta < -90) {
      if (delta > 90) {
        targetAngle += 180;
      } else if (delta < -90) {
        targetAngle -= 180;
      }
      driveMotor.setInverted(false);
    } else {
      driveMotor.setInverted(true);
    }

    targetAngle += currentAngle - currentAngleMod;

    targetAngle *= 1024.0 / 360.0;

    turnMotor.set(ControlMode.Position, targetAngle);
  }

  public void setTargetDistance(double distance) {
    driveMotor.set(ControlMode.MotionMagic, distance * INCHES_PER_ENCODER_PULSE);
  }

  public void drivePath(EncoderFollower follower) {

    if (!follower.isFinished()) {

      Trajectory.Segment seg = follower.getSegment();
      double fakeDistance = seg.position;
      double speed = follower.calculate((int) fakeDistance);
      double heading = follower.getHeading();

      setDrivePower(speed);

      double gyroAngle = SwerveDrive.getInstance().getNavX().getAngle();
      gyroAngle = Math.IEEEremainder(gyroAngle, 360);

      setTargetAngle(Pathfinder.r2d(heading) + gyroAngle);
    }
  }

  public void setBrakeMode(boolean brake) {
    driveMotor.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
  }

  public void resetDriveEncoder() {
    driveMotor.setSelectedSensorPosition(0, 0, 0);
  }
}
