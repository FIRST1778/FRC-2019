package frc.robot.auto.actions;

import frc.robot.components.Elevator;
import frc.robot.components.Elevator.ControlState;
import frc.robot.components.Elevator.HeightSetPoints;
import frc.robot.components.Manipulator;

public class LiftToHeightAction implements Action {

  private Elevator elevator = Elevator.getInstance();
  private Manipulator manipulator = Manipulator.getInstance();

  private double encoderLiftTarget;
  private double manipulatorTarget;

  public LiftToHeightAction(HeightSetPoints height) {
    encoderLiftTarget = height.heightEncoderTicks;
  }

  public LiftToHeightAction(double heightInches) {
    encoderLiftTarget = elevator.getEncoderPositionFromHeight(heightInches);
  }

  @Override
  public boolean isFinished() {
    return elevator.isCloseToTarget(elevator.getEncoderPositionFromHeight(2.0));
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
