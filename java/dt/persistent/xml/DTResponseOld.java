package dt.persistent.xml;

import java.util.ArrayList;

public class DTResponseOld {
	private ArrayList<String> response = new ArrayList<String>();

	public String getResponseText(int i) {
		return response.get(i);
	}

	public String getAllResponseText() {
		if (response.size()==0) return "[NA]";
		String result = response.get(0);
		
		for(int i=1;i<response.size();i++)
			result += " " + response.get(i);
		
		return result;
	}

	public void addResponseText(String responseTxt) {
		
		if (responseTxt.length() ==0) return;
		
		//make sure we split the #WAIT# mark from the input
		if (responseTxt.equals("#WAIT#")) {
			if (response.size()>0 && !response.get(response.size()-1).equals("#WAIT#")) response.add("#WAIT#");
		}
		else if (responseTxt.contains("#WAIT#"))
		{
			String[] responses = responseTxt.split("#WAIT#");

			response.add(responses[0]);
			for (int i=1;i<responses.length;i++){
				if (response.size()>0 && !response.get(response.size()-1).equals("#WAIT#")) response.add("#WAIT#");
				response.add(responses[i]);
			}
		}
		else response.add(responseTxt);
	}
	
	public void setResponseArray(ArrayList<String> responseTxt) {
		response = responseTxt;
	}

	public int getResponseCount() {
		return response.size();
	}
	
	public void clearResponse() {
		response = new ArrayList<String>();
	}
}
