package frc.StateMachine;

import java.util.ArrayList;

import frc.Systems.FreezyPath;
import frc.StateMachine.AutoChooser;

public class AutoNetworkBuilder {
			
	// closed-loop position cruise velocity and acceleration (used for all closed-loop position control)
	// units are RPM
	
	// ~3 ft/s - FAST
	//private final static int CLOSED_LOOP_VEL_FAST = 900;
	//private final static int CLOSED_LOOP_ACCEL_FAST = 300;

	// ~2 ft/s - SLOW
	private final static int CLOSED_LOOP_VEL_SLOW = 850; 
	private final static int CLOSED_LOOP_ACCEL_SLOW = 300;

	// ~1 ft/s - VERY SLOW
	//private final static int CLOSED_LOOP_VEL_VERY_SLOW = 400; 
	//private final static int CLOSED_LOOP_ACCEL_VERY_SLOW = 200;
	
	
	private static ArrayList<AutoNetwork> autoNets;
	
	private static boolean initialized = false;
		
	public static void initialize() {
		
		if (!initialized) {
			autoNets = null;
			
			initialized = true;
		}
	}
	
	public static ArrayList<AutoNetwork> readInNetworks() {
		
		if (!initialized)
			initialize();
		
		autoNets = new ArrayList<AutoNetwork>();
							
		// create networks
		autoNets.add(AutoChooser.DO_NOTHING, createDoNothingNetwork());	
		autoNets.add(AutoChooser.DRIVE_FORWARD, createDriveForward());	

		autoNets.add(AutoChooser.FORWARD_STRAIGHT_PATH, createFollowSinglePathNetwork(FreezyPath.STRAIGHT_PATH, true));	
		autoNets.add(AutoChooser.FORWARD_SWERVE_RIGHT_PATH, createFollowSinglePathNetwork(FreezyPath.SWERVE_RIGHT_AND_CENTER, true));	
		autoNets.add(AutoChooser.FORWARD_SWERVE_LEFT_PATH, createFollowSinglePathNetwork(FreezyPath.SWERVE_LEFT_AND_CENTER, true));
		autoNets.add(AutoChooser.FORWARD_TURN_LEFT_PATH, createFollowSinglePathNetwork(FreezyPath.SWERVE_RIGHT_TURN_LEFT, true));	
		autoNets.add(AutoChooser.FORWARD_TURN_RIGHT_PATH, createFollowSinglePathNetwork(FreezyPath.SWERVE_LEFT_TURN_RIGHT, true));

		autoNets.add(AutoChooser.REVERSE_STRAIGHT_PATH, createFollowSinglePathNetwork(FreezyPath.STRAIGHT_PATH, false));	
		autoNets.add(AutoChooser.REVERSE_SWERVE_RIGHT_PATH, createFollowSinglePathNetwork(FreezyPath.SWERVE_RIGHT_AND_CENTER, false));	
		autoNets.add(AutoChooser.REVERSE_SWERVE_LEFT_PATH, createFollowSinglePathNetwork(FreezyPath.SWERVE_LEFT_AND_CENTER, false));
		autoNets.add(AutoChooser.REVERSE_TURN_LEFT_PATH, createFollowSinglePathNetwork(FreezyPath.SWERVE_RIGHT_TURN_LEFT, false));	
		autoNets.add(AutoChooser.REVERSE_TURN_RIGHT_PATH, createFollowSinglePathNetwork(FreezyPath.SWERVE_LEFT_TURN_RIGHT, false));

		autoNets.add(AutoChooser.DOUBLE_PATH_1, createFollowDoublePathNetwork(FreezyPath.STRAIGHT_PATH, true,
																						FreezyPath.STRAIGHT_PATH, false));
		autoNets.add(AutoChooser.DOUBLE_PATH_2, createFollowDoublePathNetwork(FreezyPath.SWERVE_RIGHT_AND_CENTER, true,
																						FreezyPath.SWERVE_RIGHT_AND_CENTER, false));

		return autoNets;
	}
	
	///////////////////////////////////////////////////////////
	/*            AutoState Creation Methods                 */
	/*              Single Action States                     */
	/*            (These are used repeatedly)                */
	///////////////////////////////////////////////////////////
	
	private static AutoState createIdleState(String state_name)
	{
		AutoState idleState = new AutoState(state_name);
		IdleAction deadEnd = new IdleAction("<Dead End Action>");
		DriveForwardAction driveForwardReset = new DriveForwardAction("<Drive Forward Action -reset>", 0.0, true, 0.0);  // reset gyro
		idleState.addAction(deadEnd);
		idleState.addAction(driveForwardReset);
		
		return idleState;
	}
	
	private static AutoState createMagicDriveState(String state_name, double dist_inches, double error_inches, int max_vel_rpm, int max_accel_rpm) 
	{		
		AutoState driveState = new AutoState(state_name);
		DriveForwardMagicAction driveForwardMagicAction = new DriveForwardMagicAction("<Drive Forward Magic Action>", dist_inches, max_vel_rpm, max_accel_rpm, true, 0.0);
		//TimeEvent timer = new TimeEvent(2.5);  // drive forward timer event - allow PID time to settle
		ClosedLoopPositionEvent pos = new ClosedLoopPositionEvent(dist_inches, error_inches, 0.6);
		driveState.addAction(driveForwardMagicAction);
		//driveState.addEvent(timer);
		driveState.addEvent(pos);
		
		return driveState;
	}
	
	private static AutoState createTimerState(String state_name, double timer_sec)
	{
		AutoState timerState = new AutoState(state_name);
		IdleAction deadEnd = new IdleAction("<Dead End Action>");
		DriveForwardAction driveForwardReset = new DriveForwardAction("<Drive Forward Action -reset>", 0.0, true, 0.0);  // reset gyro
		TimeEvent timer = new TimeEvent(timer_sec);
		timerState.addAction(deadEnd);
		timerState.addAction(driveForwardReset);
		timerState.addEvent(timer);
		
		return timerState;
	}
			
	////////////////////////////////////////////////////////////


	// **** DO NOTHING Network ***** 
	private static AutoNetwork createDoNothingNetwork() {
		
		AutoNetwork autoNet = new AutoNetwork("<Do Nothing Network>");
		
		AutoState idleState = new AutoState("<Idle State>");
		IdleAction deadEnd = new IdleAction("<Dead End Action>");
		idleState.addAction(deadEnd);

		autoNet.addState(idleState);	
		
		return autoNet;
	}

	// **** MOVE FORWARD Network ***** 
	// 1) drive forward for a number of sec
	// 2) go back to idle and stay there 
	private static AutoNetwork createDriveForward() {
		
		AutoNetwork autoNet = new AutoNetwork("<Drive Forward Network>");
	
		// create states
		AutoState driveState = createMagicDriveState("<Drive State 1>", 120.0, 3.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
		AutoState idleState = createIdleState("<Idle State>");

		// connect the state sequence
		driveState.associateNextState(idleState);
						
		// add states to the network list
		autoNet.addState(driveState);
		autoNet.addState(idleState);
				
		return autoNet;
	}
		
	// **** FOLLOW SINGLE PATH Network ***** 
	// 1) follow a specified path until complete
	// 2) go back to idle and stay there 
	private static AutoNetwork createFollowSinglePathNetwork(int pathToFollow, boolean fwdPolarity) {
		
		AutoNetwork autoNet = new AutoNetwork("<Follow Single Path Network>");
	
		// create states
		AutoState followPathState = new AutoState("<Follow Path State>");
		FollowPathAction follow = new FollowPathAction("<Follow Path Action>", pathToFollow, fwdPolarity);
		PathCompleteEvent pathComplete = new PathCompleteEvent();
		followPathState.addAction(follow);
		followPathState.addEvent(pathComplete);
		
		AutoState idleState = createIdleState("<Idle State>");

		// connect the state sequence
		followPathState.associateNextState(idleState);
						
		// add states to the network list
		autoNet.addState(followPathState);
		autoNet.addState(idleState);
				
		return autoNet;
	}

	// **** FOLLOW DOUBLE PATH Network ***** 
	// 1) follow a specified path until complete
	// 2) follow a specified path until complete
	// 3) go back to idle and stay there 
	private static AutoNetwork createFollowDoublePathNetwork(int path1, boolean path1FwdPolarity, int path2, boolean path2FwdPolarity) {
		
		AutoNetwork autoNet = new AutoNetwork("<Follow Double Path Network>");
	
		// create states
		AutoState followPathState1 = new AutoState("<Follow Path State 1>");
		FollowPathAction follow1 = new FollowPathAction("<Follow Path 1 Action>", path1, path1FwdPolarity);
		PathCompleteEvent pathComplete1 = new PathCompleteEvent();
		followPathState1.addAction(follow1);
		followPathState1.addEvent(pathComplete1);

		AutoState followPathState2 = new AutoState("<Follow Path State 2>");
		FollowPathAction follow2 = new FollowPathAction("<Follow Path 2 Action>", path2, path2FwdPolarity);
		PathCompleteEvent pathComplete2 = new PathCompleteEvent();
		followPathState2.addAction(follow2);
		followPathState2.addEvent(pathComplete2);

		AutoState idleState = createIdleState("<Idle State>");

		// connect the state sequence
		followPathState1.associateNextState(followPathState2);
		followPathState2.associateNextState(idleState);
						
		// add states to the network list
		autoNet.addState(followPathState1);
		autoNet.addState(followPathState2);
		autoNet.addState(idleState);
				
		return autoNet;
	}

	
	/*****************************************************************************************************/
	/**** DEBUG NETWORKS **** Networks below this are used only for debug - disable during competition ***/
	/*****************************************************************************************************/	

	
	// **** Test Network - does nothing except transitions states ***** 
	private static AutoNetwork createTestNetwork() {
		
		AutoNetwork autoNet = new AutoNetwork("<Test Network>");
		
		AutoState idleState = new AutoState("<Idle State 1>");
		IdleAction startIdle = new IdleAction("<Start Idle Action 1>");
		IdleAction doSomething2 = new IdleAction("<Placeholder Action 2>");
		IdleAction doSomething3 = new IdleAction("<Placeholder Action 3>");
		TimeEvent timer1 = new TimeEvent(10.0);  // timer event
		idleState.addAction(startIdle);
		idleState.addAction(doSomething2);
		idleState.addAction(doSomething3);
		idleState.addEvent(timer1);
		
		AutoState idleState2 = new AutoState("<Idle State 2>");
		IdleAction startIdle2 = new IdleAction("<Start Idle Action 2>");
		IdleAction doSomething4 = new IdleAction("<Placeholder Action 4>");
		IdleAction doSomething5 = new IdleAction("<Placeholder Action 5>");
		TimeEvent timer2 = new TimeEvent(10.0);  // timer event
		idleState2.addAction(startIdle2);
		idleState2.addAction(doSomething4);
		idleState2.addAction(doSomething5);
		idleState2.addEvent(timer2);
		
		AutoState idleState3 = new AutoState("<Idle State 3>");
		IdleAction startIdle3 = new IdleAction("<Start Idle Action 3>");
		IdleAction doSomething6 = new IdleAction("<Placeholder Action 6>");
		IdleAction doSomething7 = new IdleAction("<Placeholder Action 7>");
		TimeEvent timer3 = new TimeEvent(10.0);  // timer event
		idleState3.addAction(startIdle3);
		idleState3.addAction(doSomething6);
		idleState3.addAction(doSomething7);
		idleState3.addEvent(timer3);
		
		AutoState idleState4 = new AutoState("<Idle State 4>");
		IdleAction deadEnd = new IdleAction("<Dead End Action>");
		idleState4.addAction(deadEnd);
				
		// connect each event with a state to move to
		idleState.associateNextState(idleState2);
		idleState2.associateNextState(idleState3);
		idleState3.associateNextState(idleState4);
						
		autoNet.addState(idleState);
		autoNet.addState(idleState2);
		autoNet.addState(idleState3);
		autoNet.addState(idleState4);
				
		return autoNet;
	}
		
}
