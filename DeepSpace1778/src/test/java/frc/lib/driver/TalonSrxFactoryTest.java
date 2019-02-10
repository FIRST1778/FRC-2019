package frc.lib.driver;

import static org.assertj.core.api.Assertions.assertThat;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TalonSrxFactoryTest {

  public static class ConfigurationTest {

    private TalonSrxFactory.Configuration configuration = new TalonSrxFactory.Configuration();

    @Test
    @DisplayName("Default configuration should match CTRE defaults")
    public void configurationMatchesDefault() {
      assertThat(configuration.timeoutInMs).isEqualTo(10);
      assertThat(configuration.profileSlotId).isEqualTo(0);
      assertThat(configuration.openLoopRampTimeSeconds).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.closedLoopRampTimeSeconds).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.forwardPeakOutput).isEqualTo(1.0, Offset.offset(0.001));
      assertThat(configuration.reversePeakOutput).isEqualTo(-1.0, Offset.offset(0.001));
      assertThat(configuration.forwardNominalOutput).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.reverseNominalOutput).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.neutralDeadband).isEqualTo(0.04, Offset.offset(0.001));
      assertThat(configuration.voltageCompensationSaturation).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.voltageMeasurementWindowFilter).isEqualTo(32);
      assertThat(configuration.feedbackDevice).isEqualTo(FeedbackDevice.QuadEncoder);
      assertThat(configuration.feedbackCoefficient).isEqualTo(1.0, Offset.offset(0.001));
      assertThat(configuration.velocityMeasurementPeriod)
          .isEqualTo(VelocityMeasPeriod.Period_100Ms);
      assertThat(configuration.velocityMeasurementWindow).isEqualTo(64);
      assertThat(configuration.forwardLimitSwitch).isEqualTo(LimitSwitchSource.Deactivated);
      assertThat(configuration.forwardLimitSwitchNormal).isEqualTo(LimitSwitchNormal.NormallyOpen);
      assertThat(configuration.reverseLimitSwitch).isEqualTo(LimitSwitchSource.Deactivated);
      assertThat(configuration.reverseLimitSwitchNormal).isEqualTo(LimitSwitchNormal.NormallyOpen);
      assertThat(configuration.forwardSoftLimitThreshold).isEqualTo(0);
      assertThat(configuration.reverseSoftLimitThreshold).isEqualTo(0);
      assertThat(configuration.enableForwardSoftLimit).isEqualTo(false);
      assertThat(configuration.enableReverseSoftLimit).isEqualTo(false);
      assertThat(configuration.pidKp).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.pidKi).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.pidKd).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.pidKf).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.pidIntegralZone).isEqualTo(0);
      assertThat(configuration.allowableClosedLoopError).isEqualTo(0);
      assertThat(configuration.pidMaxIntegralAccumulator).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(configuration.closedLoopPeakOutput).isEqualTo(1.0, Offset.offset(0.001));
      assertThat(configuration.closedLoopPeriod).isEqualTo(1);
      assertThat(configuration.pidInvertPolarity).isEqualTo(false);
      assertThat(configuration.motionCruiseVelocity).isEqualTo(0);
      assertThat(configuration.motionAcceleration).isEqualTo(0);
      assertThat(configuration.motionProfileTrajectoryPeriod).isEqualTo(0);
      assertThat(configuration.peakCurrentLimit).isEqualTo(0);
      assertThat(configuration.peakCurrentLimitDuration).isEqualTo(0);
      assertThat(configuration.continuousCurrentLimit).isEqualTo(0);
      assertThat(configuration.statusFrame).isEqualTo(StatusFrameEnhanced.Status_1_General);
      assertThat(configuration.statusFramePeriod).isEqualTo(10);
      assertThat(configuration.enableVoltageCompensation).isEqualTo(false);
      assertThat(configuration.enableCurrentLimit).isEqualTo(false);
      assertThat(configuration.invertSensorPhase).isEqualTo(false);
      assertThat(configuration.invert).isEqualTo(false);
      assertThat(configuration.neutralPowerMode).isEqualTo(NeutralMode.Brake);
    }
  }
}
