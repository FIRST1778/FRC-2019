package frc.robot.components;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class ElevatorTest {

  private Elevator elevator = Elevator.getInstance(false);

  @Test
  public void encoderPositionFromHeightTest() {
    assertThat((int) elevator.getEncoderPositionFromHeight(0.0), is(0));
    assertThat(
        (int)
            elevator.getEncoderPositionFromHeight(
                Elevator.HeightSetPoints.ROCKET_CARGO_HIGH.getHeightInches()),
        is(2388));
    assertThat(
        (int)
            elevator.getEncoderPositionFromHeight(
                Elevator.HeightSetPoints.FEEDER_STATION.getHeightInches()),
        is(895));
  }

  @Test
  public void heightFromEncoderPositionTest() {
    assertThat(elevator.getHeightFromEncoderPosition(0), is(0.0));
    assertThat(
        elevator.getHeightFromEncoderPosition(
            Elevator.HeightSetPoints.ROCKET_CARGO_HIGH.getHeightEncoderTicks()),
        is(80.0));
    assertThat(
        elevator.getHeightFromEncoderPosition(
            Elevator.HeightSetPoints.FEEDER_STATION.getHeightEncoderTicks()),
        is(30.0));
  }
}
