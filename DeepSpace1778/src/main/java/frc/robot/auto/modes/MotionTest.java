package frc.robot.auto.modes;

import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.actions.MoveCardinalDirectionAction;

/**
 * A simple auto mode to test that auto selection works.
 *
 * @author FRC 1778 Chill Out
 */
public class MotionTest extends AutoModeBase {

  private int teamSelectedPosition;

  public MotionTest(int driverStationPosition) {
    teamSelectedPosition = driverStationPosition;
  }

  @Override
  protected void routine() throws AutoModeEndedException {
    runAction(new MoveCardinalDirectionAction(0.0, 24.0, 0.5));
    runAction(new MoveCardinalDirectionAction(90.0, 24.0, 0.5));
  }
}
