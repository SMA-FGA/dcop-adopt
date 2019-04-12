package instantiator;

import graph.Graph;
import graph.Node;
import jade.core.Agent;
import jade.wrapper.StaleProxyException;

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
    	String filePath = "./DCOPJson/graphModi.json";
    	
    	ParseJSONtoGraph parseJSON = new ParseJSONtoGraph();
    	Graph graph = parseJSON.parse(filePath);
        
        //Execute dfs and add neighbours informations to the nodes
        graph.resetVisited();
        int pre = 0;
        
        //use first node as default for dfs
        Node startNode = graph.getNodes().get(0);
        startNode.setRoot();
        graph.dfs(startNode, pre);
        
		//Launch nodes as agents on platform
        for(Node node : graph.getNodes()) {
        	try {
                getContainerController().createNewAgent(node.getID(), "agent.DCOPAgent", node.getArgs()).start();
            } catch (StaleProxyException stale) {
            	System.out.println("Could not start agent\n");
                stale.printStackTrace();
            }
        }

        // We won't be needing this agent during the actual algorithm's execution
        doDelete();
    }
}
