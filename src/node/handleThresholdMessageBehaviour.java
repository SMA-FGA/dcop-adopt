package node;

import invariants.maintainThresholdInvariantBehaviour;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.MessageTypes;
import messages.ThresholdMessage;
import models.NodeAgentData;

public class handleThresholdMessageBehaviour extends OneShotBehaviour implements MessageTypes{
	
	private static final long serialVersionUID = -6895391790742950856L;
	private NodeAgentData data;
	private ACLMessage message;
	
	public handleThresholdMessageBehaviour(Agent a, NodeAgentData data, ACLMessage message) {
        super(a);
        this.data = data;
        this.message = message;
    }

	@Override
	public void action() {

		try {
			ThresholdMessage thresholdMessage = (ThresholdMessage) message.getContentObject();

			System.out.println("[REC THRES  ] "+myAgent.getLocalName()+
					" receive thereshold message: " + thresholdMessage.toString()+
					" from "+message.getSender().getLocalName());

			if (data.isContextCompatible(thresholdMessage.getContext())) {
				data.setThreshold(thresholdMessage.getThreshold());
				myAgent.addBehaviour(new maintainThresholdInvariantBehaviour(myAgent, data));
				myAgent.addBehaviour(new backTrackBehaviour(myAgent, data));
			}
		} catch (UnreadableException ex) {
			ex.printStackTrace();
		}
	}
}