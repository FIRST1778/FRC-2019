package frc.team4077.robot.components;

/**
 * This is the base class which all subsystems/components inherit. This requires
 * that each subsystem can print telemetry data to the SmartDashboard as well as
 * reset any sensors associated with the subsystem.
 * 
 * @author FRC 4077 MASH, Hillel Coates
 */
public abstract class Subsystem {
    /**
     * Print telemetry associated with this subsystem to the SmartDashboard.
     */
    public abstract void sendTelemetry();

    /**
     * Reset and zero all sensors associated with the subsystem.
     */
    public abstract void zeroAndReset();
}