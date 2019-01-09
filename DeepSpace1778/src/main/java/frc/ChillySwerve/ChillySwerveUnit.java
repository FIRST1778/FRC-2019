package frc.ChillySwerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.Systems.NavXSensor;

public class ChillySwerveUnit {

  // turn motor controller
  private TalonSRX turnMotor;
  private int turnTalonID = 0;

  // drive motor controller
  private TalonSRX driveMotor;
  private int driveTalonID = 1;

  public boolean rev_drive_motor = false;
  public boolean aligned_drive_sensor = true;

  private boolean rev_turn_motor = false;
  //private boolean aligned_turn_sensor = true;

  private double zero_angle_offset = 0.0;
  private double last_target_angle = 0.0;

  // talon constants
  private static final int TIMEOUT_MS = 0; // set to zero if skipping confirmation
  private static final int PIDLOOP_IDX = 0; // set to zero if primary loop
  private static final int PROFILE_SLOT = 0;

	// drive encoder Constants
	//private static final double ENCODER_PULSES_PER_REV = 256*4;  // 63R  - on 2018 competition bot motors
  private static final double ENCODER_PULSES_PER_REV = 20 * 4; // am-3314a (CIM)encoders
	private static final double INCHES_PER_REV = (5.9 * 3.14159);   // 5.9-in diameter wheel (worn)
			
	public static final double INCHES_PER_ENCODER_PULSE = INCHES_PER_REV/ENCODER_PULSES_PER_REV;
	public static final double RPM_TO_UNIT_PER_100MS = ENCODER_PULSES_PER_REV/(60*10);

  // PIDF values - drive
  private static final double drive_kP = 1.0;
  private static final double drive_kI = 0.001;
  private static final double drive_kD = 0.0;
  private static final double drive_kF = 0.0;
  private static final int drive_kIZone = 18;

  // PIDF values - turn
  private static final double turn_kP = 4.2;
  private static final double turn_kI = 0.01;
  private static final double turn_kD = 0.0;
  private static final double turn_kF = 0.0;
  private static final int turn_kIZone = 200;

  public ChillySwerveUnit(int driveTalonID, int turnTalonID, double zero_angle_offset) {

    this.driveTalonID = driveTalonID;
    this.turnTalonID = turnTalonID;
    this.zero_angle_offset = zero_angle_offset;
    last_target_angle = 0.0;

    /***************** DRIVE MOTOR SETUP *****************/
    
    // debug only (when no drive encoders present) - run drive motor as open loop (temporarily)
    //driveMotor = new TalonSRX(driveTalonID);
    //setDriveMotorForward(true);

    driveMotor =
        configureMotor(
            driveTalonID, rev_drive_motor, drive_kP, drive_kI, drive_kD, drive_kF, drive_kIZone);

    driveMotor.configSelectedFeedbackSensor(
        FeedbackDevice.QuadEncoder, PIDLOOP_IDX, TIMEOUT_MS); // am-3314a quad encoder
    driveMotor.setSensorPhase(aligned_drive_sensor);
    driveMotor.setSelectedSensorPosition(0, PIDLOOP_IDX, TIMEOUT_MS);
    /***************** DRIVE MOTOR SETUP *****************/

    /***************** TURN MOTOR SETUP *****************/
    turnMotor =
        configureMotor(
            turnTalonID, rev_turn_motor, turn_kP, turn_kI, turn_kD, turn_kF, turn_kIZone);

    // configure turn sensor as analog input
    turnMotor.configSelectedFeedbackSensor(
        FeedbackDevice.Analog, PIDLOOP_IDX, TIMEOUT_MS); // MA3 Absolute encoder
    turnMotor.setSensorPhase(true);  

    // ensures overflow data is not read along with raw analog encoder data
    turnMotor.setNeutralMode(NeutralMode.Brake);
    turnMotor.set(ControlMode.Position, 0);
    /***************** TURN MOTOR SETUP *****************/

  }

  // closed-loop motor configuration
  private TalonSRX configureMotor(
      int talonID,
      boolean revMotor,
      double pCoeff,
      double iCoeff,
      double dCoeff,
      double fCoeff,
      int iZone) {
    TalonSRX _talon;
    _talon = new TalonSRX(talonID);
    _talon.setInverted(revMotor);

    _talon.selectProfileSlot(PROFILE_SLOT, PIDLOOP_IDX);
    _talon.config_kP(PROFILE_SLOT, pCoeff, TIMEOUT_MS);
    _talon.config_kI(PROFILE_SLOT, iCoeff, TIMEOUT_MS);
    _talon.config_kD(PROFILE_SLOT, dCoeff, TIMEOUT_MS);
    _talon.config_kF(PROFILE_SLOT, fCoeff, TIMEOUT_MS);
    _talon.config_IntegralZone(PROFILE_SLOT, iZone, TIMEOUT_MS);

    _talon.configPeakCurrentLimit(50, TIMEOUT_MS);
    _talon.enableCurrentLimit(true);

    return _talon;
  }

  public void stopMotors() {
    setDrivePower(0);
    setTurnPower(0);
  }

  public void initialize() {
    // move all swerve motors to zero angle
    setTargetAngle(0);
  }

  public TalonSRX getDriveMotor() {
    return driveMotor;
  }

  public TalonSRX getTurnMotor() {
    return turnMotor;
  }

  public double getTurnZeroOffset() {
    return zero_angle_offset;
  }

  public double getTargetAngle() {
      return last_target_angle;
  }

  public double getRawAbsAngle() {
    // returns absolute (raw) absolute angle value - no offset applied
    return (double) turnMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
  }

  public double getTurnEnc() {
    // returns turn encoder value
     return (double) turnMotor.getSelectedSensorPosition(0);
  }

  public double getAbsAngle() {
    // returns absolute angle of wheel in degrees (may wrap beyond 360 deg)
    return ((double) turnMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0)) - zero_angle_offset;
  }

  public double getDistanceInches() {
    return (double) (driveMotor.getSelectedSensorPosition(0)) * INCHES_PER_ENCODER_PULSE;
  }

  public void setDrivePower(double percentVal) {
    driveMotor.set(ControlMode.PercentOutput, percentVal);
  }

  public void setTurnPower(double percentVal) {
    turnMotor.set(ControlMode.PercentOutput, percentVal);
  }

      /**
     * Get the current angle of the swerve module
     *
     * @return An angle in the range [0, 360)
     */
    public double getCurrentAngle() {
      double angle = turnMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
      angle -= zero_angle_offset;
      angle %= 360;
      if (angle < 0) angle += 360;

      return angle;
  }

	public void setTargetAngle(double targetAngle) {
		last_target_angle = targetAngle;

		targetAngle %= 360;
		targetAngle += zero_angle_offset;

		double currentAngle = getRawAbsAngle();
		double currentAngleMod = currentAngle % 360;
		if (currentAngleMod < 0) currentAngleMod += 360;

		double delta = currentAngleMod - targetAngle;

		if (delta > 180) {
			targetAngle += 360;
		} else if (delta < -180) {
			targetAngle -= 360;
    }
    
		delta = currentAngleMod - targetAngle;
		if (delta > 90 || delta < -90) {
			if (delta > 90)
				targetAngle += 180;
			else if (delta < -90)
				targetAngle -= 180;
			driveMotor.setInverted(false);
		} else {
			driveMotor.setInverted(true);
    }
    
    targetAngle += currentAngle - currentAngleMod;

    // convert to encoder ticks
    targetAngle *= 1024.0 / 360.0;
    
		turnMotor.set(ControlMode.Position,targetAngle);
	}

  public void stopBoth() {
    setDrivePower(0);
    setTurnPower(0);
  }

  public void stopDrive() {
    setDrivePower(0);
  }

  public void setBrakeMode(boolean b) {
    if (b == true) driveMotor.setNeutralMode(NeutralMode.Brake);
    else driveMotor.setNeutralMode(NeutralMode.Coast);
  }

  public void setDriveMotorForward(boolean motorPolarity) {
    rev_drive_motor = !motorPolarity;
    boolean sensorPolarity = motorPolarity;

    driveMotor.setInverted(rev_drive_motor);
    driveMotor.setSensorPhase(sensorPolarity);
  }

  public void resetDriveEnc() {
    driveMotor.setSelectedSensorPosition(0, PIDLOOP_IDX, TIMEOUT_MS);
  }

  // used in auto (assumes angle is already set)
  public void driveDistance(double targetPosInches, double speedRpm, double accelRpm) 
  {
		int nativeUnitsPer100ms = (int) ((double)speedRpm * RPM_TO_UNIT_PER_100MS);
		int accelNativeUnits = (int) ((double)accelRpm * RPM_TO_UNIT_PER_100MS);

    // uses CTRE Talon SRX motion magic
    driveMotor.configMotionCruiseVelocity(nativeUnitsPer100ms, TIMEOUT_MS);
    driveMotor.configMotionAcceleration(accelNativeUnits, TIMEOUT_MS);
    driveMotor.set(ControlMode.MotionMagic, targetPosInches/INCHES_PER_ENCODER_PULSE);
  }

}
