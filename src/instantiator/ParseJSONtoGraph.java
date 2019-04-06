package instantiator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import graph.Edge;
import graph.EdgesList;
import graph.Graph;
import graph.Node;
import graph.NodesList;

public class ParseJSONtoGraph {

	public Graph parse(String filePath) {
//String resourceName = "./graphModi.json";
//		System.out.println("teste");
//		InputStream is = Parse.class.getResourceAsStream(fileName);
//		if (is == null) {
//			System.out.println("null");
//		}
		
		File file = new File(filePath);
		
		String fileContent = null;
		try {
			fileContent = FileUtils.readFileToString(file, "utf-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        JSONObject object = new JSONObject(fileContent);
        
//        JSONArray domains = (JSONArray) object.get("domains");
//        JSONArray constraints = (JSONArray) object.get("constraints");
        JSONArray nodes = (JSONArray) object.get("nodes");
        JSONArray edges = (JSONArray) object.get("edges");
        
//        for (Object domain : domains) {
//        	JSONObject d = (JSONObject) domain;
//        	System.out.println(d.get("id"));
//		}
        
    	List<Integer> domain = new ArrayList<>(); // In the example, the domain's range is [0, 1]
        domain.add(0);
        domain.add(1);
        
        NodesList allNodes = new NodesList();
        for (Object node : nodes) {
        	JSONObject n = (JSONObject) node;
        	//System.out.println(n.get("id"));
        	allNodes.addNode(new Node((String) n.get("id"), domain));
		}
        
        EdgesList allEdges = new EdgesList();
        for (Object edge : edges) {
        	JSONObject e = (JSONObject) edge;
        	//System.out.println(e.get("id"));
        	Node nodeSource = allNodes.getNodeByID((String) e.get("source"));
            Node nodeTarget = allNodes.getNodeByID((String) e.get("target"));
        	
            allEdges.addEdge(new Edge(nodeSource, nodeTarget, "Constraint"));
		}
        
        Graph graph = new Graph(allNodes.getNodes(), allEdges.getEdges(), true);
        return graph;
       
        //constraints

	}

}
