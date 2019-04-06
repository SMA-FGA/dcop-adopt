package agent;

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

        if (message != null && !data.wasKilled()) {
            try {
            	AdoptMessage adoptMessage = (AdoptMessage) message.getContentObject();
                
                handleMessage handleMessage;
                switch (adoptMessage.getMessageType()) {
                    case VALUE_MESSAGE:
                    	handleMessage = new handleValueMessage();
                    	handleMessage.handle(myAgent, data, message);
                        break;
                    case COST_MESSAGE:
                    	handleMessage = new handleCostMessage();
                    	handleMessage.handle(myAgent, data, message);
                        break;
                    case THRESHOLD_MESSAGE:
                    	handleMessage = new handleThresholdMessage();
                    	handleMessage.handle(myAgent, data, message);
                        break;
                    case TERMINATE_MESSAGE:
                    	handleMessage = new handleTerminateMessage();
                    	handleMessage.handle(myAgent, data, message);
                        break;
                }
            } catch (UnreadableException ex) {
                ex.printStackTrace();
            }
            
            
        } else {
            block();
        }
    }
}
