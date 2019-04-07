package graph;

import java.util.ArrayList;
import java.util.List;

public class ConstraintsList {
	private List<Constraint> constraints;
	
	public ConstraintsList() {
		this.constraints = new ArrayList<Constraint>();
	}

	public List<Constraint> getConstraits() {
		return constraints;
	}

	public void setConstraits(List<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	public void addConstraint(Constraint constraint) {
		this.constraints.add(constraint);
	}
	
	public Constraint getConstraintByID(String id) {
		//to do: use hash instead list
		for (Constraint constraint : constraints) {
			if(constraint.getID().equals(id)) {
				return constraint;
			}
		}
		return null;
	}
}
