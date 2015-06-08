package aisd12.alg;

import java.util.HashSet;
import java.util.Set;

import aisd12.Graph.Graph;

public class HeldKarpAlg {

	public static int computeShortestHamiltonianCycle(Graph g) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < g.size(); i++) {
			set.add(i);
		}
		int[] dist = new int[g.size()];
		for (int i = 0; i < g.size(); i++) {
			Set<Integer> aux = new HashSet<Integer>(set);
			aux.remove(i);
			dist[i] = computeShortestPath(i, aux, g);
		}
		int result = Integer.MAX_VALUE;
		for (int i = 0; i < dist.length; i++) {
			if (result > dist[i])
				result = dist[i];
		}

		return result;
	}

	private static int computeShortestPath(int startingVertexId,
			Set<Integer> vertexesToVisit, Graph g) {
		if (vertexesToVisit.size() == 1) {
			int result = Integer.MAX_VALUE;
			for (int vertexId : vertexesToVisit) {
				result = g.getEdge(startingVertexId, vertexId);
			}
			return result;

		}
		int[] dist = new int[vertexesToVisit.size()];
		int counter = 0;
		for (int curVertexId : vertexesToVisit) {
			Set<Integer> aux = new HashSet<Integer>(vertexesToVisit);
			aux.remove(curVertexId);
			int pathDist = computeShortestPath(curVertexId, aux, g);
			if (pathDist == Integer.MAX_VALUE
					|| g.getEdge(startingVertexId, curVertexId) == Integer.MAX_VALUE)
				dist[counter] = Integer.MAX_VALUE;
			else
				dist[counter] = g.getEdge(startingVertexId, curVertexId)
						+ pathDist;
			counter++;
		}
		int result = Integer.MAX_VALUE;
		for (int i = 0; i < dist.length; i++) {
			if (result > dist[i])
				result = dist[i];
		}

		return result;
	}

}
