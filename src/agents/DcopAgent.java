package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import models.DcopAgentData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DcopAgent extends Agent {
    private AID parentAgent;
    private List<AID> children;
    private List<AID> lowerNeighbours;
    private DcopAgentData data;

    @Override
    protected void setup() {
        children = new ArrayList<>();
        lowerNeighbours = new ArrayList<>();

        List<String> childrenNames;
        List<String> lowerNeighboursNames;
        int domain;
        Object[] setupArgs = getArguments();

        childrenNames = Arrays.asList((String[])setupArgs[0]);
        lowerNeighboursNames = Arrays.asList((String[])setupArgs[1]);
        domain = (int) setupArgs[2];

        data = new DcopAgentData(childrenNames.size(), domain);
        System.out.println("Agent " + getLocalName() + " was created.");

        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());

        /*
         * Registers itself on the DF as parent of each name received as
         * argument. These services will be used so that each agent can find
         * the AID of its respective father after all DCOP agents
         * have been generated.
         */
        for (String childName : childrenNames) {
            ServiceDescription parentOfService = new ServiceDescription();
            parentOfService.setName(getLocalName());
            parentOfService.setType("parent-of-" + childName);
            description.addServices(parentOfService);

            System.out.println(parentOfService.getType());
        }

        /*
         * Also registers itself with its own name, so that parents can search
         * for their children and lower neighbours.
         */
        ServiceDescription agentService = new ServiceDescription();
        agentService.setName(getLocalName());
        agentService.setType("agent-" + getLocalName());
        description.addServices(agentService);

        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new searchForParentBehaviour(this, 5000));
        addBehaviour(new searchForChildrenBehaviour(this, 5000, childrenNames));
        addBehaviour(new searchForLowerNeighboursBehaviour(this, 5000, lowerNeighboursNames));
    }

    private class searchForParentBehaviour extends WakerBehaviour {

		private static final long serialVersionUID = -7370214749961979377L;

		public searchForParentBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onWake() {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription serviceTemplate = new ServiceDescription();
            serviceTemplate.setType("parent-of-" + getLocalName());
            template.addServices(serviceTemplate);

            System.out.println("Searching for " + serviceTemplate.getType());

            DFAgentDescription []resultSearch = null;

            try {
                resultSearch = DFService.search(myAgent, template);
            } catch (FIPAException e) {
                e.printStackTrace();
            }

            if (resultSearch.length != 0) {
                parentAgent = resultSearch[0].getName();
                System.out.println("Found parent for " + getLocalName() + ": " + parentAgent.getLocalName());
            }
        }
    }

    private class searchForChildrenBehaviour extends WakerBehaviour {
		private static final long serialVersionUID = -3081227894323949053L;
		private List<String> childrenName;

        public searchForChildrenBehaviour(Agent a, long period, List<String> children) {
            super(a, period);
            childrenName = children;
        }

        @Override
        protected void onWake() {
            for (String childName : childrenName) {
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
                    children.add(resultSearch[0].getName());
                    System.out.println("Registering agent " +
                                        resultSearch[0].getName().getLocalName() +
                                        " as " + getLocalName() + "'s child");
                }
            }
        }
    }

    private class searchForLowerNeighboursBehaviour extends WakerBehaviour {
		private static final long serialVersionUID = 2788042325314110781L;
		List<String> lowerNeighboursNames;

        public searchForLowerNeighboursBehaviour(Agent a, long period, List<String> lowerNeighbours) {
            super(a, period);
            lowerNeighboursNames = lowerNeighbours;
        }

        @Override
        protected void onWake() {
            for (String lowerNeighbourName : lowerNeighboursNames) {
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
                    lowerNeighbours.add(resultSearch[0].getName());
                    System.out.println("Registering agent " +
                            resultSearch[0].getName().getLocalName() +
                            " as " + getLocalName() + "'s lower neighbour");
                }
            }
        }
    }
}
