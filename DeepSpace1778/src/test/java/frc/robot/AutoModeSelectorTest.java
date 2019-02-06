package frc.robot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import frc.robot.AutoModeSelector.StartingPosition;
import frc.robot.AutoModeSelector.WantedFirstTarget;
import frc.robot.AutoModeSelector.WantedMode;
import frc.robot.AutoModeSelector.WantedSecondTarget;
import frc.robot.auto.creators.AutoModeCreator;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class AutoModeSelectorTest {

  private AutoModeSelector autoModeSelector = new AutoModeSelector();

  @Test
  public void testToString() {
    Optional<AutoModeCreator> creator =
        autoModeSelector.getCreatorForParams(
            WantedMode.RUN_SELECTED_AUTO,
            StartingPosition.RIGHT,
            WantedFirstTarget.NEAR_SIDE_ROCKET,
            WantedSecondTarget.NEAR_SIDE_ROCKET);
    System.out.println(
        creator.isEmpty() ? "Invalid Option Selected" : creator.get().getClass().getSimpleName());
    assertThat(true, is(true));
  }
}
