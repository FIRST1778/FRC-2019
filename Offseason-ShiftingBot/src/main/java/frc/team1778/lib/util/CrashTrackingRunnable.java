package frc.team1778.lib.util;

/** Runnable class with reports all uncaught throws to CrashTracker */
public abstract class CrashTrackingRunnable implements Runnable {

  @Override
  public final void run() {
    try {
      runCrashTracked();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  public abstract void runCrashTracked();
}
