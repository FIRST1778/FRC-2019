package frc.robot.auto.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Executes a list of actions in parallel, periodically updating each until they are all finished.
 *
 * @author FRC 254 The Cheesy Poofs
 */
public class ParallelAction implements Action {

  private final ArrayList<Action> actions;

  public ParallelAction(List<Action> actions) {
    this.actions = new ArrayList<>(actions);
  }

  @Override
  public boolean isFinished() {
    for (Action action : actions) {
      if (!action.isFinished()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void update() {
    for (Action action : actions) {
      action.update();
    }
  }

  @Override
  public void done() {
    for (Action action : actions) {
      action.done();
    }
  }

  @Override
  public void start() {
    for (Action action : actions) {
      action.start();
    }
  }
}
