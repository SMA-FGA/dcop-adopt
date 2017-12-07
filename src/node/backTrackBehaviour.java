package node;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import models.NodeAgentData;
import models.ValueMessage;

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
		myAgent.addBehaviour(new sendMessageBehaviour(myAgent, data, new ValueMessage(5)));
	}
}