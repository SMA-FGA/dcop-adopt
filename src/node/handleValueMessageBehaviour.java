package node;

import java.util.HashMap;
import java.util.Map;

import invariants.maintainThresholdInvariant;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.MessageTypes;
import messages.ValueMessage;
import models.NodeAgentData;

public class handleValueMessageBehaviour extends OneShotBehaviour implements MessageTypes{
	
	private static final long serialVersionUID = -6895391790742950856L;
	private NodeAgentData data;
	private ACLMessage message;
	
	public handleValueMessageBehaviour(Agent a, NodeAgentData data, ACLMessage message) {
        super(a);
        this.data = data;
        this.message = message;
    }

	@Override
	public void action() {

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

				maintainThresholdInvariant maintainThresholdInvariant = new maintainThresholdInvariant();
				maintainThresholdInvariant.maintainThresholdInvariantProcedure(myAgent, data);
				
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