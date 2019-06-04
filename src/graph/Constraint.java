package graph;

import java.util.List;

public class Constraint {
	private String id;
	private Integer[][] constraint;
	
	public Constraint(String id, Integer[][] constraint) {
		this.id = id;
		this.constraint = constraint;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public Integer[][] getConstraint() {
		return constraint;
	}

	public void setConstraint(Integer[][] constraint) {
		this.constraint = constraint;
	}
}
