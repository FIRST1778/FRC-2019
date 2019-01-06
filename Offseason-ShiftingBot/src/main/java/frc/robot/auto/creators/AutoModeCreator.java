package frc.robot.auto.creators;

import frc.robot.AutoFieldState;
import frc.robot.auto.AutoModeBase;

public interface AutoModeCreator {
  AutoModeBase getStateDependentAutoMode(AutoFieldState fieldState);
}
