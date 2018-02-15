package node;

import java.util.HashMap;
import java.util.Map;

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
				try {
					ValueMessage valueMessage = (ValueMessage) message.getContentObject();
					
					System.out.println("[REC VALUE  ] "+myAgent.getLocalName()+
									   " receive value message: " + valueMessage.toString()+
									   " from "+message.getSender().getLocalName());
					
					if(!data.hasReceivedTerminate()) {
						// Join choice received from value message to currentContext
						Map<String, Integer> contextUnionValue = data.getCurrentContext();
						String xi = valueMessage.getXi();
						int value = valueMessage.getValue();
				    	contextUnionValue.put(xi, value);
				    	data.setCurrentContext(contextUnionValue);
				    					    	
				    	for (int d = 0; d < data.getDomain().size(); d++) {
				    		for(int c = 0; c < data.getChildren().size(); c++) {
				    			if(!data.isContextCompatible(data.getChildrenContexts().get(d).get(c))) {
				    				data.setChildLowerBound(d, c, 0);
					    			data.setChildThreshold(d, c, 0);
					    			data.setChildUpperBound(d, c, 0);
					    			data.setChildContext(d, c, new HashMap<String, Integer>());
				    			}else {
				    				//do nothing
				    			}
				            }
				    	}

						myAgent.addBehaviour(new maintainThresholdInvariantBehaviour(myAgent, data));
						myAgent.addBehaviour(new backTrackBehaviour(myAgent, data));
					}else {
						//do nothing
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