package so5.cpu;

import so5.util.Counter;
import so5.util.Process;

public class CPU_II extends CPU {

	@Override
	protected void manageProcess(Process p) {
		if(isOverloaded())
			for(int i=0; i<maxDrawsBeforeExc;i++)
			{
				CPU c = cPUList.get(rand.nextInt(cPUList.size()));
				if(!c.isOverloaded())
				{
					c.addProcess(p);
					Counter.reportTransfer();
					break;
				}
			}
		else
			addProcess(p);
	}

}
