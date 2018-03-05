package invariants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private Map<String, List<Integer>> childrenThresholdsList;
    private Map<String, List<Integer>> childrenUpperBoundsList;
    private Map<String, List<Integer>> childrenLowerBoundsList;

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

        for (Map.Entry<String, List<Integer>> child : childrenThresholdsList.entrySet()) {
            childrenThresholds += child.getValue().get(currentValue);
        }

        return childrenThresholds;
    }

    private void incrementChildWithLowThreshold() {
        for (Map.Entry<String, List<Integer>> child : childrenThresholdsList.entrySet()) {
            List<Integer> upperBoundsForChild = childrenUpperBoundsList.get(child.getKey());
            int childUpperBound = upperBoundsForChild.get(currentValue);
            int childThreshold = child.getValue().get(currentValue);

            if (childUpperBound > childThreshold) {
                data.setChildThreshold(currentValue, child.getKey(), childThreshold++);
                return;
            }
        }
    }

    private void decrementChildWithHighThreshold() {
        for (Map.Entry<String, List<Integer>> child : childrenThresholdsList.entrySet()) {
            List<Integer> lowerBoundsForChild = childrenLowerBoundsList.get(child.getKey());
            int childLowerBound = lowerBoundsForChild.get(currentValue);
            int childThreshold = child.getValue().get(currentValue);

            if (childLowerBound < childThreshold) {
                data.setChildThreshold(currentValue, child.getKey(), childThreshold--);
                return;
            }
        }
    }

    @Override
    public void action() {
        
        List<AID> childrenList = data.getChildren();
        

        if (data.getChildren().size() > 0) {
        	
        	System.out.println(">>> [INV MAIB   ] " + myAgent.getLocalName() +
                    " starting maintain allocation invariant "+
 				   " cost: "+data.getLocalCostForVariable(currentValue)+
 				   " t: "+data.getThreshold()+
 				   " t-sum: "+getThresholdsSum());
        	
            int cost = data.getLocalCostForVariable(currentValue);

            while (data.getThreshold() > cost + getThresholdsSum()) {
            	System.out.println("enter while mai 1 "+
            			" cost: "+data.getLocalCostForVariable(currentValue)+
      				   " t: "+data.getThreshold()+
      				   " t-sum: "+getThresholdsSum());
                incrementChildWithLowThreshold();
            }

            while (data.getThreshold() < cost + getThresholdsSum()) {
            	System.out.println("enter while mai 2 "+
            			" cost: "+data.getLocalCostForVariable(currentValue)+
      				   " t: "+data.getThreshold()+
      				   " t-sum: "+getThresholdsSum());
                decrementChildWithHighThreshold();
            }

            for (int i = 0; i < childrenList.size(); i++) {
                String child = childrenList.get(i).getLocalName();

                int valueToSend = childrenThresholdsList.get(child).get(currentValue);

                List<AID> childToSend = new ArrayList<>();
                childToSend.add(childrenList.get(i));

                myAgent.addBehaviour(new sendMessageBehaviour(myAgent,
                        data, new ThresholdMessage(valueToSend, data.getCurrentContext()), childToSend));
            }
        }

    }
}
