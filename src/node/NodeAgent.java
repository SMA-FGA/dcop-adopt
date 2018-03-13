package node;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import jade.core.Agent;
import jade.util.Logger;
import models.NodeAgentData;

public class NodeAgent extends Agent {
	private static final long serialVersionUID = 783786161678161415L;
	private static final Logger LOGGER = Logger.getMyLogger(NodeAgent.class.getName());
	private NodeAgentData data;
	
    @Override
    protected void setup() {
    	LOGGER.setLevel(Level.OFF);
    	LOGGER.info("[CREATE     ] Agent " + getLocalName() + " was created.");

        data = new NodeAgentData();
        Object[] setupArgs = getArguments();
        data.setReceivedTerminate(false);
        data.setChildrenNames(Arrays.asList((String[])setupArgs[0]));
        data.setLowerNeighboursNames(Arrays.asList((String[])setupArgs[1]));
        data.setDomain(Arrays.asList((Integer[])setupArgs[2]));
        data.setUpperNeighboursNames(Arrays.asList((String[])setupArgs[3]));
        data.setConstraints((List<List<Integer>>)setupArgs[4]);
        
        LOGGER.info("[DOMAIN     ] "+data.getDomain());
        LOGGER.info("[CONSTRAINTS] "+data.getConstraints());

        addBehaviour(new registerChilrenBehaviour(this, data));
        addBehaviour(new searchForParentBehaviour(this, 5000, data));
        addBehaviour(new searchForChildrenBehaviour(this, 5000, data));
        addBehaviour(new searchForLowerNeighboursBehaviour(this, 5000, data));
        addBehaviour(new initializeBehaviour(this, 7000, data));
    }
}