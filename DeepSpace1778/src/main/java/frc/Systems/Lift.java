package frc.Systems;

import frc.NetworkComm.InputOutputComm;
import frc.Utility.HardwareIDs;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
    private static final double ROCKET_CARGO_HIGH = 80.0;
    private static final double ROCKET_CARGO_MED = 60.0;
    private static final double ROCKET_CARGO_LOW = 40.0;

    private static final double ROCKET_HATCH_HIGH = 70.0;
    private static final double ROCKET_HATCH_MED = 50.0;
    private static final double ROCKET_HATCH_LOW = 30.0;

    private static final double CARGOSHIP_CARGO = 50.0;
    private static final double CARGOSHIP_HATCH = 30.0;
    private static final double FEEDER_STATION = 30.0;

    private static final double LIFT_LEVEL_NEAR_FLOOR = 10.0;
    private static final double LIFT_LEVEL_FLOOR = 0.0;

    // arranged in same order as control panel buttons (first item as zero)
    private static double liftLevels[] = {0, ROCKET_CARGO_HIGH, ROCKET_CARGO_MED, ROCKET_CARGO_LOW,
                                            ROCKET_HATCH_HIGH, ROCKET_HATCH_MED, ROCKET_HATCH_LOW, 
                                            CARGOSHIP_CARGO, CARGOSHIP_HATCH, FEEDER_STATION,
                                            LIFT_LEVEL_NEAR_FLOOR, LIFT_LEVEL_FLOOR };

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

	// lift motor
	private static TalonSRX masterMotor, slaveMotor;

    private static Joystick gamepad, controlPanel;

    public static void initialize() {
  
        if (initialized) return;
  
        // initialize things
        InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "initializing lift...");

        gamepad = new Joystick(HardwareIDs.CO_DRIVER_GAMEPAD_ID);
        controlPanel = new Joystick(HardwareIDs.CONTROL_PANEL_ID);

        // create and initialize lift motors
	    masterMotor = configureMotor(HardwareIDs.LIFT_MASTER_TALON_ID, MASTER_REVERSE_MOTOR, ALIGNED_MASTER_SENSOR, kP, kI, kD, kF);
		slaveMotor = configureMotor(HardwareIDs.LIFT_SLAVE_TALON_ID, SLAVE_REVERSE_MOTOR, HardwareIDs.LIFT_MASTER_TALON_ID);

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
 
		// forward limit switch is for up motion
		_talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		// reverse limit switch is for down action
		_talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);

    	_talon.setNeutralMode(NeutralMode.Brake);
 
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
    	
    	_talon.setNeutralMode(NeutralMode.Brake);
   	
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
        double currentPos = getCurrPosInches();
        double cmdPos = currentPos;
           
        // check all control panel lift buttons (note they start with 1, not zero)
        for (int i = HardwareIDs.CP_ROCKET_CARGO_HIGH; i <= HardwareIDs.CP_FLOOR; i++)
        {
            if (controlPanel.getRawButton(i)) cmdPos = liftLevels[i];
        }

        // if commanded position is significantly different from current, send command to lift
        if (Math.abs(cmdPos - currentPos) > ALLOWABLE_LIFT_POS_ERROR)
        {
            // send lift to new commanded position
            runLift(cmdPos, CLOSED_LOOP_VEL_SLOW, CLOSED_LOOP_ACCEL_SLOW);
        }
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