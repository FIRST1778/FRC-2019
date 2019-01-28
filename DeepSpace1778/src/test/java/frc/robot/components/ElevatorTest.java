package frc.robot.components;

import static org.hamcrest.core.Is.is;

import org.junit.Assert;
import org.junit.Test;

public class ElevatorTest {

  private Elevator elevator = Elevator.getInstance(false);

  @Test
  public void encoderPositionFromHeightTest() {
    Assert.assertThat((int) elevator.getEncoderPositionFromHeight(0.0), is(0));
    Assert.assertThat(
        (int)
            elevator.getEncoderPositionFromHeight(
                Elevator.HeightSetPoints.ROCKET_CARGO_HIGH.getHeightInches()),
        is(2388));
    Assert.assertThat(
        (int)
            elevator.getEncoderPositionFromHeight(
                Elevator.HeightSetPoints.FEEDER_STATION.getHeightInches()),
        is(895));
  }

  @Test
  public void heightFromEncoderPositionTest() {
    Assert.assertThat(elevator.getHeightFromEncoderPosition(0), is(0.0));
    Assert.assertThat(
        elevator.getHeightFromEncoderPosition(
            Elevator.HeightSetPoints.ROCKET_CARGO_HIGH.getHeightEncoderTicks()),
        is(80.0));
    Assert.assertThat(
        elevator.getHeightFromEncoderPosition(
            Elevator.HeightSetPoints.FEEDER_STATION.getHeightEncoderTicks()),
        is(30.0));
  }
}
