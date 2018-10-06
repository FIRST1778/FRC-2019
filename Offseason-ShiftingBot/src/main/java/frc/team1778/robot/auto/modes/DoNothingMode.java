package frc.team1778.robot.auto.modes;

import frc.team1778.robot.auto.AutoModeBase;
import frc.team1778.robot.auto.AutoModeEndedException;
import frc.team1778.robot.auto.actions.PrintToNetworkTableAction;
import frc.team1778.robot.auto.actions.SeriesAction;
import frc.team1778.robot.auto.actions.WaitAction;
import frc.team1778.robot.components.Drive;
import java.util.Arrays;

public class DoNothingMode extends AutoModeBase {
  @Override
  protected void routine() throws AutoModeEndedException {
    runAction(
        new SeriesAction(
            Arrays.asList(
                new PrintToNetworkTableAction(
                    Drive.getinstance().debugTable, "Auto", "Doing Nothing"),
                new WaitAction(4),
                new PrintToNetworkTableAction(
                    Drive.getinstance().debugTable, "Auto", "Done Waiting"))));
    stop();
  }
}
