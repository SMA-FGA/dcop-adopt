package invariants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import messages.ThresholdMessage;
import models.NodeAgentData;
import node.sendMessageBehaviour;

public class maintainAllocationInvariantBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = -1528722360569048677L;
	private NodeAgentData data;
    private int currentValue;
    private List<List<Integer>> childrenThresholdsList;
    private List<List<Integer>> childrenUpperBoundsList;
    private List<List<Integer>> childrenLowerBoundsList;

    public maintainAllocationInvariantBehaviour(Agent a, NodeAgentData data) {
        super(a);
        this.data = data;
        currentValue = data.getCurrentValue();
        childrenThresholdsList = data.getChildrenThresholds();
        childrenUpperBoundsList = data.getChildrenUpperBounds();
        childrenLowerBoundsList = data.getChildrenLowerBounds();
    }

    private int getThresholdsSum() {
        int childrenThresholds = 0;

        for (int childThreshold : childrenThresholdsList.get(currentValue)) {
            childrenThresholds += childThreshold;
        }

        return childrenThresholds;
    }

    private void incrementChildWithLowThreshold() {
        for (int i = 0; i < data.getChildren().size(); i++) {
            int childUpperBound = childrenUpperBoundsList.get(currentValue).get(i);
            int childThreshold = childrenThresholdsList.get(currentValue).get(i);

            if (childUpperBound > childThreshold) {
                data.setChildThreshold(currentValue, i, childThreshold++);
                return;
            }
        }
    }

    private void decrementChildWithHighThreshold() {
        for (int i = 0; i < data.getChildren().size(); i++) {
            int childLowerBound = childrenLowerBoundsList.get(currentValue).get(i);
            int childThreshold = childrenThresholdsList.get(currentValue).get(i);

            if (childLowerBound < childThreshold) {
                data.setChildThreshold(currentValue, i, childThreshold--);
                return;
            }
        }
    }

    @Override
    public void action() {
        System.out.println("[INV MAIB   ] " + myAgent.getLocalName() +
                           " starting maintain allocation invariant");
        List<AID> childrenList = data.getChildren();

        if (data.getChildren().size() > 0) {
            int cost = data.getLocalCostForVariable(currentValue);

            while (data.getThreshold() > cost + getThresholdsSum()) {
                incrementChildWithLowThreshold();
            }

            while (data.getThreshold() < cost + getThresholdsSum()) {
                decrementChildWithHighThreshold();
            }

            for (int i = 0; i < childrenList.size(); i++) {
                int valueToSend = childrenThresholdsList.get(currentValue).get(i);

                List<AID> childToSend = new ArrayList<>();
                childToSend.add(childrenList.get(i));

                myAgent.addBehaviour(new sendMessageBehaviour(myAgent,
                        data, new ThresholdMessage(valueToSend, data.getCurrentContext()), childToSend));
            }
        }

    }
}
