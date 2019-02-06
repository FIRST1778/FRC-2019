package frc.lib.driver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TalonSrxFactoryTest {

  public static class ConfigurationTest {

    private TalonSrxFactory.Configuration configuration = new TalonSrxFactory.Configuration();

    @Test
    @DisplayName("Default configuration should match CTRE defaults")
    public void configurationMatchesDefault() {
      assertThat(configuration.timeoutInMs, is(10));
      assertThat(configuration.profileSlotId, is(0));
      assertThat(configuration.openLoopRampTimeSeconds, is(0.0));
      assertThat(configuration.closedLoopRampTimeSeconds, is(0.0));
      assertThat(configuration.forwardPeakOutput, is(1.0));
      assertThat(configuration.reversePeakOutput, is(-1.0));
      assertThat(configuration.forwardNominalOutput, is(0.0));
      assertThat(configuration.reverseNominalOutput, is(0.0));
      assertThat(configuration.neutralDeadband, is(0.04));
      assertThat(configuration.voltageCompensationSaturation, is(0.0));
      assertThat(configuration.voltageMeasurementWindowFilter, is(32));
      assertThat(configuration.feedbackDevice, is(FeedbackDevice.QuadEncoder));
      assertThat(configuration.feedbackCoefficient, is(1.0));
      assertThat(configuration.velocityMeasurementPeriod, is(VelocityMeasPeriod.Period_100Ms));
      assertThat(configuration.velocityMeasurementWindow, is(64));
      assertThat(configuration.forwardLimitSwitch, is(LimitSwitchSource.Deactivated));
      assertThat(configuration.forwardLimitSwitchNormal, is(LimitSwitchNormal.NormallyOpen));
      assertThat(configuration.reverseLimitSwitch, is(LimitSwitchSource.Deactivated));
      assertThat(configuration.reverseLimitSwitchNormal, is(LimitSwitchNormal.NormallyOpen));
      assertThat(configuration.forwardSoftLimitThreshold, is(0));
      assertThat(configuration.reverseSoftLimitThreshold, is(0));
      assertThat(configuration.enableForwardSoftLimit, is(false));
      assertThat(configuration.enableReverseSoftLimit, is(false));
      assertThat(configuration.pidKp, is(0.0));
      assertThat(configuration.pidKi, is(0.0));
      assertThat(configuration.pidKd, is(0.0));
      assertThat(configuration.pidKf, is(0.0));
      assertThat(configuration.pidIntegralZone, is(0));
      assertThat(configuration.allowableClosedLoopError, is(0));
      assertThat(configuration.pidMaxIntegralAccumulator, is(0.0));
      assertThat(configuration.closedLoopPeakOutput, is(1.0));
      assertThat(configuration.closedLoopPeriod, is(1));
      assertThat(configuration.pidInvertPolarity, is(false));
      assertThat(configuration.motionCruiseVelocity, is(0));
      assertThat(configuration.motionAcceleration, is(0));
      assertThat(configuration.motionProfileTrajectoryPeriod, is(0));
      assertThat(configuration.peakCurrentLimit, is(0));
      assertThat(configuration.peakCurrentLimitDuration, is(0));
      assertThat(configuration.continuousCurrentLimit, is(0));
      assertThat(configuration.statusFrame, is(StatusFrameEnhanced.Status_1_General));
      assertThat(configuration.statusFramePeriod, is(10));
      assertThat(configuration.enableVoltageCompensation, is(false));
      assertThat(configuration.enableCurrentLimit, is(false));
      assertThat(configuration.invertSensorPhase, is(false));
      assertThat(configuration.invert, is(false));
      assertThat(configuration.neutralPowerMode, is(NeutralMode.Brake));
    }
  }
}
