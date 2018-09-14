package frc.team1778.Systems;

import java.util.ArrayList;

import frc.team1778.Utility.HardwareIDs;

import edu.wpi.first.wpilibj.Notifier;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;

public class FreezyPath {
	
	private static boolean initialized = false;
	
	public static final double ZERO_ANGLE = 0.001;

	public static final int PATH1 = 0;
	public static final int PATH2 = 1;
	public static final int PATH3 = 2;
	public static final int PATH4 = 4;
	private static int m_pathToFollow = PATH1;

	// Time Step:           0.05 Seconds
	// Max Velocity:        30 in/s
	// Max Acceleration:    30 in/s/s
	// Max Jerk:            300 in/s/s/s

	private static final double WHEELBASE_WIDTH_INCHES = 29.5;
	private static final double PERIOD_SEC = 0.05;
	//private static final double MAX_VEL = 60.0;
	private static final double MAX_VEL = 12.0;
	private static final double MAX_ACCEL = 30.0;
	private static final double MAX_JERK = 300.0;
	
	private static final double kP = 1.0;
	private static final double kI = 0.0;
	private static final double kD = 0.25;
	private static final double kV = 1.0 / MAX_VEL;
	private static final double kA = 0.0;
	
	private static ArrayList<Trajectory> trajectoryList;
	private static EncoderFollower leftEncoderFollower, rightEncoderFollower;

	private static Notifier m_notifier;
	
	private static Runnable m_handler = new Runnable() {
		public void run() {
			FreezyPath.update();
		}
	};

	// path 1 - drive straight 5 ft
	private static Waypoint[] path1 = new Waypoint[] {
			new Waypoint(0, 0, ZERO_ANGLE),
			new Waypoint(60.0, 0.0, ZERO_ANGLE),
			new Waypoint(90.0, 0.0, ZERO_ANGLE)
	};

	// path 2 - swerve to the left and back to center
	private static Waypoint[] path2 = new Waypoint[] {
			new Waypoint(0, 0, ZERO_ANGLE),
			new Waypoint(60.0, -30.0, ZERO_ANGLE),
			new Waypoint(90.0, 0.0, ZERO_ANGLE)
	};
	
	// path 3 - swerve to the right and back to center
	private static Waypoint[] path3 = new Waypoint[] {
			new Waypoint(0, 0, ZERO_ANGLE),
			new Waypoint(60.0, 30.0, ZERO_ANGLE),
			new Waypoint(90.0, 0.0, ZERO_ANGLE)
	};
	
	// path 4 - drive in a big circle and level out straight
	private static Waypoint[] path4 = new Waypoint[] {
			new Waypoint(0, 0, ZERO_ANGLE),
			new Waypoint(60.0, -60.0, ZERO_ANGLE),
			new Waypoint(120.0, 0, Pathfinder.d2r(-90.0)),
			new Waypoint(60.0, 60.0, Pathfinder.d2r(-180.0)),
			new Waypoint(0, 0, Pathfinder.d2r(-270.0)),
			new Waypoint(60.0, 0, Pathfinder.d2r(-360.0))
	};
	
	// trajectory creation method
	private static Trajectory setupPathfinder_Path(Waypoint[] points) {
						
		// Create the Trajectory Configuration
		//
		// Arguments:
		// Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC
		// Sample Count:        SAMPLES_HIGH (100 000)
		//		                SAMPLES_LOW  (10 000)
		//		                SAMPLES_FAST (1 000)
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, 
										Trajectory.Config.SAMPLES_HIGH, 
										PERIOD_SEC, MAX_VEL, MAX_ACCEL, MAX_JERK);

		// Generate the trajectory
		Trajectory trajectory = Pathfinder.generate(points, config);

    	/*
		for (int i = 0; i < trajectory.length(); i++) {
		    Trajectory.Segment seg = trajectory.get(i);
		    
		    System.out.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", 
		        seg.dt, seg.x, seg.y, seg.position, seg.velocity, 
		            seg.acceleration, seg.jerk, seg.heading);
		}	
		*/
		
		// save the trajectory to a file
		//File outFile = new File("chillOut_Path.csv");
		//Pathfinder.writeToCSV(outFile, trajectory);
		
		return trajectory;
	}

	private static void setupFollowers(Trajectory trajectory) {
    	TankModifier tankModifier = new TankModifier(trajectory).modify(WHEELBASE_WIDTH_INCHES);
    	
    	leftEncoderFollower = new EncoderFollower(tankModifier.getLeftTrajectory());
    	rightEncoderFollower = new EncoderFollower(tankModifier.getRightTrajectory());		

    	leftEncoderFollower.configureEncoder(0, (int)HardwareIDs.ENCODER_PULSES_PER_REV, HardwareIDs.WHEEL_DIAMETER_INCHES);
    	rightEncoderFollower.configureEncoder(0, (int)HardwareIDs.ENCODER_PULSES_PER_REV, HardwareIDs.WHEEL_DIAMETER_INCHES);
    	
    	leftEncoderFollower.configurePIDVA(kP, kI, kD, kV, kA);
    	rightEncoderFollower.configurePIDVA(kP, kI, kD, kV, kA);
	}
	
	public static void reset(int pathToFollow) {
		
		if (!initialized)
			initialize();
		
		m_pathToFollow = pathToFollow;
		setupFollowers(trajectoryList.get(m_pathToFollow));
	}
	
	public static void initialize() {
		
		if (initialized)
			return;
			
		// load trajectory list
		trajectoryList = new ArrayList<Trajectory>();
		trajectoryList.add(setupPathfinder_Path(path1));
		trajectoryList.add(setupPathfinder_Path(path2));
		trajectoryList.add(setupPathfinder_Path(path3));
		trajectoryList.add(setupPathfinder_Path(path4));
		
		// create notifier
		m_notifier = new Notifier(m_handler);
				
		initialized = true;
	}
	
	public static void start() {
		m_notifier.startPeriodic(PERIOD_SEC);
	}

	public static void stop() {
		m_notifier.stop();
	}
	
	public static boolean isFinished()
	{
		return (leftEncoderFollower.isFinished() && rightEncoderFollower.isFinished());
	}
	
	public static void update() 
	{
		// retrieve encoder values from DriveAssembly
		int encoderLeft = DriveAssembly.getLeftEncoder();
		int encoderRight = DriveAssembly.getRightEncoder();
		
		// retrieve gyro value (yaw angle in degrees)
		double gyroValueDeg = NavXSensor.getAngle();
		
		// invert yaw angle sign for converting NavX yaw to Pathfinder (cartesian) yaw
		gyroValueDeg *= -1.0;
		
		double left = leftEncoderFollower.calculate(encoderLeft);
		double right = rightEncoderFollower.calculate(encoderRight);
		
        double desired_heading = Pathfinder.r2d(leftEncoderFollower.getHeading()); // Should also be in degrees

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyroValueDeg);
        double turn = 0.8 * (-1.0 / 80.0) * angleDifference;

        // send updated command to DriveAssembly
        DriveAssembly.drive(left + turn, right - turn);			
	}
}
