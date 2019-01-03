package frc.Systems;

import edu.wpi.first.wpilibj.Notifier;
import frc.ChillySwerve.ChillySwerve;
import frc.Utility.HardwareIDs;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.SwerveModifier;
import java.util.ArrayList;

public class FreezyPath {

  private static boolean initialized = false;

	public static final int STRAIGHT_PATH = 0;
	public static final int DRIVE_AND_ROTATE_PATH1 = 1;
	public static final int DRIVE_AND_ROTATE_PATH2 = 2;
	public static final int DRIVE_AND_ROTATE_PATH3 = 3;
	public static final int DRIVE_AND_ROTATE_PATH4 = 4;
	public static final int DRIVE_AND_ROTATE_PATH5 = 5;

	private static int m_pathToFollow = STRAIGHT_PATH;

	// Time Step:           0.02 Seconds (50 Hz)
	// Max Velocity:        6.0 ft/s
	// Max Acceleration:    2.0 ft/s/s
	// Max Jerk:            60 ft/s/s/s

	private static final double WHEELBASE_WIDTH_FT = 17.0/12;
	private static final double WHEELBASE_DEPTH_FT = 23.5/12;
	private static final double PERIOD_SEC = 0.02;
	private static final double MAX_VEL = 6.0;
	private static final double MAX_ACCEL = 2.0;
	private static final double MAX_JERK = 60.0;

  private static final double kP = 1.0;
  private static final double kI = 0.0;
  private static final double kD = 0.0;
  private static final double kV = 1.0 / MAX_VEL;
  private static final double kA = 0.0;

  private static ArrayList<Trajectory> trajectoryList;
  private static EncoderFollower encoderFollower_FL, encoderFollower_FR;
  private static EncoderFollower encoderFollower_BL, encoderFollower_BR;

  private static Notifier m_notifier;

  private static Runnable m_handler =
      new Runnable() {
        public void run() {
          FreezyPath.update();
        }
      };

	// path 1 - drive straight 10 ft
	private static Waypoint[] path1 = new Waypoint[] {
		new Waypoint(0, 0, Pathfinder.d2r(0)),
		new Waypoint(4.0, 0.0, Pathfinder.d2r(0)),
		new Waypoint(8.0, 0.0, Pathfinder.d2r(0))
	};

	// path 2 - drive straight (0 deg) and rotate 90 deg
	private static Waypoint[] path2 = new Waypoint[] {
		new Waypoint(0, 0, Pathfinder.d2r(0)),
		new Waypoint(2.0, 0.0, Pathfinder.d2r(45)),
		new Waypoint(4.0, 0.0, Pathfinder.d2r(90))
	};

	// path 3 - drive at 45 deg and rotate 90 deg
	private static Waypoint[] path3 = new Waypoint[] {
		new Waypoint(0, 0, Pathfinder.d2r(0)),
		new Waypoint(2.0, 2.0, Pathfinder.d2r(45)),
		new Waypoint(4.0, 4.0, Pathfinder.d2r(90))
	};

	// path 4 - drive at 45 deg and rotate 180 deg
	private static Waypoint[] path4 = new Waypoint[] {
		new Waypoint(0, 0, Pathfinder.d2r(0)),
		new Waypoint(2.0, 2.0, Pathfinder.d2r(90)),
		new Waypoint(4.0, 4.0, Pathfinder.d2r(180))
	};

	// path 5 - drive at -45 deg and rotate -90 deg
	private static Waypoint[] path5 = new Waypoint[] {
		new Waypoint(0, 0, Pathfinder.d2r(0)),
		new Waypoint(2.0, -2.0, Pathfinder.d2r(-45)),
		new Waypoint(4.0, -4.0, Pathfinder.d2r(-90))
	};

	// path 6 - drive at -45 deg and rotate -180 deg
	private static Waypoint[] path6 = new Waypoint[] {
		new Waypoint(0, 0, Pathfinder.d2r(0)),
		new Waypoint(2.0, -2.0, Pathfinder.d2r(-90)),
		new Waypoint(4.0, -4.0, Pathfinder.d2r(-180))
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
    // Time Step:           0.05 Seconds
    // Max Velocity:        30 in/s
    // Max Acceleration:    30 in/s/s
    // Max Jerk:            300 in/s/s/s
    Trajectory.Config config =
        new Trajectory.Config(
            Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_HIGH,
            PERIOD_SEC, MAX_VEL, MAX_ACCEL, MAX_JERK);

    // Generate the trajectories
    Trajectory trajectory = Pathfinder.generate(points, config);

    // save the trajectory to a file
    // File outFile = new File("chillOut_Path.csv");
    // Pathfinder.writeToCSV(outFile, trajectory);

    return trajectory;
  }

  private static void setupFollowers(Trajectory trajectory) {
    SwerveModifier modifier =
        new SwerveModifier(trajectory)
            .modify(
              WHEELBASE_WIDTH_FT, WHEELBASE_DEPTH_FT, SwerveModifier.Mode.SWERVE_DEFAULT);
    Trajectory fl = modifier.getFrontLeftTrajectory();
    Trajectory fr = modifier.getFrontRightTrajectory();
    Trajectory bl = modifier.getBackLeftTrajectory();
    Trajectory br = modifier.getBackRightTrajectory();

    encoderFollower_FL = new EncoderFollower(fl);
    encoderFollower_FR = new EncoderFollower(fr);
    encoderFollower_BL = new EncoderFollower(bl);
    encoderFollower_BR = new EncoderFollower(br);

    encoderFollower_FL.configureEncoder(
        0, HardwareIDs.ENCODER_PULSES_PER_REV, HardwareIDs.WHEEL_DIAMETER_INCHES);
    encoderFollower_FR.configureEncoder(
        0, HardwareIDs.ENCODER_PULSES_PER_REV, HardwareIDs.WHEEL_DIAMETER_INCHES);
    encoderFollower_BL.configureEncoder(
        0, HardwareIDs.ENCODER_PULSES_PER_REV, HardwareIDs.WHEEL_DIAMETER_INCHES);
    encoderFollower_BR.configureEncoder(
        0, HardwareIDs.ENCODER_PULSES_PER_REV, HardwareIDs.WHEEL_DIAMETER_INCHES);

    encoderFollower_FL.configurePIDVA(kP, kI, kD, kV, kA);
    encoderFollower_FR.configurePIDVA(kP, kI, kD, kV, kA);
    encoderFollower_BL.configurePIDVA(kP, kI, kD, kV, kA);
    encoderFollower_BR.configurePIDVA(kP, kI, kD, kV, kA);
  }

  public static void reset(int pathToFollow) {

    if (!initialized) initialize();

    m_pathToFollow = pathToFollow;
    setupFollowers(trajectoryList.get(m_pathToFollow));
  }

  public static void initialize() {

    if (initialized) return;

    // load trajectory list
    trajectoryList = new ArrayList<Trajectory>();
		trajectoryList.add(setupPathfinder_Path(path1));
		trajectoryList.add(setupPathfinder_Path(path2));
		trajectoryList.add(setupPathfinder_Path(path3));
		trajectoryList.add(setupPathfinder_Path(path4));
		trajectoryList.add(setupPathfinder_Path(path5));
		trajectoryList.add(setupPathfinder_Path(path6));

    // create notifier
    m_notifier = new Notifier(m_handler);

    initialized = true;
  }

  public static void start(boolean motorPolarity) {
    
    ChillySwerve.setDriveMotorForward(motorPolarity);

    m_notifier.startPeriodic(PERIOD_SEC);
  }

  public static void stop() {
    m_notifier.stop();
  }

  public static boolean isFinished() {
    return (encoderFollower_FL.isFinished()
        && encoderFollower_FR.isFinished()
        && encoderFollower_BL.isFinished()
        && encoderFollower_BR.isFinished());
  }

  public static void update() {
    // send updated command to Swerve (directDrive)
    ChillySwerve.directDrive(
        encoderFollower_FL, encoderFollower_FR, encoderFollower_BL, encoderFollower_BR);
  }
}
