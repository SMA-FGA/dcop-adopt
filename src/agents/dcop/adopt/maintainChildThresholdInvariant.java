package agents.dcop.adopt;

import java.util.List;
import java.util.Map;

import jade.core.Agent;
import models.DCOPAgentData;

public class maintainChildThresholdInvariant implements maintainInvariant{

	public void maintain(Agent myAgent, DCOPAgentData data) {
		System.out.println("[INV MCTI   ] "+myAgent.getLocalName()+" starting maintain child threshold invariant");

		for (Map.Entry<String, List<Integer>> child : data.getChildrenThresholds().entrySet()) {
			for (int i = 0; i < data.getDomain().size(); i++) {
				int lowerBound = data.getChildrenLowerBounds().get(child.getKey()).get(i);
				int currentThreshold = child.getValue().get(i);

				while (lowerBound > currentThreshold) {
					currentThreshold += 1;
					data.setChildThreshold(i, child.getKey(), currentThreshold);
				}
			}
		}

		for (Map.Entry<String, List<Integer>> child : data.getChildrenThresholds().entrySet()) {
			for (int i = 0; i < data.getDomain().size(); i++) {
				int upperBound = data.getChildrenUpperBounds().get(child.getKey()).get(i);
				int currentThreshold = child.getValue().get(i);

				while (currentThreshold > upperBound) {
					currentThreshold -= 1;
					data.setChildThreshold(i, child.getKey(), currentThreshold);
				}
			}
		}
	}
	
}