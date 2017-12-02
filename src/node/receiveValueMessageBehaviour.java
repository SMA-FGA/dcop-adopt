package node;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class receiveValueMessageBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = -6895391790742950856L;
	
	public receiveValueMessageBehaviour(Agent a) {
        super(a);
    }

	@Override
	public void action() {
		ACLMessage message = myAgent.receive() ;
		
		if(message != null) {
			System.out.println("[REC VALUE  ] "+myAgent.getLocalName()+" receive value message: " + message.getContent());
		}else {
			block();
		}
	}
}