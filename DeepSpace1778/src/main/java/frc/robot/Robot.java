package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;

import frc.ChillySwerve.ChillySwerve;

import frc.NetworkComm.InputOutputComm;
import frc.NetworkComm.RPIComm;

import frc.Systems.NavXSensor;
import frc.Systems.Climber;
import frc.Systems.GamePieceControl;

public class Robot extends TimedRobot {

  protected DriverStation ds;
 
  @Override
  public void robotInit() {

    // Initialize robot subsystems
    InputOutputComm.initialize();
    RPIComm.initialize();
    NavXSensor.initialize();
    Climber.initialize();
    GamePieceControl.initialize();

    // Initialize ChillySwerve Drive controller classes
    ChillySwerve.initialize();

    // retrieve Driver Station instance
    ds = DriverStation.getInstance();

    InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "robot initialized...");
  }

  @Override
  public void autonomousInit() {

    // NOTE: No notable auto modes this season
    InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "autonomous mode...");

    NavXSensor.reset();
    ChillySwerve.autoInit();   
  }

  /** This function is called periodically during autonomous */
  @Override
  public void autonomousPeriodic() {

    // read sensor values out to shuffleboard
    readSensors();
  }

  @Override
  public void teleopInit() {
    InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "teleop mode...");

    NavXSensor.reset();

    Climber.teleopInit();
    GamePieceControl.teleopInit();

    ChillySwerve.teleopInit();
  }

  @Override
  public void teleopPeriodic() {

    Climber.teleopPeriodic();
    GamePieceControl.teleopPeriodic();
    ChillySwerve.teleopPeriodic();

    // read sensor values out to shuffleboard
    readSensors();
  }

  @Override
  public void disabledInit() {

    Climber.disabledInit();
    GamePieceControl.disabledInit();
    ChillySwerve.disabledInit();
  }

  @Override
  public void disabledPeriodic() {
    ChillySwerve.disabledPeriodic();
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  private void readSensors()
  {
    // report current gyro value
    double gyroAngle = NavXSensor.getAngle(); // continuous angle (can be larger than 360 deg)
    InputOutputComm.putDouble(InputOutputComm.LogTable.kMainLog, "ChillySwerve/Sensors_Gyro/GyroAngleDeg", gyroAngle);

    // report current drive distances
    ChillySwerve.getDistanceInches();

    // report current turn wheel angles
    ChillySwerve.getTurnAngleDeg();
  }
}
