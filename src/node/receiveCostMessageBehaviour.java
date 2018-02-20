package node;

import invariants.maintainChildThresholdInvariantBehaviour;
import invariants.maintainThresholdInvariantBehaviour;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.CostMessage;
import messages.MessageTypes;
import models.NodeAgentData;

import java.util.Map;


public class receiveCostMessageBehaviour extends CyclicBehaviour  implements MessageTypes{
	private static final long serialVersionUID = -6895391790742950856L;
	NodeAgentData data;
	
	public receiveCostMessageBehaviour(Agent a, NodeAgentData data) {
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
			
			if(adoptMessage.getMessageType() == COST_MESSAGE) {
				CostMessage cost = null;
				try {
					cost = (CostMessage) message.getContentObject();

//					Map<String, Integer> contextInMessage = cost.getContext();
//					int valueInMessage = contextInMessage.get(myAgent.getLocalName());
//					data.setCurrentValue(valueInMessage);
//
//					contextInMessage.remove(myAgent.getLocalName());
//
//					if (!data.hasReceivedTerminate()) {
//						// TODO
//					}
//
//					if (data.isContextCompatible(contextInMessage)) {
//						int currentValue = data.getCurrentValue();
//						String xk = cost.getXk();
//
//						// TODO: update bounds and context
//
//						myAgent.addBehaviour(new maintainChildThresholdInvariantBehaviour(myAgent, data));
//						myAgent.addBehaviour(new maintainThresholdInvariantBehaviour(myAgent, data));
//					}

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