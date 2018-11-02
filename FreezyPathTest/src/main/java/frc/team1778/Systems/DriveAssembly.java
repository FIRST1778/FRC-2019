package frc.team1778.Systems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import frc.team1778.NetworkComm.InputOutputComm;
import frc.team1778.Utility.HardwareIDs;

//Chill Out 1778 class for controlling the drivetrain

public class DriveAssembly {
		
	private static boolean initialized = false;
	private static final int TIMEOUT_MS = 0;  // set to zero if skipping confirmation
	private static final int PIDLOOP_IDX = 0;  // set to zero if primary loop
	private static final int PROFILE_SLOT = 0;
						
	private static final double AUTO_DRIVE_ANGLE_CORRECT_COEFF = 0.02;
	private static final double GYRO_CORRECT_COEFF = 0.03;
			
	// smart controllers (motion profiling)
	private static TalonSRX mFrontLeft, mFrontRight;
	private static TalonSRX mBackLeft, mBackRight;
		
	// used as angle baseline (if we don't reset gyro)
	private static double initialAngle = 0.0;
	
	// drive assembly forward
	private static boolean motorForward = true;

	// motor polarity
	public static final boolean RIGHT_REVERSE_MOTOR = true;    // comp-bot motor polarity - right
	public static final boolean LEFT_REVERSE_MOTOR = false;		// comp-bot motor polarity - left
		
	// grayhill encoder polarity (front motors only)
	public static final boolean ALIGNED_RIGHT_SENSOR = true;	// encoder polarity - front right
	public static final boolean ALIGNED_LEFT_SENSOR = true;    // encoder polarity - front left
		
	// PIDF values - proto.bot - initial values - tested Jan 25 with 93 lb. robot
	private static final double kP = 10.0;
	private static final double kI = 0.0005;  
	private static final double kD = 0.0;
	private static final double kF = 0.0;  // Feedforward not used for closed loop position control
	
	public static void initialize() {
		
		if (initialized)
			return;
		
		//ioComm = InputOutputComm.GetInstance();
		NavXSensor.initialize();
		
		// instantiate motion profile motor control objects        
		mFrontLeft = configureMotor(HardwareIDs.LEFT_FRONT_TALON_ID, LEFT_REVERSE_MOTOR, ALIGNED_LEFT_SENSOR, kP, kI, kD, kF);
		mFrontRight = configureMotor(HardwareIDs.RIGHT_FRONT_TALON_ID, RIGHT_REVERSE_MOTOR, ALIGNED_RIGHT_SENSOR, kP, kI, kD, kF);
		mBackLeft = configureMotor(HardwareIDs.LEFT_REAR_TALON_ID, LEFT_REVERSE_MOTOR, HardwareIDs.LEFT_FRONT_TALON_ID);
		mBackRight = configureMotor(HardwareIDs.RIGHT_REAR_TALON_ID, RIGHT_REVERSE_MOTOR, HardwareIDs.RIGHT_FRONT_TALON_ID);
        
		resetMotors();
		
		resetPos();
		
        initialized = true;
	}

	public static void resetMotors()
	{		
		// turn all motors to zero power (rear motors follow front motors)
		mFrontLeft.set(ControlMode.PercentOutput,0.0);
		mFrontRight.set(ControlMode.PercentOutput,0.0);		

		// ensure all motors set to regular polarity
		setMotorForward(true);
	}
	
	public static void resetPos()
	{		
		// reset front left and right encoder pulses to zero
		mFrontLeft.setSelectedSensorPosition(0, PIDLOOP_IDX, TIMEOUT_MS);
		mFrontRight.setSelectedSensorPosition(0, PIDLOOP_IDX, TIMEOUT_MS);
	}	 	        
		
	public static void setMotorForward(boolean forward)
	{
		if (!initialized)
			initialize();

		// set motor forward setting variable
		motorForward = forward;

		if (forward == true)
		{
			// set all motors normal polarity (forward)
			mFrontLeft.setInverted(LEFT_REVERSE_MOTOR);
			mFrontRight.setInverted(RIGHT_REVERSE_MOTOR);
			mBackLeft.setInverted(LEFT_REVERSE_MOTOR);
			mBackRight.setInverted(RIGHT_REVERSE_MOTOR);

			// set sensor polarity (front motors only)
			mFrontLeft.setSensorPhase(ALIGNED_LEFT_SENSOR); 
			mFrontRight.setSensorPhase(ALIGNED_RIGHT_SENSOR); 

		}
		else
		{
			// invert all motors polarity (backward)
			mFrontLeft.setInverted(!LEFT_REVERSE_MOTOR);
			mFrontRight.setInverted(!RIGHT_REVERSE_MOTOR);
			mBackLeft.setInverted(!LEFT_REVERSE_MOTOR);
			mBackRight.setInverted(!RIGHT_REVERSE_MOTOR);

			// invert sensor polarity (front motors only)
			mFrontLeft.setSensorPhase(!ALIGNED_LEFT_SENSOR); 
			mFrontRight.setSensorPhase(!ALIGNED_RIGHT_SENSOR); 

		}
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

	public static double getDistanceInches() {
				
		// Encoders now read only raw encoder values - convert raw to inches directly
		double rightPos = mFrontRight.getSelectedSensorPosition(0)*HardwareIDs.INCHES_PER_ENCODER_PULSE;
		double leftPos = mFrontLeft.getSelectedSensorPosition(0)*HardwareIDs.INCHES_PER_ENCODER_PULSE;
				
		String posStr = String.format("%.2f", rightPos);
		InputOutputComm.putString(InputOutputComm.LogTable.kMainLog,"Auto/EncoderRight", posStr);
		posStr = String.format("%.2f", leftPos);
		InputOutputComm.putString(InputOutputComm.LogTable.kMainLog,"Auto/EncoderLeft", posStr);
		
		return rightPos;
	}
	
	public static int getLeftEncoder()
	{
		return mFrontLeft.getSelectedSensorPosition(0);
	}
	
	public static int getRightEncoder()
	{
		return mFrontRight.getSelectedSensorPosition(0);
	}
	
	public static void autoInit(boolean resetGyro, double headingDeg, boolean magicMotion) {
				
		if (resetGyro) {
			NavXSensor.reset();
			initialAngle = 0.0;
		}
		else
			//initialAngle = NavXSensor.getAngle();
			initialAngle = headingDeg;				// target heading if not resetting gyro
		
		// set current position to zero
		resetPos();
   	}
					
	public static void autoGyroStraight(double speed) {
		// autonomous operation of drive straight - uses gyro
		
		double gyroAngle = NavXSensor.getAngle();
		
		// subtract the initial angle offset, if any
		gyroAngle -= initialAngle;
		
		// calculate speed adjustment for left and right sides (negative sign added as feedback)
		double driveAngle = -gyroAngle * AUTO_DRIVE_ANGLE_CORRECT_COEFF;
				
		double leftSpeed = speed+driveAngle;		
		double rightSpeed = speed-driveAngle;
				
		// adjust speed of left and right sides
		drive(leftSpeed, rightSpeed);		 
	}
	
	public static void autoMagicStraight(double targetPosInches, int speedRpm, int accelRpm) {
		
		int nativeUnitsPer100ms = (int) ((double)speedRpm * HardwareIDs.RPM_TO_UNIT_PER_100MS);
		int accelNativeUnits = (int) ((double)accelRpm * HardwareIDs.RPM_TO_UNIT_PER_100MS);

        // left front drive straight - uses motion magic
		mFrontLeft.configMotionCruiseVelocity(nativeUnitsPer100ms, TIMEOUT_MS);
		mFrontLeft.configMotionAcceleration(accelNativeUnits, TIMEOUT_MS);
		mFrontLeft.set(ControlMode.MotionMagic, targetPosInches/HardwareIDs.INCHES_PER_ENCODER_PULSE);
		
        // right front drive straight - uses motion magic
        mFrontRight.configMotionCruiseVelocity(nativeUnitsPer100ms, TIMEOUT_MS);
        mFrontRight.configMotionAcceleration(accelNativeUnits, TIMEOUT_MS);
        mFrontRight.set(ControlMode.MotionMagic, targetPosInches/HardwareIDs.INCHES_PER_ENCODER_PULSE);
		
		// left and right back motors are following front motors
	}
	
	public static void autoMagicTurn(double targetPosInchesLeft, double targetPosInchesRight, int speedRpm, int accelRpm) {

		int nativeUnitsPer100ms = (int) ((double)speedRpm * HardwareIDs.RPM_TO_UNIT_PER_100MS);
		int accelNativeUnits = (int) ((double)accelRpm * HardwareIDs.RPM_TO_UNIT_PER_100MS);
		
        // left front drive straight - uses motion magic
		mFrontLeft.configMotionCruiseVelocity(nativeUnitsPer100ms, TIMEOUT_MS);
		mFrontLeft.configMotionAcceleration(accelNativeUnits, TIMEOUT_MS);
		mFrontLeft.set(ControlMode.MotionMagic, targetPosInchesLeft/HardwareIDs.INCHES_PER_ENCODER_PULSE);
		
        // right front drive straight - uses motion magic
        mFrontRight.configMotionCruiseVelocity(nativeUnitsPer100ms, TIMEOUT_MS);
        mFrontRight.configMotionAcceleration(accelNativeUnits, TIMEOUT_MS);
        mFrontRight.set(ControlMode.MotionMagic, targetPosInchesRight/HardwareIDs.INCHES_PER_ENCODER_PULSE);
				
		// left and right back motors are following front motors
	}
		
	public static void autoStop() {
		resetMotors();
	}
			
	public static void teleopInit() {
	}
	
	public static void teleopPeriodic() {	
	}
	
	public static void disabledInit( )  {
		resetMotors();

	}
	
	// CORE DRIVE METHOD
	// Assumes parameters are PercentVbus (0.0 to 1.0)
	public static void drive(double leftValue, double rightValue) {
				
		double adjLeftVal = leftValue;
		double adjRightVal = rightValue;
		
		//String leftSpeedStr = String.format("%.2f", adjLeftVal);
		//String rightSpeedStr = String.format("%.2f", adjRightVal);
		//String myString2 = new String("leftSpeed = " + leftSpeedStr + " rightSpeed = " + rightSpeedStr);
		//System.out.println(myString2);
		//ioComm.putString(InputOutputComm.LogTable.kMainLog,"Auto/AutoDrive", myString2);

		// set front motor values directly
		if (motorForward)
		{
			// forward, left cmd -> left motors and right cmd -> right motors
			mFrontLeft.set(ControlMode.PercentOutput, adjLeftVal);
			mFrontRight.set(ControlMode.PercentOutput, adjRightVal);	
		}
		else
		{
			// going reverse, so right cmd -> left motors and left cmd -> right motors
			mFrontLeft.set(ControlMode.PercentOutput, adjRightVal);
			mFrontRight.set(ControlMode.PercentOutput, adjLeftVal);	
		}
		
		// back motors now follow front motors
		//mBackLeft.set(ControlMode.PercentOutput, leftValue);
		//mBackRight.set(ControlMode.PercentOutput, rightValue);		
	}
	
	public static void driveDirection(double angle, double speed) {
		double gyroAngle = NavXSensor.getAngle();	
		double driveAngle = (angle-gyroAngle)*GYRO_CORRECT_COEFF;
		drive(driveAngle+speed, -driveAngle+speed);
	}
	
	public static void turnToDirection(double angle, double power) {
		double gyroAngle = NavXSensor.getAngle();
		double driveAngle = (angle-gyroAngle)*(1/360)*power;
		drive(driveAngle, -driveAngle);
	}
	
	public static void driveForward(double forwardVel) {
		drive(forwardVel, forwardVel);
	}
	
	public static void rotate(double angularVel) {
		drive(angularVel, -angularVel);
	}
	
	public static void driveVelocity(double forwardVel, double angularVel) {
		drive((forwardVel+angularVel)/2.0,(forwardVel-angularVel)/2.0);
	}
		
	//Turn methods
	//===================================================
	public static void rotateLeft(double speed) {		
		drive(-speed, speed);
	}

	public static void rotateRight(double speed) {
		drive(speed, -speed);
	}
}
