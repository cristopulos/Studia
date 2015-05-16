package so5.util;

public class Process {
	
	private int resourcesNeeded;
	private int timeNeeded;
	
	public Process(int resourcesNeeded, int timeNeeded) {
		this.resourcesNeeded = resourcesNeeded;
		this.timeNeeded = timeNeeded;
	}

	public int getResourcesNeeded() {
		return resourcesNeeded;
	}

	public int getTimeNeeded() {
		return timeNeeded;
	}
	
	public void decrementTime ()
	{
		timeNeeded--;
	}
}
