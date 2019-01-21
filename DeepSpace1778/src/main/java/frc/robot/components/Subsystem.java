package frc.robot.components;

/**
 * The base class which all subsystems/components inherit. This requires that each subsystem can
 * print telemetry data to the subsystem's NetworkTableWrapper as well as reset any sensors
 * associated with the subsystem.
 *
 * @author FRC 1778 Chill Out
 */
public abstract class Subsystem {

  public abstract void sendTelemetry();

  public abstract void resetEncoders();

  public abstract void zeroSensors();

  public String getSubsystemName() {
    return this.getClass().getSimpleName();
  }
}
