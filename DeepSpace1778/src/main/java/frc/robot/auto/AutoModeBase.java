package frc.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import frc.lib.util.DebugLog;
import frc.robot.auto.actions.Action;

/**
 * An abstract class that is the basis of the robot's autonomous routines. This is implemented in
 * auto modes (which are routines that do actions).
 *
 * @author FRC 254 The Cheesy Poofs
 */
public abstract class AutoModeBase {

  protected double updateRate = 1.0 / 50.0;
  protected boolean active = false;

  protected abstract void routine() throws AutoModeEndedException;

  public void run() {
    active = true;

    try {
      routine();
    } catch (AutoModeEndedException e) {
      DriverStation.reportError("AutoMode ended early!", false);
      DebugLog.logThrowableCrash(e);
      return;
    }

    done();
  }

  public void done() {
    System.out.println("Auto mode done");
  }

  public void stop() {
    active = false;
  }

  public boolean isActive() {
    return active;
  }

  public boolean isActiveWithThrow() throws AutoModeEndedException {
    if (!isActive()) {
      throw new AutoModeEndedException();
    }

    return isActive();
  }

  public void runAction(Action action) throws AutoModeEndedException {
    isActiveWithThrow();
    action.start();

    while (isActiveWithThrow() && !action.isFinished()) {
      action.update();
      long waitTime = (long) (updateRate * 1000.0);

      try {
        Thread.sleep(waitTime);
      } catch (InterruptedException e) {
        DebugLog.logThrowableCrash(e);
      }
    }

    action.done();
  }
}
