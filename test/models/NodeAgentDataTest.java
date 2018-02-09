package models;

import org.junit.Assert;
import org.junit.Test;

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

        List<List<Integer>> constraints = new Vector<>();
        List<Integer> constraintLine1 = new Vector<>();
        List<Integer> constraintLine2 = new Vector<>();
        constraintLine1.add(1);
        constraintLine1.add(2);
        constraintLine2.add(2);
        constraintLine2.add(0);
        constraints.add(constraintLine1);
        constraints.add(constraintLine2);
        data.setConstraints(constraints);

        int localCost = data.getLocalCostForVariable(0);
        Assert.assertEquals(2, localCost);
    }

}