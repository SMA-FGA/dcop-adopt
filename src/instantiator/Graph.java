package instantiator;
import java.util.List;

public class Graph {
	private List<Node> nodes;
	private List<Edge> edges;
	
	public Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	public void resetVisited() {
		for(Node n : this.nodes) {
			n.setVisited(false);
		}
	}
	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}
	public void addNode(Node node) {
		this.nodes.add(node);
	}
}
