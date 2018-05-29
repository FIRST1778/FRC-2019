package frc.team1778.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * Holds hardware variables as to which ports hardware is attached to.
 *
 * @author FRC 1778 Chill Out, Hillel Coates
 */
public class Ports {
  // Talons
  public static final int LEFT_MASTER_TALON_ID = 1;
  public static final int RIGHT_MASTER_TALON_ID = 3;
  public static final int LEFT_SLAVE_TALON_ID = 2;
  public static final int RIGHT_SLAVE_TALON_ID = 4;

  // Solenoids

  // Sensors
  public static final SPI.Port NAVX_SPI = SPI.Port.kMXP;
}
