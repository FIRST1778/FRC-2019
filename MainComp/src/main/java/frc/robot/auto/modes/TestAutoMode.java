package frc.robot.auto.modes;

import frc.lib.util.DebugLog;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.actions.RunOnceAction;

/**
 * A simple auto mode to test that auto selection works.
 *
 * @author FRC 1778 Chill Out
 */
public class TestAutoMode extends AutoModeBase {
  private int teamSelectedPosition;

  public TestAutoMode(int driverStationPosition) {
    teamSelectedPosition = driverStationPosition;
  }

  @Override
  protected void routine() throws AutoModeEndedException {
    runAction(
        new RunOnceAction() {
          @Override
          public void runOnce() {
            DebugLog.logNote("Running auto for " + teamSelectedPosition + " position");
          }
        });
  }
}
