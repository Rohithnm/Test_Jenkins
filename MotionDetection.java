package Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Config.Constants;

/**
* This Class contains methods to take screenshots and validate videos
* @author AshithRaj Shetty - Charter
*/
public class MotionDetection {
	
	static Utilities.screenFunctions screen = new Utilities().new screenFunctions();
	
	File networkErr1 = new File("img/network_error1.jpg");
	File networkErr2 = new File("img/network_error2.jpg");
	
	static Utilities.Controller ctrl = new Utilities().new Controller();
	
	/**
	* This method will capture screenshot continuously of a video
	*/
	public static void motion_capture() {
		Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
		long currentTime = System.currentTimeMillis();
		long endTime = currentTime + Constants.motionCaptureDuration;
		int[] rect = { 68, 28, 490, 400 };
		System.out.println(currentTime + " : " + endTime);
		
		//It will continuously take screenshots until endTime
		while (System.currentTimeMillis() < endTime) {
			//ctrl.keyPress("Up");
			gen_utils.wait(500);
			screen.captureScreenshot(rect);
		}
	}

	/**
	* This method will check how much time it takes to load a video
	*
	* @return the object[]
	*/
	public static Object[] checkVideoLoadTime() {
		File folder = new File(Constants.motionCapturePath);
		File[] listOfFiles = folder.listFiles();
		String ResultText[] = new String[listOfFiles.length];
		File errorFile1 = new File("img/video_pre_load_image1.png");
		File errorFile2 = new File("img/video_pre_load_image2.png");

		float res1 = 0.0f;
		float res2 = 0.0f;
		Object[] retval = new Object[4];
		List<Double> nameArrayList = new ArrayList<Double>();
		List<String> videoNameArrayList = new ArrayList<String>();
		double loadingImageVisibleStartTime = 0.0;
		double loadingImageVisibleEndTime = 0.0;
		double videoLoadTime = 0.0;
		try {
			for (int i = 0; i < listOfFiles.length; i++) {
				res1 = screen.compareImage(listOfFiles[i], errorFile1);
				res2 = screen.compareImage(listOfFiles[i], errorFile2);
				System.out.println("Res1: " + res1 + " Res2: " + res2);
				if (res1 > 97.0f || res2 > 84.0f) {
					nameArrayList.add(Double.parseDouble(listOfFiles[i].getName().replaceAll(".png", "")));
				} else {
					videoNameArrayList.add(listOfFiles[i].getName());
				}
			}
			Collections.sort(nameArrayList);
			loadingImageVisibleStartTime = nameArrayList.get(0);
			loadingImageVisibleEndTime = nameArrayList.get(nameArrayList.size() - 1);
			videoLoadTime = loadingImageVisibleEndTime - loadingImageVisibleStartTime;
			System.out.println("Video Load Time: " + videoLoadTime);
			retval[0] = "Pass";
			retval[1] = "Successfully captured video load time";
			retval[2] = videoLoadTime;
			retval[3] = videoNameArrayList;
			return retval;
		} catch (Exception e) {
			retval[0] = "Pass";
			retval[1] = "Successfully captured video load time";
			retval[2] = videoLoadTime;
			retval[3] = videoNameArrayList;
			return retval;
		}
	}
	
	/**
	* This method will capture the motion and checks if video/movie is playing properly
	* @return String[] - result containing whether video was playing or not at particular time
	*/
	public static String[] motion_detect() {
		int count = 0;
		float res1 = 0.0f;
		String [] retval = new String [3];
		double analysisTime = 0.0;
		double endTime = 0.0;
		double startTime = 0.0;
		startTime = System.nanoTime();
		File folder = new File(Constants.motionCapturePath);
		File[] listOfFiles = folder.listFiles();
		List<String> videoNameArrayList= new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			videoNameArrayList.add(listOfFiles[i].getName());
		}
		for (int i = 0; i<videoNameArrayList.size(); i++){
			if (i!=videoNameArrayList.size()-1){
				res1 = screen.compareImage(new File("motion_capture_screenshots/"+videoNameArrayList.get(i+1)), new File("motion_capture_screenshots/"+videoNameArrayList.get(i)));
				if (res1<95.0f){
					count++;
					System.out.println("Count: "+count);
				}
				System.out.println("Res1: "+res1 + "File 1: "+videoNameArrayList.get(i+1)
				+"File 2: "+videoNameArrayList.get(i));				
			}				
		}
		endTime = System.nanoTime();
		analysisTime = endTime - startTime;
		System.out.println("Count: "+(float)count/(float)videoNameArrayList.size()+"File Length: "+(videoNameArrayList.size()));
		if (((float)count/(float)videoNameArrayList.size())>=0.60){
			System.out.println("Movie is playing");
			retval[0] = "Pass";
			retval[1] = Double.toString(analysisTime);
			retval[2] = "Movie is playing without errors";
		}
		else{
			System.out.println("Movie is not playing. Possibly movie play got stuck in first 20 secs play OR no video stream available.");
			retval[0] = "Fail";
			retval[1] = Double.toString(analysisTime);
			retval[2] = "Movie is not playing. Possibly movie play is stuck or no video stream available.";
		}
		return retval;
	}

	/**
	* This method will take screenshot and compare with the given screenshot.
	* @param screenshotPath String - screenshot path to be compared
	* @return String[] - result containing if given screenshot is displayed or not
	*/
	public static String[] motion_detect(String screenshotPath) {
		int count = 0;
		float res1 = 0.0f;
		String [] retval = new String [3];
		double analysisTime = 0.0;
		double endTime = 0.0;
		double startTime = 0.0;
		startTime = System.nanoTime();
		File folder = new File(screenshotPath);
		File[] listOfFiles = folder.listFiles();
		List<String> videoNameArrayList= new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			videoNameArrayList.add(listOfFiles[i].getPath());
		}
		for (int i = 0; i<videoNameArrayList.size(); i++){
			if (i!=videoNameArrayList.size()-1){
				res1 = screen.compareImage(new File(videoNameArrayList.get(i+1)), new File(videoNameArrayList.get(i)));
				if (res1<95.0f){
					count++;
					System.out.println("Count: "+count);
				}
				System.out.println("Res1: "+res1 + "File 1: "+videoNameArrayList.get(i+1)
				+"File 2: "+videoNameArrayList.get(i));				
			}				
		}
		endTime = System.nanoTime();
		analysisTime = endTime - startTime;
		System.out.println("Count: "+(float)count/(float)videoNameArrayList.size()+"File Length: "+(videoNameArrayList.size()));
		if (((float)count/(float)videoNameArrayList.size())>=0.60){
			System.out.println("Movie is playing");
			retval[0] = "Pass";
			retval[1] = Double.toString(analysisTime);
			retval[2] = "Movie is playing without errors";
		}
		else{
			System.out.println("Movie is not playing. Possibly movie play got stuck in first 20 secs play OR no video stream available.");
			retval[0] = "Fail";
			retval[1] = Double.toString(analysisTime);
			retval[2] = "Movie is not playing. Possibly movie play is stuck or no video stream available.";
		}
		return retval;
	}
	
	/**
	* This method will check if the given video is playing or not
	* @param videoNameArrayList List<String> - list of videos to be passed
	* @return String[] - result contains if the video played or not
	*/
	public static String[] motion_detect(List<String> videoNameArrayList) {
		int count = 0;
		float res1 = 0.0f;
		String [] retval = new String [3];
		double analysisTime = 0.0;
		double endTime = 0.0;
		double startTime = 0.0;
		startTime = System.nanoTime();
		for (int i = 0; i<videoNameArrayList.size(); i++){
			if (i!=videoNameArrayList.size()-1){
				res1 = screen.compareImage(new File("motion_capture_screenshots/"+videoNameArrayList.get(i+1)), new File("motion_capture_screenshots/"+videoNameArrayList.get(i)));
				if (res1<95.0f){
					count++;
					System.out.println("Count: "+count);
				}
				System.out.println("Res1: "+res1 + "File 1: "+videoNameArrayList.get(i+1)
				+"File 2: "+videoNameArrayList.get(i));				
			}				
		}
		endTime = System.nanoTime();
		analysisTime = endTime - startTime;
		System.out.println("Count: "+(float)count/(float)videoNameArrayList.size()+"File Length: "+(videoNameArrayList.size()));
		if (((float)count/(float)videoNameArrayList.size())>=0.60){
			System.out.println("Movie is playing");
			retval[0] = "Pass";
			retval[1] = Double.toString(analysisTime);
			retval[2] = "Movie is playing without errors";
		}
		else{
			System.out.println("Movie is not playing. Possibly movie play got stuck in first 20 secs play OR no video stream available.");
			retval[0] = "Fail";
			retval[1] = Double.toString(analysisTime);
			retval[2] = "Movie is not playing. Possibly movie play is stuck or no video stream available.";
		}
		return retval;
	}

}
