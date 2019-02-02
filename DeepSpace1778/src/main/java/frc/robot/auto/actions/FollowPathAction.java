package frc.robot.auto.actions;

import frc.lib.pathing.Path;
import frc.robot.auto.AutoConstants;
import frc.robot.components.SwerveDrive;

public class FollowPathAction implements Action {

  public static final double ALLOWABLE_ERROR = 1;

  private SwerveDrive swerve = SwerveDrive.getInstance();
  private Path path;
  private boolean hasReset = false;
  private double angleSetpoint;

  public FollowPathAction(Path pathToFollow, double angle) {
    path = pathToFollow;
    angleSetpoint = angle;
  }

  @Override
  public boolean isFinished() {
    double currentDistance = getAverageEncoderPositions();

    if (!hasReset) {
      hasReset = currentDistance < 10.0;
      return false;
    }

    boolean end = Math.abs(path.getLength() - currentDistance) < ALLOWABLE_ERROR;
    return end;
  }

  @Override
  public void update() {
    double currentDistance = getAverageEncoderPositions();
    double angle = swerve.getNavX().getAngle();

    double pathDirection = path.getDirectionAtDistance(currentDistance) * (Math.PI / 180.0);

    double forward = Math.cos(pathDirection);
    double strafe = Math.sin(pathDirection);
    double angleRadians = Math.toRadians(angle);
    double temp = (forward * Math.cos(angleRadians)) + (strafe * Math.sin(angleRadians));
    strafe = (-forward * Math.sin(angleRadians)) + (strafe * Math.cos(angleRadians));
    forward = temp;

    double angleCorrection = (angle - angleSetpoint) * AutoConstants.GYRO_AID_KP;

    swerve.setTurnSignals(swerve.calculateModuleSignals(forward, strafe, -angleCorrection));
  }

  @Override
  public void done() {
    swerve.stop();
  }

  @Override
  public void start() {
    hasReset = false;
    swerve.resetEncoders();
    System.out.println("Path length: " + path.getLength());
    swerve.setTargetDistances(path.getLength());

    angleSetpoint = -swerve.getNavX().getAngle();
  }

  private double getAverageEncoderPositions() {
    return (swerve.getLeftFrontModule().getDistanceInches()
            + swerve.getRightFrontModule().getDistanceInches()
            + swerve.getLeftBackModule().getDistanceInches()
            + swerve.getRightBackModule().getDistanceInches())
        / 4.0;
  }
}
