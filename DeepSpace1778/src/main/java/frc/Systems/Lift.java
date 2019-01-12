package frc.Systems;

import frc.NetworkComm.InputOutputComm;
import frc.Utility.HardwareIDs;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Joystick;

public class Lift
{
    private static boolean initialized = false;
 
    // preprogrammed lift levels in inches from floor level
    private static final double cargoLiftLevel[] = { 0.0, 20.0, 40.0, 60.0, 80.0 };
    private static final double hatchLiftLevel[] = { 0.0, 10.0, 30.0, 50.0, 70.0 };

	private final static int CLOSED_LOOP_VEL_SLOW = 600; 
	private final static int CLOSED_LOOP_ACCEL_SLOW = 300;

    private static final double ALLOWABLE_LIFT_POS_ERROR = 0.5;

	// PIDF values
	private static final double kP = 4.0;
	private static final double kI = 0.001;  
	private static final double kD = 0.0;
	private static final double kF = 0.0;  // Feedforward not used for closed loop position control
	private static final int TIMEOUT_MS = 0;  // set to zero if skipping confirmation
	private static final int PIDLOOP_IDX = 0;  // set to zero if primary loop
    private static final int PROFILE_SLOT = 0;
    
	// motor polarity
	public static final boolean MASTER_REVERSE_MOTOR = true;    // motor polarity
	public static final boolean SLAVE_REVERSE_MOTOR = false;		// motor polarity
		
	// encoder polarity
	public static final boolean ALIGNED_MASTER_SENSOR = true;	// encoder polarity


    // encoder variables
    private static final int ENCODER_PULSES_PER_REV = 256 * 4; // 63R  - on the competition bot motors
    private static final double INCHES_PER_REV = 1.0;
    private static final double INCHES_PER_ENCODER_PULSE = INCHES_PER_REV / ENCODER_PULSES_PER_REV;
    private static final double RPM_TO_UNIT_PER_100MS = ENCODER_PULSES_PER_REV / (60 * 10);

    private static double currentPos = cargoLiftLevel[0];  // default starting pos

	// lift motor
	private static TalonSRX masterMotor, slaveMotor;

    private static Joystick gamepad;

    public static void initialize() {
  
        if (initialized) return;
  
        // initialize things
        InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "initializing lift...");

        gamepad = new Joystick(HardwareIDs.CO_DRIVER_GAMEPAD_ID);

        // create and initialize lift motors
	    masterMotor = configureMotor(HardwareIDs.LIFT_MASTER_TALON_ID, MASTER_REVERSE_MOTOR, ALIGNED_MASTER_SENSOR, kP, kI, kD, kF);
		slaveMotor = configureMotor(HardwareIDs.LIFT_SLAVE_TALON_ID, SLAVE_REVERSE_MOTOR, HardwareIDs.LIFT_MASTER_TALON_ID);

        currentPos = cargoLiftLevel[0];  // default starting pos, assumes lift completely down

        initialized = true;
    }

    // closed-loop motor configuration
    private static TalonSRX configureMotor(int talonID, boolean revMotor, boolean alignSensor,
    									double pCoeff, double iCoeff, double dCoeff, double fCoeff)
    {
    	TalonSRX _talon;
    	_talon = new TalonSRX(talonID);
    	_talon.setInverted(revMotor);
    	
    	_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PIDLOOP_IDX, TIMEOUT_MS);
    	_talon.setSensorPhase(alignSensor); 
    	
    	_talon.selectProfileSlot(PROFILE_SLOT, PIDLOOP_IDX);
    	_talon.config_kP(PROFILE_SLOT, pCoeff, TIMEOUT_MS);
    	_talon.config_kI(PROFILE_SLOT, iCoeff, TIMEOUT_MS);
    	_talon.config_kD(PROFILE_SLOT, dCoeff, TIMEOUT_MS);
    	_talon.config_kF(PROFILE_SLOT, fCoeff, TIMEOUT_MS);
    	_talon.configMotionCruiseVelocity(0, TIMEOUT_MS);
    	_talon.configMotionAcceleration(0, TIMEOUT_MS);
    	_talon.setSelectedSensorPosition(0, PIDLOOP_IDX, TIMEOUT_MS);
    	
    	//_talon.setNeutralMode(NeutralMode.Brake);
 
    	return _talon;
    }
    
    // open-loop motor configuration (and possibly follower)
    private static TalonSRX configureMotor(int talonID, boolean revMotor, int talonIDToFollow)
    {
    	TalonSRX _talon;
    	_talon = new TalonSRX(talonID);
    	_talon.setInverted(revMotor);
    	
    	if (talonIDToFollow > 0)
    		_talon.set(ControlMode.Follower, (double)talonIDToFollow);
    	
    	//_talon.setNeutralMode(NeutralMode.Brake);
   	
    	return _talon;
    }

    public static double getCurrPosInches()
    {
       double position = masterMotor.getSelectedSensorPosition(0)*INCHES_PER_ENCODER_PULSE;
       InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog,"Lift/CurrPosInches", position);

        return position;
    }

	public static void runLift(double targetPosInches, int speedRpm, int accelRpm)
	{
        InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog,"Lift/CmdPosInches", targetPosInches);

        int nativeUnitsPer100ms = (int) ((double)speedRpm * HardwareIDs.RPM_TO_UNIT_PER_100MS);
		int accelNativeUnits = (int) ((double)accelRpm * HardwareIDs.RPM_TO_UNIT_PER_100MS);

        // left front drive straight - uses motion magic
		masterMotor.configMotionCruiseVelocity(nativeUnitsPer100ms, TIMEOUT_MS);
		masterMotor.configMotionAcceleration(accelNativeUnits, TIMEOUT_MS);
		masterMotor.set(ControlMode.MotionMagic, targetPosInches/HardwareIDs.INCHES_PER_ENCODER_PULSE);
        
	}

    private static void checkLiftControls() 
    {
				
        boolean upHatch = false;
        boolean downHatch = false;
        boolean upCargo = false;
        boolean downCargo = false;
        
        if (gamepad.getRawButton(HardwareIDs.CO_DRIVER_A_BUTTON)) downHatch = true;
        if (gamepad.getRawButton(HardwareIDs.CO_DRIVER_B_BUTTON)) upHatch = true;
        if (gamepad.getRawButton(HardwareIDs.CO_DRIVER_X_BUTTON)) downCargo = true;
        if (gamepad.getRawButton(HardwareIDs.CO_DRIVER_Y_BUTTON)) upCargo = true;

        // no change requests, just return
        if (!downHatch && !upHatch && !downCargo && !upCargo)
            return;

        double currentPos = getCurrPosInches();
        double commandedPos = currentPos;

        if (downHatch)
        {
            for (int i=(hatchLiftLevel.length - 1); i>=0; i--)
            {
                if (hatchLiftLevel[i] < currentPos) {
                    commandedPos = hatchLiftLevel[i];
                    break;
                }
            }
        }
        else if (downCargo)
        {
            for (int i=(cargoLiftLevel.length - 1); i>=0; i--)
            {
                if (cargoLiftLevel[i] < currentPos) {
                    commandedPos = hatchLiftLevel[i];
                    break;
                }

            }
        }
        else if (upHatch)
        {
            for (int i=0; i < hatchLiftLevel.length; i++)
            {
                if (hatchLiftLevel[i] > currentPos) {
                    commandedPos = hatchLiftLevel[i];
                    break;
                }
            }
        }
        else if (upCargo)
        {
            for (int i=0; i < cargoLiftLevel.length; i++)
            {
                if (cargoLiftLevel[i] > currentPos) {
                    commandedPos = cargoLiftLevel[i];
                    break;
                }
            }
         }
        else return;

		// send lift to commanded position
		runLift(commandedPos, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
  }

    public static void teleopInit() {

    }


    public static void teleopPeriodic() {
        checkLiftControls();     
    }

    public static void disabledInit() {
        // stop lift motor
        masterMotor.set(ControlMode.PercentOutput, 0);
    }
    
}