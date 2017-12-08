package node;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import models.NodeAgentData;

public class searchForChildrenBehaviour extends WakerBehaviour {
	
	private static final long serialVersionUID = -3081227894323949053L;
	NodeAgentData data = new NodeAgentData();
	
    public searchForChildrenBehaviour(Agent a, long period, NodeAgentData data) {
        super(a, period);
        this.data = data;
    }

    @Override
    protected void onWake() {
        for (String childName : data.getChildrenNames()) {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription serviceTemplate = new ServiceDescription();
            serviceTemplate.setType("agent-" + childName);
            template.addServices(serviceTemplate);

            DFAgentDescription []resultSearch = null;

            try {
                resultSearch = DFService.search(myAgent, template);
            } catch (FIPAException e) {
                e.printStackTrace();
            }

            if (resultSearch.length != 0) {
                data.setChild(resultSearch[0].getName());
                System.out.println("[CHILD      ] Registering agent " +
                                    resultSearch[0].getName().getLocalName() +
                                    " as " + myAgent.getLocalName() + "'s child");
            }
        }
    }
}