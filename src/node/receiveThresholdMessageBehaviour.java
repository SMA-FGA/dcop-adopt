package node;

import invariants.maintainThresholdInvariantBehaviour;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.ThresholdMessage;
import models.NodeAgentData;

public class receiveThresholdMessageBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = -6895391790742950856L;
	private static final int THRESHOLD_MESSAGE = 2;
	NodeAgentData data;
	
	public receiveThresholdMessageBehaviour(Agent a, NodeAgentData data) {
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
			
			if(adoptMessage.getMessageType() == THRESHOLD_MESSAGE) {
				try {
					ThresholdMessage thresholdMessage = (ThresholdMessage) message.getContentObject();
					
					System.out.println("[REC THRES  ] "+myAgent.getLocalName()+
									   " receive thereshold message: " + thresholdMessage.toString()+
									   " from "+message.getSender().getLocalName());
					
					if(data.isContextCompatible(thresholdMessage.getContext())) {
						data.setThreshold(thresholdMessage.getThreshold());
						myAgent.addBehaviour(new maintainThresholdInvariantBehaviour(myAgent, data));
						myAgent.addBehaviour(new backTrackBehaviour(myAgent, data));
					}
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
			}
			
		}else {
			block();
		}
	}
}