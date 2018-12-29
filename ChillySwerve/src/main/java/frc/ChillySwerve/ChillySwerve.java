package frc.ChillySwerve;

import edu.wpi.first.wpilibj.Joystick;
import frc.NetworkComm.InputOutputComm;
import frc.Systems.NavXSensor;
import frc.Utility.HardwareIDs;
import jaci.pathfinder.followers.EncoderFollower;

public class ChillySwerve {

  private static final double AUTO_DRIVE_ANGLE_CORRECT_COEFF = 0.02;
  private static final double GYRO_CORRECT_COEFF = 0.03;

  // debug purposes - don't run full on
  private static final double INPUT_GAIN_FACTOR = 0.5;

  // swerve units
  private static ChillySwerveUnit frontLeft, frontRight;
  private static ChillySwerveUnit backLeft, backRight;

  // swerve inputs
  private static Joystick driveGamepad;
  private static final double JOYSTICK_DEADZONE = 0.1;

  // drive values
  private static double fwd = 0.0;
  private static double str = 0.0;
  private static double rot = 0.0;

  // field-centric mode toggle
  private static boolean field_centric = true;

  // robot dimension ratios (units don't matter, just be consistent with all three)
  private static final double l = 23.5; // drive base length
  private static final double w = 17.0; // drive base width
  private static final double r = Math.sqrt((l * l) + (w * w)); // diagonal drive base length

  // gyro angle
  private static double angleDeg = 0.0;

  private static boolean initialized = false;

  // wheel angle offsets in deg
  public static final double FL_ABS_ZERO_ANGLE_OFFSET = 5.0;
  public static final double FR_ABS_ZERO_ANGLE_OFFSET = 80.0;
  public static final double BL_ABS_ZERO_ANGLE_OFFSET = -110.0;
  public static final double BR_ABS_ZERO_ANGLE_OFFSET = -140.0;

  public static void reset() {

    // make sure we're initialized
    initialize();

    // orient all wheels forward
    setAllTurnAngle(0);

    // zero out all drive motors
    setAllDrivePower(0);

    // set all drive motor and sensor polarities, reset encoders
    setDriveMotorForward(true);
    resetAllDriveEnc();

    // reset gyro
    NavXSensor.reset();
  }

  public static void initialize() {
    if (initialized) return;

    driveGamepad = new Joystick(HardwareIDs.CONTROL_GAMEPAD_ID);

    // create the swerve modules
    frontLeft =
        new ChillySwerveUnit(
            HardwareIDs.FRONT_LEFT_DRIVE_TALON_ID, HardwareIDs.FRONT_LEFT_ROTATE_TALON_ID,
            FL_ABS_ZERO_ANGLE_OFFSET);
    frontRight =
        new ChillySwerveUnit(
            HardwareIDs.FRONT_RIGHT_DRIVE_TALON_ID, HardwareIDs.FRONT_RIGHT_ROTATE_TALON_ID,
            FR_ABS_ZERO_ANGLE_OFFSET);
    backLeft =
        new ChillySwerveUnit(
            HardwareIDs.BACK_LEFT_DRIVE_TALON_ID, HardwareIDs.BACK_LEFT_ROTATE_TALON_ID,
            BL_ABS_ZERO_ANGLE_OFFSET);
    backRight =
        new ChillySwerveUnit(
            HardwareIDs.BACK_RIGHT_DRIVE_TALON_ID, HardwareIDs.BACK_RIGHT_ROTATE_TALON_ID,
            BR_ABS_ZERO_ANGLE_OFFSET);

    // gyro needed for field-centric drive
    NavXSensor.initialize();

    // initialize the swerve modules
    frontLeft.initialize();
    frontRight.initialize();
    backLeft.initialize();
    backRight.initialize();

    initialized = true;
  }

  public static void autoInit() {
    reset();
  }

  public static void teleopInit() {
    reset();
  }

  public static void teleopPeriodic() {
    double joyVal;

    // get joystick inputs
    joyVal = INPUT_GAIN_FACTOR * driveGamepad.getRawAxis(HardwareIDs.LEFT_Y_AXIS);
    fwd = (Math.abs(joyVal) > JOYSTICK_DEADZONE) ? joyVal : 0.0;

    joyVal = -1.0 * INPUT_GAIN_FACTOR * driveGamepad.getRawAxis(HardwareIDs.LEFT_X_AXIS);
    str = (Math.abs(joyVal) > JOYSTICK_DEADZONE) ? joyVal : 0.0;

    joyVal = -1.0 * INPUT_GAIN_FACTOR * driveGamepad.getRawAxis(HardwareIDs.RIGHT_X_AXIS);
    rot = (Math.abs(joyVal) > JOYSTICK_DEADZONE) ? joyVal : 0.0;

    // swerve reference mode (toggle control)
    // left toggle switch on freezy drive control unit
    field_centric = driveGamepad.getRawButton(2);  

    if (field_centric)
    {
      // Use field coordinate system
      fieldCentricDrive(fwd, str, rot);

      InputOutputComm.putString(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AA_mode", "field_centric");
      }
    else
    {
      // Use robot coordinate system
      humanDrive(fwd, str, rot);

      InputOutputComm.putString(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AA_mode", "robot_centric");
   }


    // debug only
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AA_swerveCmd/fwd", fwd);
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AA_swerveCmd/str", str);
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AA_swerveCmd/rot", rot);
      
    // debug only
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AC_Angles/FL_absAngle", frontLeft.getAbsAngle());
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AC_Angles/FR_absAngle", frontRight.getAbsAngle());
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AC_Angles/BL_absAngle", backLeft.getAbsAngle());
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AC_Angles/BR_absAngle", backRight.getAbsAngle());

    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AD_RawAngles/FL_rawAngle", frontLeft.getRawAbsAngle());
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AD_RawAngles/FR_rawAngle", frontRight.getRawAbsAngle());
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AD_RawAngles/BL_rawAngle", backLeft.getRawAbsAngle());
    InputOutputComm.putDouble(
        InputOutputComm.LogTable.kMainLog, "ChillySwerve/AD_RawAngles/BR_rawAngle", backRight.getRawAbsAngle());
 
    InputOutputComm.putDouble(
          InputOutputComm.LogTable.kMainLog, "ChillySwerve/Encoders/FL_turnEnc", frontLeft.getTurnEnc());
    InputOutputComm.putDouble(
          InputOutputComm.LogTable.kMainLog, "ChillySwerve/Encoders/FR_turnEnc", frontRight.getTurnEnc());
    InputOutputComm.putDouble(
          InputOutputComm.LogTable.kMainLog, "ChillySwerve/Encoders/BL_turnEnc", backLeft.getTurnEnc());
    InputOutputComm.putDouble(
          InputOutputComm.LogTable.kMainLog, "ChillySwerve/Encoders/BR_turnEnc", backRight.getTurnEnc());
  
    InputOutputComm.putDouble(
          InputOutputComm.LogTable.kMainLog, "ChillySwerve/ZeroOffsets/FL_TurnZeroOffset", frontLeft.getTurnZeroOffset());
    InputOutputComm.putDouble(
          InputOutputComm.LogTable.kMainLog, "ChillySwerve/ZeroOffsets/FR_TurnZeroOffset", frontRight.getTurnZeroOffset());
    InputOutputComm.putDouble(
          InputOutputComm.LogTable.kMainLog, "ChillySwerve/ZeroOffsets/BL_TurnZeroOffset", backLeft.getTurnZeroOffset());
    InputOutputComm.putDouble(
          InputOutputComm.LogTable.kMainLog, "ChillySwerve/ZeroOffsets/BR_TurnZeroOffset", backRight.getTurnZeroOffset());

  }

  public static void disabledInit() {
     // zero out all drive motors
     setAllDrivePower(0);
  }

  public static void disabledPeriodic() {
    // nothing yet
  }

  public static void swerveDrive(double fwd, double str, double rot) {

    // theoretical trig calc - has rotation problem
    //double a = str - (rot * (l / r));
    //double b = str + (rot * (l / r));
    //double c = fwd - (rot * (w / r));
    //double d = fwd + (rot * (w / r));

    // protobot trig calc - works correctly
    double a = str - (rot * (l / r));
    double b = str + (rot * (l / r));
    double c = fwd + (rot * (w / r));
    double d = fwd - (rot * (w / r));

    double ws1 = Math.sqrt((b * b) + (c * c));
    double ws2 = Math.sqrt((b * b) + (d * d));
    double ws3 = Math.sqrt((a * a) + (d * d));
    double ws4 = Math.sqrt((a * a) + (c * c));

    double wa1 = Math.atan2(b, c) * 180 / Math.PI;
    double wa2 = Math.atan2(b, d) * 180 / Math.PI;
    double wa3 = Math.atan2(a, d) * 180 / Math.PI;
    double wa4 = Math.atan2(a, c) * 180 / Math.PI;

    double max = ws1;
    max = Math.max(max, ws2);
    max = Math.max(max, ws3);
    max = Math.max(max, ws4);
    if (max > 1) {
      ws1 /= max;
      ws2 /= max;
      ws3 /= max;
      ws4 /= max;
    }

    double fr_pwr = ws1;
    double fl_pwr = ws2;
    double bl_pwr = ws3;
    double br_pwr = ws4;

    double fr_angle = wa1;
    double fl_angle = wa2;
    double bl_angle = wa3;
    double br_angle = wa4;

    // ws1..ws4 and wa1..wa4 are the wheel speeds and wheel angles for wheels 1 through 4
    // which are front_right, front_left, rear_left, and rear_right, respectively.

    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "ChillySwerve/AB_DrivePwr/FL_pwr", fl_pwr);
    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "ChillySwerve/AB_DrivePwr/FR_pwr", fr_pwr);
    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "ChillySwerve/AB_DrivePwr/BL_pwr", bl_pwr);
    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "ChillySwerve/AB_DrivePwr/BR_pwr", br_pwr);

    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "ChillySwerve/AB_TurnAngles/FL_angle", fl_angle);
    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "ChillySwerve/AB_TurnAngles/FR_angle", fr_angle);
    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "ChillySwerve/AB_TurnAngles/BL_angle", bl_angle);
    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "ChillySwerve/AB_TurnAngles/BR_angle", br_angle);

    setDrivePower(fl_pwr, fr_pwr, bl_pwr, br_pwr);
    setTargetAngle(fl_angle, fr_angle, bl_angle, br_angle);
  }

  public static void humanDrive(double fwd, double str, double rot) {
    if (Math.abs(rot) < 0.01) rot = 0;

    if (Math.abs(fwd) < .15 && Math.abs(str) < .15 && Math.abs(rot) < 0.01) {
      setDriveBrakeMode(true);
      stopDrive();
    } else {
      setDriveBrakeMode(false);
      swerveDrive(fwd, str, rot);
    }
  }

  public static void setDriveMotorForward(boolean motorPolarity)
  {
    frontLeft.setDriveMotorForward(motorPolarity);
    frontRight.setDriveMotorForward(motorPolarity);
    backLeft.setDriveMotorForward(motorPolarity);
    backRight.setDriveMotorForward(motorPolarity);

  }

  // mode used with Pathfinder
  public static void directDrive(
      EncoderFollower fl, EncoderFollower fr, EncoderFollower bl, EncoderFollower br) {
    frontLeft.drivePath(fl);
    frontRight.drivePath(fr);
    backLeft.drivePath(bl);
    backRight.drivePath(br);
  }

  public static void fieldCentricDrive(double fwd, double str, double rot) {

    double angleRad = -1.0 * NavXSensor.getAngle() * (Math.PI / 180d);

    double temp = (fwd * Math.cos(angleRad)) + (str * Math.sin(angleRad));
    str = (-fwd * Math.sin(angleRad)) + (str * Math.cos(angleRad));
    fwd = temp;

    humanDrive(fwd, str, rot);
  }

  public static void resetAllDriveEnc() {
    frontLeft.resetDriveEnc();
    frontRight.resetDriveEnc();
    backLeft.resetDriveEnc();
    backRight.resetDriveEnc();
  }

  public static void stopDrive() {
    frontLeft.stopDrive();
    frontRight.stopDrive();
    backLeft.stopDrive();
    backRight.stopDrive();
  }

  public static void setDriveBrakeMode(boolean brake) {
    frontLeft.setBrakeMode(brake);
    frontRight.setBrakeMode(brake);
    backLeft.setBrakeMode(brake);
    backRight.setBrakeMode(brake);
  }

  public static void setDrivePower(double fl_pwr, double fr_pwr, double bl_pwr, double br_pwr) {
    frontLeft.setDrivePower(fl_pwr);
    frontRight.setDrivePower(fr_pwr);
    backLeft.setDrivePower(bl_pwr);
    backRight.setDrivePower(br_pwr);
  }

  public static void setTurnPower(double fl_pwr, double fr_pwr, double bl_pwr, double br_pwr) {
    frontLeft.setTurnPower(fl_pwr);
    frontRight.setTurnPower(fr_pwr);
    backLeft.setTurnPower(bl_pwr);
    backRight.setTurnPower(br_pwr);
  }

  public static void setTargetAngle(double fl_angle, double fr_angle, double bl_angle, double br_angle) {

    if (Math.abs(fwd) > 0.05 || Math.abs(str) > 0.05 || Math.abs(rot) > 0.05) 
        {
          frontLeft.setTargetAngle(fl_angle);
          frontRight.setTargetAngle(fr_angle);
          backLeft.setTargetAngle(bl_angle);
          backRight.setTargetAngle(br_angle);
        }
        else 
        {
          frontLeft.setTargetAngle(frontLeft.getTargetAngle());
          frontRight.setTargetAngle(frontRight.getTargetAngle());
          backLeft.setTargetAngle(backLeft.getTargetAngle());
          backRight.setTargetAngle(backRight.getTargetAngle());
        }
  }

  public static void setAllDrivePower(double power) {
    setDrivePower(power, power, power, power);
  }

  public static void setAllTurnPower(double power) {
    setTurnPower(power, power, power, power);
  }

  public static void setAllTurnAngle(double angle) {
    frontLeft.setTargetAngle(angle);
    frontRight.setTargetAngle(angle);
    backLeft.setTargetAngle(angle);
    backRight.setTargetAngle(angle);
  }

  // Orient all swerve motors to an angle and command them to drive a fixed distance
  public static void autoStraight(double targetPosInches, double angleDeg, int speedRpm, int accelRpm) 
  {    
    setAllTurnAngle(angleDeg);  

    frontLeft.driveDistance(targetPosInches, speedRpm, accelRpm);
    frontRight.driveDistance(targetPosInches, speedRpm, accelRpm);
    backLeft.driveDistance(targetPosInches, speedRpm, accelRpm);
    backRight.driveDistance(targetPosInches, speedRpm, accelRpm);
  }

    // Turn method
    // rot = [-1..1] to rotate at full CCW to full CW
  // ===================================================
  public static void rotate(double rot) {

    // bound rotation input
    rot = Math.min(rot,1.0);
    rot = Math.max(rot,-1.0);

    humanDrive(0, 0, rot);
  }

  // Sensor measurement methods
  // ==========================================================
  public static double getDistanceInches() {
    
    // use one of the swerve unit encoders
    return frontLeft.getDistanceInches();
  }

  /*
  public static void autoGyroStraight(double speed, double angleDeg) {
    // autonomous operation of drive straight in a direction relative to field POV - uses gyro

    double gyroAngle = NavXSensor.getAngle();

    // subtract the initial angle offset, if any
    gyroAngle -= initialAngle;

    // calculate speed adjustment for left and right sides (negative sign added as feedback)
    double driveAngle = -gyroAngle * AUTO_DRIVE_ANGLE_CORRECT_COEFF;

    double leftSpeed = speed + driveAngle;
    double rightSpeed = speed - driveAngle;

    // compute swerve parameters
    double angleRad = angleDeg * (Math.PI / 180);
    double fwd = speed * Math.sin(angleRad);
    double str = speed * Math.cos(angleRad);
    double rot = 0;

    fieldCentricDrive(fwd, str, rot);
  }
  */

}