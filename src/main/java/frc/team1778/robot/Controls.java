package frc.team1778.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.team1778.lib.gamepads.InterLinkElite;
import frc.team1778.lib.gamepads.LogitechDualAction;
import frc.team1778.lib.gamepads.LogitechF310;
import frc.team1778.lib.gamepads.XboxOne;
import frc.team1778.lib.gamepads.XboxOneElite;

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
  };

  private static final ControllerType DRIVER_CONTROLLER_TYPE =
      ControllerType.INTERLINK_ELITE_CONTROLLER;
  private static final ControllerType OPERATOR_CONTROLLER_TYPE = ControllerType.LOGITECH_F310;

  private static final int PORT_DRIVER_CONTROLLER = 1;
  private static final int PORT_OPERATOR_CONTROLLER = 2;

  private Joystick driverController;
  private Joystick operatorController;

  private Controls() {
    driverController = new Joystick(PORT_DRIVER_CONTROLLER);
    operatorController = new Joystick(PORT_OPERATOR_CONTROLLER);
  }

  /**
   * Returns a static instance of Controls, to be used instead of instantiating new objects of Controls.
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
   * @return the operator's joystick or controller.
   */
  public Joystick getOperatorController() {
    return operatorController;
  }

  // Driver Controls
  /**
   * Returns the driver's throttle stick.
   *
   * @return the driver's throttle stick
   */
  public double getThrottle() {
    switch (DRIVER_CONTROLLER_TYPE) {
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
   * Returns the driver's turn stick x-axis.
   *
   * @return the driver's turn stick x-axis
   */
  public double getWheelX() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawAxis(InterLinkElite.Axis.RIGHT_X);
      case LOGITECH_DUAL_ACTION:
        return driverController.getRawAxis(LogitechDualAction.Axis.RIGHT_X);
      case LOGITECH_F310:
        return driverController.getRawAxis(LogitechF310.Axis.RIGHT_X);
      case XBOX_ONE:
        return driverController.getRawAxis(XboxOne.Axis.RIGHT_X);
      case XBOX_ONE_ELITE:
        return driverController.getRawAxis(XboxOneElite.Axis.RIGHT_X);
      default:
        return 0;
    }
  }

  /**
   * Returns the driver's turn stick y-axis.
   *
   * @return the driver's turn stick y-axis.
   */
  public double getWheelY() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case INTERLINK_ELITE_CONTROLLER:
        return -driverController.getRawAxis(InterLinkElite.Axis.RIGHT_Y);
      case LOGITECH_DUAL_ACTION:
        return -driverController.getRawAxis(LogitechDualAction.Axis.RIGHT_Y);
      case LOGITECH_F310:
        return -driverController.getRawAxis(LogitechF310.Axis.RIGHT_Y);
      case XBOX_ONE:
        return -driverController.getRawAxis(XboxOne.Axis.RIGHT_Y);
      case XBOX_ONE_ELITE:
        return -driverController.getRawAxis(XboxOneElite.Axis.RIGHT_Y);
      default:
        return 0;
    }
  }

  /**
   * Returns the driver's quickturn switch state.
   *
   * @return the driver's quickturn switch state
   */
  public boolean getQuickTurn() {
    switch (DRIVER_CONTROLLER_TYPE) {
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
   * Returns the driver's gear shift down switch state.
   *
   * @return the driver's gear shift down switch state
   */
  public boolean getLowGearShift() {
    switch (DRIVER_CONTROLLER_TYPE) {
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

  /**
   * Returns the driver's gear shift up switch state.
   *
   * @return the driver's gear shift up switch state
   */
  public boolean getHighGearShift() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawButton(InterLinkElite.LEFT_SHOULDER_UP_SWITCH);
      case LOGITECH_DUAL_ACTION:
        return driverController.getRawButton(LogitechDualAction.RIGHT_TRIGGER);
      case LOGITECH_F310:
        return driverController.getRawAxis(LogitechF310.Axis.RIGHT_TRIGGER) >= 0.5;
      case XBOX_ONE:
        return driverController.getRawAxis(XboxOne.Axis.RIGHT_TRIGGER) >= 0.5;
      case XBOX_ONE_ELITE:
        return driverController.getRawAxis(XboxOneElite.Axis.RIGHT_TRIGGER) >= 0.5;
      default:
        return false;
    }
  }

  // Operator Controls
}
