package frc.robot.auto.creators;

import frc.robot.auto.AutoModeBase;
import frc.robot.auto.modes.DoNothingMode;
import frc.robot.auto.modes.MotionTest;

/**
 * A creator for the {@link DoNothingMode}.
 *
 * @author FRC 1778 Chill Out
 */
public class MotionTestCreator implements AutoModeCreator {

  private MotionTest auto = new MotionTest(1);

  @Override
  public AutoModeBase getStateDependentAutoMode(int teamDriverStationLocation) {
    return auto;
  }
}
