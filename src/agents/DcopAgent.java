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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DcopAgent extends Agent {
	private static final long serialVersionUID = 783786161678161415L;
	private DcopAgentData data;

    @Override
    protected void setup() {
        
    	System.out.println("[CREATE     ] Agent " + getLocalName() + " was created.");
        data = new DcopAgentData();
        Object[] setupArgs = getArguments();
        data.setChildrenNames(Arrays.asList((String[])setupArgs[0]));
        data.setLowerNeighboursNames(Arrays.asList((String[])setupArgs[1]));
        data.setDomain(Arrays.asList((Integer[])setupArgs[2]));
        data.setUpperNeighboursNames(Arrays.asList((String[])setupArgs[3]));
        data.setConstraints();
        
        System.out.println("[DOMAIN     ] "+data.getDomain());
        System.out.println("[CONSTRAINTS] "+data.getConstraints());

        addBehaviour(new registerChilrenOnDFBehaviour(this));
        addBehaviour(new searchForParentBehaviour(this, 5000));
        addBehaviour(new searchForChildrenBehaviour(this, 5000));
        addBehaviour(new searchForLowerNeighboursBehaviour(this, 5000));
        addBehaviour(new initialize(this, 7000));
    }

    private class initialize extends WakerBehaviour {
    	
		private static final long serialVersionUID = -2905010008367981124L;

		public initialize(Agent a, long period) {
    		super(a, period);
    	}
    	
		@Override
    	public void onWake() {
			System.out.println("[INITIALIZE ]" +myAgent.getLocalName()+" starting initialize procedure");
			data.setLowerBound(0);
	        data.setUpperBound(Integer.MAX_VALUE);
	        data.setThreshold(0);
	        data.setCurrentContext(new HashMap<>());		
	        
	        // Initialize children lower bounds
	        List<List<Integer>> childrenLowerBounds = new ArrayList<>();
	        for (int i = 0; i < data.getDomain().size(); i++) {
	            List<Integer> childrenLowerBoundsForDomain = new ArrayList<>();
	            
	            for(int j = 0; j < data.getChildren().size(); j++) {
	            	childrenLowerBoundsForDomain.add(0);
	            	System.out.println("[LOWER BOUND] lb(" + i + "," + j + ") = " + childrenLowerBoundsForDomain.get(j));
	            }
	            
	            childrenLowerBounds.add(childrenLowerBoundsForDomain);
	        }
	        data.setChildrenLowerBounds(childrenLowerBounds);
	        
	        // Initialize children upper bounds
	        List<List<Integer>> childrenUpperBounds = new ArrayList<>();
	        for (int i = 0; i < data.getDomain().size(); i++) {
	            List<Integer> childrenUpperBoundsForDomain = new ArrayList<>();
	            
	            for(int j = 0; j < data.getChildren().size(); j++) {
	            	childrenUpperBoundsForDomain.add(Integer.MAX_VALUE);
	            	System.out.println("[UPPER BOUND] ub(" + i + "," + j + ") = " + childrenUpperBoundsForDomain.get(j));
	            }
	            
	            childrenLowerBounds.add(childrenUpperBoundsForDomain);
	        }
	        data.setChildrenUpperBounds(childrenUpperBounds);
	        
	        // Initialize children thresholds
	        List<List<Integer>> childrenThresholds = new ArrayList<>();
	        for (int i = 0; i < data.getDomain().size(); i++) {
	            List<Integer> childrenThresholdsForDomain = new ArrayList<>();
	            
	            for(int j = 0; j < data.getChildren().size(); j++) {
	            	childrenThresholdsForDomain.add(0);
	            	System.out.println("[TRHESHOLD  ] t(" + i + "," + j + ") = " + childrenThresholdsForDomain.get(j));
	            }
	            
	            childrenThresholds.add(childrenThresholdsForDomain);
	        }
	        data.setChildrenThresholds(childrenThresholds);
	        
	        // Initialize children contexts
	        List<List<Map<String, Integer>>> childrenContexts = new ArrayList<>();
	        for (int i = 0; i < data.getDomain().size(); i++) {
	        	List<Map<String, Integer>> childrenContextsForDomain = new ArrayList<>();
	            
	            for(int j = 0; j < data.getChildren().size(); j++) {
	            	childrenContextsForDomain.add(new HashMap<String, Integer>());
	            	System.out.println("[CONTEXT    ] context(" + i + "," + j + ") = " + childrenContextsForDomain.get(j));
	            }
	            
	            childrenContexts.add(childrenContextsForDomain);
	        }
	        data.setChildrenContexts(childrenContexts);
	        
	        
			data.setChosenValue(data.getDomain().get(0)); // to do: di <- d that minimizes LB(d) 
			
			addBehaviour(new receiveValueMessage(myAgent));
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
			System.out.println("[BACK TRACK ] "+myAgent.getLocalName()+" starting backTrack procedure");
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
    
    public class registerChilrenOnDFBehaviour extends OneShotBehaviour {

		private static final long serialVersionUID = 1L;
		
		public registerChilrenOnDFBehaviour(Agent a) {
			super(a);
		}

		@Override
		public void action() {
	        DFAgentDescription description = new DFAgentDescription();
	        //description.setName(getAID());

	        /*
	         * Registers itself on the DF as parent of each name received as
	         * argument. These services will be used so that each agent can find
	         * the AID of its respective father after all DCOP agents
	         * have been generated.
	         */
	        for (String childName : data.getChildrenNames()) {
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
	            DFService.register(myAgent, description);
	        } catch (FIPAException e) {
	            e.printStackTrace();
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

        public searchForChildrenBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onWake() {
            for (String childName : data.getChildrenNames()) {
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

        public searchForLowerNeighboursBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onWake() {
            for (String lowerNeighbourName : data.getLowerNeighboursNames()) {
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