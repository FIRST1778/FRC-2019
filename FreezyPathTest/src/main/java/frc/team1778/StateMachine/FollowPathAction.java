package frc.team1778.StateMachine;

import frc.team1778.NetworkComm.InputOutputComm;
import frc.team1778.Systems.DriveAssembly;
import frc.team1778.Systems.FreezyPath;
import frc.team1778.Systems.NavXSensor;

public class FollowPathAction extends Action {
	
	private String name;
	private int pathToFollow;
	private boolean fwdPolarity;
	
	public FollowPathAction(int pathToFollow, boolean polarity)
	{
		this.name = "<Follow Path Action>";		
		this.pathToFollow = pathToFollow;
		this.fwdPolarity = polarity;

		DriveAssembly.initialize();
		NavXSensor.initialize();
		InputOutputComm.initialize();
		FreezyPath.initialize();
	}
	
	public FollowPathAction(String name, int pathToFollow, boolean polarity)
	{
		this.name =  name;
		this.pathToFollow = pathToFollow;
		this.fwdPolarity = polarity;
				
		DriveAssembly.initialize();
		NavXSensor.initialize();
		InputOutputComm.initialize();
		FreezyPath.initialize();
	}
	
	
	// action entry
	public void initialize() {
		// do some drivey initialization
		
		DriveAssembly.autoInit(true, 0.0, false);

		// set up path and followers
		FreezyPath.reset(pathToFollow);

		// start the pathfinder thread
		FreezyPath.start(fwdPolarity);
		
		super.initialize();
	}
	
	// called periodically
	public void process()  {
		
		// no action needed (Path Following thread is self-running)
				
		super.process();
	}
	
	// state cleanup and exit
	public void cleanup() {
		// do some drivey cleanup
					
		// stop the pathfinder thread
		FreezyPath.stop();
		
		DriveAssembly.autoStop();
		
		// cleanup base class
		super.cleanup();
	}
	
}
