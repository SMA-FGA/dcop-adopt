package messages;

import jade.util.leap.Serializable;

public class ValueMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Integer value;
	String xi;

	public ValueMessage(String xi, Integer value) {
		super();
		this.value = value;
		this.xi = xi;
	}
	
	@Override
	public String toString() {
		return "xi: " + this.xi + " value: " + this.value;
	}

	@Override
	public Integer getMessageType() {
		return super.VALUE_MESSAGE;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getXi() {
		return this.xi;
	}
}