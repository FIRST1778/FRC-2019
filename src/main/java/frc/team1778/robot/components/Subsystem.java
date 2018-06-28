package frc.team1778.robot.components;

import frc.team1778.robot.common.communication.NetworkTableWrapper;

/**
 * This is the base class which all subsystems/components inherit. This requires that each subsystem
 * can print telemetry data to the subsystem's {@link NetworkTableWrapper} as well as reset any
 * sensors associated with the subsystem.
 *
 * @author FRC 1778 Chill Out
 */
public abstract class Subsystem {
  /** Print telemetry associated with this subsystem to the SmartDashboard. */
  public abstract void sendTelemetry();

  /** Reset all encoders associated with the subsystem. */
  public abstract void resetEncoders();

  /** Zero all sensors associated with the subsystem. */
  public abstract void zeroSensors();
}
