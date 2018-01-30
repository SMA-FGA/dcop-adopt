package node;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import messages.CostMessage;
import messages.ValueMessage;
import models.NodeAgentData;

import java.util.ArrayList;
import java.util.List;

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

		if (data.getThreshold() == data.getUpperBound()) {
		    int updatedCurrentValue = data.minimizeCurrentValueForUpperBound();
            data.setCurrentValue(updatedCurrentValue);
		} else if (data.getLowerBoundForVariable(data.getCurrentValue()) > data.getThreshold()) {
		    int updatedCurrentValue = data.minimizeCurrentValueForLowerBound();
		    data.setCurrentValue(updatedCurrentValue);
        }
		// send value messages to children
		myAgent.addBehaviour(new sendMessageBehaviour(myAgent, data, new ValueMessage(80), data.getChildren()));

		if (data.getThreshold() == data.getUpperBound()) {
		    // TODO: add termination logic.
        }

        CostMessage message = new CostMessage(data.getUpperBound(), data.getLowerBound(), data.getCurrentContext());
        List<AID> receiverContainer = new ArrayList<>();
        receiverContainer.add(data.getParent());

        myAgent.addBehaviour(new sendMessageBehaviour(myAgent, data, message, receiverContainer));
	}
}