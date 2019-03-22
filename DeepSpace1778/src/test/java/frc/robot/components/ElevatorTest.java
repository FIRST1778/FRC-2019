package frc.robot.components;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

public class ElevatorTest {

  private Elevator elevator = Elevator.getInstance(false);

  @Test
  public void encoderPositionFromHeightTest() {
    assertThat((int) elevator.getEncoderPositionFromHeight(0.0)).isEqualTo(0);
    assertThat(
            (int)
                elevator.getEncoderPositionFromHeight(
                    Elevator.HeightSetPoints.HATCH_HIGH.heightInches))
        .isEqualTo(179099);
    assertThat(
            (int)
                elevator.getEncoderPositionFromHeight(
                    Elevator.HeightSetPoints.HATCH_LOW.heightInches))
        .isEqualTo(13776);
  }

  @Test
  public void heightFromEncoderPositionTest() {
    assertThat(elevator.getHeightFromEncoderPosition(0)).isEqualTo(0.0, Offset.offset(0.001));
    assertThat(
            elevator.getHeightFromEncoderPosition(
                Elevator.HeightSetPoints.HATCH_HIGH.heightEncoderTicks))
        .isEqualTo(65.0, Offset.offset(0.001));
    assertThat(
            elevator.getHeightFromEncoderPosition(
                Elevator.HeightSetPoints.HATCH_LOW.heightEncoderTicks))
        .isEqualTo(5.0, Offset.offset(0.001));
  }
}
