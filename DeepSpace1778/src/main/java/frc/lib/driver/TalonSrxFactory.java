package frc.lib.driver;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * This creates and sets most of the convenient settings for a Talon SRX. This includes feedback
 * devices, voltage limits, control modes, inversion, etc.
 *
 * @author FRC 1778 Chill Out
 */
public class TalonSrxFactory {

  /**
   * This creates and sets most of the convenient settings for a Talon SRX. This includes feedback
   * devices, voltage limits, control modes, inversion, etc.
   *
   * @author FRC 1778 Chill Out
   */
  public static class Configuration {

    public final int timeoutInMs = 10;
    public int profileSlotId = 0;

    public double openLoopRampTimeSeconds = 0.0;
    public double closedLoopRampTimeSeconds = 0.0;
    public double forwardPeakOutput = 1.0;
    public double reversePeakOutput = -1.0;
    public double forwardNominalOutput = 0.0;
    public double reverseNominalOutput = 0.0;
    public double neutralDeadband = 0.04;
    public double voltageCompensationSaturation = 0.0;
    public int voltageMeasurementWindowFilter = 32;
    public FeedbackDevice feedbackDevice = FeedbackDevice.QuadEncoder;
    public double feedbackCoefficient = 1.0;
    public boolean feedbackNotContinuous = false;
    public VelocityMeasPeriod velocityMeasurementPeriod = VelocityMeasPeriod.Period_100Ms;
    public int velocityMeasurementWindow = 64;
    public LimitSwitchSource forwardLimitSwitch = LimitSwitchSource.Deactivated;
    public LimitSwitchNormal forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
    public LimitSwitchSource reverseLimitSwitch = LimitSwitchSource.Deactivated;
    public LimitSwitchNormal reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
    public boolean clearPositionOnLimitF = false;
    public boolean clearPositionOnLimitR = false;
    public int forwardSoftLimitThreshold = 0;
    public int reverseSoftLimitThreshold = 0;
    public boolean enableForwardSoftLimit = false;
    public boolean enableReverseSoftLimit = false;
    public double pidKp = 0.0;
    public double pidKi = 0.0;
    public double pidKd = 0.0;
    public double pidKf = 0.0;
    public int pidIntegralZone = 0;
    public int allowableClosedLoopError = 0;
    public double pidMaxIntegralAccumulator = 0.0;
    public double closedLoopPeakOutput = 1.0;
    public int closedLoopPeriod = 1;
    public boolean pidInvertPolarity = false;
    public int motionCruiseVelocity = 0;
    public int motionAcceleration = 0;
    public int motionProfileTrajectoryPeriod = 0;
    public int peakCurrentLimit = 0;
    public int peakCurrentLimitDuration = 0;
    public int continuousCurrentLimit = 0;

    public StatusFrameEnhanced statusFrame = StatusFrameEnhanced.Status_1_General;
    public int statusFramePeriod = 10;

    public boolean enableVoltageCompensation = false;
    public boolean enableCurrentLimit = false;

    public boolean invertSensorPhase = false;
    public boolean invert = false;
    public NeutralMode neutralPowerMode = NeutralMode.Brake;
  }

  private static final Configuration DEFAULT_CONFIGURATION = new Configuration();

  public static TalonSRX createDefaultTalon(int id) {
    return createTalon(id, DEFAULT_CONFIGURATION);
  }

  public static TalonSRX createSlaveTalon(int id, BaseMotorController master) {
    TalonSRX talon = createDefaultTalon(id);
    talon.follow(master);
    return talon;
  }

  public static TalonSRX createTalon(int id, Configuration config) {
    TalonSRX talon = new TalonSRX(id);

    talon.clearStickyFaults(config.timeoutInMs);
    talon.configOpenloopRamp(config.openLoopRampTimeSeconds, config.timeoutInMs);
    talon.configClosedloopRamp(config.closedLoopRampTimeSeconds, config.timeoutInMs);
    talon.configPeakOutputForward(config.forwardPeakOutput, config.timeoutInMs);
    talon.configPeakOutputReverse(config.reversePeakOutput, config.timeoutInMs);
    talon.configNominalOutputForward(config.forwardNominalOutput, config.timeoutInMs);
    talon.configNominalOutputReverse(config.reverseNominalOutput, config.timeoutInMs);
    talon.configNeutralDeadband(config.neutralDeadband, config.timeoutInMs);
    talon.configVoltageCompSaturation(config.voltageCompensationSaturation, config.timeoutInMs);
    talon.configVoltageMeasurementFilter(config.voltageMeasurementWindowFilter, config.timeoutInMs);
    talon.configSelectedFeedbackSensor(config.feedbackDevice, 0, config.timeoutInMs);
    talon.configSelectedFeedbackCoefficient(config.feedbackCoefficient, 0, config.timeoutInMs);
    talon.configFeedbackNotContinuous(config.feedbackNotContinuous, config.timeoutInMs);
    talon.configVelocityMeasurementPeriod(config.velocityMeasurementPeriod, config.timeoutInMs);
    talon.configVelocityMeasurementWindow(config.velocityMeasurementWindow, config.timeoutInMs);
    talon.configForwardLimitSwitchSource(
        config.forwardLimitSwitch, config.forwardLimitSwitchNormal, config.timeoutInMs);
    talon.configReverseLimitSwitchSource(
        config.reverseLimitSwitch, config.reverseLimitSwitchNormal, config.timeoutInMs);
    talon.configForwardSoftLimitThreshold(config.forwardSoftLimitThreshold, config.timeoutInMs);
    talon.configReverseSoftLimitThreshold(config.reverseSoftLimitThreshold, config.timeoutInMs);
    talon.configForwardSoftLimitEnable(config.enableForwardSoftLimit, config.timeoutInMs);
    talon.configReverseSoftLimitEnable(config.enableReverseSoftLimit, config.timeoutInMs);
    talon.configClearPositionOnLimitF(config.clearPositionOnLimitF, config.timeoutInMs);
    talon.configClearPositionOnLimitR(config.clearPositionOnLimitR, config.timeoutInMs);
    talon.config_kP(config.profileSlotId, config.pidKp, config.timeoutInMs);
    talon.config_kI(config.profileSlotId, config.pidKi, config.timeoutInMs);
    talon.config_kD(config.profileSlotId, config.pidKd, config.timeoutInMs);
    talon.config_kF(config.profileSlotId, config.pidKf, config.timeoutInMs);
    talon.config_IntegralZone(config.profileSlotId, config.pidIntegralZone, config.timeoutInMs);
    talon.configAllowableClosedloopError(
        config.profileSlotId, config.allowableClosedLoopError, config.timeoutInMs);
    talon.configMaxIntegralAccumulator(
        config.profileSlotId, config.pidMaxIntegralAccumulator, config.timeoutInMs);
    talon.configClosedLoopPeakOutput(
        config.profileSlotId, config.closedLoopPeakOutput, config.timeoutInMs);
    talon.configClosedLoopPeriod(config.profileSlotId, config.closedLoopPeriod, config.timeoutInMs);
    talon.configAuxPIDPolarity(config.pidInvertPolarity, config.timeoutInMs);
    talon.configMotionCruiseVelocity(config.motionCruiseVelocity, config.timeoutInMs);
    talon.configMotionAcceleration(config.motionAcceleration, config.timeoutInMs);
    talon.configMotionProfileTrajectoryPeriod(
        config.motionProfileTrajectoryPeriod, config.timeoutInMs);
    talon.configPeakCurrentLimit(config.peakCurrentLimit, config.timeoutInMs);
    talon.configPeakCurrentDuration(config.peakCurrentLimitDuration, config.timeoutInMs);
    talon.configContinuousCurrentLimit(config.continuousCurrentLimit, config.timeoutInMs);

    talon.setStatusFramePeriod(config.statusFrame, config.statusFramePeriod, config.timeoutInMs);
    talon.enableVoltageCompensation(config.enableVoltageCompensation);
    talon.enableCurrentLimit(config.enableCurrentLimit);
    talon.setSensorPhase(config.invertSensorPhase);
    talon.setNeutralMode(config.neutralPowerMode);
    talon.selectProfileSlot(config.profileSlotId, 0);
    talon.setInverted(config.invert);

    return talon;
  }
}
