package frc.robot;

/**
 * Represents the state of the robot and field at the beginning of the autonomous period: the
 * starting position of the robot, and the state of each of the switch/scale plates.
 */
public class AutoFieldState {
  private static AutoFieldState instance = null;

  public enum Side {
    LEFT,
    RIGHT
  }

  private Side ourSwitchSide;
  private Side scaleSide;
  private Side opponentSwitchSide;

  private AutoFieldState() {}

  /**
   * Returns a static instance of AutoFieldState, to be used instead of instantiating new objects of
   * AutoFiledState.
   *
   * @return an instance of AutoFieldState to avoid multiple objects of the FMS's data
   */
  public static synchronized AutoFieldState getInstance() {
    if (instance == null) {
      instance = new AutoFieldState();
    }
    return instance;
  }

  /**
   * Sets the switch/scale sides based on the given GameSpecificMessage. If the message is invalid
   * or null, returns false and leaves this object unchanged; otherwise, on success, returns true.
   */
  public synchronized boolean setSides(String gameData) {
    if (gameData == null) {
      return false;
    }
    gameData = gameData.trim();
    if (gameData.length() != 3) {
      return false;
    }
    Side s0 = getCharSide(gameData.charAt(0));
    Side s1 = getCharSide(gameData.charAt(1));
    Side s2 = getCharSide(gameData.charAt(2));
    if (s0 == null || s1 == null || s2 == null) {
      return false;
    }
    ourSwitchSide = s0;
    scaleSide = s1;
    opponentSwitchSide = s2;
    return true;
  }

  /** Checks if the game data is valid and not null. */
  public synchronized boolean isValid() {
    return scaleSide != null && ourSwitchSide != null;
  }

  /** Helper method to convert 'L' or 'R' to their respective Side. */
  private Side getCharSide(char c) {
    return c == 'L' ? Side.LEFT : c == 'R' ? Side.RIGHT : null;
  }

  /** Returns which Side of our switch is our alliance's. */
  public synchronized Side getOurSwitchSide() {
    return ourSwitchSide;
  }

  /** Returns which Side of the scale is our alliance's. */
  public synchronized Side getScaleSide() {
    return scaleSide;
  }

  /** Returns which Side of our opponent's switch is our alliance's. */
  public synchronized Side getOpponentSwitchSide() {
    return opponentSwitchSide;
  }

  @Override
  public String toString() {
    return "AutoFieldState{"
        + "ourSwitchSide="
        + getOurSwitchSide()
        + ", scaleSide="
        + getScaleSide()
        + ", opponentSwitchSide="
        + getOpponentSwitchSide()
        + '}';
  }
}
