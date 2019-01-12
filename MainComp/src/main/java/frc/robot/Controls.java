package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.lib.gamepads.FreezyController;
import frc.lib.gamepads.InterLinkElite;
import frc.lib.gamepads.LogitechDualAction;
import frc.lib.gamepads.LogitechF310;
import frc.lib.gamepads.XboxOne;
import frc.lib.gamepads.XboxOneElite;
import frc.lib.util.SimpleUtil;

/**
 * Use this class to map controls between different controllers.
 *
 * @author FRC 1778 Chill Out
 */
public class Controls {
  private static Controls instance = new Controls();

  public enum ControllerType {
    INTERLINK_ELITE_CONTROLLER {
      @Override
      public String toString() {
        return "Interlink Elite Controller";
      }
    },
    FREEZY_CONTROLLER {
      @Override
      public String toString() {
        return "Freezy Controller";
      }
    },
    LOGITECH_DUAL_ACTION {
      @Override
      public String toString() {
        return "Logitech Dual Action Gamepad";
      }
    },
    LOGITECH_F310 {
      @Override
      public String toString() {
        return "Logitech F310 Gamepad";
      }
    },
    XBOX_ONE {
      @Override
      public String toString() {
        return "Xbox one Gamepad";
      }
    },
    XBOX_ONE_ELITE {
      @Override
      public String toString() {
        return "Xbox One Elite Controller";
      }
    }
  }

  private static final ControllerType DRIVER_CONTROLLER_TYPE = ControllerType.FREEZY_CONTROLLER;
  private static final ControllerType OPERATOR_CONTROLLER_TYPE = ControllerType.LOGITECH_F310;

  private static final int PORT_DRIVE_CONTROLLER = 0;
  private static final int PORT_OPERATOR_CONTROLLER = 1;

  private Joystick driverController;
  private Joystick operatorController;

  private Controls() {
    driverController = new Joystick(PORT_DRIVE_CONTROLLER);
    operatorController = new Joystick(PORT_OPERATOR_CONTROLLER);
  }

  /**
   * Returns a static instance of Controls, to be used instead of instantiating new objects of
   * Controls.
   *
   * @return an instance of Controls to avoid multiple objects of the same hardware devices
   */
  public static Controls getInstance() {
    return instance;
  }

  /**
   * Returns the driver joystick/controller.
   *
   * @return the driver's joystick or controller
   */
  public Joystick getDriverController() {
    return driverController;
  }

  /**
   * Returns the operator joystick/controller.
   *
   * @return the operator's joystick or controller
   */
  public Joystick getOperatorController() {
    return operatorController;
  }

  // Driver Controls
  /**
   * Returns the driver's longitudinal translation stick.
   *
   * @return the driver's longitudinal translation stick
   */
  public double getTranslationY() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return SimpleUtil.handleDeadband(
            driverController.getRawAxis(InterLinkElite.Axis.LEFT_Y), 0.05);
      case INTERLINK_ELITE_CONTROLLER:
        return -driverController.getRawAxis(InterLinkElite.Axis.LEFT_Y);
      case LOGITECH_DUAL_ACTION:
        return -driverController.getRawAxis(LogitechDualAction.Axis.LEFT_Y);
      case LOGITECH_F310:
        return -driverController.getRawAxis(LogitechF310.Axis.LEFT_Y);
      case XBOX_ONE:
        return -driverController.getRawAxis(XboxOne.Axis.LEFT_Y);
      case XBOX_ONE_ELITE:
        return -driverController.getRawAxis(XboxOneElite.Axis.LEFT_Y);
      default:
        return 0;
    }
  }

  /**
   * Returns the driver's lateral translation stick.
   *
   * @return the driver's lateral translation stick
   */
  public double getTranslationX() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return SimpleUtil.handleDeadband(
            -driverController.getRawAxis(InterLinkElite.Axis.LEFT_X), 0.05);
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawAxis(InterLinkElite.Axis.LEFT_X);
      case LOGITECH_DUAL_ACTION:
        return driverController.getRawAxis(LogitechDualAction.Axis.LEFT_X);
      case LOGITECH_F310:
        return driverController.getRawAxis(LogitechF310.Axis.LEFT_X);
      case XBOX_ONE:
        return driverController.getRawAxis(XboxOne.Axis.LEFT_X);
      case XBOX_ONE_ELITE:
        return driverController.getRawAxis(XboxOneElite.Axis.LEFT_X);
      default:
        return 0;
    }
  }

  /**
   * Returns the driver's rotation stick y-axis.
   *
   * @return the driver's rotation stick y-axis
   */
  public double getRotation() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return SimpleUtil.handleDeadband(
            -driverController.getRawAxis(InterLinkElite.Axis.RIGHT_X), 0.05);
      case INTERLINK_ELITE_CONTROLLER:
        return -driverController.getRawAxis(InterLinkElite.Axis.RIGHT_X);
      case LOGITECH_DUAL_ACTION:
        return -driverController.getRawAxis(LogitechDualAction.Axis.RIGHT_X);
      case LOGITECH_F310:
        return -driverController.getRawAxis(LogitechF310.Axis.RIGHT_X);
      case XBOX_ONE:
        return -driverController.getRawAxis(XboxOne.Axis.RIGHT_X);
      case XBOX_ONE_ELITE:
        return -driverController.getRawAxis(XboxOneElite.Axis.RIGHT_X);
      default:
        return 0;
    }
  }

  /**
   * Returns the driver's quickturn switch state.
   *
   * @return the driver's quickturn switch state
   */
  public boolean getSlowMode() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return driverController.getRawButton(FreezyController.RIGHT_SHOULDER_SWITCH);
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawButton(InterLinkElite.RIGHT_SHOULDER_SWITCH);
      case LOGITECH_DUAL_ACTION:
        return driverController.getRawButton(LogitechDualAction.RIGHT_BUMPER);
      case LOGITECH_F310:
        return driverController.getRawButton(LogitechF310.RIGHT_BUMPER);
      case XBOX_ONE:
        return driverController.getRawButton(XboxOne.RIGHT_BUMPER);
      case XBOX_ONE_ELITE:
        return driverController.getRawButton(XboxOneElite.RIGHT_BUMPER);
      default:
        return false;
    }
  }

  /**
   * Returns the driver's toggle to field centric switch state.
   *
   * @return the driver's toggle to field centric switch state
   */
  public boolean getFieldCentricToggle() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return driverController.getRawButton(FreezyController.LEFT_SHOULDER_SWITCH);
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawButton(InterLinkElite.LEFT_SHOULDER_DOWN_SWITCH);
      case LOGITECH_DUAL_ACTION:
        return driverController.getRawButton(LogitechDualAction.LEFT_TRIGGER);
      case LOGITECH_F310:
        return driverController.getRawAxis(LogitechF310.Axis.LEFT_TRIGGER) >= 0.5;
      case XBOX_ONE:
        return driverController.getRawAxis(XboxOne.Axis.LEFT_TRIGGER) >= 0.5;
      case XBOX_ONE_ELITE:
        return driverController.getRawAxis(XboxOneElite.Axis.LEFT_TRIGGER) >= 0.5;
      default:
        return false;
    }
  }

  // Operator Controls
}
