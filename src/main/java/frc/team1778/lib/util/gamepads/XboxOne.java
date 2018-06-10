package frc.team1778.lib.util.gamepads;

/**
 * Output mapping constants for the Xbox One gamepad
 *
 * @author FRC 1778 Chill Out
 */
public class XboxOne {
  /** The dpad 'axis', used with GetPOV() */
  public static final int DPAD = 0;
  /** The lower button of four, labelled "A" */
  public static final int A = 1;
  /** The right button of four, labelled "B" */
  public static final int B = 2;
  /** The left button of four, labelled "X" */
  public static final int X = 3;
  /** The upper button of four, labelled "Y" */
  public static final int Y = 4;
  /** The left bumper button */
  public static final int LEFT_BUMPER = 5;
  /** The right bumper button */
  public static final int RIGHT_BUMPER = 6;
  /** The back button */
  public static final int BACK = 7;
  /** The start button */
  public static final int START = 8;
  /** The left joystick button */
  public static final int LEFT_JOYSTICK_BUTTON = 9;
  /** The right joystick button */
  public static final int RIGHT_JOYSTICK_BUTTON = 10;

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
    /** The left trigger axis */
    public static final int LEFT_TRIGGER = 2;
    /** The right trigger axis */
    public static final int RIGHT_TRIGGER = 3;
    /** The right joystick x-axis */
    public static final int RIGHT_X = 4;
    /** The right joystick y-axis */
    public static final int RIGHT_Y = 5;
  }
}
