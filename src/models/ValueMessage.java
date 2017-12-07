package models;

import jade.util.leap.Serializable;

public class ValueMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Integer value;

	public ValueMessage(Integer value) {
		super();
		this.value = value;
	}
	
	public String toString() {
		return this.value.toString();
	}

	@Override
	public Integer getMessageType() {
		// TODO Auto-generated method stub
		return 0;
	}
}