package models;

import org.junit.Assert;
import org.junit.Test;

import graph.Constraint;

import java.util.*;

import static org.junit.Assert.*;

public class NodeAgentDataTest {

    /*
     * Uses values presented in adopt's article example.
     */
    @Test
    public void getLocalCostForVariable() throws Exception {
        NodeAgentData data = new NodeAgentData();

        List<String> upperNeighbours = new ArrayList<>();
        upperNeighbours.add("x1");
        upperNeighbours.add("x2");
        data.setUpperNeighboursNames(upperNeighbours);

        List<String> lowerNeighbours = new ArrayList<>();
        data.setLowerNeighboursNames(lowerNeighbours);
        
        Map<String, Integer> currentContext = new HashMap<>();
        currentContext.put("x1", 0);
        currentContext.put("x2", 0);
        data.setCurrentContext(currentContext);
        
        Integer[][] constraintArray = new Integer[][]{{1,2},{2,0}};
        Constraint constraint = new Constraint("c1", constraintArray);
        Map<String, Constraint> constraints = new HashMap<>();
        constraints.put("x2", constraint);
        constraints.put("x2", constraint);
        data.setConstraints(constraints);

        int localCost = data.getLocalCostForVariable(0);
        Assert.assertEquals(2, localCost);
    }

    @Test
    public void IsContextCompatibleText_ShouldReturnTrueWhenContextsAreEqual() {
        NodeAgentData data = new NodeAgentData();

        Map<String, Integer> initialContext = new HashMap<>();
        Map<String, Integer> receivedContext = new HashMap<>();

        initialContext.put("x1", 1);
        initialContext.put("x2", 0);

        receivedContext.put("x1", 1);
        receivedContext.put("x2", 0);

        data.setCurrentContext(initialContext);

        boolean result = data.isContextCompatible(receivedContext);
        Assert.assertTrue(result);
    }

    @Test
    public void IsContextCompatibleText_ShouldReturnTrueWhenCommonValuesAreEqual() {
        NodeAgentData data = new NodeAgentData();

        Map<String, Integer> initialContext = new HashMap<>();
        Map<String, Integer> receivedContext = new HashMap<>();

        initialContext.put("x1", 1);
        initialContext.put("x2", 0);

        receivedContext.put("x1", 1);

        data.setCurrentContext(initialContext);

        boolean result = data.isContextCompatible(receivedContext);
        Assert.assertTrue(result);
    }

    @Test
    public void IsContextCompatibleText_ShouldReturnTrueWhenCurrentContextIsEmpty() {
        NodeAgentData data = new NodeAgentData();

        Map<String, Integer> initialContext = new HashMap<>();
        Map<String, Integer> receivedContext = new HashMap<>();

        receivedContext.put("x1", 1);

        data.setCurrentContext(initialContext);

        boolean result = data.isContextCompatible(receivedContext);
        Assert.assertTrue(result);
    }

    @Test
    public void IsContextCompatibleText_ShouldReturnTrueWhenReceivedContextIsEmpty() {
        NodeAgentData data = new NodeAgentData();

        Map<String, Integer> initialContext = new HashMap<>();
        Map<String, Integer> receivedContext = new HashMap<>();

        initialContext.put("x1", 0);

        data.setCurrentContext(initialContext);

        boolean result = data.isContextCompatible(receivedContext);
        Assert.assertTrue(result);
    }

    @Test
    public void IsContextCompatibleText_ShouldReturnFalseWhenCommonValuesAreDifferent() {
        NodeAgentData data = new NodeAgentData();

        Map<String, Integer> initialContext = new HashMap<>();
        Map<String, Integer> receivedContext = new HashMap<>();

        initialContext.put("x1", 1);
        initialContext.put("x2", 0);

        receivedContext.put("x1", 0);

        data.setCurrentContext(initialContext);

        boolean result = data.isContextCompatible(receivedContext);
        Assert.assertFalse(result);
    }
}