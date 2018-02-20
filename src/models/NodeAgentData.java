package models;

import java.util.*;

import jade.core.AID;

public class NodeAgentData {
    private int threshold;
    private boolean receivedTerminate;
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
    private List<List<Map<String, Integer>>> childrenContexts;
    private Map<String, List<List<Integer>>> constraints;

    public boolean hasReceivedTerminate() {
        return receivedTerminate;
    }

    public void setReceivedTerminate(boolean receivedTerminate) {
        this.receivedTerminate = receivedTerminate;
    }

    public int getLocalCostForVariable(int variable) {
        int localCost = 0;

        for (Map.Entry<String, Integer> pair : currentContext.entrySet()) {
            List<List<Integer>> l = getConstraints().get(pair.getKey());
            localCost += l.get(variable).get(pair.getValue());
        }

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
        for (Map.Entry<String, Integer> entry : currentContext.entrySet()) {
            boolean hasKey = receivedContext.containsKey(entry.getKey());
            if (hasKey && entry.getValue() != receivedContext.get(entry.getKey())) {
                return false;
            } else {
                // Do nothing. This means either the received context doesn't
                // have the pair in its context, or that the values are equal.
            }
        }
        return true;
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
            this.childrenThresholds.put(childKey, lowerBoundsForChild);
        }
    }
      
    public void setChildUpperBound(Integer domainIndex, String childKey, Integer upperBoundToReplace) {
        if(!childrenThresholds.isEmpty()) { // agent has no children
            List<Integer> upperBoundsForChild = this.childrenUpperBounds.get(childKey);
            upperBoundsForChild.set(domainIndex, upperBoundToReplace);
            this.childrenThresholds.put(childKey, upperBoundsForChild);
        }
    }
    
    public void setChildContext(Integer domainIndex, Integer childIndex, Map<String, Integer> contextToReplace) {
    	if(!childrenContexts.get(0).isEmpty()) {
    		List<Map<String, Integer>> contextsForDomain = this.childrenContexts.get(domainIndex);
    		contextsForDomain.set(childIndex, contextToReplace);
	        this.childrenContexts.set(domainIndex, contextsForDomain);
    	}
    }

    public Map<String, List<Integer>> getChildrenThresholds() {
        return childrenThresholds;
    }
    public void setChildrenThresholds(Map<String, List<Integer>> childrenThresholds) {
        this.childrenThresholds = childrenThresholds;
    }

	public List<List<Map<String, Integer>>> getChildrenContexts() {
		return childrenContexts;
	}
	public void setChildrenContexts(List<List<Map<String, Integer>>> childrenContexts) {
		this.childrenContexts = childrenContexts;
	}

    public Map<String, List<List<Integer>>> getConstraints() {
        return this.constraints;
    }
    public void setConstraints(List<List<Integer>> constraint) {
    	Map<String, List<List<Integer>>> constraintsMap = new HashMap<>();

    	for (String upper : upperNeighboursNames) {
	        constraintsMap.put(upper, constraint);
    	}
    	
    	for (String lower : lowerNeighboursNames) {
	        constraintsMap.put(lower, constraint);
    	}
    	
    	this.constraints = constraintsMap;
    }
}
