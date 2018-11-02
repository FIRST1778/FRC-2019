package frc.team1778.robot.auto;

import frc.team1778.lib.util.CrashTrackingRunnable;

/** This class selects, runs, and stops (if necessary) a specified autonomous mode. */
public class AutoModeExecutor {
  private AutoModeBase autoMode;
  private Thread autoThread = null;

  /**
   * Selects the auto mode to be run.
   *
   * @param newAutoMode the mode to be run.
   */
  public void setAutoMode(AutoModeBase newAutoMode) {
    autoMode = newAutoMode;
    autoThread =
        new Thread(
            new CrashTrackingRunnable() {
              @Override
              public void runCrashTracked() {
                if (autoMode != null) {
                  autoMode.run();
                }
              }
            });
  }

  /** Starts the auto mode's thread. */
  public void start() {
    if (autoThread != null) {
      autoThread.start();
    }
  }

  /** Stops the auto mode's thread. */
  public void stop() {
    if (autoMode != null) {
      autoMode.stop();
    }

    autoThread = null;
  }

  public AutoModeBase getAutoMode() {
    return autoMode;
  }
}
