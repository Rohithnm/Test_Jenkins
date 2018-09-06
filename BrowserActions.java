package Utility;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

/**
* This class contains methods to perform actions on Browser.
* @author AshithRaj Shetty - Charter
*/
public class BrowserActions {
	
	static Screen s = new Screen();
	static Utilities.screenFunctions screen = new Utilities().new screenFunctions();
	static Utilities.Controller ctrl = new Utilities().new Controller();
	static Utilities.General_Utils gen_utils = new Utilities().new General_Utils();

	/**
	* This method will launch the browser.
	* @param browserName String - browser to be launched
	* @return Boolean - true if browser launched successfully
	*/
	public static boolean launchBrowser(String browserName) {

		if (browserName == "Chrome") {
			s.type(Key.ESC, KeyModifier.CTRL);
			gen_utils.wait(2000);
			s.type("Google Chrome");
			s.type(Key.ENTER);
		}
		if(screen.findScreenImage("img/google_chrome_restore_pages.png")){
			gen_utils.wait(1000);
			s.type(Key.ESC);
		}
		int errCounter = 0;
		while (!screen.findScreenImage("img/google_chrome_launched.png")) {
			gen_utils.wait(1000);
			if (errCounter == 20) {
				return false;
			}
			errCounter++;
		}
		return true;
	}
	
	
	/**
	* This method will clear cookies stored in the browser.
	* @return Boolean - true if cookies are cleared successfully
	*/
	public static boolean clearAllCookies(){
		boolean retval = true;
		try{
			if (!gen_utils.isProcessRunning("chrome.exe")){
				BrowserActions.launchBrowser("Chrome");
			}
			s.keyDown(Key.CTRL+Key.SHIFT+Key.DELETE);
			gen_utils.wait(2000);
			s.keyUp(Key.CTRL+Key.SHIFT+Key.DELETE);
			gen_utils.wait(2000);
			BrowserActions.clickElement("img/google_chrome_clear_browsing_data_button.png");
			int errorCounter = 1;
			while(screen.findScreenImage("img/google_chrome_clear_browsing_data_button.png", true)){
				gen_utils.wait(1000);
				if (errorCounter==20){
					retval = false;
					return retval;
				}
				errorCounter ++;
			}
			s.type("W", Key.CTRL);
			//App.close("Google Chrome");
			return retval;
		}
		catch (Exception e){
			retval = false;
			App.close("Google Chrome");
			return retval;
		}
	}

	/**
	* This method will open given URL in the given browser
	* @param URL String - URL
	* @param browserName String - browser to be launched
	* @return Boolean - true if browser launched successfully
	*/
	public static boolean openURL(String URL, String browserName) {
		boolean browserLaunched = BrowserActions.launchBrowser(browserName);
		if (browserLaunched) {
			ctrl.keyPress(URL);
			gen_utils.wait(2000);
			ctrl.keyPress("Enter");
			return true;
		} else {
			return false;
		}
	}

	/**
	* This metho will click on a given image
	* @param imgPath String - image to be clicked
	* @return Boolean - true if clicked on image successfully
	*/
	public static boolean clickElement(String imgPath) {
		System.out.println("Clicking "+imgPath);
		screen.ClickImage(imgPath);
		return true;
	}
	
	/**
	* This method will click on element at the given coordinates.
	* @param x int - x coordinate
	* @param int - y coordinate
	* @return Boolean - true if clicked on element successfully
	*/
	public static boolean clickElement(int x, int y) {
		System.out.println("Clicking "+x+", "+y);		
		try {
			s.click(new Location(x, y));
			return true;
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	* This method will enter text.
	* @param text String - text to be entered
	* @return Boolean - true if clicked on element successfully
	*/
	public static boolean enterText(String text) {
		ctrl.keyPress(text);
		return true;
	}

	/**
	* This method will wait for image to load.
	* @param imgPath String - image path
	* @return Boolean - true if clicked on element successfully
	*/
	public static boolean waitForImage(String imgPath) {
		int errCounter = 0;
		while (!screen.findScreenImage(imgPath)) {
			gen_utils.wait(1000);
			if (errCounter == 20) {
				return false;
			}
			errCounter++;
		}
		return true;
	}
}
