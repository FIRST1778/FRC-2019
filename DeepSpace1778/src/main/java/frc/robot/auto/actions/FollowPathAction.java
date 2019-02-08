package frc.robot.auto.actions;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.lib.pathing.Path;
import frc.lib.util.SimpleUtil;
import frc.robot.auto.AutoConstants;
import frc.robot.components.SwerveDrive;

public class FollowPathAction implements Action {

  private SwerveDrive swerve = SwerveDrive.getInstance();
  private Path path;
  private boolean hasReset = false;

  private PIDSource navXSource =
      new PIDSource() {
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {}

        @Override
        public double pidGet() {
          return swerve.getNavX().getAngle();
        }

        @Override
        public PIDSourceType getPIDSourceType() {
          return PIDSourceType.kDisplacement;
        }
      };

  private PIDController anglePid =
      new PIDController(
          AutoConstants.GYRO_AID_KP,
          AutoConstants.GYRO_AID_KI,
          AutoConstants.GYRO_AID_KD,
          navXSource,
          output -> angleCorrection = output);

  private double angleCorrection;

  public FollowPathAction(Path pathToFollow) {
    path = pathToFollow;

    anglePid.setInputRange(0, 360);
    anglePid.setOutputRange(-1.0, 1.0);
    anglePid.setContinuous(true);
    anglePid.enable();
  }

  @Override
  public boolean isFinished() {
    double currentDistance = getAverageEncoderPositions();

    if (!hasReset) {
      hasReset = currentDistance < 10.0;
      return false;
    }

    boolean end = Math.abs(path.getLength() - currentDistance) < 0.0;
    return end;
  }

  @Override
  public void update() {
    double currentDistance = getAverageEncoderPositions();
    double angle = swerve.getNavX().getAngle();

    double pathDirection = path.getDirectionAtDistance(currentDistance) * (Math.PI / 180.0);
    anglePid.setSetpoint(path.getAngleAtDistance(currentDistance));

    double forward = Math.cos(pathDirection);
    double strafe = Math.sin(pathDirection);
    double angleRadians = Math.toRadians(angle);
    double temp = (forward * Math.cos(angleRadians)) + (strafe * Math.sin(angleRadians));
    strafe = (-forward * Math.sin(angleRadians)) + (strafe * Math.cos(angleRadians));
    forward = temp;

    swerve.setTurnSignals(swerve.calculateModuleSignals(forward, strafe, angleCorrection));
  }

  @Override
  public void done() {
    swerve.stop();
  }

  @Override
  public void start() {
    hasReset = false;
    swerve.resetEncoders();
    System.out.println(
        "Path length: " + path.getLength() + ", Direction: " + path.getDirection(1.0));
    swerve.setTargetDistances(path.getLength());
  }

  private double getAverageEncoderPositions() {
    return SimpleUtil.meanWithoutLowestOutliers(
        new double[] {
          swerve.getLeftFrontModule().getDriveDistanceInches(),
          swerve.getRightFrontModule().getDriveDistanceInches(),
          swerve.getLeftBackModule().getDriveDistanceInches(),
          swerve.getRightBackModule().getDriveDistanceInches()
        },
        2);
  }
}
