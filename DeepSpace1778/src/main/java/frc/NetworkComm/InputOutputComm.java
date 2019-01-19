package frc.NetworkComm;

// import edu.wpi.first.wpilibj.networktables.NetworkTable;  // deprecated in 2018

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class InputOutputComm {

  private static boolean initialized = false;

  public class LimelightData {
      public double targetOffsetAngle_Horizontal = 0;
      public double targetOffsetAngle_Vertical = 0;
      public double targetArea = 0;
      public double targetSkew = 0;
  };

  // instance data and methods
  private static NetworkTableInstance tableInstance;
  private static NetworkTable table;
  private static NetworkTable limelightTable;

  public static void initialize() {
    if (initialized) return;

    // get default local network table
    tableInstance = NetworkTableInstance.getDefault();
    table = tableInstance.getTable("InputOutput1778/DataTable");
    limelightTable = tableInstance.getTable("limelight");

    initialized = true;
  }

  public static enum LogTable {
    kMainLog,
    kRPICommLog,
    kDriveLog
  };


  /*
  public static LimelightData getLimelightData()
  {
    LimelightData lld;

    lld.targetOffsetAngle_Horizontal = limelightTable.getEntry("tx").getDouble(0);
    lld.targetOffsetAngle_Vertical = limelightTable.getEntry("ty").getDouble(0);
    lld.targetArea = limelightTable.getEntry("ta").getDouble(0);
    lld.targetSkew = limelightTable.getEntry("ts").getDouble(0);
    return lld;
  }
  */

  public static void putBoolean(LogTable log, String key, boolean value) {

    if (table != null) table.getEntry(key).setBoolean(value);
    else System.out.println("No network table to write to!!");
  }

  public static void putDouble(LogTable log, String key, double value) {
    if (table != null) table.getEntry(key).setDouble(value);
    else System.out.println("No network table to write to!!");
  }

  public static void putInt(LogTable log, String key, int value) {
    if (table != null) table.getEntry(key).setNumber(value);
    else System.out.println("No network table to write to!!");
  }

  public static void putString(LogTable log, String key, String outputStr) {
    if (table != null) table.getEntry(key).setString(outputStr);
    else System.out.println("No network table to write to!!");
  }

  public static void deleteKey(String key) {
    if (table != null) table.delete(key);
    else System.out.println("No network table to write to!!");
  }
}
