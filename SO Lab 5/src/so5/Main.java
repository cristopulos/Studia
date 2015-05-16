package so5;

import so5.exceptions.ProbNoFreeCPUsException;

public class Main {

	public static void main (String[] args) throws ProbNoFreeCPUsException
	{
		Simiulation s = new Simiulation(200, 20, 30, 80, 100);
		s.runSimulation();
	}
	

}
