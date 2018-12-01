package instantiator;

import java.util.ArrayList;
import java.util.List;

public class Client {	
	public static void dfs(Node n, int pre) {
		if(n.wasVisited()) {
			return;
		}
		
		pre++;
		n.setPre(pre);
		n.setVisited(true);
		
		for(Node v : n.getAdjacence()) {
			if(v.getParent() == null && !v.isRoot()) {
				v.setParent(n);
			}

			if(v.wasVisited() && n.getParent() != v) {
				if(v.getPre() < n.getPre()) {
					System.out.println("pseudo aresta: "+n.getName()+ "-" + v.getName());
				}
			}
			
			dfs(v, pre);
		}
	}

	public static void main(String[] args) {
        List<Integer> domain = new ArrayList<>(); // In the example, the domain's range is [0, 1]
        domain.add(0);
        domain.add(1);
        
        Node x1 = new Node("x1", domain);
        Node x2 = new Node("x2", domain);
        Node x3 = new Node("x3", domain);
        Node x4 = new Node("x4", domain);
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(x1);
        nodes.add(x2);
        nodes.add(x3);
        nodes.add(x4);
        
        Edge a = new Edge(x1, x2, "nothing");
        Edge b = new Edge(x2, x3, "nothing");
        Edge c = new Edge(x3, x1, "nothing");
        Edge d = new Edge(x2, x4, "nothing");
        Edge e = new Edge(x3, x4, "nothing");
        List<Edge> edges = new ArrayList<Edge>();
        edges.add(a);
        edges.add(d);
        edges.add(b);
        edges.add(c);
        edges.add(e);
        
        Graph graph = new Graph(nodes, edges);
        
        for(Edge edge : graph.getEdges()) {
        	Node first = edge.getFirst();
        	Node second = edge.getSecond();
        	second.addAdjacentNode(first);
        	first.addAdjacentNode(second);
        }
        
        int pre = 0;
        x1.setRoot();
        graph.resetVisited();
        dfs(graph.getNodes().get(0), pre);

	}

}
