package frc.team1778.robot.auto.modes;

import frc.team1778.robot.auto.AutoModeBase;
import frc.team1778.robot.auto.AutoModeEndedException;

public class TestAutoMode extends AutoModeBase {
  boolean switchOnLeft;
  boolean scaleOnLeft;

  public TestAutoMode(boolean isSwitchOnLeft, boolean isScaleOnLeft) {
    switchOnLeft = isSwitchOnLeft;
    scaleOnLeft = isScaleOnLeft;
  }

  @Override
  protected void routine() throws AutoModeEndedException {
    System.out.println(
        "Running Auto for "
            + (switchOnLeft ? "left" : "right")
            + " switch, and "
            + (scaleOnLeft ? "left" : "right")
            + " scale permutation");
  }
}
