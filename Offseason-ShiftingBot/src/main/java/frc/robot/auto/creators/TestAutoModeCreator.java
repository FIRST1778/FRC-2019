package frc.robot.auto.creators;

import frc.robot.AutoFieldState;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.modes.TestAutoMode;

public class TestAutoModeCreator implements AutoModeCreator {
  private TestAutoMode leftSwitchLeftScale = new TestAutoMode(true, true);
  private TestAutoMode rightSwitchRightScale = new TestAutoMode(false, false);
  private TestAutoMode leftSwitchRightScale = new TestAutoMode(true, false);
  private TestAutoMode rightSwitchLeftScale = new TestAutoMode(false, true);

  @Override
  public AutoModeBase getStateDependentAutoMode(AutoFieldState fieldState) {
    if (fieldState.getOurSwitchSide() == AutoFieldState.Side.LEFT) {
      if (fieldState.getScaleSide() == AutoFieldState.Side.LEFT) {
        return leftSwitchLeftScale;
      } else {
        return leftSwitchRightScale;
      }
    } else {
      if (fieldState.getScaleSide() == AutoFieldState.Side.LEFT) {
        return rightSwitchLeftScale;
      } else {
        return rightSwitchRightScale;
      }
    }
  }
}
