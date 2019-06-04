package agents.dcop.preinit;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import models.DCOPAgentData;

public class registerChilrenBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	DCOPAgentData data = new DCOPAgentData();
	
	public registerChilrenBehaviour(Agent a, DCOPAgentData data) {
		super(a);
		this.data = data;
	}

	@Override
	public void action() {
        DFAgentDescription description = new DFAgentDescription();
        //description.setName(getAID());

        /*
         * Registers itself on the DF as parent of each name received as
         * argument. These services will be used so that each agent can find
         * the AID of its respective father after all DCOP agents
         * have been generated.
         */
        for (String childName : data.getChildrenNames()) {
            ServiceDescription parentOfService = new ServiceDescription();
            parentOfService.setName(myAgent.getLocalName());
            parentOfService.setType("parent-of-" + childName);
            description.addServices(parentOfService);

            System.out.println("[CREATE     ] Register "+myAgent.getLocalName()+" as "+parentOfService.getType());
        }
        
        /*
         * Also registers itself with its own name, so that parents can search
         * for their children and lower neighbours.
         */
        ServiceDescription agentService = new ServiceDescription();
        agentService.setName(myAgent.getLocalName());
        agentService.setType("agent-" + myAgent.getLocalName());
        description.addServices(agentService);
        
        try {
            DFService.register(myAgent, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
	}
}