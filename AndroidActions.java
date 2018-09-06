package Utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import Config.Constants;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

/**
* This Class contains the methods to perform actions in Android.
* @author AshithRaj Shetty - Charter
*/
public class AndroidActions {
	
	public static AndroidDriver<AndroidElement> driver = null;

	/**
	* This method will initialize settings for execution in Android
	* @param APP_PACKAGE String - application package name
	* @param APP_ACTIVITY String - activity 
	* @return Boolean - returns True if setup is successful
	* @throws IOException Signals that an I/O exception has occurred.
	*/
	public boolean initAndroidDriver(String APP_PACKAGE, String APP_ACTIVITY) throws IOException {
		String reportDirectory = "reports";
		String reportFormat = "xml";
		String testName = "Untitled";
		boolean result = false;
		
		//Read device UUID and set desired capabilities
		String UUID = new ReadConfig().readPropertiesFile("deviceUUID");
		DesiredCapabilities dc = new DesiredCapabilities();
		try {
			dc.setCapability("sessionOverride", true);
			dc.setCapability("deviceName", "Android");
			dc.setCapability("reportDirectory", reportDirectory);
			dc.setCapability("reportFormat", reportFormat);
			dc.setCapability("testName", testName);
			dc.setCapability(MobileCapabilityType.UDID, UUID);
			dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
			dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
			//Create Android driver
			driver = new AndroidDriver<AndroidElement>(new URL("http://localhost:4723/wd/hub"), dc);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	* This method is used to find elements details for the given xPath.
	* @param elementName String - element name
	* @param xPathType String - type of xPath 
	* @return string[] - It will return array of elements with the given xPath type
	*/
	public String[] findElementsDetails(String elementName, String xPathType) {
		AndroidElement element = null;
		String[] retval = new String[4];
		if (xPathType == "Text") {
			String elementString = "//*[@text='" + elementName + "']";
			System.out.println("Element String: " + elementString);
			try {
				element = driver.findElements(By.xpath(elementString)).get(0);
				retval[0] = "Pass";
				retval[1] = "Successfully found " + elementName + "element";
				retval[2] = Integer.toString(driver.findElements(By.xpath(elementString)).size());
				retval[3] = element.getText();
			} catch (Exception e) {
				e.printStackTrace();
				retval[0] = "Fail";
				retval[1] = "Failed to find the element(s)";
				retval[2] = "0";
				retval[3] = "";
			}
		}
		if (xPathType == "ID") {
			 //driver.findElement(By.xpath("//*[@id='code']")).getText();
			String elementString = "//*[@id='" + elementName + "']";
			System.out.println("Element String: " + elementString);
			try {
				element = driver.findElements(By.xpath(elementString)).get(0);
				retval[0] = "Pass";
				retval[1] = "Successfully found " + elementName + "element";
				retval[2] = Integer.toString(driver.findElements(By.xpath(elementString)).size());
				retval[3] = element.getText();
			} catch (Exception e) {
				e.printStackTrace();
				retval[0] = "Fail";
				retval[1] = "Failed to find the element(s)";
				retval[2] = "0";
				retval[3] = "";
			}
		}
		return retval;
	}
	
	/**
	* This method will check if all the elements passed are present or not
	* If the element in present it will store as Pass else it will store as Fail
	* @param elementName String[] - list of elements
	* @param xPathType String[] - xPath type of elements
	* @return String[] - result after validating if all elements are present or not
	*/
	public String[] findElementsDetails(String [] elementName, String [] xPathType) {
		List<AndroidElement> elementList = null;
		String[] retval = new String[4];
		String elementString = "/";
		int i = 0;
		for (String str: elementName){
			elementString = elementString+"/*[@" + xPathType[i] + "='" + str + "']";
			i++;
		}
		System.out.println("Element String: " + elementString);
		try {
			elementList = driver.findElements(By.xpath(elementString));
			retval[0] = "Pass";
			retval[1] = "Successfully found " + elementName + "element";
			retval[2] = Integer.toString(elementList.size());
			retval[3] = "";
			for(AndroidElement element : elementList){
				retval[3] = retval[3]+element.getText();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			retval[0] = "Fail";
			retval[1] = "Failed to find the element(s)";
			retval[2] = "0";
			retval[3] = "";
		}
		return retval;
	}

	/**
	* This method will click on a given element.
	* @param elementName String - element name
	* @return Boolean - returns True if action is successful
	*/
	public boolean performClickActionOnElement(String elementName) {
		boolean result = false;
		String elementString = "//*[@text='" + elementName + "']";
		AndroidElement element = element = driver.findElements(By.xpath(elementString)).get(0);
		//TouchAction touchAction = new TouchAction(driver);

		try {
			System.out.println("Performing click on "+elementString);
			element.click();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	* This method is used to perform Swipe action.
	* @param startX int - start position of X coordinate
	* @param startY int - start position of Y coordinate
	* @param destX int - end position of X coordinate
	* @param destY int - end position of Y coordinate
	* @return Boolean - returns true if swipe is successful
	*/
	public boolean performSwipeAction(int startX, int startY, int destX, int destY) {
		boolean result = false;
		
		TouchAction touchAction = new TouchAction(driver);
		try {
			touchAction.press(startX, startY).moveTo(destX, destY).release().perform();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * This method will Quit the driver.
	 */
	public void quit() {
		driver.quit();
	}
	
	/**
	* This method will perform the action passed as parameter
	* @param action String - action to be performed
	* @return Boolean - returns true if action is successful
	* @throws IOException Signals that an I/O exception has occurred.
	*/
	public boolean keyEvent(String action) throws IOException{
		boolean result = false;
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("Up", 19);
		hm.put("Down", 20);
		hm.put("Center", 23);
		hm.put("Left", 21);
		hm.put("Right", 22);
		String command = "adb shell input keyevent \""+hm.get(action)+"\"";
		System.out.println("Sending "+action+" key to device");
		try{
			Process run = Runtime.getRuntime().exec(command);
			result = true;
		}
		catch(Exception e){
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	* This method will perform the core actions on device through command.
	* @param action String - action to be performed
	* @return Boolean - returns true if action is successful
	* @throws IOException Signals that an I/O exception has occurred.
	*/
	public boolean deviceActions(String action) throws IOException{
		boolean result = false;
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("Back", AndroidKeyCode.BACK);
		hm.put("Down", 20);
		hm.put("Center", 23);
		String command = "adb shell input keyevent \""+hm.get(action)+"\"";
		System.out.println("Sending "+action+" key to device");
		try{
			Process run = Runtime.getRuntime().exec(command);
			result = true;
		}
		catch(Exception e){
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	* This method will wait till the element appears until timeOut.
	* @param elementName String - element name to load
	* @param type String - type of xPath
	* @param timeout int - How much time to wait
	* @return Boolean - returns true if element is displayed successfully
	*/
	public boolean waitForElement(String elementName, String type, int timeout){
		boolean result = true;
		int errCounter = 0;
		Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
		while(this.findElementsDetails(elementName, type)[2]=="0"){
			gen_utils.wait(1000);
			if(errCounter == timeout){
				result = false;
				break;
			}
			errCounter++;
		}
		return result;
	}
	
	/**
	* This method will capture the screenshot.
	* @param increment int - Number to store screenshots serially
	* @throws IOException Signals that an I/O exception has occurred.
	*/
	public void captureScreenshot(int increment) throws IOException{
		this.captureScreenshotOnMobile(increment);
		String motionCaptureFolder = Constants.motionCapturePath;
		this.copyScreenshotFromMobile(motionCaptureFolder, increment);
	}
	
	/**
	* This method will copy screenshot from mobile and store it in the given path.
	* @param outputFolderPath String - the folder path where screenshot has to be stored
	* @param increment int - Number to store screenshots serially
	*/
	public void copyScreenshotFromMobile(String outputFolderPath, int increment){
		Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
		gen_utils.folder_create(outputFolderPath);
		File folder_to_create = new File(outputFolderPath);
		String command = "adb pull /sdcard/screencap"+increment+".png "+folder_to_create.getAbsolutePath()+"/screencap"+String.valueOf(increment)+".png";
		System.out.println("Sending "+command+" to device");
		try{
			Process run = Runtime.getRuntime().exec(command);
			run.waitFor();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	* This method captures screenshot on mobile and store it in mobile.
	* @param increment int - Number to store screenshots serially
	* @throws IOException Signals that an I/O exception has occurred.
	*/
	public void captureScreenshotOnMobile(int increment) throws IOException{
		System.out.println("Capturing screenshot");
		
		/*
		 * Create a folder to store all the screenshots captured
		 */
		
		/*File screenshotFile1 = driver.getScreenshotAs(OutputType.FILE);
		System.out.println("Screenshot Location: " + screenshotFile1.getAbsolutePath());
		FileUtils.copyFile(screenshotFile1, new File(Constants.motionCapturePath));*/
		String command = "adb shell screencap -p /sdcard/screencap"+increment+".png";
		System.out.println("Sending "+command+" to device");
		try{
			Process run = Runtime.getRuntime().exec(command);
			run.waitFor();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	* This method will capture screenshots of videos.
	* @throws NumberFormatException the number format exception
	* @throws IOException Signals that an I/O exception has occurred.
	*/
	public void motionCapture() throws NumberFormatException, IOException{
		long currentTime = System.currentTimeMillis();		
		long motionCaptureDuration = Long.parseLong(new ReadConfig().readPropertiesFile("motionCaptureDuration"));
		long endTime = currentTime + Constants.motionCaptureDuration;
		Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
		System.out.println(currentTime + " : " + endTime);
		int increment=0;
		
		//It will capture screenshots continuously till end time
		while (System.currentTimeMillis() < endTime) {
			//xbx_ctrl.keyPress("Up");
			//gen_utils.wait(1000);
			this.captureScreenshotOnMobile(increment);			
			increment++;
		}
		// It will copy all the screenshots captured to the folder
		for (int i=0; i<increment; i++){
			String motionCaptureFolder = Constants.motionCapturePath;
			this.copyScreenshotFromMobile(motionCaptureFolder, i);
		}
	}

}
