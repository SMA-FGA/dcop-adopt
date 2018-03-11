package node;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.AdoptMessage;
import messages.MessageTypes;
import models.NodeAgentData;

public class receiveMessageBehaviour extends CyclicBehaviour implements MessageTypes {
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
                    myAgent.addBehaviour(new handleValueMessageBehaviour(myAgent, data, message));
                    break;
                case COST_MESSAGE:
                    break;
                case THRESHOLD_MESSAGE:
                    break;
                case TERMINATE_MESSAGE:
                    break;
            }
        } else {
            block();
        }
    }
}
