package frc.team1778.robot.auto.actions;

import frc.team1778.lib.util.NetworkTableWrapper;

/**
 * Action to wait for a given amount of time To use this Action, call runAction(new
 * WaitAction(your_time))
 */
public class PrintToNetworkTableAction implements Action {

  private NetworkTableWrapper networkTable;
  private String tableKey;
  private String tableText;

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
