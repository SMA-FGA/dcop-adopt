package node;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import messages.ValueMessage;
import models.NodeAgentData;

public class backTrackBehaviour extends OneShotBehaviour {
	
	static final long serialVersionUID = 6074892213023310464L;
	NodeAgentData data = new NodeAgentData();
	
	public backTrackBehaviour(Agent a, NodeAgentData data) {
        super(a);
        this.data = data;
    }

	@Override
	public void action() {
		System.out.println("[BACK TRACK ] "+myAgent.getLocalName()+" starting backTrack procedure");
		
		myAgent.addBehaviour(new sendMessageBehaviour(myAgent, data, new ValueMessage(80), data.getChildren()));
		
		// Example to send other types of adopt messages
		//myAgent.addBehaviour(new sendMessageBehaviour(myAgent, data, new CostMessage(9, 6, data.getCurrentContext()), List<AID> receivers));
		//myAgent.addBehaviour(new sendMessageBehaviour(myAgent, data, new ThresholdMessage(89), List<AID> receivers));
		//myAgent.addBehaviour(new sendMessageBehaviour(myAgent, data, new TerminateMessage()), List<AID> receivers);
	}
}