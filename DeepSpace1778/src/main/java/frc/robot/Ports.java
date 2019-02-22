package frc.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * Holds hardware variables as to which ports hardware is attached to.
 *
 * @author FRC 1778 Chill Out
 */
public class Ports {

  // NOTE: Talons
  public static final int LEFT_FRONT_ID = Constants.USING_COMPETITION_ROBOT ? 2 : 7;
  public static final int RIGHT_FRONT_ID = Constants.USING_COMPETITION_ROBOT ? 3 : 3;
  public static final int LEFT_BACK_ID = Constants.USING_COMPETITION_ROBOT ? 4 : 8;
  public static final int RIGHT_BACK_ID = Constants.USING_COMPETITION_ROBOT ? 5 : 4;

  public static final int LEFT_FRONT_TURN_ID = Constants.USING_COMPETITION_ROBOT ? 6 : 5;
  public static final int RIGHT_FRONT_TURN_ID = Constants.USING_COMPETITION_ROBOT ? 7 : 9;
  public static final int LEFT_BACK_TURN_ID = Constants.USING_COMPETITION_ROBOT ? 8 : 6;
  public static final int RIGHT_BACK_TURN_ID = Constants.USING_COMPETITION_ROBOT ? 9 : 10;

  public static final int ELEVATOR_MASTER_ID = 10;
  public static final int ELEVATOR_SLAVE_ID = 11;

  public static final int ARTICULATOR_ID = 20;
  public static final int CARGO_COLLECTOR_ID = 21;
  public static final int HATCH_PANEL_PICKUP_ID = 22;

  public static final int CLIMBER_MASTER_ID = 30;
  public static final int CLIMBER_SLAVE_ID = 31;

  public static final int UNUSED_SPARE_ID = 40;

  // NOTE: Sensors
  public static final SPI.Port NAVX_SPI = SPI.Port.kMXP;
}
