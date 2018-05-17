package frc.team4077.robot.common;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * A simple wrapper for the TalonSRX class. It simplifies some basic commands, but that is about it.
 *
 * <p>This is adapted from {@see <a
 * href="https://github.com/Team254/FRC-2017-Public/blob/master/src/com/team254/lib/util/drivers/LazyCANTalon.java"">254's
 * Code</a>}, but is updated to work with the newer TalonSRX API.
 *
 * @author FRC 4077 MASH, Hillel Coates
 */
public class SimpleTalonSRX extends TalonSRX {
  protected double lastSet = Double.NaN;
  protected ControlMode lastControlMode = null;

  public SimpleTalonSRX(int deviceNumber) {
    super(deviceNumber);
  }

  public void setControlMode(ControlMode controlMode) {
    if (controlMode != ControlMode.Follower) {
      super.set(controlMode, Double.NaN);
    }
  }

  public void setControlMode(ControlMode controlMode, int masterId) {
    if (controlMode == ControlMode.Follower) {
      super.set(controlMode, masterId);
    }
  }

  public void set(double value) {
    if (value != lastSet || getControlMode() != lastControlMode) {
      lastSet = value;
      lastControlMode = getControlMode();
      super.set(getControlMode(), value);
    }
  }
}
