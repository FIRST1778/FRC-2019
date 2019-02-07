package frc.lib.pathing;

public abstract class PathSegment {

  public abstract double getDirection(double percentage);

  public double getDirectionAtDistance(double distance) {
    return getDirection(distance / getLength());
  }

  public abstract double getAngle();

  public abstract double getLength();

  public abstract PathSegment getFlipped();

  public static final class RadialArc extends PathSegment {
    private final double length;
    private final double direction;
    private final double endAngle;

    public RadialArc(double length, double direction, double endAngle) {
      this.length = length;
      this.direction = direction;
      this.endAngle = endAngle;
    }

    @Override
    public double getDirection(double percentage) {
      return percentage * direction;
    }

    @Override
    public double getLength() {
      return length;
    }

    @Override
    public double getAngle() {
      return endAngle;
    }

    @Override
    public PathSegment getFlipped() {
      return new RadialArc(length, -direction, -endAngle);
    }
  }

  public static final class ArcedTranslation extends PathSegment {
    private final double length;
    private final double direction;
    private final double endAngle;

    public ArcedTranslation(double forwardTranslation, double strafeTranslation, double endAngle) {
      double radius =
          ((strafeTranslation * strafeTranslation) + (forwardTranslation * forwardTranslation))
              / (2 * strafeTranslation);
      direction = Math.atan2(forwardTranslation, strafeTranslation) * 180.0 / Math.PI * 2;
      length = (direction * Math.PI / 180.0) * radius;
      this.endAngle = endAngle;
    }

    @Override
    public double getDirection(double percentage) {
      return percentage * direction;
    }

    @Override
    public double getLength() {
      return length;
    }

    @Override
    public double getAngle() {
      return endAngle;
    }

    @Override
    public PathSegment getFlipped() {
      return new RadialArc(length, -direction, -endAngle);
    }
  }

  public static final class Line extends PathSegment {
    private final double length;
    private final double endAngle;

    public Line(double length, double endAngle) {
      this.length = length;
      this.endAngle = endAngle;
    }

    @Override
    public double getDirection(double percentage) {
      return 0;
    }

    @Override
    public double getLength() {
      return length;
    }

    @Override
    public double getAngle() {
      return endAngle;
    }

    @Override
    public PathSegment getFlipped() {
      return new Line(length, endAngle);
    }
  }
}
