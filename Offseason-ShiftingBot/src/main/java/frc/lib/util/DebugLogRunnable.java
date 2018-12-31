package frc.lib.util;

/** Runnable class with reports all uncaught throws to DebugLog. */
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
