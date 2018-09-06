/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public interface Constants {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
	Calendar now = Calendar.getInstance();
	String fmt = formatter.format(now.getTime());
	public static final double autoWaitTimeout = 1.0;
	public static final String fxNowActivationURL = "http://fxnetworks.com/activate";
	public static final String foxNowActivationURL = "http://foxnow.com/activate";
	public static final String syfyActivationURL = "http://www.syfy.com/one";
	public static final String bravoNowActivationURL = "http://bravotv.com/one";
	public static final String USANowAppsHomeSelectedImage = "img/usaNow_app_home_selected.png";
	public static final String nbcActivationURL = "http://nbc.com/one";
	public static final String telemundoActivationURL = "http://now.telemundo.com/one";
	public static final String epixActivationURL = "http://epix.com/devices";
	public static final String vh1ActivationURL = "http://vh1.com/activate";
	public static final String usaNowActivationURL = "http://usanetwork.com/one";
	public static final String amcActivationURL = "http://amc.com/activate";
	public static final String natGeoActivationURL = "http://natgeotv.com/activate";
	public static final String mtvActivationURL = "http://mtv.com/activate";
	public static final String nbcSportsActivationURL = "http://nbcsports.com/activate";
	public static final String cbsActivationURL = "http://cbs.com/one";
	public static final String showtimeActivationURL = "http://showtimeanytime.com/activate";
	public static final String espnActivationURL = "http://es.pn/one";
	public static final String fxNowAppsHomeSelectedImage = "img/fxNow_app_home_selected.png";
	public static final String foxSportsGoAppsHomeSelectedImage = "img/foxsportsgoApp_Home_Selected.png";
	public static final String bravoNowAppsHomeSelectedImage = "img/bravoNow_app_home_selected.png";
	public static final String syfyAppsHomeSelectedImage = "img/bravoNow_app_home_selected.png";
	public static final String foxNowAppsHomeSelectedImage = "img/foxnowApp_Home_Selected.png";
	public static final String vh1AppsHomeSelectedImage = "img/VH1_app_home_selected.png";
	public static final String nbcAppsHomeSelectedImage = "img/nbc_app_home_selected.png";	
	public static final String telemundoAppsHomeSelectedImage = "img/telemundo_app_home_selected.png";
	public static final String epixAppsHomeSelectedImage = "img/epix_app_home_selected.png";
	public static final String amcAppsHomeSelectedImage = "img/amc_app_home_selected.png";
	public static final String natGeoAppsHomeSelectedImage = "img/natgeo_home_selected.png";
	public static final String mtvAppsHomeSelectedImage = "img/mtv_app_home_selected.png";
	public static final String nbcSportsAppsHomeSelectedImage = "img/nbcSports_app_home_selected.png";
	public static final String cbsAppsHomeSelectedImage = "img/cbs_app_home_selected.png";
	public static final String showtimeAppsHomeSelectedImage = "img/showtime_app_home_selected.png";
	public static final String espnAppsHomeSelectedImage = "img/espn_app_home_selected.png";
	public static final int noOfIterations = 2;
	public static final String[] searchTerms = {"Jack", "Big", "Dead", "Crash", "Love"};
	public static final String reportCSVPath = "report"+fmt+".csv";
	public static final String loadingImagesScreenshotPath = "C:/Ashith/_POC/motion_capture/loading_images/";
	public static final String playingImagesScreenshotPath = "C:/Ashith/_POC/motion_capture/playing_images/";
	public static final String screenshotPath = "C:/Screenshots";
	public static final String motionCapturePath = "motion_capture_screenshots";
	public static final String suitename="_Spectrum_App";//"E2E Regression Suite - TO DELETE";
	public static final String environemnt="Production";
	public static final String applicationName="";
	public static final String testername="Rohith";
	public static final String deviceName="";
	public static final String OS=System.getProperty("os.name");
	public static final String OSVersion=System.getProperty("os.version");
	public static final String machineName="Remote-Machine";
	public static final String [] liveTVFilterList = { "Recent Channels", "All Channels", "Favorites", "Movies", "TV Shows", "Sports", "Kids" };
	public static final int noOfScreenshots = 20;
	public static final int motionCaptureDuration = 25000;
	
	public static String filepath = System.getProperty("user.dir");
	public static File dir = new File(filepath);
	public static String frameworkPath = dir.getParent();
	public static String FILEPATH = frameworkPath + "\\RokuAutomationFramework\\DataVault\\TestData.xlsx";
	public static String VALIDSheetNAME = "TestData";
	public static String INVALIDSheetNAME = "InvalidTestData";

}
