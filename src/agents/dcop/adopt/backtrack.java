package agents.dcop.adopt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import agents.solution.DCOPSolutionAgent;
import jade.core.AID;
import jade.core.Agent;
import messages.CostMessage;
import messages.TerminateMessage;
import messages.ValueMessage;
import models.DCOPAgentData;

public class backtrack{
	
	public void backtrackProcedure(Agent myAgent, DCOPAgentData data) {
		System.out.println("[BACK TRACK ] "+myAgent.getLocalName()+
						   " starting backTrack procedure");

		if (data.getThreshold() == data.getUpperBound()) {
		    int updatedCurrentValue = data.minimizeCurrentValueForUpperBound();
		    System.out.println("minimize current value for Upper Bound"+updatedCurrentValue);
            data.setCurrentValue(updatedCurrentValue);
		} else if (data.getLowerBoundForVariable(data.getCurrentValue()) > data.getThreshold()) {
		    int updatedCurrentValue = data.minimizeCurrentValueForLowerBound();
		    System.out.println("minimize current value for Lower Bound"+updatedCurrentValue);
		    data.setCurrentValue(updatedCurrentValue);
        }
		
		// send value messages to lower neighbours
		myAgent.addBehaviour(new sendMessageBehaviour(myAgent,
													  data,
													  new ValueMessage(myAgent.getLocalName(),data.getCurrentValue()),
													  data.getLowerNeighbours()));
		// Adjust thresholds
		maintainInvariant maintainInvariant = new maintainAllocationInvariant();
		maintainInvariant.maintain(myAgent, data);

		System.out.println("t: "+data.getThreshold()+"ub: "+data.getUpperBound());
		
		if (data.getThreshold() == data.getUpperBound()) {
			boolean isRoot = (data.getParent() == null);
			
			System.out.println("terminate status: "+data.hasReceivedTerminate());
			
		    if (data.hasReceivedTerminate() || isRoot) {
		    	Map<String, Integer> contextUnionChoice = data.getCurrentContext();
		    	contextUnionChoice.put(myAgent.getLocalName(), data.getCurrentValue());
		    	
//		    	myAgent.addBehaviour(new TickerBehaviour(myAgent, 10000) {
		    	
				myAgent.addBehaviour(new sendMessageBehaviour(myAgent,
																data,
																new TerminateMessage(contextUnionChoice),
																data.getChildren()));

				//ends agent receive messages
				System.out.println(myAgent.getLocalName()+" does not receive message");
				DCOPSolutionAgent dcopSolution = DCOPSolutionAgent.getInstance();
				dcopSolution.addAgentSolution(myAgent.getLocalName(), data.getCurrentValue());	
				data.setWasKilled(true);    	
			}
        }

		if(!data.getCurrentContext().isEmpty()) {
			CostMessage message = new CostMessage(data.getCurrentContext(), myAgent.getLocalName(), data.getLowerBound(), data.getUpperBound());
	        List<AID> receiverContainer = new ArrayList<>();
	        receiverContainer.add(data.getParent());

	        myAgent.addBehaviour(new sendMessageBehaviour(myAgent,
	        											  data,
	        											  message,
	        											  receiverContainer));
		}
        
	}
}