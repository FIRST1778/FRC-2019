package frc.team4077.robot.common;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team4077.robot.Constants;

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
    TalonSRX talon = new TalonSRX(0);

    public int TIMEOUT_IN_MS = 10;

    public double OPEN_LOOP_RAMP_TIME = 0.0;
    public double CLOSED_LOOP_RAMP_TIME = 0.0;
    public double PEAK_OUTPUT_FORWARD = 1.0;
    public double PEAK_OUTPUT_REVERSE = -1.0;
    public double NOMINAL_OUTPUT_FORWARD = 0.0;
    public double NOMINAL_OUTPUT_REVERSE = 0.0;
    public double NEUTRAL_DEADBAND = 0.04;
    public double VOLTAGE_COMPENSATION_SATURATION = 0.0;
    public int VOLTAGE_MEASUREMENT_SAMPLES = 32;
    public RemoteFeedbackDevice REMOTE_FEEDBACK_DEVICE = RemoteFeedbackDevice.None;
    public FeedbackDevice FEEDBACK_DEVICE = FeedbackDevice.None;
    public int REMOTE_FEEDBACK_ID = 0;

    private void d(Configuration config) {
      talon.configOpenloopRamp(config.OPEN_LOOP_RAMP_TIME, config.TIMEOUT_IN_MS); // 	0;
      talon.configClosedloopRamp(config.OPEN_LOOP_RAMP_TIME, config.TIMEOUT_IN_MS); // 	0;
      talon.configPeakOutputForward(config.PEAK_OUTPUT_FORWARD, config.TIMEOUT_IN_MS); // 	+1;
      talon.configPeakOutputReverse(config.PEAK_OUTPUT_REVERSE, config.TIMEOUT_IN_MS); // 	-1;
      talon.configNominalOutputForward(config.NOMINAL_OUTPUT_FORWARD, config.TIMEOUT_IN_MS); // 	0;
      talon.configNominalOutputReverse(config.NOMINAL_OUTPUT_REVERSE, config.TIMEOUT_IN_MS); // 	0;
      talon.configNeutralDeadband(config.NEUTRAL_DEADBAND, config.TIMEOUT_IN_MS); // 	0.04;
      talon.configVoltageCompSaturation(config.VOLTAGE_COMPENSATION_SATURATION, config.TIMEOUT_IN_MS); // 	0;
      talon.configVoltageMeasurementFilter(config.VOLTAGE_MEASUREMENT_SAMPLES, config.TIMEOUT_IN_MS); // 	32;
      talon.configSelectedFeedbackSensor(config.REMOTE_FEEDBACK_DEVICE, 0, config.TIMEOUT_IN_MS); // 	Quad (0);
      talon.configSelectedFeedbackSensor(config.FEEDBACK_DEVICE, 0, config.TIMEOUT_IN_MS); // 	Quad (0);
      //talon.configSelectedFeedbackCoefficient(, config.TIMEOUT_IN_MS); // 	1.0;
      talon.configRemoteFeedbackFilter(config.REMOTE_FEEDBACK_ID, , config.TIMEOUT_IN_MS); // 	Off (0);
      talon.configSensorTerm(, config.TIMEOUT_IN_MS); // 	Quad (0) for all term types;
      talon.configVelocityMeasurementPeriod(, config.TIMEOUT_IN_MS); // 	100;
      talon.configVelocityMeasurementWindow(, config.TIMEOUT_IN_MS); // 	64;
      talon.configForwardLimitSwitchSource(, config.TIMEOUT_IN_MS); // 	Off (0), and "Normally Open";
      talon.configReverseLimitSwitchSource(, config.TIMEOUT_IN_MS); // 	Off (0), and "Normally Open";
      talon.configForwardLimitSwitchSource(, config.TIMEOUT_IN_MS); // 	Off (0), and "Normally Open";
      talon.configForwardSoftLimitThreshold(, config.TIMEOUT_IN_MS); // 	0;
      talon.configReverseSoftLimitThreshold(, config.TIMEOUT_IN_MS); // 	0;
      talon.configForwardSoftLimitEnable(, config.TIMEOUT_IN_MS); // 	false;
      talon.configReverseSoftLimitEnable(, config.TIMEOUT_IN_MS); // 	false;
      talon.config_kP(, config.TIMEOUT_IN_MS); // 	0;
      talon.config_kI(, config.TIMEOUT_IN_MS); // 	0;
      talon.config_kD(, config.TIMEOUT_IN_MS); // 	0;
      talon.config_kF(, config.TIMEOUT_IN_MS); // 	0;
      talon.config_IntegralZone(, config.TIMEOUT_IN_MS); // 	0;
      talon.configAllowableClosedloopError(, config.TIMEOUT_IN_MS); // 	0;
      talon.configMaxIntegralAccumulator(, config.TIMEOUT_IN_MS); // 	0;
      talon.configClosedLoopPeakOutput(, config.TIMEOUT_IN_MS); // 	1.0;
      talon.configClosedLoopPeriod(, config.TIMEOUT_IN_MS); // 	1 ms;
      talon.configAuxPIDPolarity(, config.TIMEOUT_IN_MS); // 	false;
      talon.configMotionCruiseVelocity(, config.TIMEOUT_IN_MS); // 	0;
      talon.configMotionAcceleration(, config.TIMEOUT_IN_MS); // 	0;
      talon.configMotionProfileTrajectoryPeriod(, config.TIMEOUT_IN_MS); // 	0;
      talon.configSetCustomParam(, config.TIMEOUT_IN_MS); // 	0;
      talon.configPeakCurrentLimit(, config.TIMEOUT_IN_MS); // 	0;
      talon.configPeakCurrentDuration(, config.TIMEOUT_IN_MS); // 	Invalid, see errata.;
      talon.configContinuousCurrentLimit(, config.TIMEOUT_IN_MS); // 	0;
    }
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
  public static TalonSRX createTalon(int id, Configuration config) {
    TalonSRX talon = new TalonSRX(id);

    talon.configSelectedFeedbackSensor(
        FeedbackDevice.CTRE_MagEncoder_Relative, 0, config.TIMEOUT_IN_MS);
    talon.setSelectedSensorPosition(0, 0, config.TIMEOUT_IN_MS);
    talon.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10, config.TIMEOUT_IN_MS);
    talon.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_10Ms, config.TIMEOUT_IN_MS);
    talon.configVelocityMeasurementWindow(32, config.TIMEOUT_IN_MS);
    talon.configNominalOutputForward(1.5 / 12.0, config.TIMEOUT_IN_MS);
    talon.configNominalOutputReverse(-1.5 / 12.0, config.TIMEOUT_IN_MS);
    talon.configPeakOutputForward(1.0, config.TIMEOUT_IN_MS);
    talon.configPeakOutputReverse(-1.0, config.TIMEOUT_IN_MS);
    talon.configVoltageCompSaturation(12.0, config.TIMEOUT_IN_MS);
    talon.enableVoltageCompensation(true);
    talon.configOpenloopRamp(0.25, config.TIMEOUT_IN_MS);
    talon.configAllowableClosedloopError(0, 0, config.TIMEOUT_IN_MS);
    talon.setInverted(false);
    talon.setSensorPhase(false);
    talon.setNeutralMode(NeutralMode.Brake);
    talon.selectProfileSlot(0, 0);
    talon.config_kP(0, 0.2, config.TIMEOUT_IN_MS);
    talon.config_kI(0, 0.0, config.TIMEOUT_IN_MS);
    talon.config_kD(0, 24.0, config.TIMEOUT_IN_MS);
    talon.config_kF(0, 1023.0 / Constants.DRIVE_MAX_SPEED, config.TIMEOUT_IN_MS);
    talon.configMotionCruiseVelocity((int) (Constants.DRIVE_MAX_SPEED * 0.9), config.TIMEOUT_IN_MS);
    talon.configMotionAcceleration((int) (Constants.DRIVE_MAX_SPEED), config.TIMEOUT_IN_MS);

    return talon;
  }
}
