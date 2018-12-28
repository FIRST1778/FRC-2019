package frc.robot.auto.actions;

/**
 * Action to wait for a given amount of time To use this Action, call runAction(new
 * WaitAction(your_time)).
 */
public class PrintLoopTimeAction implements Action {
  boolean isFinished = false;
  long lastTime;

  @Override
  public boolean isFinished() {
    return isFinished;
  }

  @Override
  public void update() {
    lastTime = System.currentTimeMillis();
    isFinished = true;
  }

  @Override
  public void done() {
    System.out.println("Loop Time: " + (System.currentTimeMillis() - lastTime));
  }

  @Override
  public void start() {}
}
