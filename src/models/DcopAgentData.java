package models;

import java.util.*;

import jade.core.AID;

public class DcopAgentData {
    private int lowerBound;
    private int upperBound;
    private int threshold;
    private int chosenValue;

    private Map<String, Integer> currentContext;
    private AID parentAgent;
    private List<AID> children = new ArrayList<>();
    private List<AID> lowerNeighbours = new ArrayList<>();
    private List<List<Integer>> childrenLowerBounds;
    private List<List<Integer>> childrenUpperBounds;
    private List<List<Integer>> childrenThresholds;

    public DcopAgentData(int children, int domain) {
        lowerBound = 0;
        upperBound = Integer.MAX_VALUE;
        threshold = 0;

        currentContext = new HashMap<>();
        childrenLowerBounds = new ArrayList<>(children);
        childrenUpperBounds = new ArrayList<>(children);
        childrenThresholds = new ArrayList<>(children);

        initializeBounds(children, domain);
    }
    
    private void initializeBounds(int children, int domain) {
        for (int i = 0; i < children; i++) {
            childrenLowerBounds.add(new ArrayList<>());
            List<Integer> child = childrenLowerBounds.get(i);

            for (int j = 0; j < domain; j++) {
                child.add(0);
                System.out.println("lb(" + i + "," + j + ")" + " = " + child.get(j));
            }
        }

        for (int i = 0; i < children; i++) {
            childrenUpperBounds.add(new ArrayList<>());
            List<Integer> child = childrenUpperBounds.get(i);

            for (int j = 0; j < domain; j++) {
                child.add(Integer.MAX_VALUE);
                System.out.println("ub(" + i + "," + j + ")" + " = " + child.get(j));
            }
        }

        for (int i = 0; i < children; i++) {
            childrenThresholds.add(new ArrayList<>());
            List<Integer> child = childrenThresholds.get(i);

            for (int j = 0; j < domain; j++) {
                child.add(0);
                System.out.println("t(" + i + "," + j + ")" + " = " + child.get(j));
            }
        }
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
}
