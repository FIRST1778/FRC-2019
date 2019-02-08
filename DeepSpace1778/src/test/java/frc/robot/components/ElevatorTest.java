package frc.robot.components;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ElevatorTest {

  private Elevator elevator = Elevator.getInstance(false);

  @Test
  public void encoderPositionFromHeightTest() {
    assertThat((int) elevator.getEncoderPositionFromHeight(0.0)).isEqualTo(0);
    assertThat(
            (int)
                elevator.getEncoderPositionFromHeight(
                    Elevator.HeightSetPoints.ROCKET_CARGO_HIGH.getHeightInches()))
        .isEqualTo(2388);
    assertThat(
            (int)
                elevator.getEncoderPositionFromHeight(
                    Elevator.HeightSetPoints.FEEDER_STATION.getHeightInches()))
        .isEqualTo(895);
  }

  @Test
  public void heightFromEncoderPositionTest() {
    assertThat(elevator.getHeightFromEncoderPosition(0)).isEqualTo(0.0);
    assertThat(
            elevator.getHeightFromEncoderPosition(
                Elevator.HeightSetPoints.ROCKET_CARGO_HIGH.getHeightEncoderTicks()))
        .isEqualTo(80.0);
    assertThat(
            elevator.getHeightFromEncoderPosition(
                Elevator.HeightSetPoints.FEEDER_STATION.getHeightEncoderTicks()))
        .isEqualTo(30.0);
  }
}
