package graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Node {
	private String id;
	private Domain domain;
	private List<Node> adjacence;
	private Boolean wasVisited;
	private Node parent;
	private Integer pre;
	private Boolean isRoot;
	private List<String> children;
    private List<String> lowerNeighbours;
    private List<String> upperNeighbours;
    //private List<List<Integer>> constraints;
    private Map<String, Constraint> constraints;
	
	public Node(String id, Domain domain) {
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
		this.constraints = new HashMap<String, Constraint>();
	}
	
	public String getID() {
		return id;
	}
	public void setID(String name) {
		this.id = name;
	}
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
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
	
	public Map<String, Constraint> getConstraints() {
		return this.constraints;
	}
	
	public void addConstraint(String agentName, Constraint agentConstraint) {
		this.constraints.put(agentName, agentConstraint);
	}
	
	public Object[] getArgs() {
		/*
         * For some unfortunate reason, nothing happens when passing the
         * ArrayList as an argument in the agent's creation. So we convert it
         * to a regular array, then convert it back once inside the DcopAgent.
         */
		Object agentArgs[] = new Object[6];
	    agentArgs[0] = this.children.toArray(new String[]{});
	    agentArgs[1] = this.lowerNeighbours.toArray(new String[]{});
	    agentArgs[2] = this.domain.getDomainArray().toArray(new Integer[]{});
	    agentArgs[3] = this.upperNeighbours.toArray(new String[]{});
	    
	    List<String> agentsWithConstraints = new ArrayList<>(); 
	    List<Integer[][]> constraintsList = new Vector<>();
	    for (Map.Entry<String, Constraint> constraintMap: this.constraints.entrySet()) {
	    	constraintsList.add(constraintMap.getValue().getConstraint());
	    	agentsWithConstraints.add(constraintMap.getKey());
	    }
	    
	    agentArgs[4] = constraintsList;
	    agentArgs[5] = agentsWithConstraints.toArray(new String[]{});
	    
	    return agentArgs;
	}
}
