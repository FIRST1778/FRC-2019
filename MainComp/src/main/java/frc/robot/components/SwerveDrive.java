package frc.robot.components;

import frc.lib.driver.NavX;
import frc.lib.util.ModuleSignal;
import frc.robot.Constants;
import frc.robot.Ports;
import java.util.ArrayList;
import java.util.List;

public class SwerveDrive extends Subsystem {
  private static SwerveDrive instance = new SwerveDrive();

  private SwerveModule leftFront;
  private SwerveModule rightFront;
  private SwerveModule leftBack;
  private SwerveModule rightBack;

  private NavX navX;

  /**
   * Returns a static instance of SwerveDrive, to be used instead of instantiating new objects of
   * SwerveDrive.
   *
   * @return an instance of SwerveDrive to avoid multiple objects of the same hardware devices
   */
  public static SwerveDrive getinstance() {
    return instance;
  }

  private SwerveDrive() {
    leftFront =
        new SwerveModule(
            Ports.LEFT_FRONT_ID, Ports.LEFT_FRONT_TURN_ID, Constants.LEFT_FRONT_ANGLE_OFFSET);
    rightFront =
        new SwerveModule(
            Ports.RIGHT_FRONT_ID, Ports.LEFT_FRONT_TURN_ID, Constants.LEFT_FRONT_ANGLE_OFFSET);
    leftBack =
        new SwerveModule(
            Ports.LEFT_BACK_ID, Ports.LEFT_BACK_TURN_ID, Constants.LEFT_BACK_ANGLE_OFFSET);
    rightBack =
        new SwerveModule(
            Ports.RIGHT_BACK_ID, Ports.LEFT_BACK_TURN_ID, Constants.LEFT_BACK_ANGLE_OFFSET);

    navX = new NavX(Ports.NAVX_SPI);
  }

  @Override
  public void sendTelemetry() {
    Constants.teleopTab
        .add("Left Front Angle", leftFront.getRawAbsAngle())
        .withWidget("Text View")
        .withPosition(0, 0)
        .withSize(1, 1);
    Constants.teleopTab
        .add("Left Front ", leftFront.getDrivePower())
        .withWidget("Text View")
        .withPosition(0, 1)
        .withSize(1, 1);

    Constants.teleopTab
        .add("Right Front Angle", rightFront.getRawAbsAngle())
        .withWidget("Text View")
        .withPosition(1, 0)
        .withSize(1, 1);
    Constants.teleopTab
        .add("Right Front ", rightFront.getDrivePower())
        .withWidget("Text View")
        .withPosition(1, 1)
        .withSize(1, 1);

    Constants.teleopTab
        .add("Left Back Angle", leftBack.getRawAbsAngle())
        .withWidget("Text View")
        .withPosition(2, 0)
        .withSize(1, 1);
    Constants.teleopTab
        .add("Left Back ", leftBack.getDrivePower())
        .withWidget("Text View")
        .withPosition(2, 1)
        .withSize(1, 1);

    Constants.teleopTab
        .add("Right Back Angle", rightBack.getRawAbsAngle())
        .withWidget("Text View")
        .withPosition(3, 0)
        .withSize(1, 1);
    Constants.teleopTab
        .add("Right Back ", rightBack.getDrivePower())
        .withWidget("Text View")
        .withPosition(3, 1)
        .withSize(1, 1);
  }

  @Override
  public void resetEncoders() {
    leftFront.resetDriveEncoder();
    rightFront.resetDriveEncoder();
    leftBack.resetDriveEncoder();
    rightBack.resetDriveEncoder();
  }

  @Override
  public void zeroSensors() {
    navX.zeroYaw();
  }

  /**
   * Returns the drivebase's NavX IMU. Use this instead of reinstantiating the NavX, which will
   * result in no response from the sensor.
   *
   * @return the drivebase's NavX IMU
   */
  public NavX getNavX() {
    return navX;
  }

  /**
   * Calculates the angle and speed signals for each of the swerve modules, stored as an ArrayList.
   *
   * @see <a href="Ether's whitepaper (on
   *     ChiefDelphi)">https://www.chiefdelphi.com/t/paper-4-wheel-independent-drive-independent-steering-swerve/107383</a>
   * @return an ArrayList that contains 4 ModuleSignals, in the order of left front, right front,
   *     left back, and right back
   */
  public ArrayList<ModuleSignal> calculateModuleSignals(
      double forward, double strafe, double rotation) {
    ArrayList<ModuleSignal> signals = new ArrayList<ModuleSignal>(4);

    double a = strafe + (rotation * (Constants.VEHICLE_WHEELBASE / Constants.VEHICLE_DIAGONAL));
    double b = strafe - (rotation * (Constants.VEHICLE_WHEELBASE / Constants.VEHICLE_DIAGONAL));
    double c = forward + (rotation * (Constants.VEHICLE_TRACKWIDTH / Constants.VEHICLE_DIAGONAL));
    double d = forward - (rotation * (Constants.VEHICLE_TRACKWIDTH / Constants.VEHICLE_DIAGONAL));

    double leftFrontPower = Math.sqrt((a * a) + (d * d));
    double rightFrontPower = Math.sqrt((a * a) + (c * c));
    double leftBackPower = Math.sqrt((b * b) + (d * d));
    double rightBackPower = Math.sqrt((b * b) + (c * c));

    double leftFrontAngle = Math.atan2(a, d) * 180 / Math.PI;
    double rightFrontAngle = Math.atan2(a, c) * 180 / Math.PI;
    double leftBackAngle = Math.atan2(b, d) * 180 / Math.PI;
    double rightBackAngle = Math.atan2(b, c) * 180 / Math.PI;

    double max =
        Math.max(
            leftFrontPower, Math.max(rightFrontPower, Math.max(leftBackPower, rightBackPower)));

    if (max > 1) {
      leftFrontPower /= max;
      rightFrontPower /= max;
      leftBackPower /= max;
      rightBackPower /= max;
    }

    signals.add(new ModuleSignal(leftFrontPower, leftFrontAngle));
    signals.add(new ModuleSignal(rightFrontPower, rightFrontAngle));
    signals.add(new ModuleSignal(leftBackPower, leftBackAngle));
    signals.add(new ModuleSignal(rightBackPower, rightBackAngle));

    return signals;
  }

  public void setSignals(List<ModuleSignal> signals) {
    leftFront.setDrivePower(signals.get(0).getDrivePower());
    rightFront.setDrivePower(signals.get(1).getDrivePower());
    leftBack.setDrivePower(signals.get(2).getDrivePower());
    rightBack.setDrivePower(signals.get(3).getDrivePower());

    leftFront.setTargetAngle(signals.get(0).getAngle());
    rightFront.setTargetAngle(signals.get(1).getAngle());
    leftBack.setTargetAngle(signals.get(2).getAngle());
    rightBack.setTargetAngle(signals.get(3).getAngle());
  }

  public void stop() {
    setAllDrivePowers(0, 0, 0, 0);
  }

  private void setAllDrivePowers(
      double leftFrontPower, double rightFrontPower, double leftBackPower, double rightBackPower) {
    leftFront.setDrivePower(leftFrontPower);
    rightFront.setDrivePower(rightFrontPower);
    leftBack.setDrivePower(leftBackPower);
    rightBack.setDrivePower(rightBackPower);
  }
}
