package node;

import java.util.HashMap;
import java.util.Map;

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
				try {
					CostMessage cost = (CostMessage) message.getContentObject();
					
					System.out.println("[REC COST   ] "+myAgent.getLocalName()+
							   " receive cost message: " + cost.toString()+
							   " from "+message.getSender().getLocalName());

					Map<String, Integer> contextInMessage = cost.getContext();
					Integer value = data.getCurrentValue();
					
					if(contextInMessage.containsKey(myAgent.getLocalName())) {
						value = contextInMessage.get(myAgent.getLocalName());
						data.setCurrentValue(value);
					}

					contextInMessage.remove(myAgent.getLocalName());

					if(!data.hasReceivedTerminate()) {
						Map<String, Integer> contextWithNotNeighbours = data.getCurrentContext();
						
						for (Map.Entry<String, Integer> pair : contextInMessage.entrySet()) {
							if(!data.isMyNeighbour(pair.getKey())) {
								contextWithNotNeighbours.put(pair.getKey(), pair.getValue());
							}else {
								//do nothing
							}
				        }
						data.setCurrentContext(contextWithNotNeighbours);
						
						for (int d = 0; d < data.getDomain().size(); d++) {
				    		for(int c = 0; c < data.getChildren().size(); c++) {
				    			String child = data.getChildren().get(c).getLocalName();
				    			
				    			if(!data.isContextCompatible(data.getChildrenContexts().get(child).get(d))) {
				    				data.setChildLowerBound(d, child, 0);
					    			data.setChildThreshold(d, child, 0);
					    			data.setChildUpperBound(d, child, Integer.MAX_VALUE);
					    			data.setChildContext(d, child, new HashMap<String, Integer>());
				    			}else {
				    				//do nothing
				    			}
				            }
				    	}
					}else {
						//do nothing
					}
					
					if(data.isContextCompatible(contextInMessage)) {
						String sender = cost.getSender();
						
						data.setChildLowerBound(value, sender, cost.getLowerBound());
						data.setChildUpperBound(value,  sender, cost.getUpperBound());
						data.setChildContext(value,  sender, cost.getContext());
						
						myAgent.addBehaviour(new maintainChildThresholdInvariantBehaviour(myAgent, data));
						myAgent.addBehaviour(new maintainThresholdInvariantBehaviour(myAgent, data));
					}
					myAgent.addBehaviour(new backTrackBehaviour(myAgent, data));
					
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
				
			}
			
		}else {
			block();
		}
	}
}