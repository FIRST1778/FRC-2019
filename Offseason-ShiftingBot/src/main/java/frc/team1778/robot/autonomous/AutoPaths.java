package frc.team1778.robot.autonomous;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 * Contains different paths for autonomous modes.
 *
 * @author FRC 1778 Chill Out
 */
public class AutoPaths {
  public static Waypoint[] testPath =
      new Waypoint[] {
        new Waypoint(0, 0, Pathfinder.d2r(0.0)), new Waypoint(36.0, 36.0, Pathfinder.d2r(90.0))
      };
}
