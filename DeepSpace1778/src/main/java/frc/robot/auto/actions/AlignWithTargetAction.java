package frc.robot.auto.actions;

import frc.robot.Robot;
import frc.robot.components.SwerveDrive;

public class AlignWithTargetAction implements Action {

  private SwerveDrive swerve = SwerveDrive.getInstance();

  private boolean hasTarget = true;
  private double translationX;
  private double widthOfTarget;

  @Override
  public boolean isFinished() {
    return !hasTarget || widthOfTarget >= 250;
  }

  @Override
  public void update() {
    hasTarget = Robot.limelightTable.getEntry("tv").getDouble(0.0) == 0.0 ? false : true;
    translationX = Robot.limelightTable.getEntry("tx").getDouble(0.0);
    widthOfTarget = Robot.limelightTable.getEntry("thor").getDouble(0.0);

    System.out.println("Width: " + widthOfTarget);

    swerve.setSignals(
        swerve.calculateModuleSignals((320.0 - widthOfTarget) / 640.0, -translationX * 0.035, 0.0));
  }

  @Override
  public void done() {
    swerve.stop();
  }

  @Override
  public void start() {}
}
