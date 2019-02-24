package frc.robot.auto.actions;

import frc.robot.components.Manipulator;

public class HatchAction implements Action {

  private Manipulator manny = Manipulator.getInstance();
  private boolean openHatch = false;

  public HatchAction(boolean openHatch) {
    this.openHatch = openHatch;
  }

  @Override
  public boolean isFinished() {
    return manny.openHatchCollector(openHatch);
  }

  @Override
  public void update() {}

  @Override
  public void done() {}

  @Override
  public void start() {
    manny.openHatchCollector(openHatch);
  }
}
