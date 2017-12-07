package messages;

import jade.util.leap.Serializable;

public class ThresholdMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Integer threshold;

	public ThresholdMessage(Integer threshold) {
		super();
		this.threshold = threshold;
	}
	
	@Override
	public String toString() {
		return this.threshold.toString();
	}

	@Override
	public Integer getMessageType() {
		return super.THRESHOLD_MESSAGE;
	}
}