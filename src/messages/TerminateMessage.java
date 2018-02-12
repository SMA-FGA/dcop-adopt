package messages;

import java.util.Map;

import jade.util.leap.Serializable;

public class TerminateMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Map<String, Integer> context;

	public TerminateMessage(Map<String, Integer> context) {
		super();
		this.context = context;
	}
	
	@Override
	public String toString() {
		return "context: "+this.context;
	}

	@Override
	public Integer getMessageType() {
		return super.TERMINATE_MESSAGE;
	}

	public Map<String, Integer> getContext() {
		return this.context;
	}
}