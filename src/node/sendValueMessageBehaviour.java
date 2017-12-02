package node;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import models.NodeAgentData;

public class sendValueMessageBehaviour extends OneShotBehaviour {
	
	private static final long serialVersionUID = 2659869091649149638L;
	NodeAgentData data = new NodeAgentData();
	
	public sendValueMessageBehaviour(Agent a, NodeAgentData data) {
        super(a);
        this.data = data;
    }
	
	@Override
	public void action() {
					
		ACLMessage valueMessage = new ACLMessage(ACLMessage.INFORM);
		for(AID lowerNeighbour : data.getLowerNeighbours()) {
			valueMessage.addReceiver(lowerNeighbour);
			System.out.println("[SEND VALUE ] "+myAgent.getLocalName()+" send value message to: "+lowerNeighbour.getLocalName());
		}
		valueMessage.setContent(""+data.getChosenValue());
		
		try {
			myAgent.send(valueMessage);
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
}