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
  public static ShuffleboardTab debugTab = Shuffleboard.getTab("Debug");

  public static final boolean USING_COMPETITION_ROBOT = false;

  public static final double LEFT_FRONT_ANGLE_OFFSET = USING_COMPETITION_ROBOT ? -20.04 : -16.17;
  public static final double RIGHT_FRONT_ANGLE_OFFSET = USING_COMPETITION_ROBOT ? 99.14 : 30.59;
  public static final double LEFT_BACK_ANGLE_OFFSET = USING_COMPETITION_ROBOT ? 146.60 : 94.57;
  public static final double RIGHT_BACK_ANGLE_OFFSET = USING_COMPETITION_ROBOT ? -2.11 : 74.18;

  public static final double VEHICLE_WHEELBASE = USING_COMPETITION_ROBOT ? 23.25 : 23.5;
  public static final double VEHICLE_TRACKWIDTH = USING_COMPETITION_ROBOT ? 23.25 : 17.0;

  public static final double VEHICLE_DIAGONAL =
      Math.sqrt(
          (VEHICLE_WHEELBASE * VEHICLE_WHEELBASE) + (VEHICLE_TRACKWIDTH * VEHICLE_TRACKWIDTH));
  public static final double SWERVE_MAX_ACCELERATION = 85.0;
  public static final double SWERVE_MAX_VELOCITY = 132.0;

  public static final double EXTENDED_LIMIT = 36.0;
}
