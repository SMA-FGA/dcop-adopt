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
		System.out.println("[INITIALIZE ]" +myAgent.getLocalName()+" starting initialize procedure");
		data.setLowerBound(0);
        data.setUpperBound(Integer.MAX_VALUE);
        data.setThreshold(0);
        data.setCurrentContext(new HashMap<>());		
        
        data.setChildrenLowerBounds(initializeChildrenValues(LOWER_BOUND));
        data.setChildrenUpperBounds(initializeChildrenValues(UPPER_BOUND));
        data.setChildrenThresholds(initializeChildrenValues(THRESHOLD));
        
        // Initialize children contexts
        List<List<Map<String, Integer>>> childrenContexts = new ArrayList<>();
        for (int i = 0; i < data.getDomain().size(); i++) {
        	List<Map<String, Integer>> childrenContextsForDomain = new ArrayList<>();
            
            for(int j = 0; j < data.getChildren().size(); j++) {
            	childrenContextsForDomain.add(new HashMap<String, Integer>());
            	System.out.println("[CONTEXT    ] context(" + i + "," + j + ") = " + childrenContextsForDomain.get(j));
            }
            
            childrenContexts.add(childrenContextsForDomain);
        }
        data.setChildrenContexts(childrenContexts);

		data.setCurrentValue(data.getDomain().get(0)); // to do: di <- d that minimizes LB(d)
		
		// Adding behaviours to receive adopt messages
		myAgent.addBehaviour(new receiveValueMessageBehaviour(myAgent, data));
		myAgent.addBehaviour(new receiveCostMessageBehaviour(myAgent, data));
		myAgent.addBehaviour(new receiveThresholdMessageBehaviour(myAgent));
		myAgent.addBehaviour(new receiveTerminateMessageBehaviour(myAgent));
		
		myAgent.addBehaviour(new backTrackBehaviour(myAgent, data));
	}

    private List<List<Integer>> initializeChildrenValues(int valueType) {
        List<List<Integer>> values = new ArrayList<>();

        for (int i = 0; i < data.getDomain().size(); i++) {
            List<Integer> valuesForDomain = new ArrayList<>();

            for (int j = 0; j < data.getChildren().size(); j++) {
                switch (valueType) {
                    case LOWER_BOUND: {
                        valuesForDomain.add(0);
                        System.out.println("[LOWER BOUND] lb(" + i + "," + j + ") = " + valuesForDomain.get(j));
                        break;
                    }
                    case UPPER_BOUND: {
                        valuesForDomain.add(Integer.MAX_VALUE);
                        System.out.println("[UPPER BOUND] ub(" + i + "," + j + ") = " + valuesForDomain.get(j));
                        break;
                    }
                    case THRESHOLD: {
                        valuesForDomain.add(0);
                        System.out.println("[THRESHOLD  ] t(" + i + "," + j + ") = " + valuesForDomain.get(j));
                        break;
                    }
                }
            }

            values.add(valuesForDomain);
        }

        return values;
    }
}