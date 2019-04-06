package solution;

import java.util.HashMap;
import java.util.Map;

import jade.core.Agent;

public class DCOPSolutionAgent extends Agent {

	private static final long serialVersionUID = -415680438462570144L;
	Map<String, Integer> agentsChoices = new HashMap<>();
	private static DCOPSolutionAgent instance;
	
	protected void setup() {
		System.out.println("DCOPSolution Agent initializing");
	}
	
	public void addAgentSolution(String agentName, Integer agentChoice) {
		this.agentsChoices.put(agentName, agentChoice);
		System.out.println("  >>> Solution: "+agentsChoices);
	}
	
	//this agent use singleton design pattern
	public static DCOPSolutionAgent getInstance() {
		if(instance == null) {
			instance = new DCOPSolutionAgent();
		}
		return instance;
	}
}
