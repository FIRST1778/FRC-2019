package frc.robot.auto.creators;

import frc.robot.AutoFieldState;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.modes.DoNothingMode;

public class DoNothingAutoModeCreator implements AutoModeCreator {
  private DoNothingMode auto = new DoNothingMode();

  @Override
  public AutoModeBase getStateDependentAutoMode(AutoFieldState fieldState) {
    return auto;
  }
}
