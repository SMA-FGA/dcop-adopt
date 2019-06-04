package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodesList {
	private List<Node> nodes;
	private Map<String, Node> nodesMap;
	
	public NodesList() {
		this.nodes = new ArrayList<Node>();
		this.nodesMap = new HashMap<String, Node>();
	}

	public List<Node> getNodes() {
		return this.nodes;
	}

	public void addNode(Node node) {
		this.nodes.add(node);
		this.nodesMap.put(node.getID(), node);
	}
	
	public Node getNodeByID(String id) {
		return this.nodesMap.get(id);
	}
}
