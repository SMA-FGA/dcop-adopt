package messages;

import java.util.Map;

import jade.util.leap.Serializable;

public class CostMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Map<String, Integer> context;
	Integer lowerBound;
	Integer upperBound;
	String sender;

	public CostMessage(Map<String, Integer> context, String sender, Integer lowerBound, Integer upperBound) {
		super();
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.context = context;
		this.sender = sender;
	}
	
	@Override
	public String toString() {
		return "context: " + this.context + " xk: " + this.sender + " lb: " + this.lowerBound + " ub: "+this.upperBound;
	}

	@Override
	public Integer getMessageType() {
		return COST_MESSAGE;
	}
	
	public Map<String, Integer> getContext() {
		return this.context;
	}
	
	public String getSender() {
		return this.sender;
	}

	public Integer getLowerBound() {
		return this.lowerBound;
	}

	public Integer getUpperBound() {
		return this.upperBound;
	}
}