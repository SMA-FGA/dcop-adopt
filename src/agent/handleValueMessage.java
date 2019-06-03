package agent;

import java.util.HashMap;
import java.util.Map;

import invariants.maintainInvariant;
import invariants.maintainThresholdInvariant;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.ValueMessage;
import models.DCOPAgentData;

public class handleValueMessage implements handleMessage{
	
	public void handle (Agent myAgent, DCOPAgentData data, ACLMessage message) {

		try {
			ValueMessage valueMessage = (ValueMessage) message.getContentObject();
			System.out.println("[REC VALUE  ] "+myAgent.getLocalName()+
					" receive value message: " + valueMessage.toString()+
					" from "+message.getSender().getLocalName());

			if (!data.hasReceivedTerminate()) {
				// Join choice received from value message to currentContext
				Map<String, Integer> contextUnionValue = data.getCurrentContext();
				String xi = valueMessage.getSender();
				int value = valueMessage.getValue();
				contextUnionValue.put(xi, value);
				data.setCurrentContext(contextUnionValue);

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

				maintainInvariant maintainInvariant = new maintainThresholdInvariant();
				maintainInvariant.maintain(myAgent, data);
				
		        backtrack backtrack = new backtrack();
		        backtrack.backtrackProcedure(myAgent, data);
			} else {
				//do nothing
			}
		} catch (UnreadableException ex) {
			ex.printStackTrace();
		}
	}
}