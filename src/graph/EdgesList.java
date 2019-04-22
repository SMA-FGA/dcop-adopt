package graph;

import java.util.ArrayList;
import java.util.List;

public class EdgesList {
	private List<Edge> edges;
	
	public EdgesList() {
		edges = new ArrayList<Edge>();
	}

	public List<Edge> getEdges() {
		return edges;
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
}
