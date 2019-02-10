package frc.lib.pathing;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PathSegmentTest {

  public static class RadialArcTest {
    private PathSegment path = new PathSegment.RadialArc(1, 90, 90);
    private PathSegment flippedPath = path.getFlipped();

    @Test
    @DisplayName("The path's direction should stretch evenly along the path")
    public void directionShouldStretchAlongLength() {
      assertThat(path.getDirection(0.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(path.getDirection(0.5)).isEqualTo(45.0, Offset.offset(0.001));
      assertThat(path.getDirection(1.0)).isEqualTo(90.0, Offset.offset(0.001));

      assertThat(flippedPath.getDirection(0.0)).isEqualTo(-0.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirection(0.5)).isEqualTo(-45.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirection(1.0)).isEqualTo(-90.0, Offset.offset(0.001));
    }

    @Test
    @DisplayName("The path's direction at any distance should stretch evenly along the path")
    public void directionAtDistanceShouldMatchPercentage() {
      assertThat(path.getDirectionAtDistance(0.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(path.getDirectionAtDistance(0.5)).isEqualTo(45.0, Offset.offset(0.001));
      assertThat(path.getDirectionAtDistance(1.0)).isEqualTo(90.0, Offset.offset(0.001));

      assertThat(flippedPath.getDirectionAtDistance(0.0)).isEqualTo(-0.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirectionAtDistance(0.5)).isEqualTo(-45.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirectionAtDistance(1.0)).isEqualTo(-90.0, Offset.offset(0.001));
    }

    @Test
    @DisplayName("The path's length should match what it is initialized with")
    public void lengthShouldEqualInput() {
      assertThat(path.getLength()).isEqualTo(1.0, Offset.offset(0.001));

      assertThat(flippedPath.getLength()).isEqualTo(1.0, Offset.offset(0.001));
    }
  }

  public static class ArcedTranslationTest {
    private PathSegment path = new PathSegment.ArcedTranslation(1, 1, 90);
    private PathSegment flippedPath = path.getFlipped();

    @Test
    @DisplayName("The path's direction should stretch evenly along the path")
    public void directionShouldStretchAlongLength() {
      assertThat(path.getDirection(0.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(path.getDirection(0.5)).isEqualTo(45.0, Offset.offset(0.001));
      assertThat(path.getDirection(1.0)).isEqualTo(90.0, Offset.offset(0.001));

      assertThat(flippedPath.getDirection(0.0)).isEqualTo(-0.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirection(0.5)).isEqualTo(-45.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirection(1.0)).isEqualTo(-90.0, Offset.offset(0.001));
    }

    @Test
    @DisplayName("The path's direction at any distance should stretch evenly along the path")
    public void directionAtDistanceShouldMatchPercentage() {
      assertThat(path.getDirectionAtDistance(0.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(path.getDirectionAtDistance(path.getLength() * 0.5))
          .isEqualTo(45.0, Offset.offset(0.001));
      assertThat(path.getDirectionAtDistance(path.getLength()))
          .isEqualTo(90.0, Offset.offset(0.001));

      assertThat(flippedPath.getDirectionAtDistance(0.0)).isEqualTo(-0.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirectionAtDistance(flippedPath.getLength() * 0.5))
          .isEqualTo(-45.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirectionAtDistance(flippedPath.getLength()))
          .isEqualTo(-90.0, Offset.offset(0.001));
    }
  }

  public static class LineTest {
    private PathSegment path = new PathSegment.Line(2, 90);
    private PathSegment flippedPath = path.getFlipped();

    @Test
    @DisplayName("The path's direction at any percentage should be zero")
    public void directionShouldStretchAlongLength() {
      assertThat(path.getDirection(0.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(path.getDirection(0.5)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(path.getDirection(1.0)).isEqualTo(0.0, Offset.offset(0.001));

      assertThat(flippedPath.getDirection(0.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirection(0.5)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirection(1.0)).isEqualTo(0.0, Offset.offset(0.001));
    }

    @Test
    @DisplayName("The path's direction at any distance should be zero")
    public void directionAtDistanceShouldMatchPercentage() {
      assertThat(path.getDirectionAtDistance(0.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(path.getDirectionAtDistance(1.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(path.getDirectionAtDistance(2.0)).isEqualTo(0.0, Offset.offset(0.001));

      assertThat(flippedPath.getDirectionAtDistance(0.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirectionAtDistance(1.0)).isEqualTo(0.0, Offset.offset(0.001));
      assertThat(flippedPath.getDirectionAtDistance(2.0)).isEqualTo(0.0, Offset.offset(0.001));
    }

    @Test
    @DisplayName("The path's length should match what it is initialized with")
    public void lengthShouldEqualInput() {
      assertThat(path.getLength()).isEqualTo(2.0, Offset.offset(0.001));

      assertThat(flippedPath.getLength()).isEqualTo(2.0, Offset.offset(0.001));
    }
  }
}
