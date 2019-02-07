package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.modes.DoNothingMode;
import frc.robot.auto.modes.DualNearSideRocketMode;
import frc.robot.auto.modes.MotionTestMode;
import frc.robot.auto.modes.NearSideRocketAndCargoBay;
import java.util.Optional;

/**
 * Handles selecting an autonomous mode based on input from SendableChoosers in Shuffleboard. This
 * returns an AutoMode that can be run.
 *
 * @author FRC 1778 Chill Out
 */
public class AutoModeSelector {

  public enum StartingPosition {
    LEFT,
    CENTER,
    RIGHT
  }

  public enum WantedMode {
    DO_NOTHING,
    TEST_MODE,
    RUN_SELECTED_AUTO
  }

  public enum WantedFirstTarget {
    NEAR_SIDE_ROCKET,
    CARGO_BAY
  }

  public enum WantedSecondTarget {
    NOTHING,
    NEAR_SIDE_ROCKET,
    FAR_SIDE_ROCKET,
    CARGO_BAY
  }

  private WantedMode cachedWantedMode = null;
  private StartingPosition cachedStartingPosition = null;
  private WantedFirstTarget cachedWantedFirstTarget = null;
  private WantedSecondTarget cachedWantedSecondTarget = null;
  private int cargoBaySelectedPosition;

  boolean shuffleboardEnabled;

  private Optional<AutoModeBase> mode = Optional.empty();

  private SendableChooser<WantedMode> modeChooser;
  private SendableChooser<StartingPosition> startPositionChooser;
  private SendableChooser<WantedFirstTarget> firstTargetChooser;
  private SendableChooser<WantedSecondTarget> secondTargetChooser;
  private NetworkTableEntry feedbackEntry;

  public AutoModeSelector() {
    this(true);
  }

  public AutoModeSelector(boolean useShuffleboard) {
    shuffleboardEnabled = useShuffleboard;
    if (shuffleboardEnabled) {
      startPositionChooser = new SendableChooser<>();
      startPositionChooser.setDefaultOption(
          StartingPosition.RIGHT.toString(), StartingPosition.RIGHT);
      for (StartingPosition startingPosition : StartingPosition.values()) {
        if (startingPosition == StartingPosition.RIGHT) {
          continue;
        }
        startPositionChooser.addOption(startingPosition.toString(), startingPosition);
      }
      Constants.autoTab
          .add("Starting Position", startPositionChooser)
          .withWidget(BuiltInWidgets.kSplitButtonChooser)
          .withPosition(0, 0)
          .withSize(2, 1);

      modeChooser = new SendableChooser<>();
      modeChooser.setDefaultOption(WantedMode.DO_NOTHING.toString(), WantedMode.DO_NOTHING);
      for (WantedMode mode : WantedMode.values()) {
        if (mode == WantedMode.DO_NOTHING) {
          continue;
        }
        modeChooser.addOption(mode.toString(), mode);
      }
      Constants.autoTab
          .add("Auto mode", modeChooser)
          .withWidget(BuiltInWidgets.kComboBoxChooser)
          .withPosition(2, 0)
          .withSize(2, 1);

      firstTargetChooser = new SendableChooser<>();
      firstTargetChooser.setDefaultOption(
          WantedFirstTarget.NEAR_SIDE_ROCKET.toString(), WantedFirstTarget.NEAR_SIDE_ROCKET);
      for (WantedFirstTarget firstTarget : WantedFirstTarget.values()) {
        if (firstTarget == WantedFirstTarget.NEAR_SIDE_ROCKET) {
          continue;
        }
        firstTargetChooser.addOption(firstTarget.toString(), firstTarget);
      }
      Constants.autoTab
          .add("First target", firstTargetChooser)
          .withWidget(BuiltInWidgets.kComboBoxChooser)
          .withPosition(0, 1)
          .withSize(2, 1);

      secondTargetChooser = new SendableChooser<>();
      secondTargetChooser.setDefaultOption(
          WantedSecondTarget.NEAR_SIDE_ROCKET.toString(), WantedSecondTarget.NEAR_SIDE_ROCKET);
      for (WantedSecondTarget secondTarget : WantedSecondTarget.values()) {
        if (secondTarget == WantedSecondTarget.NEAR_SIDE_ROCKET) {
          continue;
        }
        secondTargetChooser.addOption(secondTarget.toString(), secondTarget);
      }
      Constants.autoTab
          .add("Second target", secondTargetChooser)
          .withWidget(BuiltInWidgets.kComboBoxChooser)
          .withPosition(2, 1)
          .withSize(2, 1);

      feedbackEntry =
          Constants.autoTab
              .add("Selected mode", "")
              .withWidget(BuiltInWidgets.kTextView)
              .withPosition(0, 2)
              .withSize(4, 1)
              .getEntry();
    }
  }

  public void updateModeCreator() {
    StartingPosition startPosition = startPositionChooser.getSelected();
    WantedMode wantedMode = modeChooser.getSelected();
    WantedFirstTarget wantedFirstTarget = firstTargetChooser.getSelected();
    WantedSecondTarget wantedSecondTarget = secondTargetChooser.getSelected();

    cargoBaySelectedPosition = DriverStation.getInstance().getLocation();
    if (cachedWantedMode != wantedMode
        || startPosition != cachedStartingPosition
        || cachedWantedFirstTarget != wantedFirstTarget
        || cachedWantedSecondTarget != wantedSecondTarget) {
      mode =
          getModeForParams(wantedMode, startPosition, wantedFirstTarget, wantedSecondTarget, true);
    }
    cachedWantedMode = wantedMode;
    cachedStartingPosition = startPosition;
    cachedWantedFirstTarget = wantedFirstTarget;
    cachedWantedSecondTarget = wantedSecondTarget;
  }

  public Optional<AutoModeBase> getModeForParams(
      WantedMode wantedMode,
      StartingPosition position,
      WantedFirstTarget firstTarget,
      WantedSecondTarget secondTarget,
      boolean useShuffleboard) {
    Optional<AutoModeBase> mode;
    String invalidMessage = "";
    switch (wantedMode) {
      case TEST_MODE:
        mode = Optional.of(new MotionTestMode());
        break;
      case DO_NOTHING:
        mode = Optional.of(new DoNothingMode());
        break;
      case RUN_SELECTED_AUTO:
        switch (position) {
          case LEFT:
            switch (firstTarget) {
              case NEAR_SIDE_ROCKET:
                switch (secondTarget) {
                  case NEAR_SIDE_ROCKET:
                    mode = Optional.of(new DualNearSideRocketMode(position));
                    break;
                  case FAR_SIDE_ROCKET:
                    mode = Optional.empty();
                    invalidMessage = "NearSideRocketAndFarSideRocket is not implemented yet";
                    break;
                  case CARGO_BAY:
                    mode =
                        Optional.of(
                            new NearSideRocketAndCargoBay(position, cargoBaySelectedPosition));
                    break;
                  default:
                    mode = Optional.empty();
                    break;
                }
                break;
              case CARGO_BAY:
                switch (secondTarget) {
                  case NEAR_SIDE_ROCKET:
                    mode = Optional.empty();
                    invalidMessage = "CargoBayAndNearSideRocket is not implemented yet";
                    break;
                  case FAR_SIDE_ROCKET:
                    mode = Optional.empty();
                    invalidMessage = "CargoBayAndFarSideRocket is not implemented yet";
                    break;
                  case CARGO_BAY:
                    mode = Optional.empty();
                    invalidMessage = "DualCargoBay is not implemented yet";
                    break;
                  default:
                    mode = Optional.empty();
                    break;
                }
                break;
              default:
                mode = Optional.empty();
                break;
            }
            break;
          case CENTER:
            switch (firstTarget) {
              case NEAR_SIDE_ROCKET:
                mode = Optional.empty();
                invalidMessage = "Can not target rocket when starting from center";
                break;
              case CARGO_BAY:
                switch (secondTarget) {
                  case NEAR_SIDE_ROCKET:
                    mode = Optional.empty();
                    invalidMessage = "Can not target rocket when starting from center";
                    break;
                  case FAR_SIDE_ROCKET:
                    mode = Optional.empty();
                    invalidMessage = "Can not target rocket when starting from center";
                    break;
                  case CARGO_BAY:
                    mode = Optional.empty();
                    invalidMessage = "DualCargoBay is not implemented yet";
                    break;
                  default:
                    mode = Optional.empty();
                    break;
                }
                break;
              default:
                mode = Optional.empty();
                break;
            }
            break;
          case RIGHT:
            switch (firstTarget) {
              case NEAR_SIDE_ROCKET:
                switch (secondTarget) {
                  case NEAR_SIDE_ROCKET:
                    mode = Optional.of(new DualNearSideRocketMode(position));
                    break;
                  case FAR_SIDE_ROCKET:
                    mode = Optional.empty();
                    invalidMessage = "NearSideRocketAndFarSideRocket is not implemented yet";
                    break;
                  case CARGO_BAY:
                    mode =
                        Optional.of(
                            new NearSideRocketAndCargoBay(position, cargoBaySelectedPosition));
                    break;
                  default:
                    mode = Optional.empty();
                    break;
                }
                break;
              case CARGO_BAY:
                switch (secondTarget) {
                  case NEAR_SIDE_ROCKET:
                    mode = Optional.empty();
                    invalidMessage = "CargoBayAndNearSideRocket is not implemented yet";
                    break;
                  case FAR_SIDE_ROCKET:
                    mode = Optional.empty();
                    invalidMessage = "CargoBayAndFarSideRocket is not implemented yet";
                    break;
                  case CARGO_BAY:
                    mode = Optional.empty();
                    invalidMessage = "DualCargoBay is not implemented yet";
                    break;
                  default:
                    mode = Optional.empty();
                    break;
                }
                break;
              default:
                mode = Optional.empty();
                break;
            }
            break;
          default:
            mode = Optional.empty();
            break;
        }
        break;
      default:
        mode = Optional.empty();
        break;
    }

    if (shuffleboardEnabled) {
      feedbackEntry.setString(
          (mode.isEmpty() ? "Invalid config" : mode.get().getClass().getSimpleName())
              + " for "
              + position.toString()
              + ". "
              + invalidMessage);
    } else {
      System.out.println(
          (mode.isEmpty() ? "Invalid config" : mode.get().getClass().getSimpleName())
              + " for "
              + position.toString()
              + ". "
              + invalidMessage);
    }

    return mode;
  }

  public void reset() {
    mode = Optional.empty();
    cachedWantedMode = null;
  }

  public Optional<AutoModeBase> getAutoMode() {
    if (!mode.isPresent()) {
      return Optional.empty();
    }
    return Optional.of(mode.get());
  }
}
