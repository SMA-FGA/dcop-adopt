package node;

import java.util.HashMap;
import java.util.Map;

import invariants.maintainChildThresholdInvariant;
import invariants.maintainInvariant;
import invariants.maintainThresholdInvariant;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.CostMessage;
import models.NodeAgentData;


public class handleCostMessage implements handleMessage{

	public void handle (Agent myAgent, NodeAgentData data, ACLMessage message) {

		try {
			CostMessage costMessage = (CostMessage) message.getContentObject();

			System.out.println("[REC COST   ] "+myAgent.getLocalName()+
					" receive cost message: " + costMessage.toString()+
					" from "+message.getSender().getLocalName());

			Map<String, Integer> contextInMessage = costMessage.getContext();
			Integer value = data.getCurrentValue();

			if(contextInMessage.containsKey(myAgent.getLocalName())) {
				value = contextInMessage.get(myAgent.getLocalName());
				data.setCurrentValue(value);
			}

			contextInMessage.remove(myAgent.getLocalName());

			if(!data.hasReceivedTerminate()) {
				Map<String, Integer> contextWithNotNeighbours = data.getCurrentContext();

				for (Map.Entry<String, Integer> pair : contextInMessage.entrySet()) {
					if (!data.isMyNeighbour(pair.getKey())) {
						contextWithNotNeighbours.put(pair.getKey(), pair.getValue());
					} else {
						//do nothing
					}
				}
				data.setCurrentContext(contextWithNotNeighbours);

				for (int d = 0; d < data.getDomain().size(); d++) {
					for (int c = 0; c < data.getChildren().size(); c++) {
						String child = data.getChildren().get(c).getLocalName();

						if (!data.isContextCompatible(data.getChildrenContexts().get(child).get(d))) {
							data.setChildLowerBound(d, child, 0);
							data.setChildThreshold(d, child, 0);
							data.setChildUpperBound(d, child, Integer.MAX_VALUE);
							data.setChildContext(d, child, new HashMap<String, Integer>());
						} else {
							//do nothing
						}
					}
				}
			} else {
				//do nothing
			}

			if (data.isContextCompatible(contextInMessage)) {
				String sender = costMessage.getSender();

				data.setChildLowerBound(value, sender, costMessage.getLowerBound());
				data.setChildUpperBound(value,  sender, costMessage.getUpperBound());
				data.setChildContext(value,  sender, costMessage.getContext());

				maintainInvariant maintainInvariant;
				
				maintainInvariant = new maintainChildThresholdInvariant();
				maintainInvariant.maintain(myAgent, data);
				
				maintainInvariant = new maintainThresholdInvariant();
				maintainInvariant.maintain(myAgent, data);
			}

	        backtrack backtrack = new backtrack();
	        backtrack.backtrackProcedure(myAgent, data);

		} catch (UnreadableException ex) {
			ex.printStackTrace();
		}
	}
}