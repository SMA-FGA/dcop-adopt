package graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Node {
	private String id;
	private List<Integer> domain;
	private List<Node> adjacence;
	private Boolean wasVisited;
	private Node parent;
	private Integer pre;
	private Boolean isRoot;
	private List<String> children;
    private List<String> lowerNeighbours;
    private List<String> upperNeighbours;
    private List<List<Integer>> constraints;
	
	public Node(String id, List<Integer> domain) {
		this.id = id;
		this.domain = domain;
		this.wasVisited = false;
		this.adjacence = new ArrayList<Node>();
		this.parent = null;
		this.pre = null;
		this.isRoot = false;
		this.children = new ArrayList<>();
		this.lowerNeighbours = new ArrayList<>();
		this.upperNeighbours = new ArrayList<>();
		this.constraints = new Vector<>();
	    List<Integer> constraintLine1 = new Vector<>();
	    List<Integer> constraintLine2 = new Vector<>();
	    constraintLine1.add(1);
	    constraintLine1.add(2);
	    constraintLine2.add(2);
	    constraintLine2.add(0);
	    constraints.add(constraintLine1);
	    constraints.add(constraintLine2);
	}
	
	public String getID() {
		return id;
	}
	public void setID(String name) {
		this.id = name;
	}
	public List<Integer> getDomain() {
		return domain;
	}
	public void setDomain(List<Integer> domain) {
		this.domain = domain;
	}
	public List<Node> getAdjacence() {
		return adjacence;
	}
	public void addAdjacentNode(Node adjacent) {
		this.adjacence.add(adjacent);
	}
	public void setVisited(Boolean wasVisited){
		this.wasVisited = wasVisited;
	}
	public Boolean wasVisited(){
		return wasVisited;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node getParent() {
		return this.parent;
	}
	public void setPre(Integer pre) {
		this.pre = pre;
	}
	public Integer getPre() {
		return this.pre;
	}
	public void setRoot(){
		this.isRoot = true;
	}
	public Boolean isRoot(){
		return isRoot;
	}

	public List<String> getChildren() {
		return children;
	}

	public void addChild(Node child) {
		this.children.add(child.getID());
	}

	public List<String> getLowerNeighbours() {
		return lowerNeighbours;
	}

	public void addLowerNeighbour(Node lower) {
		this.lowerNeighbours.add(lower.getID());
	}

	public List<String> getUpperNeighbours() {
		return upperNeighbours;
	}

	public void addUpperNeighbour(Node upper) {
		this.upperNeighbours.add(upper.getID());
	}
	
	public List<List<Integer>> getConstraints() {
		return this.constraints;
	}
	
	public Object[] getArgs() {
		Object agentArgs[] = new Object[5];
	    agentArgs[0] = this.children.toArray(new String[]{});
	    agentArgs[1] = this.lowerNeighbours.toArray(new String[]{});
	    agentArgs[2] = this.domain.toArray(new Integer[]{});
	    agentArgs[3] = this.upperNeighbours.toArray(new String[]{});
	    agentArgs[4] = this.constraints;
	    
	    return agentArgs;
	}
}
