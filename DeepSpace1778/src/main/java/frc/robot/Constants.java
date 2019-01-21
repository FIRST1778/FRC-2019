package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Holds constant properties. For example, robot dimensions, encoder ratios, etc.
 *
 * @author FRC 1778 Chill Out
 */
public class Constants {
  public static ShuffleboardTab autoTab = Shuffleboard.getTab("Autonomous");
  public static ShuffleboardTab teleopTab = Shuffleboard.getTab("TeleOp");

  public static final double LEFT_FRONT_ANGLE_OFFSET = -36.0;
  public static final double RIGHT_FRONT_ANGLE_OFFSET = 32.0;
  public static final double LEFT_BACK_ANGLE_OFFSET = -12.0;
  public static final double RIGHT_BACK_ANGLE_OFFSET = 79.0;

  public static final double VEHICLE_WHEELBASE = 23.5;
  public static final double VEHICLE_TRACKWIDTH = 17.0;
  public static final double VEHICLE_DIAGONAL =
      Math.sqrt(
          (VEHICLE_WHEELBASE * VEHICLE_WHEELBASE) + (VEHICLE_TRACKWIDTH * VEHICLE_TRACKWIDTH));
}