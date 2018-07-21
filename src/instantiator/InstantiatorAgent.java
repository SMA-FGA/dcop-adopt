package instantiator;

import jade.core.Agent;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/*
 * Creates the agents that will be used in the DCOP.
 * In the moment uses hardcoded values, but should be
 * able to create agents from the DFS tree in the future.
 */
public class InstantiatorAgent extends Agent {

	private static final long serialVersionUID = -7764996125444199018L;

	/*
     * Each created agent contains a list with the names of its children.
     */
    @Override
    protected void setup() {

        /*
         * For some unfortunate reason, nothing happens when passing the
         * ArrayList as an argument in the agent's creation. So we convert it
         * to a regular array, then convert it back once inside the DcopAgent.
         */
        List<String> children = new ArrayList<>();
        List<String> lowerNeighbours = new ArrayList<>();
        List<String> upperNeighbours = new ArrayList<>();
        List<Integer> domain = new ArrayList<>(); // In the example, the domain's range is [0, 1]

        // Constraints map is as follows:
        // (di, dj) = f(di, dj)
        // (0, 0) = 1
        // (0, 1) = 2
        // (1, 0) = 2
        // (1, 1) = 0
        List<List<Integer>> constraints = new Vector<>();
        List<Integer> constraintLine1 = new Vector<>();
        List<Integer> constraintLine2 = new Vector<>();
        constraintLine1.add(1);
        constraintLine1.add(2);
        constraintLine2.add(2);
        constraintLine2.add(0);
        constraints.add(constraintLine1);
        constraints.add(constraintLine2);

        // Add arguments to create x1
        children.add("x2");
        lowerNeighbours.add("x2");
        lowerNeighbours.add("x3");
        domain.add(0);
        domain.add(1);
        Object x1Args[] = new Object[5];
        x1Args[0] = children.toArray(new String[]{});
        x1Args[1] = lowerNeighbours.toArray(new String[]{});
        x1Args[2] = domain.toArray(new Integer[]{});
        x1Args[3] = upperNeighbours.toArray(new String[]{});
        x1Args[4] = constraints;
        children.clear();
        lowerNeighbours.clear();
        upperNeighbours.clear();
        domain.clear();

        // Add arguments to create x2
        upperNeighbours.add("x1");
        children.add("x3");
        lowerNeighbours.add("x3");
        children.add("x4");
        lowerNeighbours.add("x4");
        domain.add(0);
        domain.add(1);
        Object x2Args[] = new Object[5];
        x2Args[0] = children.toArray(new String[]{});
        x2Args[1] = lowerNeighbours.toArray(new String[]{});
        x2Args[2] = domain.toArray(new Integer[]{});
        x2Args[3] = upperNeighbours.toArray(new String[]{});
        x2Args[4] = constraints;
        children.clear();
        lowerNeighbours.clear();
        upperNeighbours.clear();
        domain.clear();

       // Add arguments to create x3
        upperNeighbours.add("x2");
        upperNeighbours.add("x1");
        domain.add(0);
        domain.add(1);
        Object x3Args[] = new Object[5];
        x3Args[0] = children.toArray(new String[]{});
        x3Args[1] = lowerNeighbours.toArray(new String[]{});
        x3Args[2] = domain.toArray(new Integer[]{});
        x3Args[3] = upperNeighbours.toArray(new String[]{});
        x3Args[4] = constraints;
        children.clear();
        lowerNeighbours.clear();
        upperNeighbours.clear();
        domain.clear();

        // Add arguments to create x4
        upperNeighbours.add("x2");
        domain.add(0);
        domain.add(1);
        Object x4Args[] = new Object[5];
        x4Args[0] = children.toArray(new String[]{});
        x4Args[1] = lowerNeighbours.toArray(new String[]{});
        x4Args[2] = domain.toArray(new Integer[]{});
        x4Args[3] = upperNeighbours.toArray(new String[]{});
        x4Args[4] = constraints;
        children.clear();
        lowerNeighbours.clear();
        upperNeighbours.clear();
        domain.clear();

        try {
            getContainerController().createNewAgent("x1", "node.NodeAgent", x1Args).start();
            getContainerController().createNewAgent("x2", "node.NodeAgent", x2Args).start();
            getContainerController().createNewAgent("x3", "node.NodeAgent", x3Args).start();
            getContainerController().createNewAgent("x4", "node.NodeAgent", x4Args).start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

        // We won't be needing this agent during the actual algorithm's execution
        doDelete();
    }
}
