package models;

import java.util.Map;

import jade.util.leap.Serializable;

public class CostMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Integer upperBound;
	Integer lowerBound;
	Map<String, Integer> currentContext;

	public CostMessage(Integer upperBound, Integer lowerBound, Map<String, Integer> currentContext) {
		super();
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.currentContext = currentContext;
	}
	
	public String toString() {
		return "ub: "+this.upperBound+" lb: "+this.lowerBound+" context: "+this.currentContext;
	}

	@Override
	public Integer getMessageType() {
		return 1;
	}
}