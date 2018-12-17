package frc.StateMachine;

import frc.NetworkComm.InputOutputComm;
import frc.Systems.DriveAssembly;
import frc.Systems.FreezyPath;

public class PathCompleteEvent extends Event {
	private String name;
	
	public PathCompleteEvent()
	{	
		this.name = "<Path Complete Event>";
		InputOutputComm.initialize();
	}
		
	// overloaded initialize method
	public void initialize()
	{
		//System.out.println("PathCompleteEvent initialized!");
		
		super.initialize();
	}
		
	
	// overloaded trigger method
	public boolean isTriggered()
	{			
		if (FreezyPath.isFinished())
		{
			System.out.println("PathCompleteEvent triggered!");
			return true;
		}
		
		return false;
	}

}
