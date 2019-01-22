package frc.robot.components;

import static org.hamcrest.core.Is.is;

import org.junit.Assert;
import org.junit.Test;

public class ElevatorTest {

  private Elevator elevator = Elevator.getInstance(false);

  @Test
  public void encoderPositionFromHeightTest() {
    Assert.assertThat(
        "Home position (5.0 in) should be zero", elevator.getEncoderPositionFromHeight(5.0), is(0));
    Assert.assertThat(
        "High rocket cargo height should be 10240 encoder ticks",
        elevator.getEncoderPositionFromHeight(
            Elevator.HeightSetPoints.ROCKET_CARGO_HIGH.getHeightInches()),
        is(76800));
    Assert.assertThat(
        "Feeder station height should be 25600 encoder ticks",
        elevator.getEncoderPositionFromHeight(
            Elevator.HeightSetPoints.FEEDER_STATION.getHeightInches()),
        is(25600));
  }

  @Test
  public void heightFromEncoderPositionTest() {
    Assert.assertThat(elevator.getHeightFromEncoderPosition(0), is(5.0));
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
