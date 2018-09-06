/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import Utility.Utilities;

/**
* This Class will fetch test data, set XML for given suite ID and will execute test cases.
* @author Rohith Mallikarjuna - Charter
*/
public class TestRunner {

	public static LinkedHashMap<String, LinkedHashMap<String, String>> filtered_DriverDictionary;
	public static LinkedHashMap<String, LinkedHashMap<String, String>> filtered_DataSheetDictionary;
	
	public static String filepath = System.getProperty("user.dir");
	public static File dir = new File(filepath);
	public static String frameworkPath = dir.getParent();
	public static String filePath = frameworkPath + "\\Framework\\DataVault\\TestData.xlsx";
	public static String xmlfilePath = frameworkPath + "\\Framework\\TestNGXML\\Testing.xml";
	
	public static String Driver_TestCase_LogicalName;
	public static String Driver_TestCase_Type;
	public static String Driver_Execution_Flag;
	public static String Driver_TestDataSource_ID;
	static Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
//	public static TestWorkBenchUpdate twb = new TestWorkBenchUpdate();
//	public static String suiteId = twb.getTWBTestSuiteID();

	/**
	* This method fetch details from excel sheet and execute the given suite.
	* @param args String
	* @throws Exception - Exception handling to be implemented
	*/
	public static void main(String[] args) throws Exception {

		BufferedWriter bw = null;
		FileWriter fw = null;

		System.out.println("FW Path: " + frameworkPath);
		try {
			filtered_DriverDictionary = ExcelScanner.extractFilteredSheetData(filePath, "Scenario",
					"Execution_Flag", "Yes");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Asith: " + filtered_DriverDictionary);
		// Suite Level Config...
		XmlSuite suite = new XmlSuite();
		suite.setName(" Automated TestSuite");

		suite.setVerbose(1);
		suite.addListener("SuiteUtils.SuiteListener");

		// test Level Configurations...
		XmlTest xmlTest = new XmlTest(suite);
		xmlTest.setName(" Automated Tests");
		xmlTest.setPreserveOrder("true");
		String PackageName = "";

		// Class Level Configurations...
		List<XmlClass> classes = new ArrayList<XmlClass>();
		//It will loop through filtered data and fetch data for given key and add related classed to XMLClass
		for (String key : filtered_DriverDictionary.keySet()) {
			Driver_TestCase_Type = ExcelScanner.getCellValue(filtered_DriverDictionary, key, "TestCase_Type");
			/*if (Driver_TestCase_Type.equals("Functional")) {
				PackageName = "TestCases.Functional";
			} else if (Driver_TestCase_Type.equals("Performance")) {
				PackageName = "TestCases.Performance";
			}*/
			PackageName = "TestCases."+Driver_TestCase_Type;
			System.out.println("Ashith: " + PackageName);
			Driver_TestCase_LogicalName = ExcelScanner.getCellValue(filtered_DriverDictionary, key,
					"TestCase_LogicalName");
			classes.add(new XmlClass(PackageName + "." + Driver_TestCase_LogicalName));
		}
		xmlTest.setXmlClasses(classes);
		
		//Setting XML suites to run and executing the running the suite
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);

		fw = new FileWriter(xmlfilePath);
		fw.write(suite.toXml());
		fw.close();
		//gen_utils.wait(4000);
		tng.run();

	}

}
