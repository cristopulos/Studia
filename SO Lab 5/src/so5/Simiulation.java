package so5;

import java.util.ArrayList;
import java.util.List;

import so5.cpu.CPU;
import so5.cpu.CPU_I;
import so5.cpu.CPU_II;
import so5.cpu.CPU_III;
import so5.util.Counter;

public class Simiulation {
	
	private int simTime;
	private int drawsQuant;
	private int minLoad;
	private int maxLoad;
	private int cpusQuant;
	private int[] queryQuant;
	private int[] transfQuant;
	private double[] avgLoad;
	private double[] avgError;
	
	public Simiulation(int simTime, int drawsQuant, int minLoad, int maxLoad,
			int cpusQuant) {
		super();
		this.simTime = simTime;
		this.drawsQuant = drawsQuant;
		this.minLoad = minLoad;
		this.maxLoad = maxLoad;
		this.cpusQuant = cpusQuant;
		queryQuant = new int[3];
		transfQuant = new int[3];
		avgError = new double[3];
		avgLoad = new double[3];
	}
	
	public void runSimulation()
	{
		runSim(0,setUp1stSim());
		runSim(1, setUp2ndSim());
		runSim(2, setUp3rdSim());
		printResults();
	}
	
	private List<CPU> setUp1stSim ()
	{
		ArrayList<CPU> cPUsList = new ArrayList<CPU>();
		for(int i=0; i<cpusQuant; i++)
		{
			cPUsList.add(new CPU_I());
		}
		return cPUsList;
	}
	
	private List<CPU> setUp2ndSim ()
	{
		ArrayList<CPU> cPUsList = new ArrayList<CPU>();
		for(int i=0; i<cpusQuant; i++)
		{
			cPUsList.add(new CPU_II());
		}
		return cPUsList;
	}
	
	private List<CPU> setUp3rdSim ()
	{
		ArrayList<CPU> cPUsList = new ArrayList<CPU>();
		for(int i=0; i<cpusQuant; i++)
		{
			cPUsList.add(new CPU_III());
		}
		return cPUsList;
	}
	


	private void runSim (int iterCount, List<CPU> cpuList)
	{
		
		CPU.setcPUList(cpuList);
		Counter.reset();
		CPU.setMinLoad(minLoad);
		CPU.setMaxLoad(maxLoad);
		CPU.setDrawsQuant(drawsQuant);
		for(int i=0; i<simTime;i++)
		{
			CPU.nextTimeunit();
		}
		queryQuant[iterCount] = Counter.getQueryQuant();
		transfQuant[iterCount] = Counter.getTransferQuant();
		double sum = 0;
		for(CPU c :cpuList)
		{
			sum += c.getAvgLoad();
		}
		avgLoad[iterCount] = sum/cpuList.size();
		double errorSum = 0;
		for(CPU c: cpuList)
		{
			errorSum = Math.abs(avgLoad[iterCount] - c.getAvgLoad());
		}
		avgError[iterCount] = errorSum / cpuList.size();
	}
	
	
	private void printResults()
	{
		for(int i=0;i<3;i++)
		{
			System.out.printf("Simulation nr %d :%n",i)
			.printf("Avg Load : %.3f%n", avgLoad[i])
			.printf("Avg Error Load : %.3f%n", avgError[i])
			.printf("Number of workload queries : %d%n",queryQuant[i])
			.printf("Number of process transfers : %d%n", transfQuant[i]);
		}
	}
	
	

}
