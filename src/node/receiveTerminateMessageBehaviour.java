package node;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import models.AdoptMessage;
import models.TerminateMessage;

public class receiveTerminateMessageBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = -6895391790742950856L;
	
	public receiveTerminateMessageBehaviour(Agent a) {
        super(a);
    }

	@Override
	public void action() {
		ACLMessage message = myAgent.receive();
		
		if(message != null) {
			AdoptMessage adoptMessage = null;
			
			try {
				adoptMessage = (AdoptMessage) message.getContentObject();
			} catch (UnreadableException e1) {
				e1.printStackTrace();
			}
			
			if(adoptMessage.getMessageType() == 3) {
				TerminateMessage terminate = null;
				try {
					terminate = (TerminateMessage) message.getContentObject();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
				System.out.println("[REC TERMI  ] "+myAgent.getLocalName()+" receive terminate message: " + terminate.toString()+" from "+message.getSender().getLocalName());
			}
			
		}else {
			block();
		}
	}
}