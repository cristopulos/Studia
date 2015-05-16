package so5.cpu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import so5.exceptions.ProbNoFreeCPUsException;
import so5.util.Counter;
import so5.util.Process;

public abstract class CPU {

	protected int workLoad;
	protected List<Process> ongoingProcesses;
	protected static List<CPU> cPUList;
	protected static Random rand = new Random();
	protected static int maxLoad;
	protected static int minLoad;
	protected static int drawsQuant;
	protected static int maxDrawsBeforeExc;
	protected double avgLoad;
	protected int avgLoadDataCount;

	public CPU() {
		workLoad = 0;
		avgLoad = 0.0d;
		avgLoadDataCount = 0;
		ongoingProcesses = new ArrayList<Process>();
	}
	
	public static void nextTimeunit() throws ProbNoFreeCPUsException
	{
		for(CPU c: cPUList)
		{
			c.generateProcesses();
		}
		for(CPU c: cPUList)
		{
			c.executeProcesses();
		}
	}

	public void generateProcesses() throws ProbNoFreeCPUsException {
		if (rand.nextFloat() > 0.2f) {
			int quant = rand.nextInt(63) + 1;
			quant = 6 - log2(quant);
			for (int i = 0; i < quant; i++) {
				int res = rand.nextInt(35) + 1;
				int time = rand.nextInt(5) + 1;
				Process p = new Process(res, time);
				manageProcess(p);
			}
		}
	}

	protected abstract void manageProcess(Process p);
	
	protected void addProcess(Process p) {
		ongoingProcesses.add(p);
		workLoad += p.getResourcesNeeded();
	}

	private void updateWorkload() {
		workLoad = 0;
		for (Process p : ongoingProcesses) {
			workLoad += p.getResourcesNeeded();
		}
	}

	public void executeProcesses() {
		avgLoad = (avgLoad * avgLoadDataCount + workLoad)/(avgLoadDataCount+1);
		avgLoadDataCount++;
		int workDone = 0;
		for (Iterator<Process> it = ongoingProcesses.iterator(); it.hasNext();) {
			Process p = it.next();
			if (p.getResourcesNeeded() + workDone <= 100) {
				p.decrementTime();
				workDone += p.getResourcesNeeded();
				if (p.getTimeNeeded() <= 0)
					it.remove();
			}
		}
		updateWorkload();
	}

	public boolean isOverloaded() {
		Counter.reportQuery();
		return workLoad >= maxLoad;
	}

	public static void setcPUList(List<CPU> cPUList) {
		CPU.cPUList = cPUList;
		CPU.maxDrawsBeforeExc = (int) Math.pow(CPU.cPUList.size(), 2);
	}

	public static void setMaxLoad(int maxLoad) {
		CPU.maxLoad = maxLoad;
	}

	public static void setMinLoad(int minLoad) {
		CPU.minLoad = minLoad;
	}
	
	public static void setDrawsQuant(int drawsQuant) {
		CPU.drawsQuant = drawsQuant;
	}

	public double getAvgLoad() {
		return avgLoad;
	}

	private int log2(int x) {
		int counter = -1;
		while (x > 0) {
			x = x / 2;
			counter++;
		}
		if (counter == -1)
			throw new ArithmeticException();
		else
			return counter;
	}

}
