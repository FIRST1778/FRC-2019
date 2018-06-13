package frc.team1778.lib.util.driver;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * A simple wrapper for the TalonSRX class. It simplifies some basic commands, but that is about it.
 *
 * <p>This is adapted from 254's code, but is updated to work with the newer TalonSRX API.
 *
 * @author FRC 1778 Chill Out
 */
public class SimpleTalonSRX extends TalonSRX {
  private double lastSet = Double.NaN;

  public SimpleTalonSRX(int deviceNumber) {
    super(deviceNumber);
  }

  @Override
  public void set(ControlMode controlMode, double value) {
    if (value != lastSet) {
      lastSet = value;
      super.set(controlMode, value);
    }
  }
}
