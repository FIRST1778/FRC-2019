package frc.team1778.StateMachine;

import java.util.ArrayList;

import frc.team1778.NetworkComm.InputOutputComm;
import frc.team1778.Systems.DriveAssembly;
import frc.team1778.Systems.NavXSensor;

import edu.wpi.first.wpilibj.DriverStation;

public class AutoStateMachine {
		
	private boolean autoNetworkEnable = false;
		
	private ArrayList<AutoNetwork> autoNetworks;
	
	private final int UNDEFINED = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	
	private int[] fieldAllianceColors = {UNDEFINED, UNDEFINED, UNDEFINED};
	
	private AutoNetwork currentNetwork;
	private AutoChooser autoChooser;
		
	public AutoStateMachine()
	{
		AutoNetworkBuilder.initialize();	
		InputOutputComm.initialize();
		NavXSensor.initialize();
		
		// create list of autonomous networks
		autoNetworks = AutoNetworkBuilder.readInNetworks();
		
		// create the smart dashboard chooser
		autoChooser = new AutoChooser();
		
	}
				
	public void start()
	{
				
		// check switch and scale lighting combination
		getFieldColorConfig();
		
		// determine if we are running auto or not
		int networkIndex = getNetworkIndex();
		
		String myString = new String("autoNetworkEnable = " + autoNetworkEnable + ", networkIndex = " + networkIndex);
		System.out.println(myString);
		InputOutputComm.putString(InputOutputComm.LogTable.kMainLog,"Auto/AutoSM_network", myString);

		// must initialize the gyro class and reset the angle to our initial rotation
		NavXSensor.reset();
		
		// reset drive system encoders to zero position
		DriveAssembly.resetPos();
				
		if (autoNetworkEnable)
		{
			// if we have a state network
			currentNetwork = autoNetworks.get(networkIndex);
			
			if (currentNetwork != null)
			{	
				//System.out.println("State machine starting with " + currentState.name);						
				currentNetwork.enter();
			}
		}		
		
	}
	
	public void process()  {
		
		if (autoNetworkEnable)
		{
			// process the current network
			if (currentNetwork != null)
			{
				currentNetwork.process();
			}	
		}
	}
	
	public void stop()  {
		if (currentNetwork != null)
		{
			currentNetwork.exit();
		}	
		
	}

	// figures out which autonomous network to run 
	private int getNetworkIndex()
	{
		// grab the selected action and position from the driver station
		int action = autoChooser.getAction();

		// pick the network, enable auto network
		int netIndex = action;
		autoNetworkEnable = true;
	
		// return index value for network selected
		return netIndex;
		
	}
		
	// retrieves color configuration of field elements relative to alliance side
	private void getFieldColorConfig()
	{
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();

		for (int i=0; i < 3; i++)
		{
			if (gameData.charAt(i) == 'L')
				fieldAllianceColors[i] = LEFT;
			else
				fieldAllianceColors[i] = RIGHT;
		}
		InputOutputComm.putString(InputOutputComm.LogTable.kMainLog,"Auto/FieldConfig", gameData);

	}
	
}
