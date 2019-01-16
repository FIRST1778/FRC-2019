package frc.robot.auto.actions;

import frc.robot.auto.AutoModeBase;

/**
 * An interface for any action. Actions are called by using {@link AutoModeBase#runAction}.
 *
 * @author FRC 254 The Cheesy Poofs
 */
public interface Action {
  boolean isFinished();

  void update();

  void done();

  void start();
}
