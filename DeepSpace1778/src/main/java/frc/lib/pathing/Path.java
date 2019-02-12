package frc.lib.pathing;

import frc.lib.util.SimpleUtil;

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
  private final double endAngle;
  private final double maxAcceleration;
  private final double maxVelocity;

  public Path(double initialDirection, double startAngle, PathSegment... segments) {
    this(initialDirection, 0, 0, startAngle, segments);
  }

  public Path(
      double initialDirection,
      double maxAcceleration,
      double maxVelocity,
      double startAngle,
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
    this.endAngle = segments[segments.length - 1].getEndAngle();
    this.startAngle = startAngle;

    segments[0].startAngle = startAngle;
    for (int i = 1; i < segments.length; i++) {
      segments[i].startAngle = segments[i - 1].getEndAngle();
    }
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

      if (lowerBound < distance && distance <= upperBound) {
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
  public double getEndAngle() {
    return endAngle;
  }

  // @Override
  public double getAngle(double percentage) {
    return getAngleAtDistance(percentage * getLength());
  }

  // @Override
  public double getAngleAtDistance(double distance) {
    int currentSegment = getSegmentAtDistance(distance);

    double deltaAngle =
        segments[currentSegment].getEndAngle() - segments[currentSegment].startAngle;
    deltaAngle = Math.abs(deltaAngle) > 180 ? deltaAngle - 360 : deltaAngle;

    return SimpleUtil.flooredMod(
        (deltaAngle
                * (distance - getDistanceAtSegment(currentSegment))
                / segments[currentSegment].getLength())
            + segments[currentSegment].startAngle,
        360.0);
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
    return new Path(
        -initialDirection,
        this.maxAcceleration,
        this.maxVelocity,
        360 - this.startAngle,
        flippedSegments);
  }
}
