package graph;

import java.util.HashMap;
import java.util.Map;

public class DomainsList {
	private Map<String, Domain> domains;
	
	public DomainsList() {
		this.domains = new HashMap<String, Domain>();
	}

	public void addDomain(Domain domain) {
		this.domains.put(domain.getID(), domain);
	}
	
	public Domain getNodeByID(String id) {
		return this.domains.get(id);
	}
}
