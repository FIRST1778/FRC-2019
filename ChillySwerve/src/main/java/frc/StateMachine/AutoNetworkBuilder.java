package frc.StateMachine;

import frc.Systems.FreezyPath;
import java.util.ArrayList;

public class AutoNetworkBuilder {

  public static final int DO_NOTHING = 0;
  public static final int DRIVE_FORWARD = 1;

  public static final int SWERVE_MOVE1 = 2;
  public static final int SWERVE_MOVE2 = 3;
  public static final int SWERVE_MOVE3 = 4;

  //public static final int FOLLOW_PATH1 = 10;
  //public static final int FOLLOW_PATH2 = 11;
  //public static final int FOLLOW_PATH3 = 12;

  // closed-loop position cruise velocity and acceleration (used for all closed-loop position
  // control)
  // units are RPM

  // ~3 ft/s - FAST
  //private static final int CLOSED_LOOP_VEL_FAST = 900;
  //private static final int CLOSED_LOOP_ACCEL_FAST = 300;

  // ~2 ft/s - SLOW
  private static final int CLOSED_LOOP_VEL_SLOW = 850;
  private static final int CLOSED_LOOP_ACCEL_SLOW = 300;

  // ~1 ft/s - VERY SLOW
  //private static final int CLOSED_LOOP_VEL_VERY_SLOW = 400;
  //private static final int CLOSED_LOOP_ACCEL_VERY_SLOW = 200;

  private static ArrayList<AutoNetwork> autoNets;

  private static boolean initialized = false;

  public static void initialize() {

    if (!initialized) {
      autoNets = null;

      initialized = true;
    }
  }

  public static ArrayList<AutoNetwork> readInNetworks() {

    if (!initialized) initialize();

    autoNets = new ArrayList<AutoNetwork>();

		// create networks
		autoNets.add(AutoChooser.DO_NOTHING, createDoNothingNetwork());	
		autoNets.add(AutoChooser.DRIVE_FORWARD, createDriveForward());	
		autoNets.add(AutoChooser.SWERVE_MOVE1, createSwerveMove1());	
		autoNets.add(AutoChooser.SWERVE_MOVE2, createSwerveMove2());	
		autoNets.add(AutoChooser.SWERVE_MOVE3, createSwerveMove3());	

    /*
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
    */

    return autoNets;
  }

  ///////////////////////////////////////////////////////////
  /*            AutoState Creation Methods                 */
  /*              Single Action States                     */
  /*            (These are used repeatedly)                */
  ///////////////////////////////////////////////////////////

  private static AutoState createIdleState(String state_name) {
    AutoState idleState = new AutoState(state_name);
    IdleAction deadEnd = new IdleAction("<Dead End Action>");
    DriveForwardAction driveForwardReset =
        new DriveForwardAction("<Drive Forward Action -reset>", 0.0, 0.0, 0, 0);
    idleState.addAction(deadEnd);
    idleState.addAction(driveForwardReset);

    return idleState;
  }

	private static AutoState createTimerState(String state_name, double timer_sec)
	{
		AutoState timerState = new AutoState(state_name);
		IdleAction deadEnd = new IdleAction("<Dead End Action>");
    DriveForwardAction driveForwardReset = 
        new DriveForwardAction("<Drive Forward Action -reset>", 0.0, 0.0, 0, 0);
		TimeEvent timer = new TimeEvent(timer_sec);
		timerState.addAction(deadEnd);
		timerState.addAction(driveForwardReset);
		timerState.addEvent(timer);
		
		return timerState;
	}

  private static AutoState createDriveState(
      String state_name,
      double dist_inches,
      double error_inches,
      double angle_deg,
      int max_vel_rpm,
      int max_accel_rpm) {
    AutoState driveState = new AutoState(state_name);
    DriveForwardAction driveForwardAction =
        new DriveForwardAction(
            "<Drive Forward Action>", dist_inches, angle_deg, max_vel_rpm, max_accel_rpm);

    // settle
    ClosedLoopPositionEvent pos = new ClosedLoopPositionEvent(dist_inches, error_inches, 0.6);
    driveState.addAction(driveForwardAction);
    driveState.addEvent(pos);

    return driveState;
  }

  private static AutoState createTurnState(
    String state_name,
    double angle_deg,
    double error_deg,
    double rotate_speed)
  {
    AutoState turnState = new AutoState(state_name);
    TurnAction turnAction =
        new TurnAction("<Turn Action>", rotate_speed);

    // settle
    ClosedLoopAngleEvent angle = new ClosedLoopAngleEvent(angle_deg, error_deg, 0.6);
    turnState.addAction(turnAction);
    turnState.addEvent(angle);

    return turnState;
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
  // 1) drive forward a number of inches
  // 2) go back to idle and stay there
  private static AutoNetwork createDriveForward() {

    AutoNetwork autoNet = new AutoNetwork("<Drive Forward Network>");

    // create states
    AutoState driveState =
        createDriveState(
            "<Drive State 1>", 60.0, 3.0, 0.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
    AutoState idleState = createIdleState("<Idle State>");

    // connect the state sequence
    driveState.associateNextState(idleState);

    // add states to the network list
    autoNet.addState(driveState);
    autoNet.addState(idleState);

    return autoNet;
  }

  // **** SWERVE Move Network 1 *****
  // 1) drive -45 degs a number of inches
  // 2) drive +45 degs a number of inches
  // 3) drive -45 degs a number of inches backwards
  // 4) drive +45 degs a number of inches backwards
  // 5) go back to idle and stay there
  private static AutoNetwork createSwerveMove1() {

    AutoNetwork autoNet = new AutoNetwork("<Swerve Move Network 1>");

    // create states
    AutoState driveState1 =
        createDriveState(
            "<Drive State 1>", 60.0, 3.0, -45.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
    AutoState driveState2 =
        createDriveState(
            "<Drive State 2>", 60.0, 3.0, +45.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
    AutoState driveState3 =
        createDriveState(
            "<Drive State 3>", -60.0, 3.0, -45.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
    AutoState driveState4 =
        createDriveState(
            "<Drive State 4>", -60.0, 3.0, +45.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
    AutoState idleState = createIdleState("<Idle State>");

    // connect the state sequence
    driveState1.associateNextState(driveState2);
    driveState2.associateNextState(driveState3);
    driveState3.associateNextState(driveState4);
    driveState4.associateNextState(idleState);

    // add states to the network list
    autoNet.addState(driveState1);
    autoNet.addState(driveState2);
    autoNet.addState(driveState3);
    autoNet.addState(driveState4);
    autoNet.addState(idleState);

    return autoNet;
  }

  // **** SWERVE Move Network 2 *****
  // 1) rotate +90 degs
  // 2) Time Delay 1 sec
  // 3) rotate +90 degs
  // 4) Time Delay 1 sec
  // 5) rotate -90 degs
  // 6) Time Delay 1 sec
  // 7) rotate -90 degs
  // 8) Time Delay 1 sec
  // 9) go back to state 1

  private static AutoNetwork createSwerveMove2() {

    AutoNetwork autoNet = new AutoNetwork("<Swerve Move Network 2>");
    
    AutoState turnState1 = 
        createTurnState("<Turn State 1>", +90.0, 3.0, 0.4);
    AutoState timerState1 = 
        createTimerState("<Timer State 1>", 1.0);
    AutoState turnState2 = 
        createTurnState("<Turn State 2>", +90.0, 3.0, 0.4);
    AutoState timerState2 = 
        createTimerState("<Timer State 2>", 1.0);
    AutoState turnState3 = 
        createTurnState("<Turn State 3>", -90.0, 3.0, 0.4);
    AutoState timerState3 = 
        createTimerState("<Timer State 3>", 1.0);
    AutoState turnState4 = 
        createTurnState("<Turn State 4>", -90.0, 3.0, 0.4);
    AutoState timerState4 = 
        createTimerState("<Timer State 4>", 1.0);

    // connect the state sequence
    turnState1.associateNextState(timerState1);
    timerState1.associateNextState(turnState2);
    turnState2.associateNextState(timerState2);
    timerState2.associateNextState(turnState3);
    turnState3.associateNextState(timerState3);
    timerState3.associateNextState(turnState4);
    turnState4.associateNextState(timerState4);
    timerState4.associateNextState(turnState1);

    autoNet.addState(turnState1);
    autoNet.addState(timerState1);
    autoNet.addState(turnState2);
    autoNet.addState(timerState2);
    autoNet.addState(turnState3);
    autoNet.addState(timerState3);
    autoNet.addState(turnState4);
    autoNet.addState(timerState4);

    return autoNet;
  }

  // **** SWERVE Move Network 3 *****
  // 1) drive -45 degs a number of inches
  // 2) rotate +90 degs
  // 3) drive -45 degs a number of inches
  // 4) rotate +90 degs
  // 5) drive -45 degs a number of inches
  // 6) rotate +90 degs
  // 7) drive -45 degs a number of inches
  // 8) rotate +90 degs
  // 9) go back to idle and stay there
  private static AutoNetwork createSwerveMove3() {

    AutoNetwork autoNet = new AutoNetwork("<Swerve Move Network 3>");

    // create states
    AutoState driveState1 =
        createDriveState(
            "<Drive State 1>", 60.0, 3.0, -45.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
    AutoState turnState1 = 
        createTurnState("<Turn State 1>", +90.0, 3.0, 0.4);
    AutoState driveState2 =
        createDriveState(
            "<Drive State 2>", 60.0, 3.0, -45.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
    AutoState turnState2 = 
        createTurnState("<Turn State 2>", +90.0, 3.0, 0.4);
    AutoState driveState3 =
        createDriveState(
            "<Drive State 3>", 60.0, 3.0, -45.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
    AutoState turnState3 = 
        createTurnState("<Turn State 3>", +90.0, 3.0, 0.4);
    AutoState driveState4 =
        createDriveState(
            "<Drive State 4>", 60.0, 3.0, -45.0, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
    AutoState turnState4 = 
        createTurnState("<Turn State 4>", +90.0, 3.0, 0.4);
    AutoState idleState = createIdleState("<Idle State>");

    // connect the state sequence
    driveState1.associateNextState(turnState1);
    turnState1.associateNextState(driveState2);
    driveState2.associateNextState(turnState2);
    turnState2.associateNextState(driveState3);
    driveState3.associateNextState(turnState3);
    turnState3.associateNextState(driveState4);
    driveState4.associateNextState(turnState4);
    turnState4.associateNextState(idleState);

    // add states to the network list
    autoNet.addState(driveState1);
    autoNet.addState(turnState1);
    autoNet.addState(driveState2);
    autoNet.addState(turnState2);
    autoNet.addState(driveState3);
    autoNet.addState(turnState3);
    autoNet.addState(driveState4);
    autoNet.addState(turnState4);
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
		FollowPathAction follow = new FollowPathAction("<Follow Path Action>", pathToFollow, fwdPolarity, true);
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
		FollowPathAction follow1 = new FollowPathAction("<Follow Path 1 Action>", path1, path1FwdPolarity, true);
		PathCompleteEvent pathComplete1 = new PathCompleteEvent();
		followPathState1.addAction(follow1);
		followPathState1.addEvent(pathComplete1);

		AutoState followPathState2 = new AutoState("<Follow Path State 2>");
		FollowPathAction follow2 = new FollowPathAction("<Follow Path 2 Action>", path2, path2FwdPolarity, true);
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

  /**
   * **************************************************************************************************
   */
  /**
   * ** DEBUG NETWORKS **** Networks below this are used only for debug - disable during competition
   * **
   */
  /**
   * **************************************************************************************************
   */

  // **** Test Network - does nothing except transitions states *****
  private static AutoNetwork createTestNetwork() {

    AutoNetwork autoNet = new AutoNetwork("<Test Network>");

    AutoState idleState = new AutoState("<Idle State 1>");
    IdleAction startIdle = new IdleAction("<Start Idle Action 1>");
    IdleAction doSomething2 = new IdleAction("<Placeholder Action 2>");
    IdleAction doSomething3 = new IdleAction("<Placeholder Action 3>");
    TimeEvent timer1 = new TimeEvent(10.0); // timer event
    idleState.addAction(startIdle);
    idleState.addAction(doSomething2);
    idleState.addAction(doSomething3);
    idleState.addEvent(timer1);

    AutoState idleState2 = new AutoState("<Idle State 2>");
    IdleAction startIdle2 = new IdleAction("<Start Idle Action 2>");
    IdleAction doSomething4 = new IdleAction("<Placeholder Action 4>");
    IdleAction doSomething5 = new IdleAction("<Placeholder Action 5>");
    TimeEvent timer2 = new TimeEvent(10.0); // timer event
    idleState2.addAction(startIdle2);
    idleState2.addAction(doSomething4);
    idleState2.addAction(doSomething5);
    idleState2.addEvent(timer2);

    AutoState idleState3 = new AutoState("<Idle State 3>");
    IdleAction startIdle3 = new IdleAction("<Start Idle Action 3>");
    IdleAction doSomething6 = new IdleAction("<Placeholder Action 6>");
    IdleAction doSomething7 = new IdleAction("<Placeholder Action 7>");
    TimeEvent timer3 = new TimeEvent(10.0); // timer event
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
