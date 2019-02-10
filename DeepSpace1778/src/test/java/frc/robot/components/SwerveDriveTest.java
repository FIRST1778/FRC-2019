package frc.robot.components;

import static org.assertj.core.api.Assertions.assertThat;

import frc.lib.util.ModuleSignal;
import java.util.ArrayList;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

public class SwerveDriveTest {

  private SwerveDrive swerve = SwerveDrive.getInstance(false);

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
      assertThat(calculatedSignalsOneHalf.get(i).getDrivePower())
          .isEqualTo(wantedSignalsOneHalf.get(i).getDrivePower(), Offset.offset(0.001));
      assertThat(calculatedSignalsOneHalf.get(i).getAngle())
          .isEqualTo(wantedSignalsOneHalf.get(i).getAngle(), Offset.offset(0.001));
    }

    for (int i = 0; i < 4; i++) {
      assertThat(calculatedSignalsOne.get(i).getDrivePower())
          .isEqualTo(wantedSignals.get(i).getDrivePower(), Offset.offset(0.001));
      assertThat(calculatedSignalsOne.get(i).getAngle())
          .isEqualTo(wantedSignals.get(i).getAngle(), Offset.offset(0.001));
    }

    for (int i = 0; i < 4; i++) {
      assertThat(calculatedSignalsTwo.get(i).getDrivePower())
          .isEqualTo(wantedSignals.get(i).getDrivePower(), Offset.offset(0.001));
      assertThat(calculatedSignalsTwo.get(i).getAngle())
          .isEqualTo(wantedSignals.get(i).getAngle(), Offset.offset(0.001));
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
      assertThat(calculatedSignalsOneHalf.get(i).getDrivePower())
          .isEqualTo(wantedSignalsOneHalf.get(i).getDrivePower(), Offset.offset(0.001));
      assertThat(calculatedSignalsOneHalf.get(i).getAngle())
          .isEqualTo(wantedSignalsOneHalf.get(i).getAngle(), Offset.offset(0.001));
    }

    for (int i = 0; i < 4; i++) {
      assertThat(calculatedSignalsOne.get(i).getDrivePower())
          .isEqualTo(wantedSignals.get(i).getDrivePower(), Offset.offset(0.001));
      assertThat(calculatedSignalsOne.get(i).getAngle())
          .isEqualTo(wantedSignals.get(i).getAngle(), Offset.offset(0.001));
    }

    for (int i = 0; i < 4; i++) {
      assertThat(calculatedSignalsTwo.get(i).getDrivePower())
          .isEqualTo(wantedSignals.get(i).getDrivePower(), Offset.offset(0.001));
      assertThat(calculatedSignalsTwo.get(i).getAngle())
          .isEqualTo(wantedSignals.get(i).getAngle(), Offset.offset(0.001));
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
      assertThat(calculatedSignalsOneHalf.get(i).getDrivePower())
          .isEqualTo(wantedSignalsOneHalf.get(i).getDrivePower(), Offset.offset(0.001));
      assertThat(calculatedSignalsOneHalf.get(i).getAngle())
          .isEqualTo(wantedSignalsOneHalf.get(i).getAngle(), Offset.offset(0.001));
    }

    for (int i = 0; i < 4; i++) {
      assertThat(calculatedSignalsOne.get(i).getDrivePower())
          .isEqualTo(wantedSignals.get(i).getDrivePower(), Offset.offset(0.001));
      assertThat(calculatedSignalsOne.get(i).getAngle())
          .isEqualTo(wantedSignals.get(i).getAngle(), Offset.offset(0.001));
    }

    for (int i = 0; i < 4; i++) {
      assertThat(calculatedSignalsTwo.get(i).getDrivePower())
          .isEqualTo(wantedSignals.get(i).getDrivePower(), Offset.offset(0.001));
      assertThat(calculatedSignalsTwo.get(i).getAngle())
          .isEqualTo(wantedSignals.get(i).getAngle(), Offset.offset(0.001));
    }
  }
}
