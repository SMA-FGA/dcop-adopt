package node;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.CostMessage;

public class receiveCostMessageBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = -6895391790742950856L;
	private static final int COST_MESSAGE = 1;
	
	public receiveCostMessageBehaviour(Agent a) {
        super(a);
    }

	@Override
	public void action() {
		ACLMessage message = myAgent.receive() ;
		
		if(message != null) {
			AdoptMessage adoptMessage = null;
			
			try {
				adoptMessage = (AdoptMessage) message.getContentObject();
			} catch (UnreadableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(adoptMessage.getMessageType() == COST_MESSAGE) {
				CostMessage cost = null;
				try {
					cost = (CostMessage) message.getContentObject();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
				System.out.println("[REC COST   ] "+myAgent.getLocalName()+" receive cost message: " + cost.toString()+" from "+message.getSender().getLocalName());
			}
			
		}else {
			block();
		}
	}
}