package invariants;

import jade.core.Agent;
import models.NodeAgentData;

public interface maintainInvariant {
	
	public void maintain(Agent myAgent, NodeAgentData data);
}
