package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import models.DcopAgentData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DcopAgent extends Agent {
    private DcopAgentData data;

    @Override
    protected void setup() {
        List<String> childrenNames;
        List<String> lowerNeighboursNames;
        int domain;
        Object[] setupArgs = getArguments();

        childrenNames = Arrays.asList((String[])setupArgs[0]);
        lowerNeighboursNames = Arrays.asList((String[])setupArgs[1]);
        domain = (int) setupArgs[2];
        System.out.println("[DOMAIN     ] "+domain);

        data = new DcopAgentData(childrenNames.size(), domain);
        System.out.println("[CREATE     ] Agent " + getLocalName() + " was created.");

        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());

        /*
         * Registers itself on the DF as parent of each name received as
         * argument. These services will be used so that each agent can find
         * the AID of its respective father after all DCOP agents
         * have been generated.
         */
        for (String childName : childrenNames) {
            ServiceDescription parentOfService = new ServiceDescription();
            parentOfService.setName(getLocalName());
            parentOfService.setType("parent-of-" + childName);
            description.addServices(parentOfService);

            System.out.println("[CREATE     ] Register "+getLocalName()+" as "+parentOfService.getType());
        }

        /*
         * Also registers itself with its own name, so that parents can search
         * for their children and lower neighbours.
         */
        ServiceDescription agentService = new ServiceDescription();
        agentService.setName(getLocalName());
        agentService.setType("agent-" + getLocalName());
        description.addServices(agentService);

        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new searchForParentBehaviour(this, 5000));
        addBehaviour(new searchForChildrenBehaviour(this, 5000, childrenNames));
        addBehaviour(new searchForLowerNeighboursBehaviour(this, 5000, lowerNeighboursNames));
        addBehaviour(new initialize(this, 7000));
        addBehaviour(new receiveValueMessage(this));
    }

    private class initialize extends WakerBehaviour {
    	
		private static final long serialVersionUID = -2905010008367981124L;

		public initialize(Agent a, long period) {
    		super(a, period);
    	}
    	
		@Override
    	public void onWake() {
			System.out.println("[INITIALIZE ]" +myAgent.getLocalName()+" starting initialize procedure");
			
			data.setChosenValue(1); // to do: di <- d that minimizes LB(d) 
			addBehaviour(new backTrack(myAgent));
    	}
    	
    	
    }
    
    private class backTrack extends OneShotBehaviour {
    	
    	static final long serialVersionUID = 6074892213023310464L;

		public backTrack(Agent a) {
            super(a);
        }

		@Override
		public void action() {
			//simple backTrack implementation
			System.out.println("[BACK TRACK ]" +myAgent.getLocalName()+" starting backTrack procedure");
			addBehaviour(new sendValueMessage(myAgent));			
		}
    	
    }
    
    private class sendValueMessage extends OneShotBehaviour {
    	
    	private static final long serialVersionUID = 2659869091649149638L;
    	
    	public sendValueMessage(Agent a) {
            super(a);
        }
		
		@Override
		public void action() {
						
			ACLMessage valueMessage = new ACLMessage(ACLMessage.INFORM);
			for(AID lowerNeighbour : data.getLowerNeighbours()) {
				valueMessage.addReceiver(lowerNeighbour);
				System.out.println("[SEND VALUE ] "+getLocalName()+" send value message to: "+lowerNeighbour.getLocalName());
			}
			valueMessage.setContent(""+data.getChosenValue());
			
			try {
				myAgent.send(valueMessage);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
    }
    
    private class receiveValueMessage extends CyclicBehaviour {
    	
    	private static final long serialVersionUID = -6895391790742950856L;
    	
    	public receiveValueMessage(Agent a) {
            super(a);
        }

		@Override
		public void action() {
			ACLMessage message = receive() ;
			
			if(message != null) {
				System.out.println("[REC VALUE  ] "+getLocalName()+" receive value message: " + message.getContent());
			}else {
				block();
			}
		}
    }
    
       
    private class searchForParentBehaviour extends WakerBehaviour {
    	
		private static final long serialVersionUID = -7370214749961979377L;

		public searchForParentBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onWake() {
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription serviceTemplate = new ServiceDescription();
            serviceTemplate.setType("parent-of-" + getLocalName());
            template.addServices(serviceTemplate);

            DFAgentDescription []resultSearch = null;

            try {
                resultSearch = DFService.search(myAgent, template);
            } catch (FIPAException e) {
                e.printStackTrace();
            }

            if (resultSearch.length != 0) {
                data.setParent(resultSearch[0].getName());
                System.out.println("[PARENT     ] Found parent for " + getLocalName() + ": " + data.getParent().getLocalName());
            }
        }
    }
    

    private class searchForChildrenBehaviour extends WakerBehaviour {
    	
		private static final long serialVersionUID = -3081227894323949053L;
		List<String> childrenNames;

        public searchForChildrenBehaviour(Agent a, long period, List<String> childrenNames) {
            super(a, period);
            this.childrenNames = childrenNames;
        }

        @Override
        protected void onWake() {
            for (String childName : childrenNames) {
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription serviceTemplate = new ServiceDescription();
                serviceTemplate.setType("agent-" + childName);
                template.addServices(serviceTemplate);

                DFAgentDescription []resultSearch = null;

                try {
                    resultSearch = DFService.search(myAgent, template);
                } catch (FIPAException e) {
                    e.printStackTrace();
                }

                if (resultSearch.length != 0) {
                    data.setChild(resultSearch[0].getName());
                    System.out.println("[CHILD      ] Registering agent " +
                                        resultSearch[0].getName().getLocalName() +
                                        " as " + getLocalName() + "'s child");
                }
            }
        }
    }
    

    private class searchForLowerNeighboursBehaviour extends WakerBehaviour {
    	
		private static final long serialVersionUID = 2788042325314110781L;
		List<String> lowerNeighboursNames;

        public searchForLowerNeighboursBehaviour(Agent a, long period, List<String> lowerNeighboursNames) {
            super(a, period);
            this.lowerNeighboursNames = lowerNeighboursNames;
        }

        @Override
        protected void onWake() {
            for (String lowerNeighbourName : lowerNeighboursNames) {
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription serviceTemplate = new ServiceDescription();
                serviceTemplate.setType("agent-" + lowerNeighbourName);
                template.addServices(serviceTemplate);

                DFAgentDescription []resultSearch = null;

                try {
                    resultSearch = DFService.search(myAgent, template);
                } catch (FIPAException e) {
                    e.printStackTrace();
                }

                if (resultSearch.length != 0) {
                    data.setLowerNeighbour(resultSearch[0].getName());
                    System.out.println("[NEIGH      ] Registering agent " +
                            resultSearch[0].getName().getLocalName() +
                            " as " + getLocalName() + "'s lower neighbour");
                }
            }
        }
    }
}