package frc.robot.auto.actions;

/**
 * Abstract class template for an action that is only run for a single loop. This immediately
 * finishes.
 *
 * @author FRC 254 The Cheesy Poofs
 */
public abstract class RunOnceAction implements Action {
  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void update() {}

  @Override
  public void done() {}

  @Override
  public void start() {
    runOnce();
  }

  public abstract void runOnce();
}
