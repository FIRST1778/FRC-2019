package frc.team1778.StateMachine;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {
		
	//  action type selection
	public static final int DO_NOTHING = 0;
	public static final int DRIVE_FORWARD = 1;
	public static final int FOLLOW_STRAIGHT_PATH = 2;
	public static final int FOLLOW_SWERVE_RIGHT_PATH = 3;
	public static final int FOLLOW_SWERVE_LEFT_PATH = 4;
	public static final int FOLLOW_TURN_LEFT_PATH = 5;
	public static final int FOLLOW_TURN_RIGHT_PATH = 6;
	public static final int FOLLOW_UTURN_LEFT_PATH = 7;
	public static final int FOLLOW_UTURN_RIGHT_PATH = 8;
	public static final int FOLLOW_CIRCLE_PATH = 9;
	

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
		chooser_action.addDefault("DO_NOTHING", new ModeSelection(DO_NOTHING));
		chooser_action.addObject("DRIVE_FORWARD", new ModeSelection(DRIVE_FORWARD));
		chooser_action.addObject("FOLLOW_STRAIGHT_PATH", new ModeSelection(FOLLOW_STRAIGHT_PATH));		
		chooser_action.addObject("FOLLOW_SWERVE_RIGHT_PATH", new ModeSelection(FOLLOW_SWERVE_RIGHT_PATH));		
		chooser_action.addObject("FOLLOW_SWERVE_LEFT_PATH", new ModeSelection(FOLLOW_SWERVE_LEFT_PATH));		
		chooser_action.addObject("FOLLOW_TURN_LEFT_PATH", new ModeSelection(FOLLOW_TURN_LEFT_PATH));		
		chooser_action.addObject("FOLLOW_TURN_RIGHT_PATH", new ModeSelection(FOLLOW_TURN_RIGHT_PATH));		
		chooser_action.addObject("FOLLOW_UTURN_LEFT_PATH", new ModeSelection(FOLLOW_UTURN_LEFT_PATH));		
		chooser_action.addObject("FOLLOW_UTURN_RIGHT_PATH", new ModeSelection(FOLLOW_UTURN_RIGHT_PATH));		
		chooser_action.addObject("FOLLOW_CIRCLE_PATH", new ModeSelection(FOLLOW_CIRCLE_PATH));		
		SmartDashboard.putData("AutoChooser_Action", chooser_action);
		
	}
	
	public int getAction() {
		
		// check action chooser
		ModeSelection action_selection = chooser_action.getSelected();
		if (action_selection.mode != DO_NOTHING)
			return action_selection.mode;	

		// default - do nothing
		return DO_NOTHING;
	}
	
}
