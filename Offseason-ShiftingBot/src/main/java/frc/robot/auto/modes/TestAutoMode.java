package frc.robot.auto.modes;

import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.actions.PrintLoopTimeAction;

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
