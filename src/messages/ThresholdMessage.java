package messages;

import java.util.Map;

import jade.util.leap.Serializable;

public class ThresholdMessage extends AdoptMessage implements Serializable{
	
	private static final long serialVersionUID = 2130899758727979611L;
	Integer threshold;
	Map<String, Integer> context;

	public ThresholdMessage(Integer threshold, Map<String, Integer> context) {
		super();
		this.threshold = threshold;
		this.context = context;
	}
	
	@Override
	public String toString() {
		return "t: "+this.threshold.toString() + " context: " + this.context;
	}

	@Override
	public Integer getMessageType() {
		return THRESHOLD_MESSAGE;
	}
	
	public Integer getThreshold() {
		return this.threshold;
	}

	public Map<String, Integer> getContext() {
		return this.context;
	}
}