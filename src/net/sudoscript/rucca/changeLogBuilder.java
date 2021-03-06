package net.sudoscript.rucca;

import java.util.ArrayList;
import java.util.List;

public class changeLogBuilder {
	
	List<String> changelog;
	
	public changeLogBuilder(){
		changelog = new ArrayList<String>();
		addToChangeLog("0.9.2", "No Changes yet! First build!");
		addToChangeLog("0.9.3", "Just some clean up in the code. Essentially the same as previous build.");
		addToChangeLog("1.0.0", "Bug Fix: Fixed Authentication Dectection. \n -Welcome to the Android Market!!");
		addToChangeLog("1.0.1", "Improve UI wording \n -More Security");
		addToChangeLog("1.0.3", "Fixed bugs related to changes in Android 4.2 \n -RUCCA now authenticates less often (Authenticated more times than needed)");
		
		//Add more changes above this comment
		changelog.add("Change Log:");
		changelog.toString();
	}
	
	private void addToChangeLog(String buildNo, String descript){
		changelog.add(" Build: " + buildNo + "\n -" + descript);
	}
	
	@Override
	public String toString(){
		String result = new String();
		for(String s : changelog){
			result =  s + "\n\n" + result; 
		}
		return result;
	}

}
