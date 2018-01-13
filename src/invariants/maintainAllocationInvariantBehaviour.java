package invariants;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import messages.ThresholdMessage;
import models.NodeAgentData;
import node.NodeAgent;
import node.sendMessageBehaviour;

import java.util.ArrayList;
import java.util.List;

public class maintainAllocationInvariantBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = -1528722360569048677L;
	NodeAgentData data;

    public maintainAllocationInvariantBehaviour(Agent a, NodeAgentData data) {
        super(a);
        this.data = data;
    }

    @Override
    public void action() {
        System.out.println("[INV MAIB   ] " + myAgent.getLocalName() +
                           " starting maintain allocation invariant");

        int childrenThresholds = 0;
        int currentValue = data.getCurrentValue();
        List<List<Integer>> childrenThresholdsList = data.getChildrenThresholds();
        List<List<Integer>> childrenUpperBoundsList = data.getChildrenUpperBounds();
        List<List<Integer>> childrenLowerBoundsList = data.getChildrenLowerBounds();
        List<AID> childrenList = data.getChildren();

        for (int childThreshold : childrenThresholdsList.get(currentValue)) {
            childrenThresholds += childThreshold;
        }

        while (data.getThreshold() > currentValue + childrenThresholds) {
            for (int i = 0; i < childrenThresholdsList.get(currentValue).size(); i++) {
                int childUpperBound = childrenUpperBoundsList.get(currentValue).get(i);
                int childThreshold = childrenThresholdsList.get(currentValue).get(i);

                if (childUpperBound > childThreshold) {
                    data.setChildThreshold(currentValue, i, childThreshold++);
                }
            }
        }

        while (data.getThreshold() < currentValue + childrenThresholds) {
            for (int i = 0; i < childrenThresholdsList.get(currentValue).size(); i++) {
                int childLowerBound = childrenLowerBoundsList.get(currentValue).get(i);
                int childThreshold = childrenThresholdsList.get(currentValue).get(i);

                if (childLowerBound < childThreshold) {
                    data.setChildThreshold(currentValue, i, childThreshold--);
                }
            }
        }

        for (int i = 0; i < childrenList.size(); i++) {
            int valueToSend = childrenThresholdsList.get(currentValue).get(i);

            List<AID> childToSend = new ArrayList<>();
            childToSend.add(childrenList.get(i));

            myAgent.addBehaviour(new sendMessageBehaviour(myAgent,
                    data, new ThresholdMessage(valueToSend), childToSend));
        }
    }
}
