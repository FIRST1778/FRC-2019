package frc.robot.components;

/**
 * This is the base class which all subsystems/components inherit. This requires that each subsystem
 * can print telemetry data to the subsystem's NetworkTableWrapper as well as reset any sensors
 * associated with the subsystem.
 *
 * @author FRC 1778 Chill Out
 */
public abstract class Subsystem {
  /** Print telemetry associated with this subsystem to the NetworkTables. */
  public abstract void sendTelemetry();

  /** Reset all encoders associated with the subsystem. */
  public abstract void resetEncoders();

  /** Zero all sensors associated with the subsystem. */
  public abstract void zeroSensors();

  /** Returns the name of the subsystem. */
  public String getSubsystemName() {
    return this.getClass().getSimpleName();
  }
}
