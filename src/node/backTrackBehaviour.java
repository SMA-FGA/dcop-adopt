package node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import invariants.maintainAllocationInvariantBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import messages.CostMessage;
import messages.TerminateMessage;
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
		System.out.println("[BACK TRACK ] "+myAgent.getLocalName()+
						   " starting backTrack procedure");

		if (data.getThreshold() == data.getUpperBound()) {
		    int updatedCurrentValue = data.minimizeCurrentValueForUpperBound();
            data.setCurrentValue(updatedCurrentValue);
		} else if (data.getLowerBoundForVariable(data.getCurrentValue()) > data.getThreshold()) {
		    int updatedCurrentValue = data.minimizeCurrentValueForLowerBound();
		    data.setCurrentValue(updatedCurrentValue);
        }
		
		// send value messages to lower neighbours
		myAgent.addBehaviour(new sendMessageBehaviour(myAgent, 
													  data,
													  new ValueMessage(myAgent.getLocalName(),data.getCurrentValue()),
													  data.getLowerNeighbours()));
		// Adjust thresholds
		myAgent.addBehaviour(new maintainAllocationInvariantBehaviour(myAgent, data));

		if (data.getThreshold() == data.getUpperBound()) {
			boolean isRoot = (data.getParent() == null);
			
		    if (data.hasReceivedTerminate() || isRoot) {
		    	Map<String, Integer> contextUnionChoice = data.getCurrentContext();
		    	contextUnionChoice.put(myAgent.getLocalName(), data.getCurrentValue());
		    	
		    	myAgent.addBehaviour(new sendMessageBehaviour(myAgent,
		    												  data,
		    												  new TerminateMessage(contextUnionChoice),
		    												  data.getChildren()));
		    	myAgent.doDelete(); // ends execution for this agent
			}
        }

        CostMessage message = new CostMessage(data.getCurrentContext(), myAgent.getLocalName(), data.getLowerBound(), data.getUpperBound());
        List<AID> receiverContainer = new ArrayList<>();
        receiverContainer.add(data.getParent());

        myAgent.addBehaviour(new sendMessageBehaviour(myAgent,
        											  data,
        											  message,
        											  receiverContainer));
	}
}