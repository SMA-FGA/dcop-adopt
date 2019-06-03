package invariants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import agent.sendMessageBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.util.Logger;
import messages.ThresholdMessage;
import models.DCOPAgentData;

public class maintainAllocationInvariant implements maintainInvariant{
	private static final Logger LOGGER = Logger.getMyLogger(maintainAllocationInvariant.class.getName());
	
    private void incrementChildWithLowThreshold(DCOPAgentData data, int currentValue) {
        for (Map.Entry<String, List<Integer>> child : data.getChildrenThresholds().entrySet()) {
            List<Integer> upperBoundsForChild = data.getChildrenUpperBounds().get(child.getKey());
            int childUpperBound = upperBoundsForChild.get(currentValue);
            int childThreshold = child.getValue().get(currentValue);

            LOGGER.info("child: "+child.getKey()+"ub: "+childUpperBound+" t: "+childThreshold);
            if (childUpperBound > childThreshold) {
            	LOGGER.info("Enter if increment");
                data.setChildThreshold(currentValue, child.getKey(), childThreshold+1);
                return;
            }
        }
    }

    private void decrementChildWithHighThreshold(DCOPAgentData data, int currentValue) {
        for (Map.Entry<String, List<Integer>> child : data.getChildrenThresholds().entrySet()) {
            List<Integer> lowerBoundsForChild = data.getChildrenLowerBounds().get(child.getKey());
            int childLowerBound = lowerBoundsForChild.get(currentValue);
            int childThreshold = child.getValue().get(currentValue);

            LOGGER.info("child: "+child.getKey()+"lb: "+childLowerBound+" t: "+childThreshold);
            if (childLowerBound < childThreshold) {
            	LOGGER.info("Enter if decrement");
                data.setChildThreshold(currentValue, child.getKey(), childThreshold-1);
                return;
            }
        }
    }

    public void maintain(Agent myAgent, DCOPAgentData data) {
    	
    	LOGGER.setLevel(Level.ALL);
        List<AID> childrenList = data.getChildren();
        int currentValue = data.getCurrentValue();
        
        LOGGER.info("Agent "+myAgent.getLocalName()+" starting maitain allocation invariant");
        
        if (data.getChildren().size() > 0) {
        	
            int cost = data.getLocalCostForVariable(currentValue);
            int threshold = data.getThreshold();

            while (threshold > cost + data.getThresholdsSum(currentValue)) {
            	LOGGER.info("Enter while mai 1 "+
            			" cost: "+cost+
      				   " t: "+data.getThreshold()+
      				   " t-sum: "+data.getThresholdsSum(currentValue));
            	
                incrementChildWithLowThreshold(data, currentValue);
            }

            while (threshold < cost + data.getThresholdsSum(currentValue)) {
            	LOGGER.info("Enter while mai 2 "+
            			" cost: "+cost+
      				   " t: "+data.getThreshold()+
      				   " t-sum: "+data.getThresholdsSum(currentValue));
            	
                decrementChildWithHighThreshold(data, currentValue);
            }

            for (int i = 0; i < childrenList.size(); i++) {
                String child = childrenList.get(i).getLocalName();

                int valueToSend = data.getChildrenThresholds().get(child).get(currentValue);

                List<AID> childToSend = new ArrayList<>();
                childToSend.add(childrenList.get(i));

                myAgent.addBehaviour(new sendMessageBehaviour(myAgent,
                        data, new ThresholdMessage(valueToSend, data.getCurrentContext()), childToSend));
            }
        }

    }
}
