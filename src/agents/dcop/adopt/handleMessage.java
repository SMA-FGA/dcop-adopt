package agents.dcop.adopt;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import models.DCOPAgentData;

public interface handleMessage {
	
	public void handle(Agent myAgent, DCOPAgentData data, ACLMessage message);
}
