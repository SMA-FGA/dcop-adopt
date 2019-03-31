package instantiator;

import jade.core.Agent;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

import graph.Edge;
import graph.Graph;
import graph.Node;

/*
 * Creates the agents that will be used in the DCOP.
 */
public class InstantiatorAgent extends Agent {

	private static final long serialVersionUID = -7764996125444199018L;

	/*
     * Each created agent contains a list with the names of its children, lower neighbours, upper neighbours.
     */
    @Override
    protected void setup() {

        /*
         * For some unfortunate reason, nothing happens when passing the
         * ArrayList as an argument in the agent's creation. So we convert it
         * to a regular array, then convert it back once inside the DcopAgent.
         */
        
    	List<Integer> domain = new ArrayList<>(); // In the example, the domain's range is [0, 1]
        domain.add(0);
        domain.add(1);
        
        // Creating graph
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
        Edge d = new Edge(x1, x3, "nothing");
        Edge g = new Edge(x2, x4, "nothing");
        List<Edge> edges = new ArrayList<Edge>();
        edges.add(a);
        edges.add(b);
        edges.add(d);
        edges.add(g);
        
        Graph graph = new Graph(nodes, edges, true);
        
        //Execute dfs and add neighbours informations to the nodes
        graph.resetVisited();
        int pre = 0;
        x1.setRoot();
        graph.dfs(x1, pre);
        
		//Launch nodes as agents on platform
        for(Node node : nodes) {
        	try {
                getContainerController().createNewAgent(node.getName(), "node.NodeAgent", node.getArgs()).start();
            } catch (StaleProxyException stale) {
            	System.out.println("Could not start agent\n");
                stale.printStackTrace();
            }
        }

        // We won't be needing this agent during the actual algorithm's execution
        doDelete();
    }
}
