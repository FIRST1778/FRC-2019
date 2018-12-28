package frc.robot.auto.actions;

import frc.lib.util.NetworkTableWrapper;

/**
 * Action to wait for a given amount of time To use this Action, call runAction(new
 * WaitAction(your_time)).
 */
public class PrintToNetworkTableAction implements Action {

  private NetworkTableWrapper networkTable;
  private String tableKey;
  private String tableText;

  /**
   * Constructor for this action.
   *
   * @param table the NetworkTable to send data to
   * @param key the key on the NetworkTable to send text to
   * @param text the String of data to send
   */
  public PrintToNetworkTableAction(NetworkTableWrapper table, String key, String text) {
    networkTable = table;
    tableKey = key;
    tableText = text;
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void update() {}

  @Override
  public void done() {}

  @Override
  public void start() {
    networkTable.putString(tableKey, tableText);
  }
}
