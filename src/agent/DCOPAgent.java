package agent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import graph.Constraint;
import jade.core.Agent;
import jade.util.Logger;
import models.NodeAgentData;

public class DCOPAgent extends Agent {
	private static final long serialVersionUID = 783786161678161415L;
	private static final Logger LOGGER = Logger.getMyLogger(DCOPAgent.class.getName());
	private NodeAgentData data;
	
    @Override
    protected void setup() {
    	LOGGER.setLevel(Level.OFF);
    	LOGGER.info("[CREATE     ] Agent " + getLocalName() + " was created.");

        data = new NodeAgentData();
        Object[] setupArgs = getArguments();
        List<String> childrenNames = Arrays.asList((String[])setupArgs[0]);
        List<String> lowerNeighboursNames = Arrays.asList((String[])setupArgs[1]);
        List<Integer> domain = Arrays.asList((Integer[])setupArgs[2]);
        List<String> upperNeighboursNames = Arrays.asList((String[])setupArgs[3]);
        
        List<List<List<Integer>>> constraintsEmbacadas = (List<List<List<Integer>>>)setupArgs[4];
        
        System.out.println(constraintsEmbacadas);
        
        Map<String, Constraint> constraintsMap = new HashMap<>();
        
        for(int i = 0; i < upperNeighboursNames.size(); i++) {
        	System.out.println("put: "+upperNeighboursNames.get(i)+" "+constraintsEmbacadas.get(i));
        	constraintsMap.put(upperNeighboursNames.get(i), new Constraint("a", constraintsEmbacadas.get(i)));
	    }
        
        for(int i = 0; i < lowerNeighboursNames.size(); i++) {
        	System.out.println("put: "+lowerNeighboursNames.get(i)+" "+constraintsEmbacadas.get(i));
	    	constraintsMap.put(lowerNeighboursNames.get(i), new Constraint("a", constraintsEmbacadas.get(i+upperNeighboursNames.size())));
	    }
        
        //System.out.println(constraintsMap);
        
        data.setChildrenNames(childrenNames);
        data.setLowerNeighboursNames(lowerNeighboursNames);
        data.setDomain(domain);
        data.setUpperNeighboursNames(upperNeighboursNames);
        data.setConstraints(constraintsMap);
        
        LOGGER.info("[DOMAIN     ] "+data.getDomain());
        LOGGER.info("[CONSTRAINTS] "+data.getConstraints());

        addBehaviour(new registerChilrenBehaviour(this, data));
        addBehaviour(new searchForParentBehaviour(this, 5000, data));
        addBehaviour(new searchForChildrenBehaviour(this, 5000, data));
        addBehaviour(new searchForLowerNeighboursBehaviour(this, 5000, data));
        addBehaviour(new initializeBehaviour(this, 7000, data));
    }
}