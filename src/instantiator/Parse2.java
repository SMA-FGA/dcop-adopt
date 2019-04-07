package instantiator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import graph.Domain;
import graph.DomainsList;

public class Parse2 {

	public static void main(String[] args) {
		String filePath = "./DCOPJson/graphTaylor.json";
		File file = new File(filePath);
		
		String fileContent = null;
		try {
			fileContent = FileUtils.readFileToString(file, "utf-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONObject object = new JSONObject(fileContent);
        
        JSONArray domains = (JSONArray) object.get("domains");
        
        DomainsList allDomains = new DomainsList();
        for (Object domain : domains) {
        	JSONObject d = (JSONObject) domain;
        	
        	System.out.println(d.get("value"));
        	
        	ArrayList<Integer> listdata = new ArrayList<Integer>();
        	
        	JSONArray jArray = (JSONArray) d.get("value");
        	if (jArray != null) {
        	   for (int i=0;i<jArray.length();i++){ 
        	    listdata.add(jArray.getInt(i));
        	   } 
        	}
        	
        	allDomains.addDomain(new Domain((String) d.get("id"), listdata));
		}

	}

}
