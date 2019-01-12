package frc.Systems;

import frc.NetworkComm.InputOutputComm;
import frc.Utility.HardwareIDs;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Joystick;

public class Climber 
{
    private static boolean initialized = false;

    private static Spark pistonMotor;
    private static Spark rollerMotor;

    private static Joystick gamepad;

    private static final boolean PISTON_INVERTED = false;
    private static final boolean ROLLER_INVERTED = false;
  
    public static void initialize() {
  
      if (initialized) return;
  
      // initialize things
      InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "initializing climber...");

      gamepad = new Joystick(HardwareIDs.CO_DRIVER_GAMEPAD_ID);

      // create and initialize piston and roller motors (open-loop)
      pistonMotor = new Spark(HardwareIDs.CLIMBER_PISTON_PWM_ID);
      pistonMotor.setInverted(PISTON_INVERTED);
      rollerMotor = new Spark(HardwareIDs.CLIMBER_ROLLER_PWM_ID);
      rollerMotor.setInverted(ROLLER_INVERTED);
  
      initialized = true;
    }

    public static void teleopInit() {

    }

    public static void teleopPeriodic() {
      
    }

    public static void disabledInit() {
    }
    
}