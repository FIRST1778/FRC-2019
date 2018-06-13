package frc.team1778.lib.util.driver;

import com.kauailabs.navx.AHRSProtocol.AHRSUpdateBase;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.ITimestampedDataSubscriber;
import edu.wpi.first.wpilibj.SPI;

/** Driver for a NavX board. Basically a wrapper for the {@link AHRS} class */
public class NavX {
  protected class Callback implements ITimestampedDataSubscriber {
    @Override
    public void timestampedDataReceived(
        long system_timestamp, long sensor_timestamp, AHRSUpdateBase update, Object context) {
      synchronized (NavX.this) {
        // This handles the fact that the sensor is inverted from our coordinate
        // conventions.
        if (lastSensorTimestampMs != INVALID_TIMESTAMP
            && lastSensorTimestampMs < sensor_timestamp) {
          yawRateDegreesPerSecond =
              1000.0
                  * (-yawDegrees - update.yaw)
                  / (double) (sensor_timestamp - lastSensorTimestampMs);
        }
        lastSensorTimestampMs = sensor_timestamp;
        yawDegrees = -update.yaw;
      }
    }
  }

  protected AHRS AHRS;

  protected double angleAdjustment;
  protected double yawDegrees;
  protected double yawRateDegreesPerSecond;
  protected final long INVALID_TIMESTAMP = -1;
  protected long lastSensorTimestampMs;

  public NavX(SPI.Port spi_port_id) {
    AHRS = new AHRS(spi_port_id, (byte) 200);
    resetState();
    AHRS.registerCallback(new Callback(), null);
  }

  public synchronized void reset() {
    AHRS.reset();
    resetState();
  }

  public synchronized void zeroYaw() {
    AHRS.zeroYaw();
    resetState();
  }

  private void resetState() {
    lastSensorTimestampMs = INVALID_TIMESTAMP;
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
    return AHRS.getRawAccelX();
  }
}
