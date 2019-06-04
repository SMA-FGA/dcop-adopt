package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import graph.Constraint;

public class DCOPAgentDataTest {

    /*
     * Uses values presented in adopt's article example.
     */
    @Test
    public void getLocalCostForVariable() throws Exception {
        DCOPAgentData data = new DCOPAgentData();

        List<String> upperNeighbours = new ArrayList<>();
        upperNeighbours.add("x1");
        upperNeighbours.add("x2");
        data.setUpperNeighboursNames(upperNeighbours);

        List<String> lowerNeighbours = new ArrayList<>();
        data.setLowerNeighboursNames(lowerNeighbours);
        
        Map<String, Integer> currentContext = new HashMap<>();
        currentContext.put("x1", 0);
        currentContext.put("x2", 1);
        data.setCurrentContext(currentContext);
        
        Integer[][] constraintArray = new Integer[][]{{1,2},{2,0}};
        Constraint constraint = new Constraint("c1", constraintArray);
        Map<String, Constraint> constraints = new HashMap<>();
        constraints.put("x1", constraint);
        constraints.put("x2", constraint);
        data.setConstraints(constraints);

        int localCost = data.getLocalCostForVariable(0);
        Assert.assertEquals(3, localCost); //2+1
    }

    @Test
    public void IsContextCompatibleText_ShouldReturnTrueWhenContextsAreEqual() {
        DCOPAgentData data = new DCOPAgentData();

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
        DCOPAgentData data = new DCOPAgentData();

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
        DCOPAgentData data = new DCOPAgentData();

        Map<String, Integer> initialContext = new HashMap<>();
        Map<String, Integer> receivedContext = new HashMap<>();

        receivedContext.put("x1", 1);

        data.setCurrentContext(initialContext);

        boolean result = data.isContextCompatible(receivedContext);
        Assert.assertTrue(result);
    }

    @Test
    public void IsContextCompatibleText_ShouldReturnTrueWhenReceivedContextIsEmpty() {
        DCOPAgentData data = new DCOPAgentData();

        Map<String, Integer> initialContext = new HashMap<>();
        Map<String, Integer> receivedContext = new HashMap<>();

        initialContext.put("x1", 0);

        data.setCurrentContext(initialContext);

        boolean result = data.isContextCompatible(receivedContext);
        Assert.assertTrue(result);
    }

    @Test
    public void IsContextCompatibleText_ShouldReturnFalseWhenCommonValuesAreDifferent() {
        DCOPAgentData data = new DCOPAgentData();

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