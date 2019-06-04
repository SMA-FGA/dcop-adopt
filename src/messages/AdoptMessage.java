package messages;

import jade.util.leap.Serializable;

public abstract class AdoptMessage implements Serializable, MessageTypes{
	public static final long serialVersionUID = 247808931907196589L;
			
	public abstract Integer getMessageType();
	public abstract String toString();
}
