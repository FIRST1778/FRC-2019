package frc.robot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import frc.robot.AutoModeSelector.StartingPosition;
import frc.robot.AutoModeSelector.WantedFirstTarget;
import frc.robot.AutoModeSelector.WantedMode;
import frc.robot.AutoModeSelector.WantedSecondTarget;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.modes.DualNearSideRocketMode;
import frc.robot.auto.modes.NearSideRocketAndCargoBay;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class AutoModeSelectorTest {

  private AutoModeSelector autoModeSelector = new AutoModeSelector(false);

  @Test
  @DisplayName("Should throw NoSuchElementException when an auto that does not exist is wanted")
  public void testWhenNoAutonomousExistsForSelectedParameters() {
    Optional<AutoModeBase> mode =
        autoModeSelector.getModeForParams(
            WantedMode.RUN_SELECTED_AUTO,
            StartingPosition.CENTER,
            WantedFirstTarget.NEAR_SIDE_ROCKET,
            WantedSecondTarget.NEAR_SIDE_ROCKET,
            false);
    assertThrows(
        NoSuchElementException.class,
        new Executable() {
          @Override
          public void execute() throws Throwable {
            mode.get();
          }
        });
  }

  @Test
  @DisplayName("A DualNearSideRocketMode should be returned for both left and right positions")
  public void testDualNearSideRocketMode() {
    Optional<AutoModeBase> mode =
        autoModeSelector.getModeForParams(
            WantedMode.RUN_SELECTED_AUTO,
            StartingPosition.LEFT,
            WantedFirstTarget.NEAR_SIDE_ROCKET,
            WantedSecondTarget.NEAR_SIDE_ROCKET,
            false);
    System.out.println(mode.get().getClass().getName());
    assertThat(mode.get().getClass(), is(DualNearSideRocketMode.class));

    mode =
        autoModeSelector.getModeForParams(
            WantedMode.RUN_SELECTED_AUTO,
            StartingPosition.RIGHT,
            WantedFirstTarget.NEAR_SIDE_ROCKET,
            WantedSecondTarget.NEAR_SIDE_ROCKET,
            false);
    System.out.println(mode.get().getClass().getName());
    assertThat(mode.get().getClass(), is(DualNearSideRocketMode.class));
  }

  @Test
  @DisplayName("A NearSideRocketAndCargo should be returned for both left and right positions")
  public void testNearSideRocketAndCargo() {
    Optional<AutoModeBase> mode =
        autoModeSelector.getModeForParams(
            WantedMode.RUN_SELECTED_AUTO,
            StartingPosition.LEFT,
            WantedFirstTarget.NEAR_SIDE_ROCKET,
            WantedSecondTarget.CARGO_BAY,
            false);
    System.out.println(mode.get().getClass().getName());
    assertThat(mode.get().getClass(), is(NearSideRocketAndCargoBay.class));

    mode =
        autoModeSelector.getModeForParams(
            WantedMode.RUN_SELECTED_AUTO,
            StartingPosition.RIGHT,
            WantedFirstTarget.NEAR_SIDE_ROCKET,
            WantedSecondTarget.CARGO_BAY,
            false);
    System.out.println(mode.get().getClass().getName());
    assertThat(mode.get().getClass(), is(NearSideRocketAndCargoBay.class));
  }
}
