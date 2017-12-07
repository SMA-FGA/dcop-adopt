package messages;

import jade.util.leap.Serializable;

public class TerminateMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Integer value;

	public TerminateMessage() {
		super();
	}
	
	@Override
	public String toString() {
		return "TERMINATE";
	}

	@Override
	public Integer getMessageType() {
		return super.TERMINATE_MESSAGE;
	}
}