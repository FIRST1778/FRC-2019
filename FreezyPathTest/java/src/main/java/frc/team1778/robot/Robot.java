/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1778.robot;

import frc.team1778.NetworkComm.InputOutputComm;
import frc.team1778.StateMachine.AutoStateMachine;
import frc.team1778.Systems.DriveAssembly;
import frc.team1778.Systems.FreezyPath;
import frc.team1778.Systems.NavXSensor;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	
	protected AutoStateMachine autoSM;
	protected DriverStation ds;
		
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		// Initialize robot subsystems
		InputOutputComm.initialize();
		NavXSensor.initialize();
		DriveAssembly.initialize();
		FreezyPath.initialize();
		
		// Create Autonomous State Machine
		autoSM = new AutoStateMachine();

		// retrieve Driver Station instance
		ds = DriverStation.getInstance();

	}

	@Override
	public void autonomousInit() {
		  	
    	DriveAssembly.autoInit(true, 0.0, false);
    	
    	// start the auto state machine
    	autoSM.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {

    	autoSM.process();
    	 
    	// debug only (read position sensors)
    	DriveAssembly.getDistanceInches();
    	getGyroAngle();   				
	}

	private double getGyroAngle() {
		double gyroAngle = NavXSensor.getAngle();  // continuous angle (can be larger than 360 deg)		
	    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog,"Auto/GyroAngle", gyroAngle);		

		return gyroAngle;
	}
	
    public void disabledInit() {

    	DriveAssembly.disabledInit();
    }

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
