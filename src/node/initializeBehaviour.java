package node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import models.NodeAgentData;

public class initializeBehaviour extends WakerBehaviour {
	private static final long serialVersionUID = -2905010008367981124L;

	// Constants for initializeChildrenValues method
	private static final int LOWER_BOUND = 0;
	private static final int UPPER_BOUND = 1;
	private static final int THRESHOLD = 2;

	NodeAgentData data = new NodeAgentData();
	public initializeBehaviour(Agent a, long period, NodeAgentData data) {
		super(a, period);
		this.data = data;
	}

	@Override
	public void onWake() {
		System.out.println("[INITIALIZE ]" + myAgent.getLocalName()+" starting initialize procedure");
        data.setThreshold(0);
        data.setCurrentContext(new HashMap<String, Integer>());		
        
        data.setChildrenLowerBounds(initializeChildrenValues(LOWER_BOUND));
        data.setChildrenUpperBounds(initializeChildrenValues(UPPER_BOUND));
        data.setChildrenThresholds(initializeChildrenValues(THRESHOLD));
        
        // Initialize children contexts
        Map<String, List<Map<String, Integer>>> childrenContexts = new HashMap<>();
        for(int i = 0; i < data.getChildren().size(); i++) {
        	String child = data.getChildren().get(i).getLocalName();
        	List<Map<String, Integer>> childrenContextsForDomain = new ArrayList<>();
            
        	for (int j = 0; j < data.getDomain().size(); j++) {
            	childrenContextsForDomain.add(new HashMap<String, Integer>());
            	System.out.println("[CONTEXT    ] context(" + child + "," + j + ") = " + childrenContextsForDomain.get(j));
            }
            
            childrenContexts.put(child, childrenContextsForDomain);
        }
        data.setChildrenContexts(childrenContexts);

        //di <- d that minimizes LB(d)
		data.setCurrentValue(data.minimizeCurrentValueForLowerBound());
		
		// Adding behaviours to receive adopt messages
        myAgent.addBehaviour(new receiveMessageBehaviour(myAgent, data));
		myAgent.addBehaviour(new receiveCostMessageBehaviour(myAgent, data));
		myAgent.addBehaviour(new receiveThresholdMessageBehaviour(myAgent, data));
		myAgent.addBehaviour(new receiveTerminateMessageBehaviour(myAgent, data));
		
		myAgent.addBehaviour(new backTrackBehaviour(myAgent, data));
	}

    private Map<String, List<Integer>> initializeChildrenValues(int valueType) {
        Map<String, List<Integer>> values = new HashMap<>();

        for (int i = 0; i < data.getChildren().size(); i++) {
            String child = data.getChildren().get(i).getLocalName();
            List<Integer> valuesForChild = new ArrayList<>();

            for (int j = 0; j < data.getDomain().size(); j++) {
                switch (valueType) {
                    case LOWER_BOUND: {
                        valuesForChild.add(0);
                        System.out.println("[LOWER BOUND] lb(" + child + "," + j + ") = " + valuesForChild.get(j));
                        break;
                    }
                    case UPPER_BOUND: {
                        valuesForChild.add(Integer.MAX_VALUE);
                        System.out.println("[UPPER BOUND] ub(" + child + "," + j + ") = " + valuesForChild.get(j));
                        break;
                    }
                    case THRESHOLD: {
                        valuesForChild.add(0);
                        System.out.println("[THRESHOLD  ] t(" + child + "," + j + ") = " + valuesForChild.get(j));
                        break;
                    }
                }
            }

            values.put(child, valuesForChild);
        }

        return values;
    }
}