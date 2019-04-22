package graph;

import java.util.HashMap;
import java.util.Map;

public class ConstraintsList {
	private Map<String, Constraint> constraints;
	
	public ConstraintsList() {
		this.constraints = new HashMap<String, Constraint>();
	}
	
	public void addConstraint(Constraint constraint) {
		this.constraints.put(constraint.getID(), constraint);
	}
	
	public Constraint getConstraintByID(String id) {
		return this.constraints.get(id);
	}
}
