package frc.robot.auto.actions;

import frc.robot.components.SwerveDrive;

/**
 * Follows a simple path that drives in a direction a certain distance and speed.
 *
 * @author FRC 1778 Chill Out
 */
public class MoveCardinalDirectionAction implements Action {
  private SwerveDrive swerve = SwerveDrive.getInstance();
  private double distance;
  private double angle;
  private double speed;

  private double leftFrontEncoderOffset;
  private double rightFrontEncoderOffset;
  private double leftBackEncoderOffset;
  private double rightBackEncoderOffset;

  public MoveCardinalDirectionAction(
      double angleCardinal, double distanceInches, double powerPercentage) {
    distance = distanceInches;
    angle = angleCardinal * Math.PI / 180.0;
    speed = powerPercentage;
  }

  @Override
  public boolean isFinished() {
    return (swerve.getLeftFrontModule().getDistanceInches() >= distance)
        && (swerve.getRightFrontModule().getDistanceInches() >= distance)
        && (swerve.getLeftBackModule().getDistanceInches() >= distance)
        && (swerve.getRightBackModule().getDistanceInches() >= distance);
  }

  @Override
  public void update() {
    double turn = 0; // (endAngle - swerve.getNavX().getAngle()) * AutoConstants.GYRO_AID_KP;

    swerve.setSignals(
        swerve.calculateModuleSignals(Math.cos(angle) * speed, Math.sin(angle) * speed, turn));
  }

  @Override
  public void done() {
    swerve.stop();
  }

  @Override
  public void start() {
    swerve.resetEncoders();
  }
}
