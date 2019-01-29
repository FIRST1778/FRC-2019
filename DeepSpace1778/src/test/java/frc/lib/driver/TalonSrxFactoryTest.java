package frc.lib.driver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import org.junit.jupiter.api.Test;

public class TalonSrxFactoryTest {

  public static class ConfigurationTest {

    TalonSrxFactory.Configuration configuration = new TalonSrxFactory.Configuration();

    @Test
    public void timeoutInMsShouldDefault() {
      assertThat(configuration.timeoutInMs, is(10));
    }

    @Test
    public void profileSlotIdShouldDefault() {
      assertThat(configuration.profileSlotId, is(0));
    }

    @Test
    public void openLoopRampTimeSecondsShouldDefault() {
      assertThat(configuration.openLoopRampTimeSeconds, is(0.0));
    }

    @Test
    public void closedLoopRampTimeSecondsShouldDefault() {
      assertThat(configuration.closedLoopRampTimeSeconds, is(0.0));
    }

    @Test
    public void forwardPeakOutputShouldDefault() {
      assertThat(configuration.forwardPeakOutput, is(1.0));
    }

    @Test
    public void reversePeakOutputShouldDefault() {
      assertThat(configuration.reversePeakOutput, is(-1.0));
    }

    @Test
    public void forwardNominalOutputShouldDefault() {
      assertThat(configuration.forwardNominalOutput, is(0.0));
    }

    @Test
    public void reverseNominalOutputShouldDefault() {
      assertThat(configuration.reverseNominalOutput, is(0.0));
    }

    @Test
    public void neutralDeadbandShouldDefault() {
      assertThat(configuration.neutralDeadband, is(0.04));
    }

    @Test
    public void voltageCompensationSaturationShouldDefault() {
      assertThat(configuration.voltageCompensationSaturation, is(0.0));
    }

    @Test
    public void voltageMeasurementWindowFilterShouldDefault() {
      assertThat(configuration.voltageMeasurementWindowFilter, is(32));
    }

    @Test
    public void feedbackDeviceShouldDefault() {
      assertThat(configuration.feedbackDevice, is(FeedbackDevice.QuadEncoder));
    }

    @Test
    public void feedbackCoefficientShouldDefault() {
      assertThat(configuration.feedbackCoefficient, is(1.0));
    }

    @Test
    public void velocityMeasurementPeriodShouldDefault() {
      assertThat(configuration.velocityMeasurementPeriod, is(VelocityMeasPeriod.Period_100Ms));
    }

    @Test
    public void velocityMeasurementWindowShouldDefault() {
      assertThat(configuration.velocityMeasurementWindow, is(64));
    }

    @Test
    public void forwardLimitSwitchShouldDefault() {
      assertThat(configuration.forwardLimitSwitch, is(LimitSwitchSource.Deactivated));
    }

    @Test
    public void forwardLimitSwitchNormalShouldDefault() {
      assertThat(configuration.forwardLimitSwitchNormal, is(LimitSwitchNormal.NormallyOpen));
    }

    @Test
    public void reverseLimitSwitchShouldDefault() {
      assertThat(configuration.reverseLimitSwitch, is(LimitSwitchSource.Deactivated));
    }

    @Test
    public void reverseLimitSwitchNormalShouldDefault() {
      assertThat(configuration.reverseLimitSwitchNormal, is(LimitSwitchNormal.NormallyOpen));
    }

    @Test
    public void forwardSoftLimitThresholdShouldDefault() {
      assertThat(configuration.forwardSoftLimitThreshold, is(0));
    }

    @Test
    public void reverseSoftLimitThresholdShouldDefault() {
      assertThat(configuration.reverseSoftLimitThreshold, is(0));
    }

    @Test
    public void enableForwardSoftLimitShouldDefault() {
      assertThat(configuration.enableForwardSoftLimit, is(false));
    }

    @Test
    public void enableReverseSoftLimitShouldDefault() {
      assertThat(configuration.enableReverseSoftLimit, is(false));
    }

    @Test
    public void pidKpShouldDefault() {
      assertThat(configuration.pidKp, is(0.0));
    }

    @Test
    public void pidKiShouldDefault() {
      assertThat(configuration.pidKi, is(0.0));
    }

    @Test
    public void pidKdShouldDefault() {
      assertThat(configuration.pidKd, is(0.0));
    }

    @Test
    public void pidKfShouldDefault() {
      assertThat(configuration.pidKf, is(0.0));
    }

    @Test
    public void pidIntegralZoneShouldDefault() {
      assertThat(configuration.pidIntegralZone, is(0));
    }

    @Test
    public void allowableClosedLoopErrorShouldDefault() {
      assertThat(configuration.allowableClosedLoopError, is(0));
    }

    @Test
    public void pidMaxIntegralAccumulatorShouldDefault() {
      assertThat(configuration.pidMaxIntegralAccumulator, is(0.0));
    }

    @Test
    public void closedLoopPeakOutputShouldDefault() {
      assertThat(configuration.closedLoopPeakOutput, is(1.0));
    }

    @Test
    public void closedLoopPeriodShouldDefault() {
      assertThat(configuration.closedLoopPeriod, is(1));
    }

    @Test
    public void pidInvertPolarityShouldDefault() {
      assertThat(configuration.pidInvertPolarity, is(false));
    }

    @Test
    public void motionCruiseVelocityShouldDefault() {
      assertThat(configuration.motionCruiseVelocity, is(0));
    }

    @Test
    public void motionAccelerationShouldDefault() {
      assertThat(configuration.motionAcceleration, is(0));
    }

    @Test
    public void motionProfileTrajectoryPeriodShouldDefault() {
      assertThat(configuration.motionProfileTrajectoryPeriod, is(0));
    }

    @Test
    public void peakCurrentLimitShouldDefault() {
      assertThat(configuration.peakCurrentLimit, is(0));
    }

    @Test
    public void peakCurrentLimitDurationShouldDefault() {
      assertThat(configuration.peakCurrentLimitDuration, is(0));
    }

    @Test
    public void continuousCurrentLimitShouldDefault() {
      assertThat(configuration.continuousCurrentLimit, is(0));
    }

    @Test
    public void statusFrameShouldDefault() {
      assertThat(configuration.statusFrame, is(StatusFrameEnhanced.Status_1_General));
    }

    @Test
    public void statusFramePeriodShouldDefault() {
      assertThat(configuration.statusFramePeriod, is(10));
    }

    @Test
    public void enableVoltageCompensationShouldDefault() {
      assertThat(configuration.enableVoltageCompensation, is(false));
    }

    @Test
    public void enableCurrentLimitShouldDefault() {
      assertThat(configuration.enableCurrentLimit, is(false));
    }

    @Test
    public void invertSensorPhaseShouldDefault() {
      assertThat(configuration.invertSensorPhase, is(false));
    }

    @Test
    public void invertShouldDefault() {
      assertThat(configuration.invert, is(false));
    }

    @Test
    public void neutralPowerModeShouldDefault() {
      assertThat(configuration.neutralPowerMode, is(NeutralMode.Brake));
    }
  }
}
