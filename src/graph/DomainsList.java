package graph;

import java.util.ArrayList;
import java.util.List;

public class DomainsList {
	private List<Domain> domains;
	
	public DomainsList() {
		domains = new ArrayList<Domain>();
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}
	
	public void addDomain(Domain domain) {
		domains.add(domain);
	}
	
	public Domain getNodeByID(String id) {
		//to do: use hash instead list
		for (Domain domain : domains) {
			if(domain.getID().equals(id)) {
				return domain;
			}
		}
		return null;
	}
}
