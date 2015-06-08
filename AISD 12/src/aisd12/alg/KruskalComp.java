package aisd12.alg;

import java.util.Comparator;

import aisd12.Graph.Edge;

public class KruskalComp implements Comparator<Edge>{

	@Override
	public int compare(Edge o1, Edge o2) {
		return (int)Math.signum(o1.length-o2.length);
	}

}
