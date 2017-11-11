package models;

import java.util.*;

import jade.core.AID;

public class DcopAgentData {
    private int lowerBound;
    private int upperBound;
    private int threshold;
    private Map<String, Integer> currentContext;
    private List<Integer> domain;
    private int chosenValue;
    private AID parentAgent;
    private List<AID> children = new ArrayList<>();
    private List<AID> lowerNeighbours = new ArrayList<>();
    private List<String> childrenNames;
    private List<String> lowerNeighboursNames;
    private List<List<Integer>> childrenLowerBounds;
    private List<List<Integer>> childrenUpperBounds;
    private List<List<Integer>> childrenThresholds;
    private List<List<Map<String, Integer>>> childrenContexts;

    public DcopAgentData() {
        System.out.println("[CREATE     ] Agent data was created.");
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
    public AID getParent() {
    	return this.parentAgent;
    }
    public void setParent(AID parentAID) {
    	this.parentAgent = parentAID;
    }
    public List<AID> getLowerNeighbours() {
    	return this.lowerNeighbours;
    }
    public void setLowerNeighbour(AID agentAID) {
    	this.lowerNeighbours.add(agentAID);
    }
    public List<AID> getChildren() {
    	return this.children;
    }
    public void setChild(AID agentAID) {
    	this.children.add(agentAID);
    }
    public int getChosenValue() {
    	return this.chosenValue;
    }
    public void setChosenValue(int chosenValue) {
    	this.chosenValue = chosenValue;
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
    public List<List<Integer>> getChildrenUpperBounds() {
        return childrenUpperBounds;
    }
    public void setChildrenUpperBounds(List<List<Integer>> childrenUpperBounds) {
        this.childrenUpperBounds = childrenUpperBounds;
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
}
