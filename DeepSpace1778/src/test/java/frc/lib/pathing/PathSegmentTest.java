package frc.lib.pathing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class PathSegmentTest {

  public static class RadialArcTest {
    private PathSegment radialArc = new PathSegment.RadialArc(2, 90, 0);
    private PathSegment flippedRadialArc = radialArc.getFlipped();

    @Test
    public void directionShouldStretchAlongLength() {
      assertThat(0.0, is(radialArc.getDirection(0.0)));
      assertThat(45.0, is(radialArc.getDirection(0.5)));
      assertThat(90.0, is(radialArc.getDirection(1.0)));

      assertThat(-0.0, is(flippedRadialArc.getDirection(0.0)));
      assertThat(-45.0, is(flippedRadialArc.getDirection(0.5)));
      assertThat(-90.0, is(flippedRadialArc.getDirection(1.0)));
    }

    @Test
    public void directionAtDistanceShouldMatchPercentage() {
      assertThat(0.0, is(radialArc.getDirectionAtDistance(0.0)));
      assertThat(45.0, is(radialArc.getDirectionAtDistance(1.0)));
      assertThat(90.0, is(radialArc.getDirectionAtDistance(2.0)));

      assertThat(-0.0, is(flippedRadialArc.getDirectionAtDistance(0.0)));
      assertThat(-45.0, is(flippedRadialArc.getDirectionAtDistance(1.0)));
      assertThat(-90.0, is(flippedRadialArc.getDirectionAtDistance(2.0)));
    }

    @Test
    public void lengthShouldEqualInput() {
      assertThat(2.0, is(radialArc.getLength()));

      assertThat(2.0, is(flippedRadialArc.getLength()));
    }
  }

  public static class ArcedTranslation {
    private PathSegment arcedTranslation = new PathSegment.ArcedTranslation(1, 1, 0);
    private PathSegment flippedArcedTranslation = arcedTranslation.getFlipped();

    @Test
    public void directionShouldStretchAlongLength() {
      assertThat(0.0, is(arcedTranslation.getDirection(0.0)));
      assertThat(0.0, is(arcedTranslation.getDirection(0.5)));
      assertThat(0.0, is(arcedTranslation.getDirection(1.0)));

      assertThat(0.0, is(flippedArcedTranslation.getDirection(0.0)));
      assertThat(0.0, is(flippedArcedTranslation.getDirection(0.5)));
      assertThat(0.0, is(flippedArcedTranslation.getDirection(1.0)));
    }

    @Test
    public void directionAtDistanceShouldMatchPercentage() {
      assertThat(0.0, is(arcedTranslation.getDirectionAtDistance(0.0)));
      assertThat(0.0, is(arcedTranslation.getDirectionAtDistance(1.0)));
      assertThat(90.0, is(arcedTranslation.getDirectionAtDistance(2.0)));

      assertThat(0.0, is(flippedArcedTranslation.getDirectionAtDistance(0.0)));
      assertThat(0.0, is(flippedArcedTranslation.getDirectionAtDistance(1.0)));
      assertThat(0.0, is(flippedArcedTranslation.getDirectionAtDistance(2.0)));
    }

    @Test
    public void lengthShouldEqualInput() {
      assertThat(2.0, is(arcedTranslation.getLength()));

      assertThat(2.0, is(flippedArcedTranslation.getLength()));
    }
  }

  public static class LineTest {
    private PathSegment line = new PathSegment.Line(2, 0);
    private PathSegment flippedLine = line.getFlipped();

    @Test
    public void directionShouldStretchAlongLength() {
      assertThat(0.0, is(line.getDirection(0.0)));
      assertThat(0.0, is(line.getDirection(0.5)));
      assertThat(0.0, is(line.getDirection(1.0)));

      assertThat(0.0, is(flippedLine.getDirection(0.0)));
      assertThat(0.0, is(flippedLine.getDirection(0.5)));
      assertThat(0.0, is(flippedLine.getDirection(1.0)));
    }

    @Test
    public void directionAtDistanceShouldMatchPercentage() {
      assertThat(0.0, is(line.getDirectionAtDistance(0.0)));
      assertThat(0.0, is(line.getDirectionAtDistance(1.0)));
      assertThat(0.0, is(line.getDirectionAtDistance(2.0)));

      assertThat(0.0, is(flippedLine.getDirectionAtDistance(0.0)));
      assertThat(0.0, is(flippedLine.getDirectionAtDistance(1.0)));
      assertThat(0.0, is(flippedLine.getDirectionAtDistance(2.0)));
    }

    @Test
    public void lengthShouldEqualInput() {
      assertThat(2.0, is(line.getLength()));

      assertThat(2.0, is(flippedLine.getLength()));
    }
  }
}
