package frc.robot.auto.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Executes a list of actions in series, executing the next as soon as the first is completed.
 *
 * @author FRC 254 The Cheesy Poofs
 */
public class SeriesAction implements Action {

  private Action currentAction;
  private final ArrayList<Action> remainingActions;

  public SeriesAction(List<Action> actions) {
    remainingActions = new ArrayList<>(actions);
    currentAction = null;
  }

  @Override
  public boolean isFinished() {
    return remainingActions.isEmpty() && currentAction == null;
  }

  @Override
  public void start() {}

  @Override
  public void update() {
    if (currentAction == null) {
      if (remainingActions.isEmpty()) {
        return;
      }

      currentAction = remainingActions.remove(0);
      currentAction.start();
    }

    currentAction.update();

    if (currentAction.isFinished()) {
      currentAction.done();
      currentAction = null;
    }
  }

  @Override
  public void done() {}
}
