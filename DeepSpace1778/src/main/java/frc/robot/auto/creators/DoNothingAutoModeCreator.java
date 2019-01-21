package frc.robot.auto.creators;

import frc.robot.auto.AutoModeBase;
import frc.robot.auto.modes.DoNothingMode;

/**
 * A creator for the {@link DoNothingMode}.
 *
 * @author FRC 1778 Chill Out
 */
public class DoNothingAutoModeCreator implements AutoModeCreator {

  private DoNothingMode auto = new DoNothingMode();

  @Override
  public AutoModeBase getStateDependentAutoMode(int teamDriverStationLocation) {
    return auto;
  }
}
