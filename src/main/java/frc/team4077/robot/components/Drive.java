package frc.team4077.robot.components;

/**
 * This is the robot's drivetrain. This class handles the four TalonSRX motor
 * controllers attached to the ganged left and right motors.
 * <p>
 * The drivetrain consists of four (4) TalonSRX motor controllers, four (4) CIM
 * motors, one (1) solenoid, two (2) shifting pistons, and one (1) NavX IMU.
 * 
 * @author FRC 4077 MASH, Hillel Coates
 * @see Subsystem.java
 */
public class Drive extends Subsystem {
    private static Drive mInstance = new Drive();
    
    /**
     * Returns a static instance of Drive, to be used instead of instantiating
     * new objects of Drive.
     */         
    public static Drive getInstance() {
        return mInstance;
    }

    private Drive() {

    }

    @Override
    public void sendTelemetry() {
    }

    @Override
    public void zeroAndReset() {

    }
}