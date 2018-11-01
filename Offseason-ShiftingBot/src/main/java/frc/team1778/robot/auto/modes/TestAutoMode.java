package frc.team1778.robot.auto.modes;

import frc.team1778.robot.auto.AutoModeBase;
import frc.team1778.robot.auto.AutoModeEndedException;
import frc.team1778.robot.auto.actions.PrintLoopTimeAction;

public class TestAutoMode extends AutoModeBase {
  boolean switchOnLeft;
  boolean scaleOnLeft;

  public TestAutoMode(boolean isSwitchOnLeft, boolean isScaleOnLeft) {
    switchOnLeft = isSwitchOnLeft;
    scaleOnLeft = isScaleOnLeft;
  }

  @Override
  protected void routine() throws AutoModeEndedException {
    runAction(new PrintLoopTimeAction());
  }
}
