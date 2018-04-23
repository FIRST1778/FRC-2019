package ChillySwerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import Utility.HardwareIDs;

public class ChillySwerveUnit {
	
	// turn motor controller
	private TalonSRX turnMotor;
	private int turnTalonID = 0;
	
	// drive motor controller
	private TalonSRX driveMotor;
	private int driveTalonID = 1;
	
	// talon constants
	private static final int TIMEOUT_MS = 0;  // set to zero if skipping confirmation
	private static final int PIDLOOP_IDX = 0;  // set to zero if primary loop
	private static final int PROFILE_SLOT = 0;

	public static final boolean REVERSE_DRIVE_MOTOR = false;   // motor polarity
	public static final boolean ALIGNED_DRIVE_SENSOR = true;	// encoder polarity

	public static final boolean REVERSE_TURN_MOTOR = false;   // motor polarity
	public static final boolean ALIGNED_TURN_SENSOR = true;	// encoder polarity

	// encoder variables	
	private final double ENCODER_PULSES_PER_REV = 256*4;  // 63R  - on the competition bot motors
	private final double INCHES_PER_REV = (5.9 * 3.14159);   // 5.9-in diameter wheel (worn)		

	// PIDF values - drive
	private static final double drive_kP = 1.0;
	private static final double drive_kI = 0.0005;  
	private static final double drive_kD = 0.0;
	private static final double drive_kF = 0.0;	
	private static final int drive_kIZone = 18;	

	// PIDF values - turn
	private static final double turn_kP = 0.007;
	private static final double turn_kI = 0.00002;  
	private static final double turn_kD = 0.0;
	private static final double turn_kF = 0.0;	
	private static final int turn_kIZone = 18;

	public ChillySwerveUnit(int driveTalonID, int turnTalonID) {
		this.driveTalonID = driveTalonID;
		this.turnTalonID = turnTalonID;
		
		driveMotor = configureMotor(driveTalonID, REVERSE_DRIVE_MOTOR, ALIGNED_DRIVE_SENSOR, 
				drive_kP, drive_kI, drive_kD, drive_kF, drive_kIZone);
		turnMotor = configureMotor(turnTalonID, REVERSE_TURN_MOTOR, ALIGNED_TURN_SENSOR,
				turn_kP, turn_kI, turn_kD, turn_kF, turn_kIZone);
		
		initialize();
	}
	
    // closed-loop motor configuration
    private TalonSRX configureMotor(int talonID, boolean revMotor, boolean alignSensor,
    									double pCoeff, double iCoeff, double dCoeff, double fCoeff, int iZone)
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
    	_talon.config_IntegralZone(PROFILE_SLOT, iZone, TIMEOUT_MS);
    	
    	_talon.setSelectedSensorPosition(0, PIDLOOP_IDX, TIMEOUT_MS);
    	 
    	return _talon;
    }

	public void stopMotors() {
		setDrivePower(0);
		setTurnPower(0);
	}
	
	public void initialize() {
		stopMotors();
	}
	
	public double getTurnEncPos() {
		return turnMotor.getSelectedSensorPosition(0);
	}
	
	public double getAbsPos() {
		//return (turnMotor.getPulseWidthPosition() & 0xFFF)/4095d;
		return 0;
	}

	public void resetTurnEnc() {
		turnMotor.setSelectedSensorPosition(0, PIDLOOP_IDX, TIMEOUT_MS);
	}

	public void setEncPos(int d) {
		turnMotor.setSelectedSensorPosition(d, PIDLOOP_IDX, TIMEOUT_MS);
	}
	
	public int getTurnRotations() {
		return (int) (turnMotor.getSelectedSensorPosition(0)/ ENCODER_PULSES_PER_REV);
	}
		
	public void setDrivePower(double percentVal) {
		driveMotor.set(ControlMode.PercentOutput, percentVal);	
	}
	
	public void setTurnPower(double percentVal) {
		turnMotor.set(ControlMode.PercentOutput, percentVal);	
	}
	
	public void setTurnLocation(double loc) {
		
		double base = getTurnRotations() * ENCODER_PULSES_PER_REV;
		double turnEncPos = getTurnEncPos();
		
		if (turnEncPos >= 0) {
			if ((base + (loc * ENCODER_PULSES_PER_REV)) - turnEncPos < -ENCODER_PULSES_PER_REV/2) {
				base += ENCODER_PULSES_PER_REV;
			} else if ((base + (loc * ENCODER_PULSES_PER_REV)) - turnEncPos > ENCODER_PULSES_PER_REV/2) {
				base -= ENCODER_PULSES_PER_REV;
			}
			turnMotor.set(ControlMode.Position,(((loc * ENCODER_PULSES_PER_REV) + (base))));
		} 
		else {
			if ((base - ((1-loc) * ENCODER_PULSES_PER_REV)) - turnEncPos < -ENCODER_PULSES_PER_REV/2) {
				base += ENCODER_PULSES_PER_REV;
			} else if ((base -((1-loc) * ENCODER_PULSES_PER_REV)) - turnEncPos > ENCODER_PULSES_PER_REV/2) {
				base -= ENCODER_PULSES_PER_REV;
			}
			turnMotor.set(ControlMode.Position,(base - (((1-loc) * ENCODER_PULSES_PER_REV))));	
		}		
	}
	
	public void stopBoth() {
		setDrivePower(0);
		setTurnPower(0);
	}
	
	public void stopDrive() {
		setDrivePower(0);
	}
	
	public void setBrakeMode(boolean b) {
		if (b == true)
			driveMotor.setNeutralMode(NeutralMode.Brake);
		else
			driveMotor.setNeutralMode(NeutralMode.Coast);
	}

	
}
