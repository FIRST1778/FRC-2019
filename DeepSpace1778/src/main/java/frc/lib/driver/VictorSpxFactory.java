package frc.lib.driver;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * This creates and sets most of the convenient settings for a Victor SPX. This includes voltage
 * limits, control modes, inversion, etc.
 *
 * @author FRC 1778 Chill Out
 */
public class VictorSpxFactory {

  /**
   * This creates and sets most of the convenient settings for a Victor SPX. This includes voltage
   * limits, control modes, inversion, etc.
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
    public VelocityMeasPeriod velocityMeasurementPeriod = VelocityMeasPeriod.Period_100Ms;
    public int velocityMeasurementWindow = 64;
    public RemoteLimitSwitchSource forwardLimitSwitch = RemoteLimitSwitchSource.Deactivated;
    public LimitSwitchNormal forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
    public RemoteLimitSwitchSource reverseLimitSwitch = RemoteLimitSwitchSource.Deactivated;
    public LimitSwitchNormal reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
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

  public static VictorSPX createDefaultVictor(int id) {
    return createVictor(id, DEFAULT_CONFIGURATION);
  }

  public static VictorSPX createSlaveVictor(int id, BaseMotorController master) {
    VictorSPX victor = createDefaultVictor(id);
    victor.follow(master);
    return victor;
  }

  public static VictorSPX createVictor(int id, Configuration config) {
    VictorSPX victor = new VictorSPX(id);

    victor.clearStickyFaults(config.timeoutInMs);
    victor.configOpenloopRamp(config.openLoopRampTimeSeconds, config.timeoutInMs);
    victor.configClosedloopRamp(config.closedLoopRampTimeSeconds, config.timeoutInMs);
    victor.configPeakOutputForward(config.forwardPeakOutput, config.timeoutInMs);
    victor.configPeakOutputReverse(config.reversePeakOutput, config.timeoutInMs);
    victor.configNominalOutputForward(config.forwardNominalOutput, config.timeoutInMs);
    victor.configNominalOutputReverse(config.reverseNominalOutput, config.timeoutInMs);
    victor.configNeutralDeadband(config.neutralDeadband, config.timeoutInMs);
    victor.configVoltageCompSaturation(config.voltageCompensationSaturation, config.timeoutInMs);
    victor.configVoltageMeasurementFilter(
        config.voltageMeasurementWindowFilter, config.timeoutInMs);
    victor.configSelectedFeedbackSensor(config.feedbackDevice, 0, config.timeoutInMs);
    victor.configSelectedFeedbackCoefficient(config.feedbackCoefficient, 0, config.timeoutInMs);
    victor.configVelocityMeasurementPeriod(config.velocityMeasurementPeriod, config.timeoutInMs);
    victor.configVelocityMeasurementWindow(config.velocityMeasurementWindow, config.timeoutInMs);
    victor.configForwardLimitSwitchSource(
        config.forwardLimitSwitch, config.forwardLimitSwitchNormal, config.timeoutInMs);
    victor.configReverseLimitSwitchSource(
        config.reverseLimitSwitch, config.reverseLimitSwitchNormal, config.timeoutInMs);
    victor.configForwardSoftLimitThreshold(config.forwardSoftLimitThreshold, config.timeoutInMs);
    victor.configReverseSoftLimitThreshold(config.reverseSoftLimitThreshold, config.timeoutInMs);
    victor.configForwardSoftLimitEnable(config.enableForwardSoftLimit, config.timeoutInMs);
    victor.configReverseSoftLimitEnable(config.enableReverseSoftLimit, config.timeoutInMs);
    victor.config_kP(config.profileSlotId, config.pidKp, config.timeoutInMs);
    victor.config_kI(config.profileSlotId, config.pidKi, config.timeoutInMs);
    victor.config_kD(config.profileSlotId, config.pidKd, config.timeoutInMs);
    victor.config_kF(config.profileSlotId, config.pidKf, config.timeoutInMs);
    victor.config_IntegralZone(config.profileSlotId, config.pidIntegralZone, config.timeoutInMs);
    victor.configAllowableClosedloopError(
        config.profileSlotId, config.allowableClosedLoopError, config.timeoutInMs);
    victor.configMaxIntegralAccumulator(
        config.profileSlotId, config.pidMaxIntegralAccumulator, config.timeoutInMs);
    victor.configClosedLoopPeakOutput(
        config.profileSlotId, config.closedLoopPeakOutput, config.timeoutInMs);
    victor.configClosedLoopPeriod(
        config.profileSlotId, config.closedLoopPeriod, config.timeoutInMs);
    victor.configAuxPIDPolarity(config.pidInvertPolarity, config.timeoutInMs);
    victor.configMotionCruiseVelocity(config.motionCruiseVelocity, config.timeoutInMs);
    victor.configMotionAcceleration(config.motionAcceleration, config.timeoutInMs);
    victor.configMotionProfileTrajectoryPeriod(
        config.motionProfileTrajectoryPeriod, config.timeoutInMs);
    victor.enableVoltageCompensation(config.enableVoltageCompensation);
    victor.setSensorPhase(config.invertSensorPhase);
    victor.setNeutralMode(config.neutralPowerMode);
    victor.selectProfileSlot(config.profileSlotId, 0);
    victor.setInverted(config.invert);

    return victor;
  }
}
