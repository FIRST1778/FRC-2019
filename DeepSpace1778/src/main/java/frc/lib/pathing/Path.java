package frc.lib.pathing;

/**
 * Stores a set of PathSegments, allowing for multiple motions to be strung together into a single
 * path. This is based upon FRC 2910 Jack in the Bot's 2018 code, with more capabilities
 *
 * @author FRC 1778 Chill Out
 */
public class Path extends PathSegment {

  private final double initialDirection;
  private final PathSegment[] segments;
  private final double length;
  private final double maxAcceleration;
  private final double maxVelocity;

  public Path(double initialDirection, PathSegment... segments) {
    this(initialDirection, 0, 0, segments);
  }

  public Path(
      double initialDirection,
      double maxAcceleration,
      double maxVelocity,
      PathSegment... segments) {
    this.initialDirection = initialDirection;
    this.segments = segments;
    this.maxAcceleration = maxAcceleration;
    this.maxVelocity = maxVelocity;

    double totalLength = 0;
    for (PathSegment segment : segments) {
      totalLength += segment.getLength();
    }
    this.length = totalLength;
  }

  private double getDistanceAtSegment(int segment) {
    double distance = 0;
    for (int i = 0; i < segment; i++) {
      distance += segments[i].getLength();
    }
    return distance;
  }

  private double getDirectionAtSegment(int segment) {
    double direction = initialDirection;
    for (int i = 0; i < segment; i++) {
      direction += segments[i].getDirection(1);
    }
    return direction;
  }

  private int getSegmentAtDistance(double distance) {
    if (distance < getDistanceAtSegment(1)) {
      return 0;
    }

    for (int i = 0; i < segments.length - 1; i++) {
      double lowerBound = getDistanceAtSegment(i);
      double upperBound = getDistanceAtSegment(i + 1);

      if (lowerBound < distance && distance < upperBound) {
        return i;
      }
    }

    return segments.length - 1;
  }

  public double getDuration() {
    if (((maxVelocity * maxVelocity) / maxAcceleration) < getLength()) {
      return (maxVelocity / maxAcceleration) + (getLength() / maxVelocity);
    } else {
      return Math.sqrt(getLength() / maxAcceleration);
    }
  }

  @Override
  public double getLength() {
    return length;
  }

  @Override
  public double getDirection(double percentage) {
    return getDirectionAtDistance(percentage * getLength());
  }

  @Override
  public double getDirectionAtDistance(double distance) {
    int currentSegment = getSegmentAtDistance(distance);

    double percentageOfSegment =
        (distance - getDistanceAtSegment(currentSegment)) / segments[currentSegment].getLength();

    return getDirectionAtSegment(currentSegment)
        + segments[currentSegment].getDirection(percentageOfSegment);
  }

  @Override
  public Path getFlipped() {
    PathSegment[] flippedSegments = new PathSegment[segments.length];
    for (int i = 0; i < segments.length; i++) {
      flippedSegments[i] = segments[i].getFlipped();
    }
    return new Path(-initialDirection, this.maxAcceleration, this.maxVelocity, flippedSegments);
  }
}
