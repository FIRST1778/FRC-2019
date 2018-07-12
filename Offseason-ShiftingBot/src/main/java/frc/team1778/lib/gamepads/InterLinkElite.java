package frc.team1778.lib.gamepads;

/**
 * Output mapping constants for the InterLink Elite controller
 *
 * @author FRC 1778 Chill Out
 */
public class InterLinkElite {
  /** The left front switch, labelled "CH. 5 DUAL RATES" */
  public static final int LEFT_SWITCH = 2;
  /** The right top switch, labelled "CH. 7 SMOKE/RETRACT THR. HOLD" */
  public static final int RIGHT_SHOULDER_SWITCH = 1;
  /** The red reset button, labelled "RESET" */
  public static final int RESET_BUTTON = 3;
  /** The 3-position left top switch when pushed back, labelled "CH. 8 MODE IDLE UP" */
  public static final int LEFT_SHOULDER_DOWN_SWITCH = 4;
  /** The 3-position left top switch when pushed forward, labelled "CH. 8 MODE IDLE UP" */
  public static final int LEFT_SHOULDER_UP_SWITCH = 5;

  /**
   * Axis mappings for the joysticks
   *
   * @author FRC 1778 Chill Out
   */
  public class Axis {
    /** The left joystick x-axis */
    public static final int LEFT_X = 0;
    /** The left joystick y-axis */
    public static final int LEFT_Y = 1;
    /** The right joystick x-axis */
    public static final int RIGHT_X = 2;
    /** The right joystick y-axis */
    public static final int RIGHT_Y = 3;
    /** The right front dial, labelled "CH. 6 FLAPS GAIN" */
    public static final int TUNER = 5;
  }
}
