package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import frc.ChillySwerve.ChillySwerve;
import frc.NetworkComm.InputOutputComm;
import frc.NetworkComm.RPIComm;
import frc.StateMachine.AutoStateMachine;
import frc.Systems.NavXSensor;
import frc.Systems.FreezyPath;

public class Robot extends IterativeRobot {

  protected DriverStation ds;
  protected AutoStateMachine autoSM;

  @Override
  public void robotInit() {
    // Initialize robot subsystems
    InputOutputComm.initialize();
    RPIComm.initialize();
    NavXSensor.initialize();
    FreezyPath.initialize();

    // Initialize ChillySwerve Drive controller classes
    ChillySwerve.initialize();

    // Create Autonomous State Machine
    autoSM = new AutoStateMachine();

    // retrieve Driver Station instance
    ds = DriverStation.getInstance();

    InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "robot initialized...");
  }

  @Override
  public void autonomousInit() {
    InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "autonomous mode...");

    //IMPORTANT!! Only reset the gyro ONCE, here, at beginning of auto
    NavXSensor.reset();

    ChillySwerve.autoInit();
    
    // start the auto state machine
    autoSM.start();
  }

  /** This function is called periodically during autonomous */
  @Override
  public void autonomousPeriodic() {

    autoSM.process();

    // read sensor values out to shuffleboard
    readSensors();
  }

  @Override
  public void teleopInit() {
    InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "teleop mode...");

    ChillySwerve.teleopInit();
  }

  @Override
  public void teleopPeriodic() {

    // ChillySwerve-Drive command for all controllers
    ChillySwerve.teleopPeriodic();

    // read sensor values out to shuffleboard
    readSensors();
  }

  @Override
  public void disabledInit() {

    ChillySwerve.disabledInit();

    FreezyPath.stop();
    
    autoSM.stop();
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
