package models;

import jade.util.leap.Serializable;

public abstract class AdoptMessage implements Serializable{
	private static final long serialVersionUID = 247808931907196589L;

	public abstract String toString();
	public abstract Integer getMessageType();
}
