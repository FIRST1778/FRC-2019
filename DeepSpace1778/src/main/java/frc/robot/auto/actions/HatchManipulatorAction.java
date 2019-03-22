package frc.robot.auto.actions;

import frc.robot.components.Manipulator;

public class HatchManipulatorAction implements Action {

  private Manipulator manipulator = Manipulator.getInstance();
  private boolean open;

  public HatchManipulatorAction(boolean grab) {
    open = grab;
  }

  @Override
  public boolean isFinished() {
    return manipulator.hasHatchPanel();
  }

  @Override
  public void update() {}

  @Override
  public void done() {}

  @Override
  public void start() {
    manipulator.openHatchCollector(open);
  }
}
