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
        children.add("x2");

        Object x1Args[] = new Object[1];
        x1Args[0] = children.toArray(new String[]{});

        children.clear();
        children.add("x3");
        children.add("x4");

        Object x2Args[] = new Object[1];
        x2Args[0] = children.toArray(new String[]{});

        children.clear();

        Object x3Args[] = new Object[1];
        x3Args[0] = children.toArray(new String[]{});

        Object x4Args[] = new Object[1];
        x4Args[0] = children.toArray(new String[]{});

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
        takeDown();
    }
}
