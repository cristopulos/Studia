package aisd12.Graph;

public class Edge {
	
	public int start;
	public int end;
	public int length;
	public Edge(int start, int end, int length) {
		super();
		this.start = start;
		this.end = end;
		this.length = length;
	}

	public String toString()
	{
		return String.format("s : %d , e : %d, l : %d", start,end,length);
	}
}
