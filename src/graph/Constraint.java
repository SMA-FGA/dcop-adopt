package graph;

import java.util.List;

public class Constraint {
	private String id;
	private List<List<Integer>> constraint;
	
	public Constraint(String id, List<List<Integer>> constraint) {
		this.id = id;
		this.constraint = constraint;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public List<List<Integer>> getConstraint() {
		return constraint;
	}

	public void setConstraint(List<List<Integer>> constraint) {
		this.constraint = constraint;
	}
}
