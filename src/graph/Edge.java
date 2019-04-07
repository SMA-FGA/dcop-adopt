package graph;

public class Edge {
	private Node first;
	private Node second;
	private Constraint constraint;
	
	public Edge(Node first, Node second, Constraint constraint) {
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

	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}
}
