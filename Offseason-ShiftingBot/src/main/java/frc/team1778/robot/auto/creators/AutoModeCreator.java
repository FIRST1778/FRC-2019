package frc.team1778.robot.auto.creators;

import frc.team1778.robot.AutoFieldState;
import frc.team1778.robot.auto.AutoModeBase;

public interface AutoModeCreator {
  AutoModeBase getStateDependentAutoMode(AutoFieldState fieldState);
}
