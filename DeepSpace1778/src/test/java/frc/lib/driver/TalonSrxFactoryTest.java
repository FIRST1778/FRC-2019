package frc.lib.driver;

import static org.hamcrest.core.Is.is;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import org.junit.Assert;
import org.junit.Test;

public class TalonSrxFactoryTest {

  public static class ConfigurationTest {

    TalonSrxFactory.Configuration configuration = new TalonSrxFactory.Configuration();

    @Test
    public void timeoutInMsShouldDefault() {
      Assert.assertThat(configuration.timeoutInMs, is(10));
    }

    @Test
    public void profileSlotIdShouldDefault() {
      Assert.assertThat(configuration.profileSlotId, is(0));
    }

    @Test
    public void openLoopRampTimeSecondsShouldDefault() {
      Assert.assertThat(configuration.openLoopRampTimeSeconds, is(0.0));
    }

    @Test
    public void closedLoopRampTimeSecondsShouldDefault() {
      Assert.assertThat(configuration.closedLoopRampTimeSeconds, is(0.0));
    }

    @Test
    public void forwardPeakOutputShouldDefault() {
      Assert.assertThat(configuration.forwardPeakOutput, is(1.0));
    }

    @Test
    public void reversePeakOutputShouldDefault() {
      Assert.assertThat(configuration.reversePeakOutput, is(-1.0));
    }

    @Test
    public void forwardNominalOutputShouldDefault() {
      Assert.assertThat(configuration.forwardNominalOutput, is(0.0));
    }

    @Test
    public void reverseNominalOutputShouldDefault() {
      Assert.assertThat(configuration.reverseNominalOutput, is(0.0));
    }

    @Test
    public void neutralDeadbandShouldDefault() {
      Assert.assertThat(configuration.neutralDeadband, is(0.04));
    }

    @Test
    public void voltageCompensationSaturationShouldDefault() {
      Assert.assertThat(configuration.voltageCompensationSaturation, is(0.0));
    }

    @Test
    public void voltageMeasurementWindowFilterShouldDefault() {
      Assert.assertThat(configuration.voltageMeasurementWindowFilter, is(32));
    }

    @Test
    public void feedbackDeviceShouldDefault() {
      Assert.assertThat(configuration.feedbackDevice, is(FeedbackDevice.QuadEncoder));
    }

    @Test
    public void feedbackCoefficientShouldDefault() {
      Assert.assertThat(configuration.feedbackCoefficient, is(1.0));
    }

    @Test
    public void velocityMeasurementPeriodShouldDefault() {
      Assert.assertThat(
          configuration.velocityMeasurementPeriod, is(VelocityMeasPeriod.Period_100Ms));
    }

    @Test
    public void velocityMeasurementWindowShouldDefault() {
      Assert.assertThat(configuration.velocityMeasurementWindow, is(64));
    }

    @Test
    public void forwardLimitSwitchShouldDefault() {
      Assert.assertThat(configuration.forwardLimitSwitch, is(LimitSwitchSource.Deactivated));
    }

    @Test
    public void forwardLimitSwitchNormalShouldDefault() {
      Assert.assertThat(configuration.forwardLimitSwitchNormal, is(LimitSwitchNormal.NormallyOpen));
    }

    @Test
    public void reverseLimitSwitchShouldDefault() {
      Assert.assertThat(configuration.reverseLimitSwitch, is(LimitSwitchSource.Deactivated));
    }

    @Test
    public void reverseLimitSwitchNormalShouldDefault() {
      Assert.assertThat(configuration.reverseLimitSwitchNormal, is(LimitSwitchNormal.NormallyOpen));
    }

    @Test
    public void forwardSoftLimitThresholdShouldDefault() {
      Assert.assertThat(configuration.forwardSoftLimitThreshold, is(0));
    }

    @Test
    public void reverseSoftLimitThresholdShouldDefault() {
      Assert.assertThat(configuration.reverseSoftLimitThreshold, is(0));
    }

    @Test
    public void enableForwardSoftLimitShouldDefault() {
      Assert.assertThat(configuration.enableForwardSoftLimit, is(false));
    }

    @Test
    public void enableReverseSoftLimitShouldDefault() {
      Assert.assertThat(configuration.enableReverseSoftLimit, is(false));
    }

    @Test
    public void pidKpShouldDefault() {
      Assert.assertThat(configuration.pidKp, is(0.0));
    }

    @Test
    public void pidKiShouldDefault() {
      Assert.assertThat(configuration.pidKi, is(0.0));
    }

    @Test
    public void pidKdShouldDefault() {
      Assert.assertThat(configuration.pidKd, is(0.0));
    }

    @Test
    public void pidKfShouldDefault() {
      Assert.assertThat(configuration.pidKf, is(0.0));
    }

    @Test
    public void pidIntegralZoneShouldDefault() {
      Assert.assertThat(configuration.pidIntegralZone, is(0));
    }

    @Test
    public void allowableClosedLoopErrorShouldDefault() {
      Assert.assertThat(configuration.allowableClosedLoopError, is(0));
    }

    @Test
    public void pidMaxIntegralAccumulatorShouldDefault() {
      Assert.assertThat(configuration.pidMaxIntegralAccumulator, is(0.0));
    }

    @Test
    public void closedLoopPeakOutputShouldDefault() {
      Assert.assertThat(configuration.closedLoopPeakOutput, is(1.0));
    }

    @Test
    public void closedLoopPeriodShouldDefault() {
      Assert.assertThat(configuration.closedLoopPeriod, is(1));
    }

    @Test
    public void pidInvertPolarityShouldDefault() {
      Assert.assertThat(configuration.pidInvertPolarity, is(false));
    }

    @Test
    public void motionCruiseVelocityShouldDefault() {
      Assert.assertThat(configuration.motionCruiseVelocity, is(0));
    }

    @Test
    public void motionAccelerationShouldDefault() {
      Assert.assertThat(configuration.motionAcceleration, is(0));
    }

    @Test
    public void motionProfileTrajectoryPeriodShouldDefault() {
      Assert.assertThat(configuration.motionProfileTrajectoryPeriod, is(0));
    }

    @Test
    public void peakCurrentLimitShouldDefault() {
      Assert.assertThat(configuration.peakCurrentLimit, is(0));
    }

    @Test
    public void peakCurrentLimitDurationShouldDefault() {
      Assert.assertThat(configuration.peakCurrentLimitDuration, is(0));
    }

    @Test
    public void continuousCurrentLimitShouldDefault() {
      Assert.assertThat(configuration.continuousCurrentLimit, is(0));
    }

    @Test
    public void statusFrameShouldDefault() {
      Assert.assertThat(configuration.statusFrame, is(StatusFrameEnhanced.Status_1_General));
    }

    @Test
    public void statusFramePeriodShouldDefault() {
      Assert.assertThat(configuration.statusFramePeriod, is(10));
    }

    @Test
    public void enableVoltageCompensationShouldDefault() {
      Assert.assertThat(configuration.enableVoltageCompensation, is(false));
    }

    @Test
    public void enableCurrentLimitShouldDefault() {
      Assert.assertThat(configuration.enableCurrentLimit, is(false));
    }

    @Test
    public void invertSensorPhaseShouldDefault() {
      Assert.assertThat(configuration.invertSensorPhase, is(false));
    }

    @Test
    public void invertShouldDefault() {
      Assert.assertThat(configuration.invert, is(false));
    }

    @Test
    public void neutralPowerModeShouldDefault() {
      Assert.assertThat(configuration.neutralPowerMode, is(NeutralMode.Brake));
    }
  }
}
