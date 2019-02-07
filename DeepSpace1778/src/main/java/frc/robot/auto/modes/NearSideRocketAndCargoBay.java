package frc.robot.auto.modes;

import frc.robot.AutoModeSelector.StartingPosition;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.AutoPaths;
import frc.robot.auto.actions.FollowPathAction;
import frc.robot.auto.actions.RunOnceAction;
import frc.robot.components.SwerveDrive;

/**
 * An auto mode to score two hatch panels on the near side of the rocket.
 *
 * @author FRC 1778 Chill Out
 */
public class NearSideRocketAndCargoBay extends AutoModeBase {

  StartingPosition startingPosition;
  int cargoBay;

  public NearSideRocketAndCargoBay(StartingPosition position, int cargoBayNumber) {
    startingPosition = position;
    cargoBay = cargoBayNumber;
  }

  @Override
  public void routine() throws AutoModeEndedException {
    runAction(
        new RunOnceAction() {
          @Override
          public void runOnce() {
            SwerveDrive.getInstance().zeroSensors();
          }
        });
    switch (startingPosition) {
      case LEFT:
        runAction(new FollowPathAction(AutoPaths.START_LEFT_TO_LEFT_ROCKET_NEAR_SIDE));
        runAction(new FollowPathAction(AutoPaths.LEFT_ROCKET_NEAR_SIDE_TO_LEFT_FEEDER_STATION));
        switch (cargoBay) {
          default:
          case 1:
            runAction(new FollowPathAction(AutoPaths.LEFT_FEEDER_STATION_TO_LEFT_CARGO_BAY_NEAR));
            break;
          case 2:
            runAction(new FollowPathAction(AutoPaths.LEFT_FEEDER_STATION_TO_LEFT_CARGO_BAY_CENTER));
            break;
          case 3:
            runAction(new FollowPathAction(AutoPaths.LEFT_FEEDER_STATION_TO_LEFT_CARGO_BAY_FAR));
            break;
        }
        break;
      default:
      case RIGHT:
        runAction(new FollowPathAction(AutoPaths.START_RIGHT_TO_RIGHT_ROCKET_NEAR_SIDE));
        runAction(new FollowPathAction(AutoPaths.RIGHT_ROCKET_NEAR_SIDE_TO_RIGHT_FEEDER_STATION));
        switch (cargoBay) {
          default:
          case 1:
            runAction(new FollowPathAction(AutoPaths.RIGHT_FEEDER_STATION_TO_RIGHT_CARGO_BAY_NEAR));
            break;
          case 2:
            runAction(
                new FollowPathAction(AutoPaths.RIGHT_FEEDER_STATION_TO_RIGHT_CARGO_BAY_CENTER));
            break;
          case 3:
            runAction(new FollowPathAction(AutoPaths.RIGHT_FEEDER_STATION_TO_RIGHT_CARGO_BAY_FAR));
            break;
        }
        break;
    }
  }
}
