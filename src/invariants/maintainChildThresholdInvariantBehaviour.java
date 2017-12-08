package invariants;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import models.NodeAgentData;

public class maintainChildThresholdInvariantBehaviour extends OneShotBehaviour{

	private static final long serialVersionUID = -1491184944608291784L;
	 NodeAgentData data;
	 
	public maintainChildThresholdInvariantBehaviour(Agent a, NodeAgentData data) {
		super(a);
		this.data = data;
	}
	
	@Override
	public void action() {
		System.out.println("[INV MCTI   ] "+myAgent.getLocalName()+" starting maintain child threshold invariant");
		
        for (int i = 0; i < data.getDomain().size(); i++) { 
            for(int j = 0; j < data.getChildren().size(); j++) {
            	while(data.getChildrenLowerBounds().get(i).get(j) > data.getChildrenThresholds().get(i).get(j)) {
            		data.setChildThreshold(i, j, data.getChildrenThresholds().get(i).get(j) + 1);
            	}
            }
            for(int j = 0; j < data.getChildren().size(); j++) {
            	while(data.getChildrenLowerBounds().get(i).get(j) > data.getChildrenThresholds().get(i).get(j)) {
            		data.setChildThreshold(i, j, data.getChildrenThresholds().get(i).get(j) - 1);
            	} 
            }
        } 
	}
	
}