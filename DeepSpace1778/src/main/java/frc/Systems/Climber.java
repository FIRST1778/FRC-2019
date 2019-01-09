package frc.Systems;

import frc.NetworkComm.InputOutputComm;

public class Climber 
{
    private static boolean initialized = false;

    public static void initialize() {
  
      if (initialized) return;
  
      // initialize things
      InputOutputComm.putString(InputOutputComm.LogTable.kMainLog, "MainLog", "initializing climber...");

      initialized = true;
    }

    public static void teleopInit() {

    }

    public static void teleopPeriodic() {
      
    }
  
}