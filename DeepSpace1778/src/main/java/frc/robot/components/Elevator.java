package frc.robot.components;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.lib.driver.TalonSrxFactory;
import frc.robot.Ports;

/**
 * Handles all control over the two (2) Talon SRXs, each connected to a 775Pro. THe master Talon SRX
 * is connected with a Grayhill 63R encoder and limit switches.
 *
 * @author FRC 1778 Chill Out
 */
public class Elevator {
  private static Elevator instance = new Elevator();

  private TalonSRX elevatorMaster;
  private TalonSRX elevatorSlave;

  private static TalonSrxFactory.Configuration masterConfiguration;

  public static Elevator getInstance() {
    return instance;
  }

  private Elevator() {
    masterConfiguration = new TalonSrxFactory.Configuration();
    masterConfiguration.feedbackDevice = FeedbackDevice.QuadEncoder;
    masterConfiguration.continuousCurrentLimit = 25;
    masterConfiguration.peakCurrentLimit = 30;
    masterConfiguration.peakCurrentLimitDuration = 100;
    masterConfiguration.enableCurrentLimit = true;
    masterConfiguration.invert = false;
    masterConfiguration.invertSensorPhase = false;

    elevatorMaster = TalonSrxFactory.createTalon(Ports.ELEVATOR_MASTER_ID, masterConfiguration);
    elevatorSlave = TalonSrxFactory.createSlaveTalon(Ports.ELEVATOR_SLAVE_ID, elevatorMaster);
  }
}
