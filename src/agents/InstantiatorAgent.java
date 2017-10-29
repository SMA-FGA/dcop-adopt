package agents;

import jade.core.Agent;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

/*
 * Creates the agents that will be used in the DCOP.
 * In the moment uses hardcoded values, but should be
 * able to create agents from the DFS tree in the future.
 */
public class InstantiatorAgent extends Agent {

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
        int domain = 2; // In the example, the domain's range is [0, 1]


        children.add("x2");

        lowerNeighbours.add("x2");
        lowerNeighbours.add("x3");

        Object x1Args[] = new Object[3];
        x1Args[0] = children.toArray(new String[]{});
        x1Args[1] = lowerNeighbours.toArray(new String[]{});
        x1Args[2] = domain;

        children.clear();
        children.add("x3");
        children.add("x4");

        lowerNeighbours.clear();
        lowerNeighbours.add("x3");
        lowerNeighbours.add("x4");

        Object x2Args[] = new Object[3];
        x2Args[0] = children.toArray(new String[]{});
        x2Args[1] = lowerNeighbours.toArray(new String[]{});
        x2Args[2] = domain;

        children.clear();
        lowerNeighbours.clear();

        Object x3Args[] = new Object[3];
        x3Args[0] = children.toArray(new String[]{});
        x3Args[1] = lowerNeighbours.toArray(new String[]{});
        x3Args[2] = domain;

        Object x4Args[] = new Object[3];
        x4Args[0] = children.toArray(new String[]{});
        x4Args[1] = lowerNeighbours.toArray(new String[]{});
        x4Args[2] = domain;

        try {
            getContainerController().createNewAgent("x1",
                                                    "agents.DcopAgent",
                                                    x1Args).start();
            getContainerController().createNewAgent("x2",
                                                    "agents.DcopAgent",
                                                    x2Args).start();
            getContainerController().createNewAgent("x3",
                                                    "agents.DcopAgent",
                                                    x3Args).start();
            getContainerController().createNewAgent("x4",
                                                    "agents.DcopAgent",
                                                    x4Args).start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

        // We won't be needing this agent during the actual algorithm's execution
        doDelete();
    }
}
