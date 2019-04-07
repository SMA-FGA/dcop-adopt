package graph;

import java.util.List;

public class Domain {
	private String id;
	private List<Integer> domainArray;
	
	public Domain(String id, List<Integer> domainArray){
		this.id = id;
		this.domainArray = domainArray;
	}

	public String getID() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Integer> getDomainArray() {
		return domainArray;
	}

	public void setDomainArray(List<Integer> domainArray) {
		this.domainArray = domainArray;
	}
}
