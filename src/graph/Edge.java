package graph;

public class Edge {
	private Node first;
	private Node second;
	private String constraint;
	
	public Edge(Node first, Node second, String constraint) {
		this.first = first;
		this.second = second;
		this.constraint = constraint;
	}

	public Node getFirst() {
		return first;
	}

	public void setFirst(Node first) {
		this.first = first;
	}

	public Node getSecond() {
		return second;
	}

	public void setSecond(Node second) {
		this.second = second;
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}
	
	

}
