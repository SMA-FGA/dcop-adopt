package messages;

import jade.util.leap.Serializable;

public class ValueMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Integer value;
	String sender;

	public ValueMessage(String sender, Integer value) {
		super();
		this.value = value;
		this.sender = sender;
	}
	
	@Override
	public String toString() {
		return "xi: " + this.sender + " value: " + this.value;
	}

	@Override
	public Integer getMessageType() {
		return VALUE_MESSAGE;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getSender() {
		return this.sender;
	}
}