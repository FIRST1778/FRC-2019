package frc.robot.components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import frc.lib.util.ModuleSignal;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class SwerveDriveTest {

  private SwerveDrive swerve = SwerveDrive.getinstance(false);

  @Test
  public void calculateModuleSignalsForwardsShouldMaxAtOne() {
    ArrayList<ModuleSignal> wantedSignalsOneHalf = new ArrayList<ModuleSignal>(4);
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 0.0));
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 0.0));
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 0.0));
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 0.0));

    ArrayList<ModuleSignal> wantedSignals = new ArrayList<ModuleSignal>(4);
    wantedSignals.add(new ModuleSignal(1.0, 0.0));
    wantedSignals.add(new ModuleSignal(1.0, 0.0));
    wantedSignals.add(new ModuleSignal(1.0, 0.0));
    wantedSignals.add(new ModuleSignal(1.0, 0.0));

    ArrayList<ModuleSignal> calculatedSignalsOneHalf = swerve.calculateModuleSignals(0.5, 0.0, 0.0);
    ArrayList<ModuleSignal> calculatedSignalsOne = swerve.calculateModuleSignals(1.0, 0.0, 0.0);
    ArrayList<ModuleSignal> calculatedSignalsTwo = swerve.calculateModuleSignals(2.0, 0.0, 0.0);

    for (int i = 0; i < 4; i++) {
      assertEquals(
          wantedSignalsOneHalf.get(i).getDrivePower(),
          calculatedSignalsOneHalf.get(i).getDrivePower(),
          0.001);
      assertEquals(
          wantedSignalsOneHalf.get(i).getAngle(),
          calculatedSignalsOneHalf.get(i).getAngle(),
          0.001);
    }

    for (int i = 0; i < 4; i++) {
      assertEquals(
          wantedSignals.get(i).getDrivePower(), calculatedSignalsOne.get(i).getDrivePower(), 0.001);
      assertEquals(wantedSignals.get(i).getAngle(), calculatedSignalsOne.get(i).getAngle(), 0.001);
    }

    for (int i = 0; i < 4; i++) {
      assertEquals(
          wantedSignals.get(i).getDrivePower(), calculatedSignalsTwo.get(i).getDrivePower(), 0.001);
      assertEquals(wantedSignals.get(i).getAngle(), calculatedSignalsTwo.get(i).getAngle(), 0.001);
    }
  }

  @Test
  public void calculateModuleSignalsStrafeShouldMaxAtOne() {
    ArrayList<ModuleSignal> wantedSignalsOneHalf = new ArrayList<ModuleSignal>(4);
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 90.0));
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 90.0));
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 90.0));
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 90.0));

    ArrayList<ModuleSignal> wantedSignals = new ArrayList<ModuleSignal>(4);
    wantedSignals.add(new ModuleSignal(1.0, 90.0));
    wantedSignals.add(new ModuleSignal(1.0, 90.0));
    wantedSignals.add(new ModuleSignal(1.0, 90.0));
    wantedSignals.add(new ModuleSignal(1.0, 90.0));

    ArrayList<ModuleSignal> calculatedSignalsOneHalf = swerve.calculateModuleSignals(0.0, 0.5, 0.0);
    ArrayList<ModuleSignal> calculatedSignalsOne = swerve.calculateModuleSignals(0.0, 1.0, 0.0);
    ArrayList<ModuleSignal> calculatedSignalsTwo = swerve.calculateModuleSignals(0.0, 2.0, 0.0);

    for (int i = 0; i < 4; i++) {
      assertEquals(
          wantedSignalsOneHalf.get(i).getDrivePower(),
          calculatedSignalsOneHalf.get(i).getDrivePower(),
          0.001);
      assertEquals(
          wantedSignalsOneHalf.get(i).getAngle(),
          calculatedSignalsOneHalf.get(i).getAngle(),
          0.001);
    }

    for (int i = 0; i < 4; i++) {
      assertEquals(
          wantedSignals.get(i).getDrivePower(), calculatedSignalsOne.get(i).getDrivePower(), 0.001);
      assertEquals(wantedSignals.get(i).getAngle(), calculatedSignalsOne.get(i).getAngle(), 0.001);
    }

    for (int i = 0; i < 4; i++) {
      assertEquals(
          wantedSignals.get(i).getDrivePower(), calculatedSignalsTwo.get(i).getDrivePower(), 0.001);
      assertEquals(wantedSignals.get(i).getAngle(), calculatedSignalsTwo.get(i).getAngle(), 0.001);
    }
  }

  @Test
  public void calculateModuleSignalsRotateShouldMaxAtOne() {
    ArrayList<ModuleSignal> wantedSignalsOneHalf = new ArrayList<ModuleSignal>(4);
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 125.882));
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, 54.117));
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, -125.882));
    wantedSignalsOneHalf.add(new ModuleSignal(0.5, -54.117));

    ArrayList<ModuleSignal> wantedSignals = new ArrayList<ModuleSignal>(4);
    wantedSignals.add(new ModuleSignal(1.0, 125.882));
    wantedSignals.add(new ModuleSignal(1.0, 54.117));
    wantedSignals.add(new ModuleSignal(1.0, -125.882));
    wantedSignals.add(new ModuleSignal(1.0, -54.117));

    ArrayList<ModuleSignal> calculatedSignalsOneHalf = swerve.calculateModuleSignals(0.0, 0.0, 0.5);
    ArrayList<ModuleSignal> calculatedSignalsOne = swerve.calculateModuleSignals(0.0, 0.0, 1.0);
    ArrayList<ModuleSignal> calculatedSignalsTwo = swerve.calculateModuleSignals(0.0, 0.0, 2.0);

    for (int i = 0; i < 4; i++) {
      assertEquals(
          wantedSignalsOneHalf.get(i).getDrivePower(),
          calculatedSignalsOneHalf.get(i).getDrivePower(),
          0.001);
      assertEquals(
          wantedSignalsOneHalf.get(i).getAngle(),
          calculatedSignalsOneHalf.get(i).getAngle(),
          0.001);
    }

    for (int i = 0; i < 4; i++) {
      assertEquals(
          wantedSignals.get(i).getDrivePower(), calculatedSignalsOne.get(i).getDrivePower(), 0.001);
      assertEquals(wantedSignals.get(i).getAngle(), calculatedSignalsOne.get(i).getAngle(), 0.001);
    }

    for (int i = 0; i < 4; i++) {
      assertEquals(
          wantedSignals.get(i).getDrivePower(), calculatedSignalsTwo.get(i).getDrivePower(), 0.001);
      assertEquals(wantedSignals.get(i).getAngle(), calculatedSignalsTwo.get(i).getAngle(), 0.001);
    }
  }
}
