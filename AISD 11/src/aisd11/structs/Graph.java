package aisd11.structs;

import java.util.Random;

public class Graph {
	
	public final int[][] edges;
	public final int size;
	
	public Graph (int townsQuant, double connectivityCoeff)
	{
		Random rand = new Random();
		size = townsQuant;
		edges  = new int[townsQuant][townsQuant];
		for(int i=0;i<townsQuant;i++)
			for(int j=0; j< townsQuant;j++)
			{
				if(i == j)
					continue;
				int chance  = rand.nextInt(10);
				if(chance<connectivityCoeff*10)
					edges[i][j] = rand.nextInt(20)+1;
				else
					edges[i][j] = 0;
			}
	}

}
