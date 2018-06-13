package frc.team1778.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * Holds hardware variables as to which ports hardware is attached to.
 *
 * @author FRC 1778 Chill Out
 */
public class Ports {
  // Talons
  public static final int LEFT_DRIVE_MASTER_ID = 1;
  public static final int LEFT_DRIVE_SLAVE_ID = 3;
  public static final int RIGHT_DRIVE_MASTER_ID = 2;
  public static final int RIGHT_DRIVE_SLAVE_ID = 4;

  // Solenoids
  public static final int LEFT_DRIVE_SHIFTER = 0;
  public static final int RIGHT_DRIVE_SHIFTER = 1;

  // Sensors
  public static final SPI.Port NAVX_SPI = SPI.Port.kMXP;
}
