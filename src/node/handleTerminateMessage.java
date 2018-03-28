package node;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.MessageTypes;
import messages.TerminateMessage;
import models.NodeAgentData;

public class handleTerminateMessage {

	public void handleTerminateMessageProcedure(Agent myAgent, NodeAgentData data, ACLMessage message) {
		try {
			TerminateMessage terminate = (TerminateMessage) message.getContentObject();

			System.out.println("[REC TERMI  ] "+myAgent.getLocalName()+
					" receive terminate message: " + terminate.toString()+
					" from "+message.getSender().getLocalName());

			data.setReceivedTerminate(true);
			data.setCurrentContext(terminate.getContext());
			
	        backtrack backtrack = new backtrack();
	        backtrack.backtrackProcedure(myAgent, data);
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}
}