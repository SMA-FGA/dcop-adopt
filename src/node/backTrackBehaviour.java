package node;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
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
		//simple backTrack implementation
		System.out.println("[BACK TRACK ] "+myAgent.getLocalName()+" starting backTrack procedure");
		myAgent.addBehaviour(new sendValueMessageBehaviour(myAgent, data));		
	}
}