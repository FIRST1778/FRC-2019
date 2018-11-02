package frc.team1778.lib.driver;

import com.kauailabs.navx.AHRSProtocol.AHRSUpdateBase;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.ITimestampedDataSubscriber;
import edu.wpi.first.wpilibj.SPI;

/** Driver for a NavX board. Basically a wrapper for the AHRS class */
public class NavX {
  protected class Callback implements ITimestampedDataSubscriber {
    @Override
    public void timestampedDataReceived(
        long systemTimestamp, long sensorTimestamp, AHRSUpdateBase update, Object context) {
      synchronized (NavX.this) {
        // This handles the fact that the sensor is inverted from our coordinate
        // conventions.
        if (lastSensorTimestampMs != invalidTimestamp && lastSensorTimestampMs < sensorTimestamp) {
          yawRateDegreesPerSecond =
              1000.0
                  * (-yawDegrees - update.yaw)
                  / (double) (sensorTimestamp - lastSensorTimestampMs);
        }
        lastSensorTimestampMs = sensorTimestamp;
        yawDegrees = -update.yaw;
      }
    }
  }

  protected AHRS ahrs;

  protected double angleAdjustment;
  protected double yawDegrees;
  protected double yawRateDegreesPerSecond;
  protected final long invalidTimestamp = -1;
  protected long lastSensorTimestampMs;

  /**
   * Yeet.
   *
   * @param spiPortId the port
   */
  public NavX(SPI.Port spiPortId) {
    ahrs = new AHRS(spiPortId, (byte) 200);
    resetState();
    ahrs.registerCallback(new Callback(), null);
  }

  public synchronized void reset() {
    ahrs.reset();
    resetState();
  }

  public synchronized void zeroYaw() {
    ahrs.zeroYaw();
    resetState();
  }

  private void resetState() {
    lastSensorTimestampMs = invalidTimestamp;
    yawDegrees = 0.0;
    yawRateDegreesPerSecond = 0.0;
  }

  public synchronized void setAngleAdjustment(double adjustment) {
    angleAdjustment = adjustment;
  }

  protected synchronized double getRawYawDegrees() {
    return yawDegrees;
  }

  public double getYaw() {
    return angleAdjustment + getRawYawDegrees();
  }

  public double getYawRateDegreesPerSec() {
    return yawRateDegreesPerSecond;
  }

  public double getYawRateRadiansPerSec() {
    return 180.0 / Math.PI * getYawRateDegreesPerSec();
  }

  public double getRawAccelX() {
    return ahrs.getRawAccelX();
  }
}
