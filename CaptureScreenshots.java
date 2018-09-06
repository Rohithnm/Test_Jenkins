package Utility;

/**
* This class contains methods to capture screenshots and upload it to TWB
* @author AshithRaj Shetty - Charter
*/
public class CaptureScreenshots {
	
	/**
	* This method will capture.
	* @param screenshotPath the screenshot path
	* @param testCaseInstanceId the test case instance id
	*/
	public static void captureScreenshotsAndUpload(String screenshotPath, String testCaseInstanceId) {
		Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
		Utilities.screenFunctions screen = new Utilities().new screenFunctions();
		String screenshotFilePath = "";

		gen_utils.folder_create(screenshotPath);
		screenshotFilePath = screen.captureScreenshot(screenshotPath);
		CaptureScreenshots.uploadScreenshotToTWB(screenshotFilePath, testCaseInstanceId);

	}

	/**
	* Upload screenshot to TWB.
	* @param screenshotFilePath the screenshot file path
	* @param testCaseInstanceId the test case instance id
	*/
	public static void uploadScreenshotToTWB(String screenshotFilePath, String testCaseInstanceId) {
//		TestWorkBenchUpdate twb = new TestWorkBenchUpdate();
//		twb.updateScreenshotsRestAPI(testCaseInstanceId, screenshotFilePath);
	}
}
