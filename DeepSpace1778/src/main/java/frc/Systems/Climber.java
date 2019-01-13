package frc.Systems;

import frc.NetworkComm.InputOutputComm;
import frc.Utility.HardwareIDs;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Joystick;

public class Climber 
{
    private static boolean initialized = false;

    private static TalonSRX pistonMotor;
    private static Spark rollerMotor;

    private static Joystick gamepad, controlPanel;

    private static final boolean PISTON_INVERTED = false;
    private static final boolean ROLLER_INVERTED = false;
    private static final double JOYSTICK_DEAD_ZONE = 0.05;

    private static final double PISTON_FACTOR = 0.5;
    private static final double ROLLER_FACTOR = 0.5;
  
    public static void initialize() {
  
      if (initialized) return;
  
      // initialize things
      InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "initializing climber...");

      gamepad = new Joystick(HardwareIDs.CO_DRIVER_GAMEPAD_ID);
      controlPanel = new Joystick(HardwareIDs.CONTROL_PANEL_ID);

      // create and initialize piston motor (open loop with limits)
      pistonMotor = configureMotor(HardwareIDs.CLIMBER_PISTON_TALON_ID , PISTON_INVERTED);

      // create and initialize roller motor (open loop)
      rollerMotor = new Spark(HardwareIDs.CLIMBER_ROLLER_PWM_ID);
      rollerMotor.setInverted(ROLLER_INVERTED);
  
      initialized = true;
    }

    // open-loop/limit switch motor configuration
    private static TalonSRX configureMotor(int talonID, boolean revMotor)
    {
    	TalonSRX _talon;
    	_talon = new TalonSRX(talonID);
    	_talon.setInverted(revMotor);
    	    	 
		  // forward limit switch is for up motion
		  _talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		  // reverse limit switch is for down action
		  _talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);

      _talon.setNeutralMode(NeutralMode.Brake);
 
    	return _talon;
    }

    public static void stopMotors()
    {
      // stop climber motors
      rollerMotor.set(0);	
      pistonMotor.set(ControlMode.PercentOutput, 0);
    }
  
    public static void teleopInit() 
    {
    }

    public static void teleopPeriodic() 
    {
      if (controlPanel.getRawButton(HardwareIDs.CLIMBER_OUTRIGGER_DEPLOY))
      {
          // trigger solenoid to release outrigger
      }

      // climb strength input
      double climbStrength = gamepad.getRawAxis(HardwareIDs.CO_DRIVER_LEFT_Y_AXIS);
      climbStrength = (climbStrength < JOYSTICK_DEAD_ZONE) ? 0.0 : climbStrength;

      // send commands to motors
      pistonMotor.set(ControlMode.PercentOutput, climbStrength*PISTON_FACTOR);
      rollerMotor.set(climbStrength*ROLLER_FACTOR);
    }

    public static void disabledInit() 
    {
      stopMotors();
    }
    
}