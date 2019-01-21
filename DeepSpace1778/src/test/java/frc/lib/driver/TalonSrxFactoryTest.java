package frc.lib.driver;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class TalonSrxFactoryTest {

  public static class ConfigurationTest {

    TalonSrxFactory.Configuration configuration = new TalonSrxFactory.Configuration();

    @Test
    public void timeoutInMsShouldDefault() {
      Assert.assertThat(configuration.timeoutInMs, Is.is(10));
    }

    @Test
    public void profileSlotIdShouldDefault() {
      Assert.assertThat(configuration.profileSlotId, Is.is(0));
    }

    @Test
    public void openLoopRampTimeSecondsShouldDefault() {
      Assert.assertThat(configuration.openLoopRampTimeSeconds, Is.is(0.0));
    }

    @Test
    public void closedLoopRampTimeSecondsShouldDefault() {
      Assert.assertThat(configuration.closedLoopRampTimeSeconds, Is.is(0.0));
    }

    @Test
    public void forwardPeakOutputShouldDefault() {
      Assert.assertThat(configuration.forwardPeakOutput, Is.is(1.0));
    }

    @Test
    public void reversePeakOutputShouldDefault() {
      Assert.assertThat(configuration.reversePeakOutput, Is.is(-1.0));
    }

    @Test
    public void forwardNominalOutputShouldDefault() {
      Assert.assertThat(configuration.forwardNominalOutput, Is.is(0.0));
    }

    @Test
    public void reverseNominalOutputShouldDefault() {
      Assert.assertThat(configuration.reverseNominalOutput, Is.is(0.0));
    }

    @Test
    public void neutralDeadbandShouldDefault() {
      Assert.assertThat(configuration.neutralDeadband, Is.is(0.04));
    }

    @Test
    public void voltageCompensationSaturationShouldDefault() {
      Assert.assertThat(configuration.voltageCompensationSaturation, Is.is(0.0));
    }

    @Test
    public void voltageMeasurementWindowFilterShouldDefault() {
      Assert.assertThat(configuration.voltageMeasurementWindowFilter, Is.is(32));
    }

    @Test
    public void feedbackDeviceShouldDefault() {
      Assert.assertThat(configuration.feedbackDevice, Is.is(FeedbackDevice.QuadEncoder));
    }

    @Test
    public void feedbackCoefficientShouldDefault() {
      Assert.assertThat(configuration.feedbackCoefficient, Is.is(1.0));
    }

    @Test
    public void velocityMeasurementPeriodShouldDefault() {
      Assert.assertThat(
          configuration.velocityMeasurementPeriod, Is.is(VelocityMeasPeriod.Period_100Ms));
    }

    @Test
    public void velocityMeasurementWindowShouldDefault() {
      Assert.assertThat(configuration.velocityMeasurementWindow, Is.is(64));
    }

    @Test
    public void forwardLimitSwitchShouldDefault() {
      Assert.assertThat(configuration.forwardLimitSwitch, Is.is(LimitSwitchSource.Deactivated));
    }

    @Test
    public void forwardLimitSwitchNormalShouldDefault() {
      Assert.assertThat(
          configuration.forwardLimitSwitchNormal, Is.is(LimitSwitchNormal.NormallyOpen));
    }

    @Test
    public void reverseLimitSwitchShouldDefault() {
      Assert.assertThat(configuration.reverseLimitSwitch, Is.is(LimitSwitchSource.Deactivated));
    }

    @Test
    public void reverseLimitSwitchNormalShouldDefault() {
      Assert.assertThat(
          configuration.reverseLimitSwitchNormal, Is.is(LimitSwitchNormal.NormallyOpen));
    }

    @Test
    public void forwardSoftLimitThresholdShouldDefault() {
      Assert.assertThat(configuration.forwardSoftLimitThreshold, Is.is(0));
    }

    @Test
    public void reverseSoftLimitThresholdShouldDefault() {
      Assert.assertThat(configuration.reverseSoftLimitThreshold, Is.is(0));
    }

    @Test
    public void enableForwardSoftLimitShouldDefault() {
      Assert.assertThat(configuration.enableForwardSoftLimit, Is.is(false));
    }

    @Test
    public void enableReverseSoftLimitShouldDefault() {
      Assert.assertThat(configuration.enableReverseSoftLimit, Is.is(false));
    }

    @Test
    public void pidKpShouldDefault() {
      Assert.assertThat(configuration.pidKp, Is.is(0.0));
    }

    @Test
    public void pidKiShouldDefault() {
      Assert.assertThat(configuration.pidKi, Is.is(0.0));
    }

    @Test
    public void pidKdShouldDefault() {
      Assert.assertThat(configuration.pidKd, Is.is(0.0));
    }

    @Test
    public void pidKfShouldDefault() {
      Assert.assertThat(configuration.pidKf, Is.is(0.0));
    }

    @Test
    public void pidIntegralZoneShouldDefault() {
      Assert.assertThat(configuration.pidIntegralZone, Is.is(0));
    }

    @Test
    public void allowableClosedLoopErrorShouldDefault() {
      Assert.assertThat(configuration.allowableClosedLoopError, Is.is(0));
    }

    @Test
    public void pidMaxIntegralAccumulatorShouldDefault() {
      Assert.assertThat(configuration.pidMaxIntegralAccumulator, Is.is(0.0));
    }

    @Test
    public void closedLoopPeakOutputShouldDefault() {
      Assert.assertThat(configuration.closedLoopPeakOutput, Is.is(1.0));
    }

    @Test
    public void closedLoopPeriodShouldDefault() {
      Assert.assertThat(configuration.closedLoopPeriod, Is.is(1));
    }

    @Test
    public void pidInvertPolarityShouldDefault() {
      Assert.assertThat(configuration.pidInvertPolarity, Is.is(false));
    }

    @Test
    public void motionCruiseVelocityShouldDefault() {
      Assert.assertThat(configuration.motionCruiseVelocity, Is.is(0));
    }

    @Test
    public void motionAccelerationShouldDefault() {
      Assert.assertThat(configuration.motionAcceleration, Is.is(0));
    }

    @Test
    public void motionProfileTrajectoryPeriodShouldDefault() {
      Assert.assertThat(configuration.motionProfileTrajectoryPeriod, Is.is(0));
    }

    @Test
    public void peakCurrentLimitShouldDefault() {
      Assert.assertThat(configuration.peakCurrentLimit, Is.is(0));
    }

    @Test
    public void peakCurrentLimitDurationShouldDefault() {
      Assert.assertThat(configuration.peakCurrentLimitDuration, Is.is(0));
    }

    @Test
    public void continuousCurrentLimitShouldDefault() {
      Assert.assertThat(configuration.continuousCurrentLimit, Is.is(0));
    }

    @Test
    public void statusFrameShouldDefault() {
      Assert.assertThat(configuration.statusFrame, Is.is(StatusFrameEnhanced.Status_1_General));
    }

    @Test
    public void statusFramePeriodShouldDefault() {
      Assert.assertThat(configuration.statusFramePeriod, Is.is(10));
    }

    @Test
    public void enableVoltageCompensationShouldDefault() {
      Assert.assertThat(configuration.enableVoltageCompensation, Is.is(false));
    }

    @Test
    public void enableCurrentLimitShouldDefault() {
      Assert.assertThat(configuration.enableCurrentLimit, Is.is(false));
    }

    @Test
    public void invertSensorPhaseShouldDefault() {
      Assert.assertThat(configuration.invertSensorPhase, Is.is(false));
    }

    @Test
    public void invertShouldDefault() {
      Assert.assertThat(configuration.invert, Is.is(false));
    }

    @Test
    public void neutralPowerModeShouldDefault() {
      Assert.assertThat(configuration.neutralPowerMode, Is.is(NeutralMode.Brake));
    }
  }
}
