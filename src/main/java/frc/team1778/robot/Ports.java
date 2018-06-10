package frc.team1778.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * Holds hardware variables as to which ports hardware is attached to.
 *
 * @author FRC 1778 Chill Out
 */
public class Ports {
  // Talons
  public static final int LEFT_FRONT_DRIVE_TALON_ID = 1;
  public static final int LEFT_FRONT_ROTATION_TALON_ID = 2;
  public static final int LEFT_REAR_DRIVE_TALON_ID = 3;
  public static final int LEFT_REAR_ROTATION_TALON_ID = 4;
  public static final int RIGHT_REAR_DRIVE_TALON_ID = 5;
  public static final int RIGHT_REAR_ROTATION_TALON_ID = 6;
  public static final int RIGHT_FRONT_DRIVE_TALON_ID = 7;
  public static final int RIGHT_FRONT_ROTATION_TALON_ID = 8;

  // Solenoids

  // Sensors
  public static final SPI.Port NAVX_SPI = SPI.Port.kMXP;
}
