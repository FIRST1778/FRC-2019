package frc.team1778.lib.gamepads;

/**
 * Output mapping constants for the Logitech Dual Action gamepad
 *
 * @author FRC 1778 Chill Out
 */
public class LogitechDualAction {
  /** The dPad 'axis', used with GetPOV() */
  public static final int DPAD = 0;
  /** The left button of four, labelled "1" */
  public static final int B1 = 1;
  /** The lower button of four, labelled "2" */
  public static final int B2 = 2;
  /** The right button of four, labelled "3" */
  public static final int B3 = 3;
  /** The upper button of four, labelled "4" */
  public static final int B4 = 4;
  /** The left bumper button */
  public static final int LEFT_BUMPER = 5;
  /** The right bumper button */
  public static final int RIGHT_BUMPER = 6;
  /** The left trigger */
  public static final int LEFT_TRIGGER = 7;
  /** The right trigger */
  public static final int RIGHT_TRIGGER = 8;
  /** The left button in the middle, labeled as "9" */
  public static final int B9 = 9;
  /** The right button in the middle, labeled as "10" */
  public static final int B10 = 10;
  /** The left joystick button */
  public static final int LEFT_JOYSTICK_BUTTON = 11;
  /** The right joystick button */
  public static final int RIGHT_JOYSTICK_BUTTON = 12;

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
  }
}
