package frc.robot.auto.creators;

import frc.robot.auto.AutoModeBase;
import frc.robot.auto.modes.TestAutoMode;

/**
 * A creator for the {@link TestAutoMode}.
 *
 * @author FRC 1778 Chill Out
 */
public class TestAutoModeCreator implements AutoModeCreator {

  private TestAutoMode modePositionOne = new TestAutoMode(1);
  private TestAutoMode modePositionTwo = new TestAutoMode(2);
  private TestAutoMode modePositionThree = new TestAutoMode(3);

  @Override
  public AutoModeBase getStateDependentAutoMode(int teamDriverStationNumber) {
    switch (teamDriverStationNumber) {
      case 1:
        return modePositionOne;
      case 2:
        return modePositionTwo;
      case 3:
      default:
        return modePositionThree;
    }
  }
}
