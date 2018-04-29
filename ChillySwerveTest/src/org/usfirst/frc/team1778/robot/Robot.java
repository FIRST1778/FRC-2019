package org.usfirst.frc.team1778.robot;

import ChillySwerve.ChillySwerve;
import NetworkComm.InputOutputComm;
import NetworkComm.RPIComm;
import Systems.NavXSensor;
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

	protected DriverStation ds;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		// Initialize robot subsystems
		InputOutputComm.initialize();
		RPIComm.initialize();
		NavXSensor.initialize();
		
		// Initialize ChillySwerve Drive controller classes
		ChillySwerve.initialize();

		// Create Autonomous State Machine
		//autoSM = new AutoStateMachine();
		
		// retrieve Driver Station instance
		ds = DriverStation.getInstance();
		
    	InputOutputComm.putString(InputOutputComm.LogTable.kMainLog,"MainLog","robot initialized...");        

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
    	InputOutputComm.putString(InputOutputComm.LogTable.kMainLog,"MainLog","autonomous mode...");
    	    	 	    	
    	// start the auto state machine
    	//autoSM.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
    	
    	//autoSM.process();
 
    	// debug only (read position sensors)
    	getGyroAngle();   	
	}

	public void teleopInit() {
    	InputOutputComm.putString(InputOutputComm.LogTable.kMainLog,"MainLog","teleop mode...");
    	
    	ChillySwerve.teleopInit();
   	    	
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
    	// ChillySwerve-Drive command for all controllers
		ChillySwerve.teleopPeriodic();
	 	
	}
	
    /**
     * This function is called periodically during operator control
     */
    
	private double getGyroAngle() {
		//double gyroAngle = 0.0;
		//double gyroAngle = NavXSensor.getYaw();  // -180 deg to +180 deg
		double gyroAngle = NavXSensor.getAngle();  // continuous angle (can be larger than 360 deg)
		
		//System.out.println("getGyroAngle:  Gyro angle = " + gyroAngle);
			
		// send output data for test & debug
	    //InputOutputComm.putBoolean(InputOutputComm.LogTable.kMainLog,"Auto/IMU_Connected",navX.isConnected());
	    //InputOutputComm.putBoolean(InputOutputComm.LogTable.kMainLog,"Auto/IMU_Calibrating",navX.isCalibrating());

		//System.out.println("gyroAngle = " + gyroAngle);
	    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog,"Auto/GyroAngle", gyroAngle);		

		return gyroAngle;
	}
	
    public void disabledInit() {

    	ChillySwerve.disabledInit();
    }
    
    public void disabledPeriodic() {

    	ChillySwerve.disabledPeriodic();
    }

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

