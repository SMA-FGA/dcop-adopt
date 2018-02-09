package node;

import java.io.IOException;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import messages.AdoptMessage;
import models.NodeAgentData;

public class sendMessageBehaviour extends OneShotBehaviour {
	
	private static final long serialVersionUID = 2659869091649149638L;
	NodeAgentData data = new NodeAgentData();
	AdoptMessage adoptMessage;
	List<AID> receivers;
	
	public sendMessageBehaviour(Agent a, NodeAgentData data, AdoptMessage adoptMessage, List<AID> receivers) {
        super(a);
        this.data = data;
        this.adoptMessage = adoptMessage;
        this.receivers = receivers;
    }
	
	@Override
	public void action() {
					
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		
		for(AID receiver : this.receivers) {
			if(receiver != null) {
				message.addReceiver(receiver);
				System.out.println("[SEND "+adoptMessage.getMessageType()+"     ] "+myAgent.getLocalName()+" send message to: "+receiver.getLocalName()+" content: ["+adoptMessage.toString()+"]");
			}
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