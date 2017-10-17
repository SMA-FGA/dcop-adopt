package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DcopAgent extends Agent {
    private AID parent;
    private List<AID> children;
    private List<AID> lowerNeighbours;

    @Override
    protected void setup() {
        List<String> childrenNames;
        Object[] setupArgs = getArguments();

        childrenNames = Arrays.asList((String[])setupArgs[1]);
        System.out.println("Agent " + getLocalName() + " was created.");

        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());

        /*
         * Registers itself on the DF as parent of each name received as
         * argument. These services will be used so that each agent can find
         * the AIDs of its respective father and children after all DCOP agents
         * have been generated.
         */
        for (String childName : childrenNames) {
            ServiceDescription parentOfService = new ServiceDescription();
            parentOfService.setType("parent-of-" + childName);
            description.addServices(parentOfService);

            System.out.println("Agent " + getLocalName() + " is parent of " + childName);
        }
    }

}
