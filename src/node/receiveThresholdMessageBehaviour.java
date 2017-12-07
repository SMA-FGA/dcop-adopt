package node;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.ThresholdMessage;

public class receiveThresholdMessageBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = -6895391790742950856L;
	private static final int THRESHOLD_MESSAGE = 2;
	
	public receiveThresholdMessageBehaviour(Agent a) {
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
				e1.printStackTrace();
			}
			
			if(adoptMessage.getMessageType() == THRESHOLD_MESSAGE) {
				ThresholdMessage threshold = null;
				try {
					threshold = (ThresholdMessage) message.getContentObject();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
				System.out.println("[REC THRES  ] "+myAgent.getLocalName()+" receive thereshold message: " + threshold.toString()+" from "+message.getSender().getLocalName());
			}
			
		}else {
			block();
		}
	}
}