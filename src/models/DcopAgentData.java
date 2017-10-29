package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DcopAgentData {
    private int lowerBound;
    private int upperBound;
    private int threshold;

    private Map<String, Integer> currentContext;
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

        initializeBounds(domain);
    }

    private void initializeBounds(int domain) {
        for (List<Integer> child : childrenLowerBounds) {
            for (int i = 0; i < domain; i++) {
                child.add(0);
            }
        }

        for (List<Integer> child : childrenUpperBounds) {
            for (int i = 0; i < domain; i++) {
                child.add(Integer.MAX_VALUE);
            }
        }

        for (List<Integer> child : childrenThresholds) {
            for (int i = 0; i < domain; i++) {
                child.add(0);
            }
        }
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
