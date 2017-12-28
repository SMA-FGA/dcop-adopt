package node;

import java.util.Arrays;
import java.util.Vector;

import jade.core.Agent;
import models.NodeAgentData;

public class NodeAgent extends Agent {
	private static final long serialVersionUID = 783786161678161415L;
	private NodeAgentData data;

    @Override
    protected void setup() {
        
    	System.out.println("[CREATE     ] Agent " + getLocalName() + " was created.");
        data = new NodeAgentData();
        Object[] setupArgs = getArguments();
        data.setChildrenNames(Arrays.asList((String[])setupArgs[0]));
        data.setLowerNeighboursNames(Arrays.asList((String[])setupArgs[1]));
        data.setDomain(Arrays.asList((Integer[])setupArgs[2]));
        data.setUpperNeighboursNames(Arrays.asList((String[])setupArgs[3]));
        data.setConstraints((Vector<Vector<Integer>>)setupArgs[4]);
        
        System.out.println("[DOMAIN     ] "+data.getDomain());
        System.out.println("[CONSTRAINTS] "+data.getConstraints());

        addBehaviour(new registerChilrenBehaviour(this, data));
        addBehaviour(new searchForParentBehaviour(this, 5000, data));
        addBehaviour(new searchForChildrenBehaviour(this, 5000, data));
        addBehaviour(new searchForLowerNeighboursBehaviour(this, 5000, data));
        addBehaviour(new initializeBehaviour(this, 7000, data));
    }
}