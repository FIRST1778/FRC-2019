package frc.robot.auto.creators;

import frc.robot.AutoModeSelector.StartingPosition;
import frc.robot.auto.AutoModeBase;

/**
 * An interface for all other creators. This enforces that any creator is able to return an auto
 * mode based on the position of the driver station.
 *
 * @author FRC 1778 Chill Out
 */
public interface AutoModeCreator {
  AutoModeBase getStateDependentAutoMode(
      int teamDriverStationLocation, StartingPosition startingPosition);
}
