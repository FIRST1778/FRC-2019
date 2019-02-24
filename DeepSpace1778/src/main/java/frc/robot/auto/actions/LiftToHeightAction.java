package frc.robot.auto.actions;

import frc.robot.components.Elevator;
import frc.robot.components.Elevator.ControlState;
import frc.robot.components.Elevator.HeightSetPoints;

public class LiftToHeightAction implements Action {

  private Elevator elevator = Elevator.getInstance();

  private double encoderLiftTarget;

  public LiftToHeightAction(HeightSetPoints height) {
    encoderLiftTarget = height.heightEncoderTicks;
  }

  public LiftToHeightAction(double heightInches) {
    encoderLiftTarget = elevator.getEncoderPositionFromHeight(heightInches);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(encoderLiftTarget - elevator.getCurrentHeightEncoder()) < 50;
  }

  @Override
  public void update() {}

  @Override
  public void done() {}

  @Override
  public void start() {
    elevator.setControlType(ControlState.MOTION_MAGIC);
    elevator.setTarget(encoderLiftTarget);
  }
}
