package frc.robot.auto.actions;

import frc.lib.pathing.Path;
import frc.lib.pathing.Trajectory;
import frc.robot.Constants;
import frc.robot.components.SwerveDrive;

public class FollowPathAction implements Action {
  public static final double ALLOWABLE_ERROR = 3;

  private SwerveDrive swerve = SwerveDrive.getInstance();
  private Path path;
  private Trajectory trajectory;
  private boolean hasReset = false;
  private double angleSetpoint;

  private double angleCorrection;

  public FollowPathAction(Path pathToFollow) {
    this(pathToFollow, Constants.SWERVE_MAX_ACCELERATION, Constants.SWERVE_MAX_VELOCITY);
  }

  public FollowPathAction(Path pathToFollow, double maxAcceleration, double maxVelocity) {
    path = pathToFollow;
    trajectory = new Trajectory(pathToFollow, maxAcceleration, maxVelocity);
  }

  @Override
  public boolean isFinished() {
    double currentDistance = getAverageEncoderPositions();

    if (!hasReset) {
      hasReset = currentDistance < 10.0;
      return false;
    }

    return Math.abs(path.getLength() - currentDistance) < ALLOWABLE_ERROR;
  }

  @Override
  public void update() {
    double currentDistance = getAverageEncoderPositions();
    double angleCorrection = swerve.getNavX().getAngle() * 0.01;

    double pathDirection = path.getDirectionAtDistance(currentDistance) * (180.0 / Math.PI);
    swerve.setTurnSignals(
        swerve.calculateModuleSignals(
            Math.cos(pathDirection), Math.sin(pathDirection), angleCorrection));
  }

  @Override
  public void done() {
    swerve.stop();
  }

  @Override
  public void start() {
    hasReset = false;
    swerve.resetEncoders();
    swerve.setTargetDistances(path.getLength());

    angleSetpoint = swerve.getNavX().getAngle();
  }

  private double getAverageEncoderPositions() {
    return (swerve.getLeftFrontModule().getDistanceInches()
            + swerve.getRightFrontModule().getDistanceInches()
            + swerve.getLeftBackModule().getDistanceInches()
            + swerve.getRightBackModule().getDistanceInches())
        / 4.0;
  }
}
