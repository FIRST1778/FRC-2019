package frc.robot.auto;

import frc.lib.util.DebugLogRunnable;

/**
 * Executes a chosen auto mode. By running an auto mode through this executor, it ensures that any
 * errors will be logged and can be safely terminated if need be.
 *
 * @author FRC 1778 Chill Out
 */
public class AutoModeExecutor {
  private AutoModeBase autoMode;
  private Thread autoThread = null;

  public void setAutoMode(AutoModeBase newAutoMode) {
    autoMode = newAutoMode;
    DebugLogRunnable runnableAutoMode =
        new DebugLogRunnable() {
          @Override
          public void runCrashTracked() {
            if (autoMode != null) {
              autoMode.run();
            }
          }
        };

    autoThread = new Thread(runnableAutoMode);
  }

  public void start() {
    if (autoThread != null) {
      autoThread.start();
    }
  }

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
