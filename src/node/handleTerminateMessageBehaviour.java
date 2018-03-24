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

public class handleTerminateMessageBehaviour extends OneShotBehaviour implements MessageTypes{
	
	private static final long serialVersionUID = -6895391790742950856L;
	private NodeAgentData data;
	private ACLMessage message;
	
	public handleTerminateMessageBehaviour(Agent a, NodeAgentData data, ACLMessage message) {
        super(a);
        this.data = data;
        this.message = message;
    }

	@Override
	public void action() {
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