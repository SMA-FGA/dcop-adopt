package node;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import models.NodeAgentData;

public class searchForParentBehaviour extends WakerBehaviour {
	
	private static final long serialVersionUID = -7370214749961979377L;
	NodeAgentData data = new NodeAgentData();
	
	public searchForParentBehaviour(Agent a, long period, NodeAgentData data) {
        super(a, period);
        this.data = data;
    }

    @Override
    protected void onWake() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription serviceTemplate = new ServiceDescription();
        serviceTemplate.setType("parent-of-" + myAgent.getLocalName());
        template.addServices(serviceTemplate);

        DFAgentDescription []resultSearch = null;

        try {
            resultSearch = DFService.search(myAgent, template);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        if (resultSearch.length != 0) {
            data.setParent(resultSearch[0].getName());
            System.out.println("[PARENT     ] Found parent for " + myAgent.getLocalName() + ": " + data.getParent().getLocalName());
        }
    }
}