package node;

import java.io.IOException;
import java.io.Serializable;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import models.AdoptMessage;
import models.NodeAgentData;
import models.ValueMessage;

public class sendMessageBehaviour extends OneShotBehaviour {
	
	private static final long serialVersionUID = 2659869091649149638L;
	NodeAgentData data = new NodeAgentData();
	AdoptMessage adoptMessage;
	
	public sendMessageBehaviour(Agent a, NodeAgentData data, AdoptMessage adoptMessage) {
        super(a);
        this.data = data;
        this.adoptMessage = adoptMessage;
    }
	
	@Override
	public void action() {
					
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		for(AID lowerNeighbour : data.getLowerNeighbours()) {
			message.addReceiver(lowerNeighbour);
			System.out.println("[SEND "+adoptMessage.getMessageType()+"     ] "+myAgent.getLocalName()+" send message to: "+lowerNeighbour.getLocalName());
		}
		try {
			message.setContentObject(adoptMessage);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			myAgent.send(message);
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
}