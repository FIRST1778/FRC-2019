package frc.team1778.lib.util.driver;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * This creates and sets most of the convenient settings for TalonSRX. This includes feedback
 * devices, voltage limits, control modes, inversion, etc.
 *
 * <p>This is adapted from 254's code, but is updated to work with the newer TalonSRX API.
 *
 * @author FRC 1778 Chill Out
 * @see <a
 *     href="https://github.com/Team254/FRC-2017-Public/blob/master/src/com/team254/lib/util/drivers/LazyCANTalon.java">254's
 *     Code</a>
 */
public class TalonSRXFactory {

  /**
   * Contains the configuration parameters for the TalonSRX.
   *
   * @author FRC 1778 Chill Out
   */
  public static class Configuration {
    public int TIMEOUT_IN_MS = 10;
    public int PROFILE_SLOT_ID = 0;

    public FeedbackDevice FEEDBACK_DEVICE = FeedbackDevice.None;
    public StatusFrameEnhanced STATUS_FRAME = StatusFrameEnhanced.Status_1_General;
    public int STATUS_FRAME_PERIOD = 10;
    public VelocityMeasPeriod VELOCITY_MEASUREMENT_PERIOD = VelocityMeasPeriod.Period_10Ms;
    public int VELOCITY_MEASUREMENT_WINDOW = 32;
    public double FORWARD_NOMINAL_OUTPUT = 0.0;
    public double REVERSE_NOMINAL_OUTPUT = 0.0;
    public double FORWARD_PEAK_OUTPUT = 1.0;
    public double REVERSE_PEAK_OUTPUT = -1.0;
    public double VOLTAGE_COMPENSATION_SATURATION = 12.0;
    public boolean ENABLE_VOLTAGE_COMPENSATION = false;
    public int CONTINUOUS_CURRENT_LIMIT = 0;
    public int PEAK_CURRENT_LIMIT = 0;
    public int PEAK_CURRENT_LIMIT_DURATION = 0;
    public boolean ENABLE_CURRENT_LIMIT = false;
    public double OPEN_LOOP_RAMP_TIME_SECONDS = 0.0;
    public boolean INVERT_DIRECTION = false;
    public boolean INVERT_SENSOR_PHASE = false;
    public NeutralMode NEUTRAL_POWER_MODE = NeutralMode.Brake;

    public int ALLOWABLE_CLOSED_LOOP_ERROR = 0;
    public double PID_KP = 0.0;
    public double PID_KI = 0.0;
    public double PID_KD = 0.0;
    public double PID_KF = 0.0;
    public int MOTION_CRUISE_VELOCITY = 0;
    public int MOTION_ACCELERATION = 0;
  }

  /**
   * Create a basic SimpleTalonSRX.
   *
   * @param id This is the CAN ID in which the TalonSRX is configured with.
   * @return A TalonSRX, configured with the default parameters.
   */
  public static SimpleTalonSRX createDefaultTalon(int id) {
    return new SimpleTalonSRX(id);
  }

  /**
   * Create a slave SimpleTalonSRX.
   *
   * @param id This is the CAN ID in which the TalonSRX is configured with.
   * @param masterId This is the CAN ID for the slave to follow.
   * @param config This is the Configuration that stores all of the settings of the Talon.
   * @return A TalonSRX, configured to follow the master.
   */
  public static SimpleTalonSRX createSlaveTalon(int id, TalonSRX master) {
    SimpleTalonSRX talon = createDefaultTalon(id);
    talon.follow(master);
    return talon;
  }

  /**
   * Create a full fledged SimpleTalonSRX, this sets everything that will be used.
   *
   * @param id This is the CAN ID in which the TalonSRX is configured with.
   * @param config This is the Configuration that stores all of the settings of the Talon.
   * @return The configured TalonSRX
   */
  public static SimpleTalonSRX createTalon(int id, Configuration config) {
    SimpleTalonSRX talon = new SimpleTalonSRX(id);

    talon.configSelectedFeedbackSensor(config.FEEDBACK_DEVICE, 0, config.TIMEOUT_IN_MS);
    talon.setSelectedSensorPosition(0, 0, config.TIMEOUT_IN_MS);
    talon.setStatusFramePeriod(
        config.STATUS_FRAME, config.STATUS_FRAME_PERIOD, config.TIMEOUT_IN_MS);
    talon.configVelocityMeasurementPeriod(config.VELOCITY_MEASUREMENT_PERIOD, config.TIMEOUT_IN_MS);
    talon.configVelocityMeasurementWindow(config.VELOCITY_MEASUREMENT_WINDOW, config.TIMEOUT_IN_MS);
    talon.configNominalOutputForward(config.FORWARD_NOMINAL_OUTPUT, config.TIMEOUT_IN_MS);
    talon.configNominalOutputReverse(config.REVERSE_NOMINAL_OUTPUT, config.TIMEOUT_IN_MS);
    talon.configPeakOutputForward(config.FORWARD_PEAK_OUTPUT, config.TIMEOUT_IN_MS);
    talon.configPeakOutputReverse(config.REVERSE_PEAK_OUTPUT, config.TIMEOUT_IN_MS);
    talon.configVoltageCompSaturation(config.VOLTAGE_COMPENSATION_SATURATION, config.TIMEOUT_IN_MS);
    talon.enableVoltageCompensation(config.ENABLE_VOLTAGE_COMPENSATION);
    talon.configContinuousCurrentLimit(config.CONTINUOUS_CURRENT_LIMIT, config.TIMEOUT_IN_MS);
    talon.configPeakCurrentLimit(config.PEAK_CURRENT_LIMIT, config.TIMEOUT_IN_MS);
    talon.configPeakCurrentDuration(config.PEAK_CURRENT_LIMIT_DURATION, config.TIMEOUT_IN_MS);
    talon.configOpenloopRamp(config.OPEN_LOOP_RAMP_TIME_SECONDS, config.TIMEOUT_IN_MS);
    talon.configAllowableClosedloopError(
        config.PROFILE_SLOT_ID, config.ALLOWABLE_CLOSED_LOOP_ERROR, config.TIMEOUT_IN_MS);
    talon.setInverted(config.INVERT_DIRECTION);
    talon.setSensorPhase(config.INVERT_SENSOR_PHASE);
    talon.setNeutralMode(config.NEUTRAL_POWER_MODE);
    talon.selectProfileSlot(config.PROFILE_SLOT_ID, 0);
    talon.config_kP(config.PROFILE_SLOT_ID, config.PID_KP, config.TIMEOUT_IN_MS);
    talon.config_kI(config.PROFILE_SLOT_ID, config.PID_KI, config.TIMEOUT_IN_MS);
    talon.config_kD(config.PROFILE_SLOT_ID, config.PID_KD, config.TIMEOUT_IN_MS);
    talon.config_kF(config.PROFILE_SLOT_ID, config.PID_KF, config.TIMEOUT_IN_MS);
    talon.configMotionCruiseVelocity(config.MOTION_CRUISE_VELOCITY, config.TIMEOUT_IN_MS);
    talon.configMotionAcceleration(config.MOTION_ACCELERATION, config.TIMEOUT_IN_MS);

    return talon;
  }
}
