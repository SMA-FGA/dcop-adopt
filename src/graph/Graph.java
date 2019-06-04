package graph;
import java.util.List;

public class Graph {
	private List<Node> nodes;
	private List<Edge> edges;
	
	public Graph(List<Node> nodes, List<Edge> edges, boolean isBidirected) {
		this.nodes = nodes;
		this.edges = edges;
		
		if(isBidirected) {
	        for(Edge edge : edges) {
	        	Node first = edge.getFirst();
	        	Node second = edge.getSecond();
	        	second.addAdjacentNode(first);
	        	first.addAdjacentNode(second);
	        }
		}
	}
	
	/*
	 *Add neighbors to nodes given its class in pseudo-tree
	 */
	public void dfs(Node n, int pre) {
		if(n.wasVisited()) {
			return;
		}
		
		pre++;
		n.setPre(pre);
		n.setVisited(true);
		
		for(Node v : n.getAdjacence()) {
			if(v.getParent() == null && !v.isRoot()) {
				n.addChild(v);
				n.addLowerNeighbour(v);
				v.addUpperNeighbour(n);
				v.setParent(n);
			}

			if(v.wasVisited() && n.getParent() != v) {
				if(v.getPre() < n.getPre()) {
					v.addLowerNeighbour(n);
					n.addUpperNeighbour(v);
					System.out.println("pseudo aresta: "+n.getID()+ "-" + v.getID());
				}
			}
			
			dfs(v, pre);
		}
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
