package node;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import models.NodeAgentData;

public class searchForLowerNeighboursBehaviour extends WakerBehaviour {
	
	private static final long serialVersionUID = 2788042325314110781L;
	NodeAgentData data = new NodeAgentData();
	
    public searchForLowerNeighboursBehaviour(Agent a, long period, NodeAgentData data) {
        super(a, period);
        this.data = data;
    }

    @Override
    protected void onWake() {
        for (String lowerNeighbourName : data.getLowerNeighboursNames()) {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription serviceTemplate = new ServiceDescription();
            serviceTemplate.setType("agent-" + lowerNeighbourName);
            template.addServices(serviceTemplate);

            DFAgentDescription []resultSearch = null;

            try {
                resultSearch = DFService.search(myAgent, template);
            } catch (FIPAException e) {
                e.printStackTrace();
            }

            if (resultSearch.length != 0) {
                data.setLowerNeighbour(resultSearch[0].getName());
                System.out.println("[NEIGH      ] Registering agent " +
                        resultSearch[0].getName().getLocalName() +
                        " as " + myAgent.getLocalName() + "'s lower neighbour");
            }
        }
    }
}