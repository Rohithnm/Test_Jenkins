/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;
import org.sikuli.script.FindFailed;

import Utility.Utilities;
import Utility.Utilities.Controller;

/**
* This Class contains methods to navigate between channels.
* @author Rohith Mallikarjuna - Charter
*/
public class Navigate {
	
	/**
	* This method will fetch current channel Information from the given position.
	* @param screen_rect int[] - integer array with details of position of required info on screen
	* @return String - Information present on the screen is returned
	* @see Utilities.screenFunctions#extractTextFromROI(int[], boolean, boolean)
	*/
	public String retreive_channel_info(int[] screen_rect){
		Utilities.screenFunctions s = new Utilities().new screenFunctions();
		Controller ctrl = new Utilities().new Controller();
		Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
		String ret = null;
		ctrl.keyPress("Right");
		gen_utils.wait(2000);
		if (s.findScreenImage("img/all_channels_more_info.png")){
			ctrl.keyPress("Up");
			gen_utils.wait(2000);
			ctrl.keyPress("Enter");
			gen_utils.wait(10000);		 				
			try {
				ret = s.extractTextFromROI(screen_rect, false, true);
				System.out.println("Network Name: "+ret);
			} catch (FindFailed e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//gen_utils.wait(5000);
		ctrl.keyPress("b");
		gen_utils.wait(2000);
		return ret;
	}
	
	/**
	* This method will retrieve the Channel number
	* @param screen_rect int[] - integer array with details of position of required info on screen
	* @return String - returns Channel Number 
	*/
	public String retrieve_from_now_playing(int[] screen_rect){
		String ret = null;
		Utilities.screenFunctions screen = new Utilities().new screenFunctions();
		Utilities.Controller ctrl = new Utilities().new Controller();
		Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
		while (!screen.findScreenImage("img/now_playing.png",true)){
			ctrl.keyPress("Up");
		}
		gen_utils.wait(2000);		 				
		try {
			ret = screen.extractTextFromROI(screen_rect, false, false);
			System.out.println("Channel Num: "+ret);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
