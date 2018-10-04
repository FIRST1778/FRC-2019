package frc.team1778.robot.auto.modes;

import frc.team1778.robot.auto.AutoModeBase;
import frc.team1778.robot.auto.AutoModeEndedException;

public class DoNothingMode extends AutoModeBase {
  @Override
  protected void routine() throws AutoModeEndedException {
    System.out.println("Doing nothing");
  }
}
