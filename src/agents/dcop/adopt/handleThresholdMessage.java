package agents.dcop.adopt;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.ThresholdMessage;
import models.DCOPAgentData;

public class handleThresholdMessage implements handleMessage{
	
	public void handle(Agent myAgent, DCOPAgentData data, ACLMessage message) {

		try {
			ThresholdMessage thresholdMessage = (ThresholdMessage) message.getContentObject();

			System.out.println("[REC THRES  ] "+myAgent.getLocalName()+
					" receive thereshold message: " + thresholdMessage.toString()+
					" from "+message.getSender().getLocalName());

			if (data.isContextCompatible(thresholdMessage.getContext())) {
				data.setThreshold(thresholdMessage.getThreshold());
				
				maintainInvariant maintainInvariant = new maintainThresholdInvariant();
				maintainInvariant.maintain(myAgent, data);
				
		        backtrack backtrack = new backtrack();
		        backtrack.backtrackProcedure(myAgent, data);
			}
		} catch (UnreadableException ex) {
			ex.printStackTrace();
		}
	}
}