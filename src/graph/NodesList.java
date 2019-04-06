package graph;

import java.util.ArrayList;
import java.util.List;

public class NodesList {
	private List<Node> nodes;
	
	public NodesList() {
		nodes = new ArrayList<Node>();
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public void addNode(Node node) {
		nodes.add(node);
	}
	
	public Node getNodeByID(String id) {
		//to do: use hash instead list
		for (Node node : nodes) {
			if(node.getID().equals(id)) {
				return node;
			}
		}
		return null;
	}
}
