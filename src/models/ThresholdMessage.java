package models;

import jade.util.leap.Serializable;

public class ThresholdMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	private static final int THRESHOLD_MESSAGE = 2;
	Integer threshold;

	public ThresholdMessage(Integer threshold) {
		super();
		this.threshold = threshold;
	}
	
	public String toString() {
		return this.threshold.toString();
	}

	@Override
	public Integer getMessageType() {
		return THRESHOLD_MESSAGE;
	}
}