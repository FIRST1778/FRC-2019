package StateMachine;

import NetworkComm.InputOutputComm;
import Systems.DriveAssembly;
import Systems.FreezyPath;
import Systems.NavXSensor;

public class FollowPathAction extends Action {
	
	private String name;
	private int pathToFollow;
	
	public FollowPathAction(int pathToFollow)
	{
		this.name = "<Follow Path Action>";		
		this.pathToFollow = pathToFollow;

		DriveAssembly.initialize();
		NavXSensor.initialize();
		InputOutputComm.initialize();
		FreezyPath.initialize();
		FreezyPath.reset(pathToFollow);
	}
	
	public FollowPathAction(String name, int pathToFollow)
	{
		this.name =  name;
		this.pathToFollow = pathToFollow;
				
		DriveAssembly.initialize();
		NavXSensor.initialize();
		InputOutputComm.initialize();
		FreezyPath.initialize();
		FreezyPath.reset(pathToFollow);
	}
	
	
	// action entry
	public void initialize() {
		// do some drivey initialization
		
		DriveAssembly.autoInit(true, 0.0, false);
		FreezyPath.start();
		
		super.initialize();
	}
	
	// called periodically
	public void process()  {
		
		// no action needed (Path Following is self-running)
				
		super.process();
	}
	
	// state cleanup and exit
	public void cleanup() {
		// do some drivey cleanup
					
		FreezyPath.stop();
		DriveAssembly.autoStop();
		
		// cleanup base class
		super.cleanup();
	}
	
}
