package messages;

import jade.util.leap.Serializable;

public abstract class AdoptMessage implements Serializable{
	public static final long serialVersionUID = 247808931907196589L;
	public final int VALUE_MESSAGE = 0;
	public final int COST_MESSAGE = 1;
	public final int THRESHOLD_MESSAGE = 2;
	public final int TERMINATE_MESSAGE = 3;
			
	public abstract String toString();
	public abstract Integer getMessageType();
}
