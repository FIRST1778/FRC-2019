package frc.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * Holds hardware variables as to which ports hardware is attached to.
 *
 * @author FRC 1778 Chill Out
 */
public class Ports {
  // Talons
  public static final int LEFT_FRONT_ID = 7;
  public static final int RIGHT_FRONT_ID = 3;
  public static final int LEFT_BACK_ID = 8;
  public static final int RIGHT_BACK_ID = 4;

  public static final int LEFT_FRONT_TURN_ID = 5;
  public static final int RIGHT_FRONT_TURN_ID = 9;
  public static final int LEFT_BACK_TURN_ID = 6;
  public static final int RIGHT_BACK_TURN_ID = 10;

  // Sensors
  public static final SPI.Port NAVX_SPI = SPI.Port.kMXP;
}
