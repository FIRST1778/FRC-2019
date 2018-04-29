package frc.team4077.robot.common;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * This creates and sets most of the convenient settings for TalonSRX. This
 * includes feedback devices, voltage limits, control modes, inversion, etc.
 * 
 * @author FRC 4077 MASH, Hillel Coates
 */
public class TalonSRXBuilder {

	public static class Configuration {
		public boolean LIMIT_SWITCH_NORMALLY_OPEN = true;
		public double MAX_OUTPUT_VOLTAGE = 12;
		public double NOMINAL_VOLTAGE = 0;
		public double PEAK_VOLTAGE = 12;
		public boolean ENABLE_BRAKE = false;
		public boolean ENABLE_CURRENT_LIMIT = false;
		public boolean ENABLE_SOFT_LIMIT = false;
		public boolean ENABLE_LIMIT_SWITCH = false;
		public int CURRENT_LIMIT = 0;
		public double FORWARD_SOFT_LIMIT = 0;
		public boolean INVERTED = false;
		public double NOMINAL_CLOSED_LOOP_VOLTAGE = 12;
		public double REVERSE_SOFT_LIMIT = 0;
		public boolean SAFETY_ENABLED = false;

		public int CONTROL_FRAME_PERIOD_MS = 5;
		public int MOTION_CONTROL_FRAME_PERIOD_MS = 100;
		public int GENERAL_STATUS_FRAME_RATE_MS = 5;
		public int FEEDBACK_STATUS_FRAME_RATE_MS = 100;
		public int QUAD_ENCODER_STATUS_FRAME_RATE_MS = 100;
		public int ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 100;
		public int PULSE_WIDTH_STATUS_FRAME_RATE_MS = 100;
		public int VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW = 64;

		public double VOLTAGE_COMPENSATION_RAMP_RATE = 0;
		public double VOLTAGE_RAMP_RATE = 0;
	}

	/**
	 * Create a basic TalonSRX, this just sets the ID and allows for reversing the motor
	 * direction.
	 * 
	 * @param id This is the CAN ID in which the TalonSRX is configured with.
	 * @param isReversed When true, all motor signals will be reversed, e.g. 1 will
	 * actually send -1.
	 */
	public static TalonSRX createBasicTalon(int id, boolean isReversed) {
		TalonSRX talon = new TalonSRX(id);
		talon.setInverted(isReversed);
		return talon;
	}

	/**
	 * Create a slave TalonSRX. This will follow a master that is defined.
	 * direction.
	 * 
	 * @param id This is the CAN ID in which the TalonSRX is configured with.
	 * @param isReversed When true, all motor signals will be reversed, e.g. 1 will
	 * actually send -1.
	 */
	public static TalonSRX createSlaveCanTalon(int id, boolean isReversed) {
		TalonSRX talon = new TalonSRX(id);
		talon.setInverted(isReversed);
		return talon;
	}

	/**
	 * Create a full fledged TalonSRX, this sets everything that will be used in most cases:
	 * 
	 * 
	 * @param id This is the CAN ID in which the TalonSRX is configured with.
	 * @param isReversed When true, all motor signals will be reversed, e.g. 1 will
	 * actually send -1.
	 */
	public static TalonSRX createFullTalon(int id, boolean isReversed) {
		TalonSRX talon = new TalonSRX(id);
		talon.setInverted(isReversed);
		return talon;
	}
}