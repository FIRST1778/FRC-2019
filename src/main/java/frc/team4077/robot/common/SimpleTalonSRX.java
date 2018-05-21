package frc.team4077.robot.common;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * A simple wrapper for the TalonSRX class. It simplifies some basic commands, but that is about it.
 *
 * <p>This is adapted from 254's code, but is updated to work with the newer TalonSRX API.
 *
 * @author FRC 4077 MASH, Hillel Coates
 */
public class SimpleTalonSRX extends TalonSRX {
  protected double lastSet = Double.NaN;
  protected ControlMode lastControlMode = null;

  public SimpleTalonSRX(int deviceNumber) {
    super(deviceNumber);
  }

  /**
   * Sets the control mode of the TalonSRX.
   *
   * @param controlMode The control mode to set the TalonSRX to. If set as Follower, the second
   *     parameter will be used.
   * @param masterId The ID of the master to follow. If controlMode is not Follower, it is not used.
   */
  public void setControlMode(ControlMode controlMode, double masterId) {
    super.set(controlMode, controlMode == ControlMode.Follower ? masterId : Double.NaN);
  }

  /**
   * Sets the output of the TalonSRX, which can mean different things depending on the mode it is
   * in.
   *
   * @param value The output value to send to the TalonSRX. This changes depending on what mode it
   *     is set to.
   *     <ul>
   *       <li>In PercentOutput, the output is between -1.0 and 1.0, with 0.0 as stopped.
   *       <li>In Current mode, output value is in amperes.
   *       <li>In Velocity mode, output value is in position change / 100ms.
   *       <li>In Position mode, output value is in encoder ticks or an analog value, depending on
   *           the sensor.
   *       <li>See In Follower mode, the output value is the integer device ID of the talon to
   *           duplicate.
   *     </ul>
   */
  public void set(double value) {
    if (value != lastSet || getControlMode() != lastControlMode) {
      lastSet = value;
      lastControlMode = getControlMode();
      super.set(getControlMode(), value);
    }
  }
}
