package frc.team4077.robot.common;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * This creates and sets most of the convenient settings for TalonSRX. This includes feedback
 * devices, voltage limits, control modes, inversion, etc.
 *
 * <p>This is adapted from {@see <a href=
 * "https://github.com/Team254/FRC-2017-Public/blob/master/src/com/team254/lib/util/drivers/CANTalonFactory.java">254's
 * Code</a>}, but is updated to work with
 *
 * @author FRC 4077 MASH, Hillel Coates
 */
public class TalonSRXFactory {

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

  // private static final Configuration DEFAULT_CONFIGURATION = new
  // Configuration();
  // private static final Configuration SLAVE_CONFIGURATION = new Configuration();

  static {
    // SLAVE_CONFIGURATION.ADASDASDASD = ASDASD;
  }

  /**
   * Create a basic TalonSRX, this just sets the ID and allows for reversing the motor direction.
   *
   * @param id This is the CAN ID in which the TalonSRX is configured with.
   * @param isReversed When true, all motor signals will be reversed, e.g. 1 will actually send -1.
   */
  public static TalonSRX createDefaultTalonSRX(int id) {
    return new TalonSRX(id);
  }

  /**
   * Create a slave TalonSRX. This will follow a master that is defined. direction.
   *
   * @param id This is the CAN ID in which the TalonSRX is configured with.
   * @param masterId This is the CAN ID of the TalonSRX that this TalonSRX is supposed to follow.
   */
  public static TalonSRX createPermanentSlaveTalonSRX(int id, int masterId) {
    TalonSRX talon = new TalonSRX(id);
    // talon.setInverted(isReversed);
    return talon;
  }

  /**
   * Create a full fledged TalonSRX, this sets everything that will be used.
   *
   * @param id This is the CAN ID in which the TalonSRX is configured with.
   * @param config This is the Configuration that stores all of the settings of the Talon.
   */
  /*
   * public static TalonSRX createTalon(int id, Configuration config) { TalonSRX
   * talon = new TalonSRX(id);
   * talon.changeControlMode(TalonSRX.TalonControlMode.Voltage);
   * talon.changeMotionControlFramePeriod(config.MOTION_CONTROL_FRAME_PERIOD_MS);
   * talon.clearIAccum(); talon.ClearIaccum();
   * talon.clearMotionProfileHasUnderrun();
   * talon.clearMotionProfileTrajectories(); talon.clearStickyFaults();
   * talon.ConfigFwdLimitSwitchNormallyOpen(config.LIMIT_SWITCH_NORMALLY_OPEN);
   * talon.configMaxOutputVoltage(config.MAX_OUTPUT_VOLTAGE);
   * talon.configNominalOutputVoltage(config.NOMINAL_VOLTAGE,
   * -config.NOMINAL_VOLTAGE); talon.configPeakOutputVoltage(config.PEAK_VOLTAGE,
   * -config.PEAK_VOLTAGE);
   * talon.ConfigRevLimitSwitchNormallyOpen(config.LIMIT_SWITCH_NORMALLY_OPEN);
   * talon.enableBrakeMode(config.ENABLE_BRAKE);
   * talon.EnableCurrentLimit(config.ENABLE_CURRENT_LIMIT);
   * talon.enableForwardSoftLimit(config.ENABLE_SOFT_LIMIT);
   * talon.enableLimitSwitch(config.ENABLE_LIMIT_SWITCH,
   * config.ENABLE_LIMIT_SWITCH);
   * talon.enableReverseSoftLimit(config.ENABLE_SOFT_LIMIT);
   * talon.enableZeroSensorPositionOnForwardLimit(false);
   * talon.enableZeroSensorPositionOnIndex(false, false);
   * talon.enableZeroSensorPositionOnReverseLimit(false);
   * talon.reverseOutput(false); talon.reverseSensor(false);
   * talon.setAnalogPosition(0); talon.setCurrentLimit(config.CURRENT_LIMIT);
   * talon.setExpiration(config.EXPIRATION_TIMEOUT_SECONDS);
   * talon.setForwardSoftLimit(config.FORWARD_SOFT_LIMIT);
   * talon.setInverted(config.INVERTED);
   * talon.setNominalClosedLoopVoltage(config.NOMINAL_CLOSED_LOOP_VOLTAGE);
   * talon.setPosition(0); talon.setProfile(0); talon.setPulseWidthPosition(0);
   * talon.setReverseSoftLimit(config.REVERSE_SOFT_LIMIT);
   * talon.setSafetyEnabled(config.SAFETY_ENABLED);
   * talon.SetVelocityMeasurementPeriod(config.VELOCITY_MEASUREMENT_PERIOD);
   * talon.SetVelocityMeasurementWindow(config.
   * VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW);
   * talon.setVoltageCompensationRampRate(config.VOLTAGE_COMPENSATION_RAMP_RATE);
   * talon.setVoltageRampRate(config.VOLTAGE_RAMP_RATE);
   *
   * talon.setStatusFrameRateMs(TalonSRX.StatusFrameRate.General,
   * config.GENERAL_STATUS_FRAME_RATE_MS);
   * talon.setStatusFrameRateMs(TalonSRX.StatusFrameRate.Feedback,
   * config.FEEDBACK_STATUS_FRAME_RATE_MS);
   * talon.setStatusFrameRateMs(TalonSRX.StatusFrameRate.QuadEncoder,
   * config.QUAD_ENCODER_STATUS_FRAME_RATE_MS);
   * talon.setStatusFrameRateMs(TalonSRX.StatusFrameRate.AnalogTempVbat,
   * config.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS);
   * talon.setStatusFrameRateMs(TalonSRX.StatusFrameRate.PulseWidth,
   * config.PULSE_WIDTH_STATUS_FRAME_RATE_MS);
   *
   * return talon; }
   */
}
