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

  public static final int ELEVATOR_MASTER_ID = 21;
  public static final int ELEVATOR_SLAVE_ID = 22;

  public static final int MANIPULATOR_PIVOT_ID = 31;
  public static final int CARGO_COLLECTOR_ID = 33;
  public static final int HATCH_PANEL_PICKUP_ID = 34;

  public static final int CLIMBER_PISTON_ID = 41;
  public static final int CLIMBER_ROLLER_ID = 42;

  // Sensors
  public static final SPI.Port NAVX_SPI = SPI.Port.kMXP;
}
