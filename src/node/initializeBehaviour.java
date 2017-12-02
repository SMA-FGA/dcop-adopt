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
        
        // Initialize children lower bounds
        List<List<Integer>> childrenLowerBounds = new ArrayList<>();
        for (int i = 0; i < data.getDomain().size(); i++) {
            List<Integer> childrenLowerBoundsForDomain = new ArrayList<>();
            
            for(int j = 0; j < data.getChildren().size(); j++) {
            	childrenLowerBoundsForDomain.add(0);
            	System.out.println("[LOWER BOUND] lb(" + i + "," + j + ") = " + childrenLowerBoundsForDomain.get(j));
            }
            
            childrenLowerBounds.add(childrenLowerBoundsForDomain);
        }
        data.setChildrenLowerBounds(childrenLowerBounds);
        
        // Initialize children upper bounds
        List<List<Integer>> childrenUpperBounds = new ArrayList<>();
        for (int i = 0; i < data.getDomain().size(); i++) {
            List<Integer> childrenUpperBoundsForDomain = new ArrayList<>();
            
            for(int j = 0; j < data.getChildren().size(); j++) {
            	childrenUpperBoundsForDomain.add(Integer.MAX_VALUE);
            	System.out.println("[UPPER BOUND] ub(" + i + "," + j + ") = " + childrenUpperBoundsForDomain.get(j));
            }
            
            childrenLowerBounds.add(childrenUpperBoundsForDomain);
        }
        data.setChildrenUpperBounds(childrenUpperBounds);
        
        // Initialize children thresholds
        List<List<Integer>> childrenThresholds = new ArrayList<>();
        for (int i = 0; i < data.getDomain().size(); i++) {
            List<Integer> childrenThresholdsForDomain = new ArrayList<>();
            
            for(int j = 0; j < data.getChildren().size(); j++) {
            	childrenThresholdsForDomain.add(0);
            	System.out.println("[TRHESHOLD  ] t(" + i + "," + j + ") = " + childrenThresholdsForDomain.get(j));
            }
            
            childrenThresholds.add(childrenThresholdsForDomain);
        }
        data.setChildrenThresholds(childrenThresholds);
        
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
        
        
		data.setChosenValue(data.getDomain().get(0)); // to do: di <- d that minimizes LB(d) 
		
		myAgent.addBehaviour(new receiveValueMessageBehaviour(myAgent));
		myAgent.addBehaviour(new backTrackBehaviour(myAgent, data));
	}
}