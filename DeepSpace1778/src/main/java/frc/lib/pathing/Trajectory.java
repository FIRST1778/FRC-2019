package frc.lib.pathing;

public class Trajectory {
  private final Path path;
  private final double maxAcceleration;
  private final double maxVelocity;

  public Trajectory(Path path, double maxAcceleration, double maxVelocity) {
    this.path = path;
    this.maxAcceleration = maxAcceleration;
    this.maxVelocity = maxVelocity;
  }

  public boolean isTrapezoidal() {
    return ((maxVelocity * maxVelocity) / maxAcceleration) < path.getLength();
  }

  /**
   * Get the amount of time it takes to complete the path.
   *
   * @return The amount of time to complete the path.
   */
  public double getDuration() {
    if (isTrapezoidal()) {
      return (maxVelocity / maxAcceleration) + (path.getLength() / maxVelocity);
    } else {
      return Math.sqrt(path.getLength() / maxAcceleration);
    }
  }
}
