package so5.cpu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import so5.exceptions.ProbNoFreeCPUsException;
import so5.util.Counter;
import so5.util.Process;

public class CPU_III extends CPU_II {


	@Override
	public void generateProcesses() throws ProbNoFreeCPUsException {
		super.generateProcesses();
		if(workLoad<minLoad)
		{
			for (int i=0;i<drawsQuant;i++)
			{
				CPU c = cPUList.get(rand.nextInt(cPUList.size()));
				if(c.isOverloaded())
				{
					transferProcessestoCPU(c);
					break;
				}
			}
		}
	}

	private List<Process> transferProcessestoCPU(CPU source) {
		List<Process> result = new ArrayList<Process>();
		int target = (minLoad + maxLoad) / 2;
		int addedWork = 0;
		Iterator<Process> it = source.ongoingProcesses.iterator();
		while (workLoad + addedWork <= target && it.hasNext()) {
			Process p = it.next();
			result.add(p);
			Counter.reportTransfer();
			it.remove();
			addedWork += p.getResourcesNeeded();
		}
		return result;
	}

}
