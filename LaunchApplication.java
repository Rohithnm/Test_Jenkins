package Utility;

import org.sikuli.script.App;

/**
* This Class contains method to launch given application.
* @author Rohith Mallikarjuna - Charter
*/
public class LaunchApplication {
	
	/**
	* This method will launch the given application
	* @param appName String - application name
	* @return true, if successful
	*/
	public static boolean launchApp(String appName){
		boolean retval = true;
		App.focus(appName);
		return retval;
	}
}
