package frc.team4077.robot.components;

public abstract class Subsystem {
    /**
     * Print telemetry associated with this subsystem to the SmartDashboard.
     */
    public abstract void outputToSmartDashboard();

    /**
     * Reset and zero all sensors associated with the subsystem.
     */
    public abstract void zeroAndReset();
}