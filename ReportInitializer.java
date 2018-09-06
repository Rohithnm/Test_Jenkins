/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;

import java.io.File;
import java.net.URL;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import Utility.Utilities;

/**
* This class is used to initialize the report before starting test scripts execution.
* @author Rohith Mallikarjuna - Charter
*/
public class ReportInitializer {
	
    			public static LogManager logger;
    			public static String CSVFilePath;
    			public static String localSuiteName;
    			Process p=null;

    			public static  String scenario_ClassName;
    			public static String suiteID = "1";

    			/**
    			 * In this method initial setups are done before starting test scripts execution. 
    			 */
			    // TestWorkBenchUpdate twb = new TestWorkBenchUpdate();
			    @BeforeSuite
				public void suiteManager(){
			    	//suiteID = twb.getTWBTestSuiteID();
			    	//System.out.println("Suite ID: "+suiteID);
					URL rootFolder = LogManager.class.getProtectionDomain().getCodeSource().getLocation();
					File rootFolderPath = new File(rootFolder.getPath());
					String filePath = rootFolderPath.getParentFile().getParentFile().getAbsolutePath();
					Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
					//Thread.currentThread().getStackTrace()[1].
					
					
					String TimeStamp = String.format( "%tc",new Date());
					String formatedTimeStamp = TimeStamp.replace(":", "-");
					
					String CSVFileName = "\\CSVResults\\" + localSuiteName + "-" + formatedTimeStamp +".csv" ;
					gen_utils.folder_create(filePath+"/CSVResults");
					gen_utils.folder_create(filePath+"/RunResults/B.Log");
					gen_utils.folder_create(filePath+"/RunResults/B.html");
					gen_utils.folder_create(filePath+"/RunResults/B.xml");
					scenario_ClassName  = this.getClass().getName();
					System.out.println(scenario_ClassName);
				    CSVFilePath = filePath + CSVFileName;					
				}
			    
			    /**
    			 * This method will display logs before starting execution of test script
    			 */
    			@BeforeMethod
			    public void beforeTest(){
			    	//scenario_ClassName  = this.getClass().getName();
					logger = new LogManager( "FileAppender",  "PatternLayout",  "DEFAULT",  scenario_ClassName);
					logger.publishLog();
			    }
			    
			    /**
    			 * This method will perform actions given after executing methods.
    			 */
    			@AfterMethod
			    public void teardown(){
			    }

}
