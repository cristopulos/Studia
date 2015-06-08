package aisd12.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import aisd12.Graph.Edge;
import aisd12.Graph.Graph;

public class KruskalAlg {
	public static LinkedList<Edge> edgeList =  new LinkedList<Edge>();
	
	public static int computeMinTree(Graph g)
	{
		int pathLength = 0;
		boolean ok = false;
		HashSet<Integer> setStart = null, setEnd = null;
		edgeList =  new LinkedList<Edge>();
		List<Edge> temp = new ArrayList<Edge>(g.getEdges());
		Collections.sort(temp,new KruskalComp());
		ArrayList<HashSet<Integer>> vertexes =  new ArrayList<HashSet<Integer>>();
		for (int i=0;i<g.size();i++)
		{
			HashSet<Integer> temp2 = new HashSet<Integer>();
			temp2.add(i+1);
			vertexes.add(temp2);
		}
		while(vertexes.size() !=1 && !temp.isEmpty())
		{
			ok = true;
			Edge e = temp.remove(0);
			for(HashSet<Integer> set : vertexes)
			{
				if(set.contains(e.start))
				{
					setStart = set;
					if(set.contains(e.end))
					{
						ok = false;
						break;
					}
				}
				if(set.contains(e.end))
				{
					setEnd = set;
					if(set.contains(e.start))
					{
						ok = false;
						break;
					}
				}
			}
			if(ok)
			{
			setStart.addAll(setEnd);
			vertexes.remove(setEnd);
			pathLength += e.length;
			edgeList.add(e);
			}
			
		}
		return pathLength;
	}

}
