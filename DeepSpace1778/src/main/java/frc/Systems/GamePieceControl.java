package frc.Systems;

import frc.NetworkComm.InputOutputComm;
import frc.Utility.HardwareIDs;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Joystick;

public class GamePieceControl 
{
  private static boolean initialized = false;

  private static Joystick gamepad;

	// collector strength (%VBus - max is 1.0)
	private static final double COLLECTOR_MAX_STRENGTH = 1.0;   // joystick max limit
	private static final double COLLECTOR_IN_FACTOR = 0.7;
	private static final double COLLECTOR_OUT_FACTOR = -0.7;
  private static final double COLLECTOR_DEAD_ZONE = 0.05;

	private static final boolean LEFT_COLLECTOR_INVERTED = true;
	private static final boolean RIGHT_COLLECTOR_INVERTED = false;
	private static final boolean ARTICULATOR_INVERTED = false;
	private static final boolean VACUUM_INVERTED = false;

	private static final double COLLECTOR_OUT_AUTOEXPEL_STRENGTH = -0.65;  // auto out for expelling

	// collector intake motors
	private static Spark leftCollectorMotor, rightCollectorMotor; 

	// panel suction cup motor
	private static Spark vacuumMotor;
	
	// articulator (moves collector up and down)
	private static Spark articulator;
  
  public static void initialize() {

    if (initialized) return;

    // initialize things
    InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "initializing GamePieceControl...");

    gamepad = new Joystick(HardwareIDs.CO_DRIVER_GAMEPAD_ID);

		// create and initialize collector motors (open-loop)
		leftCollectorMotor = new Spark(HardwareIDs.LEFT_COLLECTOR_PWM_ID);
		leftCollectorMotor.setInverted(LEFT_COLLECTOR_INVERTED);
		rightCollectorMotor = new Spark(HardwareIDs.RIGHT_COLLECTOR_PWM_ID);
		rightCollectorMotor.setInverted(RIGHT_COLLECTOR_INVERTED);

		articulator = new Spark(HardwareIDs.ARTICULATOR_PWM_ID);
		articulator.setInverted(ARTICULATOR_INVERTED);

		vacuumMotor = new Spark(HardwareIDs.VACUUM_PWM_ID);
		vacuumMotor.setInverted(VACUUM_INVERTED);

    initialized = true;
  }
  
	public static void stopMotors()
	{
		// stop collector motors
		leftCollectorMotor.set(0);
    rightCollectorMotor.set(0);		
  }
  
  public static void depositCargo()
	{
		InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog,"GamePieceCtrl/CollectorStrength", COLLECTOR_OUT_AUTOEXPEL_STRENGTH);
		leftCollectorMotor.set(COLLECTOR_OUT_AUTOEXPEL_STRENGTH);
		rightCollectorMotor.set(COLLECTOR_OUT_AUTOEXPEL_STRENGTH);
			
	}

	public static void collectCargo(double strength)
	{
		InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog,"GamePieceCtrl/CollectorStrength", strength);
		leftCollectorMotor.set(strength);
		rightCollectorMotor.set(strength);
  }
  

	private static void checkCollectorControls() {	
		
		// collector intake control
		double collectorInStrength = gamepad.getRawAxis(HardwareIDs.CO_DRIVER_LEFT_TRIGGER_AXIS);
		// clamp intake strength to operating range
		collectorInStrength = (collectorInStrength < COLLECTOR_DEAD_ZONE) ? 0.0 : collectorInStrength;
		collectorInStrength = (collectorInStrength > COLLECTOR_MAX_STRENGTH) ? COLLECTOR_MAX_STRENGTH : collectorInStrength;
		
		// collector expel control
		double collectorOutStrength = gamepad.getRawAxis(HardwareIDs.CO_DRIVER_RIGHT_TRIGGER_AXIS);
		// clamp expel strength to operating range
		collectorOutStrength = (collectorOutStrength < COLLECTOR_DEAD_ZONE) ? 0.0 : collectorOutStrength;
		collectorOutStrength = (collectorOutStrength > COLLECTOR_MAX_STRENGTH) ? COLLECTOR_MAX_STRENGTH : collectorOutStrength;
		
		// determine if collector operating in intake, expel or default
		double collectorMotorStrength;
		if (collectorInStrength > 0)
			collectorMotorStrength = collectorInStrength * COLLECTOR_IN_FACTOR;
		else if (collectorOutStrength > 0)
			collectorMotorStrength = collectorOutStrength * COLLECTOR_OUT_FACTOR;
		else
			collectorMotorStrength = 0.0;  // default auto motor rate in (for cube retention)
		
		InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog,"GamePieceCtrl/CollectorStrength", collectorMotorStrength);

		leftCollectorMotor.set(collectorMotorStrength);
		rightCollectorMotor.set(collectorMotorStrength);
	}

  public static void teleopInit() {

  }

  public static void teleopPeriodic() {
    checkCollectorControls();
  }

  public static void disabledInit() {
    stopMotors();
  }

}