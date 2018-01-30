package models;

import java.util.*;

import jade.core.AID;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NodeAgentData {
    private int lowerBound;
    private int upperBound;
    private int threshold;
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

        for (List<Integer> childUpperBound : childrenUpperBounds) {
            upperBound += childUpperBound.get(variable);
        }

        return upperBound;
    }

    public int getLowerBoundForVariable(int variable) {
        int localCost = getLocalCostForVariable(variable);
        int upperBound = localCost;

        for (List<Integer> childLowerBound : childrenLowerBounds) {
            upperBound += childLowerBound.get(variable);
        }

        return upperBound;
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
        return lowerBound;
    }
    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
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

    public void setChildThreshold(Integer i, Integer j, Integer threshold) {
    	List<Integer> thresholds = this.childrenThresholds.get(i);
    	thresholds.set(j, threshold);
    	this.childrenThresholds.set(i, thresholds);
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
