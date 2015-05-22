package aisd11.alg;

import java.util.HashSet;
import java.util.Set;

import aisd11.structs.Graph;

public class Dijkstra {
	
	public static int[][] countDistFromTownWithId (int townId, Graph graph){
		
		if(townId>= graph.size)
			throw new IndexOutOfBoundsException();
		
		int [][] aux = new int [graph.size][2];
		for(int i=0;i<graph.size;i++)
		{
			if(i!=townId)
				aux[i][0] = Integer.MAX_VALUE;
			else
				aux[i][0] = 0;
			aux[i][1] = -1;
		}
		
		Set<Integer> vertexesToVisit = new HashSet<Integer>();
		Set<Integer> visitedVertexes = new HashSet<Integer>();
		for(int i=0;i<graph.size;i++)
			vertexesToVisit.add(i);
		while(!vertexesToVisit.isEmpty())
		{
			int minInd = -1;
			int min = Integer.MAX_VALUE;
			for(int i=0;i<graph.size;i++)
			{
				if(!vertexesToVisit.contains(i))
					continue;
				if(aux[i][0]<min)
				{
					minInd = i;
					min = aux[i][0];
				}
			}
			if(min == Integer.MAX_VALUE)
				break;
			vertexesToVisit.remove(minInd);
			visitedVertexes.add(minInd);
			for(int i=0; i<graph.size;i++)
			{
				if(graph.edges[minInd][i]!=0 && vertexesToVisit.contains(i))
				{
					if(aux[i][0] > aux[minInd][0] + graph.edges[minInd][i])
					{
						aux[i][0] = aux[minInd][0] + graph.edges[minInd][i];
						aux[i][1] = minInd;
					}
				}
			}
			
		}
		
		
		return aux;
	}

}
