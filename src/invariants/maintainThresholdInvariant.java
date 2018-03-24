package invariants;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import models.NodeAgentData;

public class maintainThresholdInvariant{

	public void maintainThresholdInvariantProcedure(Agent myAgent, NodeAgentData data) {
		int lowerBound = data.getLowerBound();
		int upperBound = data.getUpperBound();
		int threshold = data.getThreshold();

		System.out.println("[INV MTI    ] "+myAgent.getLocalName()+" starting maintain threshold invariant");
		
		if(threshold < lowerBound) {
			data.setThreshold(lowerBound);
		}
		if(threshold > upperBound) {
			data.setThreshold(upperBound);
		}
		
	}
}
