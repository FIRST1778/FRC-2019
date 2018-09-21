
package frc.team1778.Systems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

public class NavXSensor {
	
	private static boolean initialized = false;
    
	public static void initialize() {
		
		if (initialized)
			return;
		
		System.out.println("NavXSensor initialize called...");
		
		try {
			ahrs = new AHRS(SPI.Port.kMXP);     
		} catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }

		reset();
		
		initialized = true;
	}
			
	// instance data and methods
	private static AHRS ahrs = null;
	
	private static double yawOffset = 0.0;
	private static double lastRawAngle = 0.0;
	
	public static class Angles {
		float roll = 0f;
		float pitch = 0f;
		float yaw = 0f;
	}
				
	public static void reset()
	{
		System.out.println("NavXSensor::reset called!");
				
		if (ahrs != null) 
		{
			ahrs.reset();
			
			// get the absolute angle after reset - Not sure why it is non-zero, but we need to record it to zero it out
			//yawOffset = ahrs.getAngle();	
			//System.out.println("yawOffset read = " + yawOffset);

			yawOffset = 0;	
		}
	}

	public static AHRS getAHRS() {		
		return ahrs;
	}
	
	public static boolean isConnected() {
		if (ahrs != null) {
			return ahrs.isConnected();
		}
		
		return false;
	}
	
	public static boolean isCalibrating() {
		if (ahrs != null) {
			return ahrs.isCalibrating();
		}
		
		return false;
	}
	
	public static Angles getAngles()
	{
		Angles angles = new Angles();
		
		if (ahrs != null) {
			angles.roll = ahrs.getRoll();	
			angles.pitch = ahrs.getPitch();	
			angles.yaw = ahrs.getYaw();	
		}			
		
		return angles;
	}
	
	// returns yaw angle (-180 deg to +180 deg)
	public static float getYaw() 
	{
		float yaw = 0f;
		
		if (ahrs != null) {
			yaw = ahrs.getYaw();	
		}		
		
		return yaw;
		
	}
	
	/**
	 * Returns the angle of the NavX.
	 * Turning past 360 degrees will go onto 361
	 * Turning before 0 degrees will go to -1
	 * 
	 * @return The angle, in degrees, of the NavX
	 */
	public static double getAngle() 
	{
		/*
		double yaw = 0f;
		
		if (ahrs != null) {
			yaw = ahrs.getAngle();	
			yaw -= yawOffset;  // needed to get to true angle
		}	
		return yaw;
		*/

		if (ahrs == null)
			return 0;

		double rawAngle = ahrs.getAngle();
		
		if (rawAngle > 340 && lastRawAngle < 20) {
			yawOffset -= 360.0;
		}
		else if (rawAngle < 20 && lastRawAngle > 340) {
			yawOffset += 360;
		}
		
		lastRawAngle = rawAngle;
		
		return rawAngle + yawOffset;
		
	}
	
}
