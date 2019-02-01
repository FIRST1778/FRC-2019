package frc.lib.pathing;

public abstract class PathSegment {
  public abstract double getDirection(double percentage);

  public double getDirectionAtDistance(double distance) {
    return getDirection(distance / getLength());
  }

  public abstract double getLength();

  protected abstract PathSegment getFlipped();

  public static final class RadialArc extends PathSegment {
    private final double length;
    private final double direction;

    public RadialArc(double length, double direction) {
      this.length = length;
      this.direction = direction;
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
    protected PathSegment getFlipped() {
      return new RadialArc(length, -direction);
    }
  }

  public static final class Line extends PathSegment {
    private final double length;

    public Line(double length) {
      this.length = length;
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
    protected PathSegment getFlipped() {
      return new Line(length);
    }
  }
}
