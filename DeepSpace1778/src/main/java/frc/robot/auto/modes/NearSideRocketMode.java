package frc.robot.auto.modes;

import frc.robot.AutoModeSelector.StartingPosition;
import frc.robot.Robot;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.AutoModeEndedException;
import frc.robot.auto.AutoPaths;
import frc.robot.auto.actions.FollowPathAction;
import frc.robot.auto.actions.LiftToHeightAction;
import frc.robot.auto.actions.ParallelAction;
import frc.robot.auto.actions.RunOnceAction;
import frc.robot.auto.actions.SeriesAction;
import frc.robot.auto.actions.WaitAction;
import frc.robot.components.Elevator.HeightSetPoints;
import frc.robot.components.SwerveDrive;
import java.util.List;

/**
 * An auto mode to score two hatch panels on the near side of the rocket.
 *
 * @author FRC 1778 Chill Out
 */
public class NearSideRocketMode extends AutoModeBase {

  StartingPosition startingPosition;

  public NearSideRocketMode(StartingPosition position) {
    startingPosition = position;
  }

  @Override
  public void routine() throws AutoModeEndedException {
    runAction(
        new RunOnceAction() {
          @Override
          public void runOnce() {
            SwerveDrive.getInstance().zeroSensors();
            Robot.limelightTable.getEntry("camMode").setDouble(1.0);
            Robot.limelightTable.getEntry("ledMode").setDouble(1.0);
          }
        });
    switch (startingPosition) {
      case LEFT:
        runAction(
            new ParallelAction(
                List.of(
                    new FollowPathAction(AutoPaths.START_LEFT_TO_LEFT_ROCKET_NEAR_SIDE),
                    new SeriesAction(
                        List.of(
                            new LiftToHeightAction(HeightSetPoints.CARGO_PICKUP),
                            new WaitAction(
                                AutoPaths.START_LEFT_TO_LEFT_ROCKET_NEAR_SIDE.getDuration() - 1.0),
                            new LiftToHeightAction(HeightSetPoints.CARGO_LOW))))));
        // runAction(new AlignWithTargetAction(28.77));
        break;
      default:
      case RIGHT:
        runAction(
            new ParallelAction(
                List.of(
                    new FollowPathAction(AutoPaths.START_RIGHT_TO_RIGHT_ROCKET_NEAR_SIDE),
                    new SeriesAction(
                        List.of(
                            new LiftToHeightAction(HeightSetPoints.CARGO_PICKUP),
                            new WaitAction(
                                AutoPaths.START_RIGHT_TO_RIGHT_ROCKET_NEAR_SIDE.getDuration()
                                    - 1.0),
                            new LiftToHeightAction(HeightSetPoints.CARGO_LOW))))));
        // runAction(new AlignWithTargetAction(331.23));
        break;
    }
  }
}
