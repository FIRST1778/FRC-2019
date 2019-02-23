package frc.robot.auto.actions;

import frc.robot.components.Manipulator;

/**
 * An interface for any action. Actions are called by using {@link AutoModeBase#runAction}.
 *
 * @author FRC 254 The Cheesy Poofs
 */
public class HatchAction implements Action {

  private Manipulator manny = Manipulator.getInstance();
  private boolean openHatch = false;

  public HatchAction(boolean openHatch) {
    this.openHatch = openHatch;
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void update() {
    // no recurring operations on hatch
  }

  @Override
  public void done() {}

  @Override
  public void start() {
    manny.openHatchCollector(openHatch);
  }
}
