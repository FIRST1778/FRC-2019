package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.lib.util.DebugLog;
import frc.robot.auto.AutoModeBase;
import frc.robot.auto.creators.*;
import java.util.Optional;

public class AutoModeSelector {
  enum StartingPosition {
    LEFT,
    CENTER,
    RIGHT
  }

  enum WantedMode {
    DO_NOTHING,
    TEST_MODE
  }

  private WantedMode cachedWantedMode = null;
  private StartingPosition cachedStartingPosition = null;

  private Optional<AutoModeCreator> creator = Optional.empty();

  private SendableChooser<WantedMode> modeChooser;
  private SendableChooser<StartingPosition> startPositionChooser;

  public AutoModeSelector() {
    modeChooser = new SendableChooser<>();
    modeChooser.addOption("Test", WantedMode.TEST_MODE);
    modeChooser.setDefaultOption("Do Nothing", WantedMode.DO_NOTHING);
    Constants.autoTab
        .add("Auto mode", modeChooser)
        .withWidget("ComboBox Chooser")
        .withPosition(2, 0)
        .withSize(2, 1);

    startPositionChooser = new SendableChooser<>();
    startPositionChooser.setDefaultOption("Left", StartingPosition.LEFT);
    startPositionChooser.addOption("Center", StartingPosition.CENTER);
    startPositionChooser.addOption("Right", StartingPosition.RIGHT);
    Constants.autoTab
        .add("Starting Position", startPositionChooser)
        .withWidget("Split Button Chooser")
        .withPosition(0, 0)
        .withSize(2, 1);
  }

  public void updateModeCreator() {
    WantedMode wantedMode = modeChooser.getSelected();
    StartingPosition startPosition = startPositionChooser.getSelected();
    if (cachedWantedMode != wantedMode || startPosition != cachedStartingPosition) {
      creator = getCreatorForParams(wantedMode, startPosition);
    }
    cachedWantedMode = wantedMode;
    cachedStartingPosition = startPosition;
  }

  private Optional<AutoModeCreator> getCreatorForParams(
      WantedMode mode, StartingPosition position) {
    switch (mode) {
      case TEST_MODE:
        return Optional.of(new TestAutoModeCreator());
      case DO_NOTHING:
        return Optional.of(new DoNothingAutoModeCreator());
      default:
        break;
    }

    DebugLog.logNote("No valid auto mode found for " + mode);
    return Optional.empty();
  }

  public void reset() {
    creator = Optional.empty();
    cachedWantedMode = null;
  }

  public Optional<AutoModeBase> getAutoMode(AutoFieldState fieldState) {
    if (!creator.isPresent()) {
      return Optional.empty();
    }
    return Optional.of(creator.get().getStateDependentAutoMode(fieldState));
  }
}
