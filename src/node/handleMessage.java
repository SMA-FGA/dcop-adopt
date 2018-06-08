package node;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import models.NodeAgentData;

public interface handleMessage {
	
	public void handle(Agent myAgent, NodeAgentData data, ACLMessage message);
}
