package frc.robot.auto.actions;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;
import frc.robot.auto.AutoConstants;
import frc.robot.components.SwerveDrive;

public class AlignWithTargetAction implements Action {

  private SwerveDrive swerve = SwerveDrive.getInstance();

  private boolean isCloseToTarget;
  private boolean hasTarget;
  private double translationX;
  private double widthOfTarget;

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

  public AlignWithTargetAction(double targetAngle) {
    anglePid.setInputRange(0, 360);
    anglePid.setOutputRange(-1.0, 1.0);
    anglePid.setContinuous(true);
    anglePid.setSetpoint(targetAngle);
    anglePid.enable();
  }

  @Override
  public boolean isFinished() {
    return isCloseToTarget && !hasTarget;
  }

  @Override
  public void update() {
    hasTarget = Robot.limelightTable.getEntry("tv").getDouble(0.0) == 0.0 ? false : true;
    translationX = hasTarget ? Robot.limelightTable.getEntry("tx").getDouble(0.0) : translationX;
    widthOfTarget =
        hasTarget ? Robot.limelightTable.getEntry("thor").getDouble(0.0) : widthOfTarget;

    isCloseToTarget = !isCloseToTarget ? widthOfTarget >= 250 : isCloseToTarget;

    swerve.setSignals(
        swerve.calculateModuleSignals(
            (((320.0 - widthOfTarget) / 320) * 0.5) + 0.2, -translationX * 0.005, angleCorrection));
  }

  @Override
  public void done() {
    swerve.stop();
    Robot.limelightTable.getEntry("camMode").setDouble(1.0);
    Robot.limelightTable.getEntry("ledMode").setDouble(1.0);
  }

  @Override
  public void start() {
    Robot.limelightTable.getEntry("camMode").setDouble(0.0);
    Robot.limelightTable.getEntry("ledMode").setDouble(0.0);
  }
}
