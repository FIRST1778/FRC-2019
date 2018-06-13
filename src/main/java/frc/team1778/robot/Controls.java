package frc.team1778.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.team1778.lib.util.gamepads.InterLinkElite;
import frc.team1778.lib.util.gamepads.LogitechDualAction;
import frc.team1778.lib.util.gamepads.LogitechF310;

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
    LOGITECH_F310 {
      @Override
      public String toString() {
        return "Logitech F310 Gamepad";
      }
    },
    LOGITECH_DUAL_ACTION {
      @Override
      public String toString() {
        return "Logitech Dual Action Gamepad";
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

  public static Controls getInstance() {
    return instance;
  }

  /**
   * Returns the driver joystick/controller.
   *
   * @return The driver joystick/controller.
   */
  public Joystick getDriverController() {
    return driverController;
  }

  /**
   * Returns the oeprator joystick/controller.
   *
   * @return The operator joystick/controller.
   */
  public Joystick getOperatorController() {
    return operatorController;
  }

  // Driver Controls
  /**
   * Returns the driver's throttl stick y-axis.
   *
   * @return The driver controller's throttle stick y-axis.
   */
  public double getThrottle() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawAxis(InterLinkElite.Axis.LEFT_Y);
      case LOGITECH_F310:
        return -driverController.getRawAxis(LogitechF310.Axis.LEFT_Y);
      case LOGITECH_DUAL_ACTION:
        return -driverController.getRawAxis(LogitechDualAction.Axis.LEFT_Y);
      default:
        return 0;
    }
  }

  /**
   * Returns the driver's turn stick x-axis.
   *
   * @return The driver controller's turning stick x-axis.
   */
  public double getWheelX() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawAxis(InterLinkElite.Axis.RIGHT_X);
      case LOGITECH_F310:
        return driverController.getRawAxis(LogitechF310.Axis.RIGHT_X);
      case LOGITECH_DUAL_ACTION:
        return driverController.getRawAxis(LogitechDualAction.Axis.RIGHT_X);
      default:
        return 0;
    }
  }

  /**
   * Returns the driver's turn stick y-axis.
   *
   * @return The driver controller's turning stick y-axis.
   */
  public double getWheelY() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawAxis(InterLinkElite.Axis.RIGHT_Y);
      case LOGITECH_F310:
        return -driverController.getRawAxis(LogitechF310.Axis.RIGHT_Y);
      case LOGITECH_DUAL_ACTION:
        return -driverController.getRawAxis(LogitechDualAction.Axis.RIGHT_Y);
      default:
        return 0;
    }
  }

  /**
   * Returns the driver's quickturn toggle switch.
   *
   * @return The driver controller's quickturn switch state.
   */
  public boolean getQuickTurn() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawButton(InterLinkElite.RIGHT_TOP_SWITCH);
      case LOGITECH_F310:
        return driverController.getRawButton(LogitechF310.B);
      case LOGITECH_DUAL_ACTION:
        return driverController.getRawButton(LogitechDualAction.B2);
      default:
        return false;
    }
  }

  // Operator Controls
}
