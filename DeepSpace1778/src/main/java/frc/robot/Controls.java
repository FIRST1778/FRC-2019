package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.lib.gamepads.FreezyBoard;
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
    FREEZY_BOARD {
      @Override
      public String toString() {
        return "Freezy Board";
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
  private static final ControllerType OPERATOR_CONTROLLER_TYPE = ControllerType.FREEZY_BOARD;
  private static final ControllerType SECOND_OPERATOR_CONTROLLER_TYPE =
      ControllerType.LOGITECH_F310;

  private static final int PORT_DRIVE_CONTROLLER = 0;
  private static final int PORT_OPERATOR_CONTROLLER = 1;
  private static final int PORT_SECOND_OPERATOR_CONTROLLER = 2;

  private Joystick driverController;
  private Joystick operatorController;
  private Joystick secondOperatorController;

  private Controls() {
    driverController = new Joystick(PORT_DRIVE_CONTROLLER);
    operatorController = new Joystick(PORT_OPERATOR_CONTROLLER);
    secondOperatorController = new Joystick(PORT_SECOND_OPERATOR_CONTROLLER);
  }

  public static Controls getInstance() {
    return instance;
  }

  public Joystick getDriverController() {
    return driverController;
  }

  public Joystick getOperatorController() {
    return operatorController;
  }

  public Joystick getSecondOperatorController() {
    return secondOperatorController;
  }

  public ControllerType getDriverControllerType() {
    return DRIVER_CONTROLLER_TYPE;
  }

  public ControllerType getOperatorControllerType() {
    return OPERATOR_CONTROLLER_TYPE;
  }

  // Driver Controls
  public double getTranslationY() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return SimpleUtil.handleDeadband(
            driverController.getRawAxis(FreezyController.AXIS_LEFT_Y), 0.05);
      case INTERLINK_ELITE_CONTROLLER:
        return -driverController.getRawAxis(InterLinkElite.AXIS_LEFT_Y);
      case LOGITECH_DUAL_ACTION:
        return -driverController.getRawAxis(LogitechDualAction.AXIS_LEFT_Y);
      case LOGITECH_F310:
        return -driverController.getRawAxis(LogitechF310.AXIS_LEFT_Y);
      case XBOX_ONE:
        return -driverController.getRawAxis(XboxOne.AXIS_LEFT_Y);
      case XBOX_ONE_ELITE:
        return -driverController.getRawAxis(XboxOneElite.AXIS_LEFT_Y);
      default:
        return 0;
    }
  }

  public double getTranslationX() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return SimpleUtil.handleDeadband(
            -driverController.getRawAxis(FreezyController.AXIS_LEFT_X), 0.05);
      case INTERLINK_ELITE_CONTROLLER:
        return -driverController.getRawAxis(InterLinkElite.AXIS_LEFT_X);
      case LOGITECH_DUAL_ACTION:
        return -driverController.getRawAxis(LogitechDualAction.AXIS_LEFT_X);
      case LOGITECH_F310:
        return -driverController.getRawAxis(LogitechF310.AXIS_LEFT_X);
      case XBOX_ONE:
        return -driverController.getRawAxis(XboxOne.AXIS_LEFT_X);
      case XBOX_ONE_ELITE:
        return -driverController.getRawAxis(XboxOneElite.AXIS_LEFT_X);
      default:
        return 0;
    }
  }

  public double getRotation() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return SimpleUtil.handleDeadband(
            -driverController.getRawAxis(FreezyController.AXIS_RIGHT_X), 0.05);
      case INTERLINK_ELITE_CONTROLLER:
        return -driverController.getRawAxis(InterLinkElite.AXIS_RIGHT_X);
      case LOGITECH_DUAL_ACTION:
        return -driverController.getRawAxis(LogitechDualAction.AXIS_RIGHT_X);
      case LOGITECH_F310:
        return -driverController.getRawAxis(LogitechF310.AXIS_RIGHT_X);
      case XBOX_ONE:
        return -driverController.getRawAxis(XboxOne.AXIS_RIGHT_X);
      case XBOX_ONE_ELITE:
        return -driverController.getRawAxis(XboxOneElite.AXIS_RIGHT_X);
      default:
        return 0;
    }
  }

  public boolean getResetFieldCentric() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return driverController.getRawButton(FreezyController.RIGHT_SHOULDER_SWITCH);
      case INTERLINK_ELITE_CONTROLLER:
        return !driverController.getRawButton(InterLinkElite.RIGHT_SHOULDER_SWITCH);
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

  public boolean getFieldCentricToggle() {
    switch (DRIVER_CONTROLLER_TYPE) {
      case FREEZY_CONTROLLER:
        return driverController.getRawButton(FreezyController.LEFT_SHOULDER_SWITCH);
      case INTERLINK_ELITE_CONTROLLER:
        return driverController.getRawButton(InterLinkElite.LEFT_SWITCH);
      case LOGITECH_DUAL_ACTION:
        return driverController.getRawButton(LogitechDualAction.LEFT_TRIGGER);
      case LOGITECH_F310:
        return driverController.getRawAxis(LogitechF310.AXIS_LEFT_TRIGGER) >= 0.5;
      case XBOX_ONE:
        return driverController.getRawAxis(XboxOne.AXIS_LEFT_TRIGGER) >= 0.5;
      case XBOX_ONE_ELITE:
        return driverController.getRawAxis(XboxOneElite.AXIS_LEFT_TRIGGER) >= 0.5;
      default:
        return false;
    }
  }

  // Operator Controls
  public boolean getLiftToCargoHigh() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case LOGITECH_F310:
        return operatorController.getRawButton(LogitechF310.X);
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.RIGHT_THREE_BUTTON);
      default:
        return false;
    }
  }

  public boolean getLiftToCargoMedium() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case LOGITECH_F310:
        return operatorController.getRawButton(LogitechF310.Y);
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.RIGHT_TWO_BUTTON);
      default:
        return false;
    }
  }

  public boolean getLiftToCargoShipCargo() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case LOGITECH_F310:
        return operatorController.getRawButton(LogitechF310.B);
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.RIGHT_FOUR_BUTTON);
      default:
        return false;
    }
  }

  public boolean getLiftToCargoLow() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case LOGITECH_F310:
        return operatorController.getRawButton(LogitechF310.A);
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.RIGHT_ONE_BUTTON)
            && operatorController.getRawButton(FreezyBoard.LEFT_TOGGLE_SWITCH);
      default:
        return false;
    }
  }

  public boolean getLiftToHatchHigh() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.LEFT_THREE_BUTTON);
      default:
        return false;
    }
  }

  public boolean getLiftToHatchMedium() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.LEFT_TWO_BUTTON);
      default:
        return false;
    }
  }

  public boolean getLiftToHatchLow() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.LEFT_ONE_BUTTON);
      default:
        return false;
    }
  }

  public boolean getLiftToHatchFloorPickup() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.LEFT_ONE_BUTTON);
      default:
        return false;
    }
  }

  public boolean getLiftToCargoPickup() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.RIGHT_ONE_BUTTON)
            && !operatorController.getRawButton(FreezyBoard.LEFT_TOGGLE_SWITCH);
      default:
        return false;
    }
  }

  public boolean getExtendArticulator() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case LOGITECH_F310:
        return operatorController.getRawButton(LogitechF310.RIGHT_BUMPER);
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.LEFT_TOGGLE_SWITCH);
      default:
        return false;
    }
  }

  public double getManualArticulator() {
    switch (SECOND_OPERATOR_CONTROLLER_TYPE) {
      case LOGITECH_F310:
        return -secondOperatorController.getRawAxis(LogitechF310.AXIS_RIGHT_Y);
      default:
        return 0.0;
    }
  }

  public boolean getOpenHatchPanel() {
    switch (SECOND_OPERATOR_CONTROLLER_TYPE) {
      case LOGITECH_F310:
        return secondOperatorController.getRawButton(LogitechF310.LEFT_BUMPER);
      case FREEZY_BOARD:
        return operatorController.getRawButton(FreezyBoard.LEFT_UPPER_BUTTON);
      default:
        return false;
    }
  }

  public double getCargoIntake() {
    switch (OPERATOR_CONTROLLER_TYPE) {
      case LOGITECH_F310:
        return secondOperatorController.getRawAxis(LogitechF310.AXIS_RIGHT_TRIGGER)
            - secondOperatorController.getRawAxis(LogitechF310.AXIS_LEFT_TRIGGER);
      case FREEZY_BOARD:
        return SimpleUtil.handleDeadband(
            operatorController.getRawAxis(FreezyBoard.AXIS_RIGHT_Y), 0.1);
      default:
        return 0;
    }
  }

  public double getLiftManualControl() {
    switch (SECOND_OPERATOR_CONTROLLER_TYPE) {
      case LOGITECH_F310:
        return -secondOperatorController.getRawAxis(LogitechF310.AXIS_LEFT_Y);
      default:
        return 0;
    }
  }
}
