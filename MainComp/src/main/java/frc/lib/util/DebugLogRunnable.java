package frc.lib.util;

/**
 * Runnable class which reports all uncaught throwable exceptions to DebugLog.
 *
 * @author FRC 1778 Chill Out
 */
public abstract class DebugLogRunnable implements Runnable {

  @Override
  public final void run() {
    try {
      runCrashTracked();
    } catch (Throwable t) {
      DebugLog.logThrowableCrash(t);
      throw t;
    }
  }

  public abstract void runCrashTracked();
}
