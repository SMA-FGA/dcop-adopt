package node;

import invariants.maintainThresholdInvariant;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.ThresholdMessage;
import models.NodeAgentData;

public class handleThresholdMessage implements handleMessage{
	
	public void handle(Agent myAgent, NodeAgentData data, ACLMessage message) {

		try {
			ThresholdMessage thresholdMessage = (ThresholdMessage) message.getContentObject();

			System.out.println("[REC THRES  ] "+myAgent.getLocalName()+
					" receive thereshold message: " + thresholdMessage.toString()+
					" from "+message.getSender().getLocalName());

			if (data.isContextCompatible(thresholdMessage.getContext())) {
				data.setThreshold(thresholdMessage.getThreshold());
				
				maintainThresholdInvariant maintainThresholdInvariant = new maintainThresholdInvariant();
				maintainThresholdInvariant.maintainThresholdInvariantProcedure(myAgent, data);
				
		        backtrack backtrack = new backtrack();
		        backtrack.backtrackProcedure(myAgent, data);
			}
		} catch (UnreadableException ex) {
			ex.printStackTrace();
		}
	}
}