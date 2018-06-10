package frc.team1778.lib.util;

/**
 * Implements something similar to "Cheesy Drive", courtesy of 254, and "Culver Drive", courtesy of
 * 33, making it "Freezy Drive".
 *
 * @author FRC 1778 Chill Out
 */
public class FreezyDrive {
  private static final double THROTTLE_DEADBAND = 0.02;
  private static final double WHEEL_DEADBAND = 0.02;

  private static final double HIGH_GEAR_WHEEL_NON_LINEARITY = 0.65;
  private static final double LOW_GEAR_WHEEL_NON_LINEARITY = 0.65;

  private static final double HIGH_NEGATIVE_INERTIA_SCALAR = 4.0;

  private static final double LOW_NEGATIVE_INERTIA_THRESHOLD = 0.65;
  private static final double LOW_NEGATIVE_TURN_SCALAR = 3.5;
  private static final double LOW_NEGATIVE_INERTIA_CLOSE_SCALAR = 4.0;
  private static final double LOW_NEGATIVE_INERTIA_FAR_SCALAR = 5.0;

  private static final double HIGH_GEAR_SENSITIVITY = 0.95;
  private static final double LOW_GEAR_SENSITIVITY = 1.3;

  private static final double QUICKSTOP_DEAD_BAND = 0.2;
  private static final double QUICKSTOP_WEIGHT = 0.1;
  private static final double QUICKSTOP_SCALAR = 5.0;

  private double oldWheel = 0.0;
  private double quickstopAccumulator = 0.0;
  private double negativeInertiaAccumulator = 0.0;
}
