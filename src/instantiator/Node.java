package instantiator;
import java.util.ArrayList;
import java.util.List;

public class Node {
	private String name;
	private List<Integer> domain;
	private List<Node> adjacence;
	private Boolean wasVisited;
	private Node parent;
	private Integer pre;
	private Boolean isRoot;
	
	public Node(String name, List<Integer> domain) {
		this.name = name;
		this.domain = domain;
		this.wasVisited = false;
		this.adjacence = new ArrayList<Node>();
		this.parent = null;
		this.pre = null;
		this.isRoot = false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
