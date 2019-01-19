package frc.StateMachine;

import frc.ChillySwerve.ChillySwerve;

public class TurnAction extends Action {

  private double speedToTurn = 0.0;

  public TurnAction(double rotateSpeed) {
    this.name = "<Turn Action>";
    this.speedToTurn = rotateSpeed;

    ChillySwerve.initialize();
  }

  public TurnAction(String name, double rotateSpeed) {
    this.name = name;
    this.speedToTurn = rotateSpeed;

    ChillySwerve.initialize();
  }

  // action entry
  public void initialize() {

    // command swerve to rotate
    ChillySwerve.rotate(speedToTurn);

    super.initialize();
  }

  // called periodically
  public void process() {

    super.process();
  }

  // action cleanup and exit
  public void cleanup() {
    // do some drivey cleanup

    ChillySwerve.stopDrive();

    // cleanup base class
    super.cleanup();
  }
}
