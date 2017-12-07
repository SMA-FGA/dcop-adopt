package node;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import models.AdoptMessage;
import models.ValueMessage;

public class receiveValueMessageBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = -6895391790742950856L;
	
	public receiveValueMessageBehaviour(Agent a) {
        super(a);
    }

	@Override
	public void action() {
		ACLMessage message = myAgent.receive() ;
		
		if(message != null) {
			ValueMessage value = null;
			try {
				value = (ValueMessage) message.getContentObject();
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
			System.out.println("[REC VALUE  ] "+myAgent.getLocalName()+" receive value message: " + value.toString());
		}else {
			block();
		}
	}
}