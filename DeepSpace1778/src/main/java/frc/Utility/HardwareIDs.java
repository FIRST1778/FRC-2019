package frc.Utility;

public class HardwareIDs {

  // Swerve motor controller IDs
  public static final int FRONT_LEFT_DRIVE_TALON_ID = 7;
  public static final int FRONT_RIGHT_DRIVE_TALON_ID = 3;
  public static final int BACK_LEFT_DRIVE_TALON_ID = 8;
  public static final int BACK_RIGHT_DRIVE_TALON_ID = 4;

  public static final int FRONT_LEFT_ROTATE_TALON_ID = 5;
  public static final int FRONT_RIGHT_ROTATE_TALON_ID = 9;
  public static final int BACK_LEFT_ROTATE_TALON_ID = 6;
  public static final int BACK_RIGHT_ROTATE_TALON_ID = 10;

  // lift motor controller ID
  public static final int LIFT_TALON_ID = 21;
  public static final int CLIMBER_TALON_ID = 31;

  // Spark IDs
  public static final int LEFT_COLLECTOR_PWM_ID = 0;
	public static final int RIGHT_COLLECTOR_PWM_ID = 1;

  // swerve and co-driver controller joystick IDs
  public static final int CONTROL_GAMEPAD_ID = 0;
  public static final int CO_DRIVER_GAMEPAD_ID = 1;

  // deadzones
  public static final double DRIVER_JOYSTICK_DEADZONE = 0.03;
  public static final double CO_DRIVER_JOYSTICK_DEADZONE = 0.1;

  // driver control joysticks
  public static final int DRIVER_LEFT_X_AXIS = 0;
  public static final int DRIVER_LEFT_Y_AXIS = 1;
  public static final int DRIVER_RIGHT_X_AXIS = 2;
  public static final int DRIVER_RIGHT_Y_AXIS = 3;
  public static final int DRIVER_LEFT_SWITCH = 2;

  // logitech gamepad controls - co-driver only
  public static final int CO_DRIVER_LEFT_X_AXIS = 0;
  public static final int CO_DRIVER_LEFT_Y_AXIS = 1;
  public static final int CO_DRIVER_RIGHT_X_AXIS = 4;
  public static final int CO_DRIVER_RIGHT_Y_AXIS = 5;

  public static final int CO_DRIVER_LEFT_TRIGGER_AXIS = 2;
  public static final int CO_DRIVER_RIGHT_TRIGGER_AXIS = 3;
  public static final int CO_DRIVER_LEFT_BUMPER = 5;
  public static final int CO_DRIVER_RIGHT_BUMPER = 6;

  public static final int CO_DRIVER_A_BUTTON = 1;
  public static final int CO_DRIVER_B_BUTTON = 2;
  public static final int CO_DRIVER_X_BUTTON = 3;
  public static final int CO_DRIVER_Y_BUTTON = 4;

  // encoder variables
  public static final int ENCODER_PULSES_PER_REV = 256 * 4; // 63R  - on the competition bot motors
  public static final double WHEEL_DIAMETER_INCHES = 5.9;
  public static final double INCHES_PER_REV =
      (WHEEL_DIAMETER_INCHES * 3.14159); // 5.9-in diameter wheel (worn)

  public static final double INCHES_PER_ENCODER_PULSE = INCHES_PER_REV / ENCODER_PULSES_PER_REV;
  public static final double RPM_TO_UNIT_PER_100MS = ENCODER_PULSES_PER_REV / (60 * 10);
}
