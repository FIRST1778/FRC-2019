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

  public static final double LEFT_FRONT_ANGLE_OFFSET = USING_COMPETITION_ROBOT ? 0.0 : 325.55;
  public static final double RIGHT_FRONT_ANGLE_OFFSET = USING_COMPETITION_ROBOT ? 0.0 : 29.88;
  public static final double LEFT_BACK_ANGLE_OFFSET = USING_COMPETITION_ROBOT ? 0.0 : 347.34;
  public static final double RIGHT_BACK_ANGLE_OFFSET = USING_COMPETITION_ROBOT ? 0.0 : 72.42;

  public static final double VEHICLE_WHEELBASE = USING_COMPETITION_ROBOT ? 23.25 : 23.5;
  public static final double VEHICLE_TRACKWIDTH = USING_COMPETITION_ROBOT ? 23.25 : 17.0;

  public static final double VEHICLE_DIAGONAL =
      Math.sqrt(
          (VEHICLE_WHEELBASE * VEHICLE_WHEELBASE) + (VEHICLE_TRACKWIDTH * VEHICLE_TRACKWIDTH));
  public static final double SWERVE_MAX_ACCELERATION = 66.0;
  public static final double SWERVE_MAX_VELOCITY = 120.0;
}
