package frc.StateMachine;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {

  //  action type selection
  public static final int DO_NOTHING = 0;
  public static final int DRIVE_FORWARD = 1;

	public static final int SWERVE_MOVE1 = 2;
	public static final int SWERVE_MOVE2 = 3;
  public static final int SWERVE_MOVE3 = 4;
  
	public static final int DRIVE_AND_ROTATE_PATH1 = 5;
	public static final int DRIVE_AND_ROTATE_PATH2 = 6;
	public static final int DRIVE_AND_ROTATE_PATH3 = 7;
	public static final int DRIVE_AND_ROTATE_PATH4 = 8;
	public static final int DRIVE_AND_ROTATE_PATH5 = 9;
	public static final int DRIVE_AND_ROTATE_PATH6 = 10;

  // internal selection class used for SendableChooser only
  public class ModeSelection {
    public int mode = DO_NOTHING;

    ModeSelection(int mode) {
      this.mode = mode;
    }
  }

  int mode;

  private SendableChooser<ModeSelection> chooser_action;

  public AutoChooser() {

    // action chooser setup
    chooser_action = new SendableChooser<ModeSelection>();

    chooser_action.setDefaultOption("DO_NOTHING", new ModeSelection(DO_NOTHING));
    chooser_action.addOption("DRIVE_FORWARD", new ModeSelection(DRIVE_FORWARD));

    chooser_action.addOption("SWERVE_MOVE1", new ModeSelection(SWERVE_MOVE1));
    chooser_action.addOption("SWERVE_MOVE2", new ModeSelection(SWERVE_MOVE2));
    chooser_action.addOption("SWERVE_MOVE3", new ModeSelection(SWERVE_MOVE3));

    chooser_action.addOption("DRIVE_AND_ROTATE_PATH1", new ModeSelection(DRIVE_AND_ROTATE_PATH1));
    chooser_action.addOption("DRIVE_AND_ROTATE_PATH2", new ModeSelection(DRIVE_AND_ROTATE_PATH2));
    chooser_action.addOption("DRIVE_AND_ROTATE_PATH3", new ModeSelection(DRIVE_AND_ROTATE_PATH3));
    chooser_action.addOption("DRIVE_AND_ROTATE_PATH4", new ModeSelection(DRIVE_AND_ROTATE_PATH4));
    chooser_action.addOption("DRIVE_AND_ROTATE_PATH5", new ModeSelection(DRIVE_AND_ROTATE_PATH5));
    chooser_action.addOption("DRIVE_AND_ROTATE_PATH6", new ModeSelection(DRIVE_AND_ROTATE_PATH6));

    SmartDashboard.putData("AutoChooser_Action", chooser_action);

  }

  public int getAction() {

    // check action chooser
    ModeSelection action_selection = chooser_action.getSelected();
    if (action_selection.mode != DO_NOTHING) return action_selection.mode;

    // default - do nothing
    return DO_NOTHING;
  }

}
