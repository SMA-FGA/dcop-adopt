package invariants;

import jade.core.Agent;
import models.DCOPAgentData;

public interface maintainInvariant {
	
	public void maintain(Agent myAgent, DCOPAgentData data);
}
