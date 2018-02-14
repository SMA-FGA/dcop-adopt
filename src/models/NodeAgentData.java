package models;

import java.util.*;

import jade.core.AID;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NodeAgentData {
    private int lowerBound;
    private int upperBound;
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
    private List<List<Integer>> childrenLowerBounds;
    private List<List<Integer>> childrenUpperBounds;
    private List<List<Integer>> childrenThresholds;
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

        for (int childUpperBound : childrenUpperBounds.get(variable)) {
            upperBound += childUpperBound;
        }

        return upperBound;
    }

    public int getLowerBoundForVariable(int variable) {
        int localCost = getLocalCostForVariable(variable);
        int lowerBound = localCost;

        for (int childLowerBound : childrenLowerBounds.get(variable)) {
            lowerBound += childLowerBound;
        }

        return lowerBound;
    }

    public int minimizeCurrentValueForUpperBound() {
        int updatedCurrentValue = Integer.MAX_VALUE;

        for (int i : getDomain()) {
            updatedCurrentValue = Integer.min(updatedCurrentValue, getUpperBoundForVariable(i));
        }

        return updatedCurrentValue;
    }

    public int minimizeCurrentValueForLowerBound() {
        int updatedCurrentValue = Integer.MAX_VALUE;

        for (int i : getDomain()) {
            updatedCurrentValue = Integer.min(updatedCurrentValue, getLowerBoundForVariable(i));
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
    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        int minUpperBound = getUpperBoundForVariable(0);

        for (int i = 1; i < domain.size(); i++) {
            minUpperBound = Math.min(minUpperBound, getUpperBoundForVariable(i));
        }

        return minUpperBound;
    }
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
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

    public List<List<Integer>> getChildrenLowerBounds() {
        return childrenLowerBounds;
    }
    public void setChildrenLowerBounds(List<List<Integer>> childrenLowerBounds) {
        this.childrenLowerBounds = childrenLowerBounds;
    }

    public Integer getChildLowerBound(int domainIndex, int agentIndex) {
    	return this.childrenLowerBounds.get(domainIndex).get(agentIndex);
    }

    public List<List<Integer>> getChildrenUpperBounds() {
        return childrenUpperBounds;
    }
    public void setChildrenUpperBounds(List<List<Integer>> childrenUpperBounds) {
        this.childrenUpperBounds = childrenUpperBounds;
    }

    public void setChildThreshold(Integer domainIndex, Integer childIndex, Integer thresholdToReplace) {
    	if(!childrenThresholds.get(0).isEmpty()) { // agent has not children
    		//System.out.println("domainIndex: "+domainIndex+" ChildIndex:"+ChildIndex+" thresholdToReplace: "+thresholdToReplace);
            //System.out.println("before: "+this.childrenThresholds);
            List<Integer> thresholdsForDomain = this.childrenThresholds.get(domainIndex);
            thresholdsForDomain.set(childIndex, thresholdToReplace);
            this.childrenThresholds.set(domainIndex, thresholdsForDomain);
            //System.out.println("then: "+this.childrenThresholds);
    	}
    }
      
    public void setChildLowerBound(Integer domainIndex, Integer childIndex, Integer lowerBoundToReplace) {
    	if(!childrenLowerBounds.get(0).isEmpty()) {
	        List<Integer> lowerBoundsForDomain = this.childrenThresholds.get(domainIndex);
	        lowerBoundsForDomain.set(childIndex, lowerBoundToReplace);
	        this.childrenLowerBounds.set(domainIndex, lowerBoundsForDomain);
    	}
    }
      
    public void setChildUpperBound(Integer domainIndex, Integer childIndex, Integer upperBoundToReplace) {
    	if(!childrenUpperBounds.get(0).isEmpty()) {
	        List<Integer> upperBoundsForDomain = this.childrenThresholds.get(domainIndex);
	        upperBoundsForDomain.set(childIndex, upperBoundToReplace);
	        this.childrenUpperBounds.set(domainIndex, upperBoundsForDomain);
    	}
    }
    
    public void setChildContext(Integer domainIndex, Integer childIndex, Map<String, Integer> contextToReplace) {
    	if(!childrenContexts.get(0).isEmpty()) {
    		List<Map<String, Integer>> contextsForDomain = this.childrenContexts.get(domainIndex);
    		contextsForDomain.set(childIndex, contextToReplace);
	        this.childrenContexts.set(domainIndex, contextsForDomain);
    	}
    }

    public List<List<Integer>> getChildrenThresholds() {
        return childrenThresholds;
    }
    public void setChildrenThresholds(List<List<Integer>> childrenThresholds) {
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
