package messages;

import java.util.Map;

import jade.util.leap.Serializable;

public class CostMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Map<String, Integer> context;
	Integer lowerBound;
	Integer upperBound;
	String xk;

	public CostMessage(Map<String, Integer> context, String xk, Integer lowerBound, Integer upperBound) {
		super();
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.context = context;
		this.xk = xk;
	}
	
	@Override
	public String toString() {
		return "context: " + this.context + " xk: " + this.xk + " lb: " + this.lowerBound + " ub: "+this.upperBound;
	}

	@Override
	public Integer getMessageType() {
		return super.COST_MESSAGE;
	}
}