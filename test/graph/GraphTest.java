package graph;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import graph.Constraint;
import java.util.*;

public class GraphTest {
	public static Graph graph;
	
	@Before
	public void createGraph() {
		ArrayList<Integer> domainValue = new ArrayList<Integer>();
		domainValue.add(0);
		domainValue.add(1);
		Domain d = new Domain("d", domainValue);
		
		NodesList allNodes = new NodesList();
		Node x1 = new Node("x1", d);
		Node x2 = new Node("x2", d);
		allNodes.addNode(x1);
		allNodes.addNode(x2);
		
		EdgesList allEdges = new EdgesList();
		Integer[][] constraintArray = {{7,2}, {2,3}};
		Constraint c = new Constraint("c", constraintArray);
		allEdges.addEdge(new Edge(x1, x2, c));
		
		graph = new Graph(allNodes.getNodes(), allEdges.getEdges(), true);
	}
	
//	@Test
//	public void isChildrenCorrectAfterDFS() {
//		int pre = 0;
//        Node root = graph.getNodes().get(0);
//        root.setRoot();
//        graph.dfs(root, pre);
//
//        Assert.assertEquals(, actual);
//	}
	
	@Test
	public void IsNodeSizeCorrect_SholdReturnNumberOfNodesInTheGraph() {
		List<Node> nodes = graph.getNodes();
		Assert.assertEquals(nodes.size(), 2);
	}
	
	
}
