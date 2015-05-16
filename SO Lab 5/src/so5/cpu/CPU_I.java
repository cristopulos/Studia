package so5.cpu;

import so5.util.Counter;
import so5.util.Process;

public class CPU_I extends CPU{

	@Override
	protected void manageProcess(Process p) {
		boolean processAssigned = false;
		for(int i=0; i<drawsQuant; i++)
		{
			CPU c = cPUList.get(rand.nextInt(cPUList.size()));
			if(!c.isOverloaded())
			{
				c.addProcess(p);
				Counter.reportTransfer();
				processAssigned = true;	
				break;
			}
		}
		if(!processAssigned)
			addProcess(p);
	}

}
