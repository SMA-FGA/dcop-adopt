package instantiator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import graph.Constraint;
import graph.ConstraintsList;
import graph.Domain;
import graph.DomainsList;
import graph.Edge;
import graph.EdgesList;
import graph.Graph;
import graph.Node;
import graph.NodesList;

public class ParseJSONtoGraph {

	public Graph parse(String filePath) {
		File file = new File(filePath);
		
		String fileContent = null;
		try {
			fileContent = FileUtils.readFileToString(file, "utf-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        JSONObject object = new JSONObject(fileContent);
        
        JSONArray domains = (JSONArray) object.get("domains");
        JSONArray constraintsArray = (JSONArray) object.get("constraints");
        JSONArray nodes = (JSONArray) object.get("nodes");
        JSONArray edges = (JSONArray) object.get("edges");
        
        //parse constraints
		ConstraintsList allConstraints = new ConstraintsList();
        for (Object constraint : constraintsArray) {
        	JSONObject c = (JSONObject) constraint;
        	
        	System.out.println(c.get("value"));
        	
        	List<List<Integer>> constraintComplete = new ArrayList<List<Integer>>();
        	
        	JSONArray valueConstraintArray = (JSONArray) c.get("value");
        	if (valueConstraintArray != null) {
        	   for (int i=0;i<valueConstraintArray.length();i++){
        		   ArrayList<Integer> listdata = new ArrayList<Integer>();
        		   JSONArray jArray2 = (JSONArray) valueConstraintArray.get(i);
        		   for (int j=0;j<jArray2.length();j++){
        			   System.out.println(jArray2.get(j));
        			   listdata.add(jArray2.getInt(j));
        		   }
        		   
        		   constraintComplete.add(listdata);
        		//System.out.println(valueArray.get(i));
        	    //listdata.add(jArray.getInt(i));
        	   } 
        	}
        	
        	allConstraints.addConstraint(new Constraint((String) c.get("id"), constraintComplete));
        }
        
        //parse domains
        DomainsList allDomains = new DomainsList();
        for (Object domain : domains) {
        	JSONObject d = (JSONObject) domain;
        	ArrayList<Integer> domainValue = new ArrayList<Integer>();
        	
        	JSONArray valueArray = (JSONArray) d.get("value");
        	if (valueArray != null) {
        	   for (int i=0; i < valueArray.length(); i++){ 
        		   domainValue.add(valueArray.getInt(i));
        	   } 
        	}
        	
        	allDomains.addDomain(new Domain((String) d.get("id"), domainValue));
		}
        
        //parse nodes
        NodesList allNodes = new NodesList();
        for (Object node : nodes) {
        	JSONObject n = (JSONObject) node;
        	//System.out.println(n.get("id"));
        	allNodes.addNode(new Node((String) n.get("id"), allDomains.getNodeByID((String) n.get("domain"))));
		}
        
        //parse edges
        EdgesList allEdges = new EdgesList();
        for (Object edge : edges) {
        	JSONObject e = (JSONObject) edge;
        	//System.out.println(e.get("id"));
        	Node nodeSource = allNodes.getNodeByID((String) e.get("source"));
            Node nodeTarget = allNodes.getNodeByID((String) e.get("target"));
        	
            allEdges.addEdge(new Edge(nodeSource, nodeTarget, allConstraints.getConstraintByID((String) e.get("constraint"))));
		}
        
        Graph graph = new Graph(allNodes.getNodes(), allEdges.getEdges(), true);
        return graph;

	}

}
