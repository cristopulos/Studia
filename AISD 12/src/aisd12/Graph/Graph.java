package aisd12.Graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	List<Edge> edges;
	boolean isSymetric;
	int vertQuant;
	
	public Graph (int size, boolean isSymetric)
	{
		this.isSymetric = isSymetric;
		edges = new ArrayList<Edge>();
		vertQuant = size;
	}
	
	public void addVertex(int start, int end, int length)
	{
		edges.add(new Edge(start, end, length));
		if (isSymetric)
			edges.add(new Edge(end, start, length));
	}
	
	public List<Edge> getEdges()
	{
		return edges;
	}
	
	public int size()
	{
		return vertQuant;
	}
}
