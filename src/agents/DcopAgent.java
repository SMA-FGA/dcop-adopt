package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DcopAgent extends Agent {
    private AID parentAgent;
    private List<AID> children;
    private List<AID> lowerNeighbours;

    @Override
    protected void setup() {
        children = new ArrayList<>();
        lowerNeighbours = new ArrayList<>();

        List<String> childrenNames;
        Object[] setupArgs = getArguments();

        childrenNames = Arrays.asList((String[])setupArgs[0]);
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
         * for their children.e.printStackTrace();
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
    }

    private class searchForParentBehaviour extends WakerBehaviour {

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

}
