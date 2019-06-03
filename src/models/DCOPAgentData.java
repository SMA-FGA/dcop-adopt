package models;

import java.util.*;

import graph.Constraint;
import jade.core.AID;

public class DCOPAgentData {
    private int threshold;
    private boolean receivedTerminate = false;
    private boolean wasKilled = false;
    private Map<String, Integer> currentContext;
    private List<Integer> domain;
    private int currentValue;
    private AID parentAID;
    private List<AID> childrenAID = new ArrayList<>();
    private List<AID> lowerNeighboursAID = new ArrayList<>();
    private List<String> childrenNames;
    private List<String> lowerNeighboursNames;
    private List<String> upperNeighboursNames;
    private Map<String, List<Integer>> childrenLowerBounds;
    private Map<String, List<Integer>> childrenUpperBounds;
    private Map<String, List<Integer>> childrenThresholds;
    private Map<String, List<Map<String, Integer>>> childrenContexts;
    private Map<String, Constraint> constraints;

    public boolean hasReceivedTerminate() {
        return this.receivedTerminate;
    }

    public void setReceivedTerminate(boolean receivedTerminate) {
        this.receivedTerminate = receivedTerminate;
    }
    
    public boolean wasKilled() {
    	return this.wasKilled;
    }
    
    public void setWasKilled(boolean wasKilled) {
    	this.wasKilled = wasKilled;
    }
    
    public int getConstraintCost(int myChoice, String upperNeighbourName, Integer upperNeighbourChice) {
    	int cost = -1;
    	
    	cost = constraints.get(upperNeighbourName).getConstraint()[myChoice][upperNeighbourChice];
    	
    	return cost;
    }

    public int getLocalCostForVariable(int myChoice) {
    	//System.out.println("MY CHICE: "+myChoice+", STORED: "+this.currentValue);
        int localCost = 0;
        
        if(!this.currentContext.isEmpty()) {
        	//System.out.println("My context: "+this.currentContext);
        	
        	for (Map.Entry<String, Integer> agentInContext: this.currentContext.entrySet()) {
        		
        		if(this.upperNeighboursNames.contains(agentInContext.getKey())) {
        			localCost += getConstraintCost(myChoice, agentInContext.getKey(), agentInContext.getValue());
        		}
            }
        }
        
        //System.out.println("[LOCAL COST] local cost: "+localCost);
        return localCost;
    }

    // TODO: Make following methods more generic
    // (same logic used for both upper and lower bound).

    public int getUpperBoundForVariable(int variable) {
        int localCost = getLocalCostForVariable(variable);
        int upperBound = localCost;

        for (Map.Entry<String, List<Integer>> child : childrenUpperBounds.entrySet()) {
            int childUpperBound = child.getValue().get(variable);
            
            if (childUpperBound == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            upperBound += childUpperBound;
        }

        return upperBound;
    }

    public int getLowerBoundForVariable(int variable) {
        int localCost = getLocalCostForVariable(variable);
        int lowerBound = localCost;

        for (Map.Entry<String, List<Integer>> child : childrenLowerBounds.entrySet()) {
            int childLowerBound = child.getValue().get(variable);

            lowerBound += childLowerBound;
        }

        return lowerBound;
    }

    public int minimizeCurrentValueForUpperBound() {
        int minUpperBound = Integer.MAX_VALUE;
        int updatedCurrentValue = currentValue;

        for (int i : getDomain()) {
            int upperBound = getUpperBoundForVariable(i);

            if (upperBound < minUpperBound) {
                minUpperBound = upperBound;
                updatedCurrentValue = i;
            }
        }
        
        return updatedCurrentValue;
    }

    public int minimizeCurrentValueForLowerBound() {
        int minLowerBound = Integer.MAX_VALUE;
        int updatedCurrentValue = currentValue;


        for (int i : getDomain()) {
            int lowerBound = getLowerBoundForVariable(i);

            if (lowerBound < minLowerBound) {
                minLowerBound = lowerBound;
                updatedCurrentValue = i;
            }
        }
        
        return updatedCurrentValue;
    }
    // TODO ends here.

    public boolean isContextCompatible(Map<String, Integer> receivedContext) {
    	boolean isCompatible = true;
    			
        for (Map.Entry<String, Integer> entry : this.currentContext.entrySet()) {
            
            for(Map.Entry<String, Integer> received : receivedContext.entrySet()) {
            	if(received.getKey() == entry.getKey()) {
                	if(entry.getValue() != received.getValue()) {
                		isCompatible = false;
                	}
                }
            }
        }
        
        System.out.println("Teste compatibilidade "+receivedContext+" and "+this.currentContext+": "+isCompatible);
        return isCompatible;
    }

    public List<Integer> getDomain() {
    	return this.domain;
    }
    public void setDomain(List<Integer> domain) {
    	this.domain = domain;
    }

    public List<String> getChildrenNames() {
    	return this.childrenNames;
    }
    public void setChildrenNames(List<String> childrenNames) {
    	this.childrenNames = childrenNames;
    }

    public List<String> getLowerNeighboursNames() {
    	return this.lowerNeighboursNames;
    }
    public void setLowerNeighboursNames(List<String> lowerNeighboursNames) {
        this.lowerNeighboursNames = lowerNeighboursNames;
    }

    public List<String> getUpperNeighboursNames() {
        return this.upperNeighboursNames;
    }
    public void setUpperNeighboursNames(List<String> upperNeighboursNames) {
    	this.upperNeighboursNames = upperNeighboursNames;
    }

    public AID getParent() {
    	return this.parentAID;
    }
    public void setParent(AID parentAID) {
    	this.parentAID = parentAID;
    }

    public List<AID> getLowerNeighbours() {
    	return this.lowerNeighboursAID;
    }
    public void setLowerNeighbour(AID agentAID) {
    	this.lowerNeighboursAID.add(agentAID);
    }

    public List<AID> getChildren() {
    	return this.childrenAID;
    }
    public void setChild(AID agentAID) {
    	this.childrenAID.add(agentAID);
    }

    public int getCurrentValue() {
    	return this.currentValue;
    }
    public void setCurrentValue(int currentValue) {
    	this.currentValue = currentValue;
    }

    public int getLowerBound() {
        int minLowerBound = getLowerBoundForVariable(0);

        for (int i = 1; i < domain.size(); i++) {
            minLowerBound = Math.min(minLowerBound, getLowerBoundForVariable(i));
        }

        return minLowerBound;
    }

    public int getUpperBound() {
        int minUpperBound = getUpperBoundForVariable(0);

        for (int i = 1; i < domain.size(); i++) {
            minUpperBound = Math.min(minUpperBound, getUpperBoundForVariable(i));
        }

        return minUpperBound;
    }

    public int getThreshold() {
        return threshold;
    }
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Map<String, Integer> getCurrentContext() {
        return currentContext;
    }
    public void setCurrentContext(Map<String, Integer> currentContext) {
        this.currentContext = currentContext;
    }

    public Map<String, List<Integer>> getChildrenLowerBounds() {
        return childrenLowerBounds;
    }
    public void setChildrenLowerBounds(Map<String, List<Integer>> childrenLowerBounds) {
        this.childrenLowerBounds = childrenLowerBounds;
    }

    public Map<String, List<Integer>> getChildrenUpperBounds() {
        return childrenUpperBounds;
    }
    public void setChildrenUpperBounds(Map<String, List<Integer>> childrenUpperBounds) {
        this.childrenUpperBounds = childrenUpperBounds;
    }

    public void setChildThreshold(Integer domainIndex, String childKey, Integer thresholdToReplace) {
    	if(!childrenThresholds.isEmpty()) { // agent has no children
            List<Integer> thresholdsForChild = this.childrenThresholds.get(childKey);
            thresholdsForChild.set(domainIndex, thresholdToReplace);
            this.childrenThresholds.put(childKey, thresholdsForChild);
    	}
    }
      
    public void setChildLowerBound(Integer domainIndex, String childKey, Integer lowerBoundToReplace) {
        if(!childrenLowerBounds.isEmpty()) { // agent has no children
            List<Integer> lowerBoundsForChild = this.childrenLowerBounds.get(childKey);
            lowerBoundsForChild.set(domainIndex, lowerBoundToReplace);
            this.childrenLowerBounds.put(childKey, lowerBoundsForChild);
        }
    }
      
    public void setChildUpperBound(Integer domainIndex, String childKey, Integer upperBoundToReplace) {
        if(!childrenUpperBounds.isEmpty()) { // agent has no children
            List<Integer> upperBoundsForChild = this.childrenUpperBounds.get(childKey);
            upperBoundsForChild.set(domainIndex, upperBoundToReplace);
            this.childrenUpperBounds.put(childKey, upperBoundsForChild);
        }
    }
    
    public void setChildContext(Integer domainIndex, String childKey, Map<String, Integer> contextToReplace) {
    	if(!childrenContexts.isEmpty()) {
    		List<Map<String, Integer>> contextsForDomain = this.childrenContexts.get(childKey);
    		contextsForDomain.set(domainIndex, contextToReplace);
	        this.childrenContexts.put(childKey, contextsForDomain);
    	}
    }

    public Map<String, List<Integer>> getChildrenThresholds() {
        return this.childrenThresholds;
    }
    public void setChildrenThresholds(Map<String, List<Integer>> childrenThresholds) {
        this.childrenThresholds = childrenThresholds;
    }

	public Map<String, List<Map<String, Integer>>> getChildrenContexts() {
		return childrenContexts;
	}
	public void setChildrenContexts(Map<String, List<Map<String, Integer>>> childrenContexts) {
		this.childrenContexts = childrenContexts;
	}

    public Map<String, Constraint> getConstraints() {
        return this.constraints;
    }
    public void setConstraints(Map<String, Constraint> constraints) {
    	this.constraints = constraints;
    }

	public boolean isMyNeighbour(String agentName) {
		boolean isMyNeighbour = false;
		
		for (String upper : upperNeighboursNames) {
			if(upper.equals(agentName)) {
				isMyNeighbour = true;
			}
    	}
    	
    	for (String lower : lowerNeighboursNames) {
    		if(lower.equals(agentName)) {
    			isMyNeighbour = true;
    		}
    	}
		
		return isMyNeighbour;
	}
	
	public int getThresholdsSum(Integer current) {
        int childrenThresholdsSum = 0;
        
        for (Map.Entry<String, List<Integer>> child : childrenThresholds.entrySet()) {
            childrenThresholdsSum += child.getValue().get(current);
        }

        return childrenThresholdsSum;
    }
	
}
