package node;

import invariants.maintainThresholdInvariantBehaviour;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.ValueMessage;
import models.NodeAgentData;

public class receiveValueMessageBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = -6895391790742950856L;
	private static final int VALUE_MESSAGE = 0;
	NodeAgentData data;
	
	public receiveValueMessageBehaviour(Agent a, NodeAgentData data) {
        super(a);
        this.data = data;
    }

	@Override
	public void action() {
		ACLMessage message = myAgent.receive() ;
		
		if(message != null) {
			AdoptMessage adoptMessage = null;
			
			try {
				adoptMessage = (AdoptMessage) message.getContentObject();
			} catch (UnreadableException e1) {
				e1.printStackTrace();
			}
			
			if(adoptMessage.getMessageType() == VALUE_MESSAGE) {
				ValueMessage value = null;
				try {
					value = (ValueMessage) message.getContentObject();
					myAgent.addBehaviour(new maintainThresholdInvariantBehaviour(myAgent, data));
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
				System.out.println("[REC VALUE  ] "+myAgent.getLocalName()+" receive value message: " + value.toString()+" from "+message.getSender().getLocalName());
			}
			
		}else {
			block();
		}
	}
}