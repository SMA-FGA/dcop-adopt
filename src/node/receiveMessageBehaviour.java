package node;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.MessageTypes;
import models.NodeAgentData;

public class receiveMessageBehaviour extends CyclicBehaviour implements MessageTypes {

	private static final long serialVersionUID = 7797604769937095938L;
	private NodeAgentData data;

    public receiveMessageBehaviour(Agent a, NodeAgentData data) {
        super(a);
        this.data = data;
    }

    @Override
    public void action() {
        ACLMessage message = myAgent.receive();

        if (message != null) {
            AdoptMessage adoptMessage = null;

            try {
                adoptMessage = (AdoptMessage) message.getContentObject();
            } catch (UnreadableException ex) {
                ex.printStackTrace();
            }

            switch (adoptMessage.getMessageType()) {
                case VALUE_MESSAGE:
                	handleValueMessage handleValueMessage = new handleValueMessage();
                	handleValueMessage.handleValueMessageProcedure(myAgent, data, message);
                    break;
                case COST_MESSAGE:
                	handleCostMessage handleCostMessage = new handleCostMessage();
                	handleCostMessage.handleCostMessageProcedure(myAgent, data, message);
                    break;
                case THRESHOLD_MESSAGE:
                	handleThresholdMessage handleThresholdMessage = new handleThresholdMessage();
                	handleThresholdMessage.handleThresholdMessageProcedure(myAgent, data, message);
                    break;
                case TERMINATE_MESSAGE:
                	handleTerminateMessage handleTerminateMessage = new handleTerminateMessage();
                	handleTerminateMessage.handleTerminateMessageProcedure(myAgent, data, message);
                    break;
            }
        } else {
            block();
        }
    }
}
