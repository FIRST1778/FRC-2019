package frc.lib.pathing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PathSegmentTest {

  public static class RadialArcTest {
    private PathSegment path = new PathSegment.RadialArc(1, 90, 0);
    private PathSegment flippedPath = path.getFlipped();

    @Test
    @DisplayName("The path's direction should stretch evenly along the path")
    public void directionShouldStretchAlongLength() {
      assertThat(path.getDirection(0.0), is(0.0));
      assertThat(path.getDirection(0.5), is(45.0));
      assertThat(path.getDirection(1.0), is(90.0));

      assertThat(flippedPath.getDirection(0.0), is(-0.0));
      assertThat(flippedPath.getDirection(0.5), is(-45.0));
      assertThat(flippedPath.getDirection(1.0), is(-90.0));
    }

    @Test
    @DisplayName("The path's direction at any distance should stretch evenly along the path")
    public void directionAtDistanceShouldMatchPercentage() {
      assertThat(path.getDirectionAtDistance(0.0), is(0.0));
      assertThat(path.getDirectionAtDistance(0.5), is(45.0));
      assertThat(path.getDirectionAtDistance(1.0), is(90.0));

      assertThat(flippedPath.getDirectionAtDistance(0.0), is(-0.0));
      assertThat(flippedPath.getDirectionAtDistance(0.5), is(-45.0));
      assertThat(flippedPath.getDirectionAtDistance(1.0), is(-90.0));
    }

    @Test
    @DisplayName("The path's length should match what it is initialized with")
    public void lengthShouldEqualInput() {
      assertThat(path.getLength(), is(1.0));

      assertThat(flippedPath.getLength(), is(1.0));
    }
  }

  public static class ArcedTranslationTest {
    private PathSegment path = new PathSegment.ArcedTranslation(1, 1, 0);
    private PathSegment flippedPath = path.getFlipped();

    @Test
    @DisplayName("The path's direction should stretch evenly along the path")
    public void directionShouldStretchAlongLength() {
      assertThat(path.getDirection(0.0), is(0.0));
      assertThat(path.getDirection(0.5), is(45.0));
      assertThat(path.getDirection(1.0), is(90.0));

      assertThat(flippedPath.getDirection(0.0), is(-0.0));
      assertThat(flippedPath.getDirection(0.5), is(-45.0));
      assertThat(flippedPath.getDirection(1.0), is(-90.0));
    }

    @Test
    @DisplayName("The path's direction at any distance should stretch evenly along the path")
    public void directionAtDistanceShouldMatchPercentage() {
      assertThat(path.getDirectionAtDistance(0.0), is(0.0));
      assertThat(path.getDirectionAtDistance(path.getLength() * 0.5), is(45.0));
      assertThat(path.getDirectionAtDistance(path.getLength()), is(90.0));

      assertThat(flippedPath.getDirectionAtDistance(0.0), is(-0.0));
      assertThat(flippedPath.getDirectionAtDistance(flippedPath.getLength() * 0.5), is(-45.0));
      assertThat(flippedPath.getDirectionAtDistance(flippedPath.getLength()), is(-90.0));
    }
  }

  public static class LineTest {
    private PathSegment path = new PathSegment.Line(2, 0);
    private PathSegment flippedPath = path.getFlipped();

    @Test
    @DisplayName("The path's direction at any percentage should be zero")
    public void directionShouldStretchAlongLength() {
      assertThat(path.getDirection(0.0), is(0.0));
      assertThat(path.getDirection(0.5), is(0.0));
      assertThat(path.getDirection(1.0), is(0.0));

      assertThat(flippedPath.getDirection(0.0), is(0.0));
      assertThat(flippedPath.getDirection(0.5), is(0.0));
      assertThat(flippedPath.getDirection(1.0), is(0.0));
    }

    @Test
    @DisplayName("The path's direction at any distance should be zero")
    public void directionAtDistanceShouldMatchPercentage() {
      assertThat(path.getDirectionAtDistance(0.0), is(0.0));
      assertThat(path.getDirectionAtDistance(1.0), is(0.0));
      assertThat(path.getDirectionAtDistance(2.0), is(0.0));

      assertThat(flippedPath.getDirectionAtDistance(0.0), is(0.0));
      assertThat(flippedPath.getDirectionAtDistance(1.0), is(0.0));
      assertThat(flippedPath.getDirectionAtDistance(2.0), is(0.0));
    }

    @Test
    @DisplayName("The path's length should match what it is initialized with")
    public void lengthShouldEqualInput() {
      assertThat(path.getLength(), is(2.0));

      assertThat(flippedPath.getLength(), is(2.0));
    }
  }
}
