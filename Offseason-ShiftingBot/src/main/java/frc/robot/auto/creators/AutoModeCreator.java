package frc.robot.auto.creators;

import frc.robot.auto.AutoFieldState;
import frc.robot.auto.AutoModeBase;

public interface AutoModeCreator {
  AutoModeBase getStateDependentAutoMode(AutoFieldState fieldState);
}
