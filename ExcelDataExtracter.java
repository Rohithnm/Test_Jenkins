/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
* This class contains methods to extract data from excel sheet by calling methods from Excel Scanner class.
* @author Rohith Mallikarjuna - Charter
*/
public class ExcelDataExtracter extends ReportInitializer {

	public static LinkedHashMap<String, LinkedHashMap<String, String>> filtered_DriverDictionary;
	public static LinkedHashMap<String, LinkedHashMap<String, String>> filtered_DataSheetDictionary;
	public static String filepath = System.getProperty("user.dir");
	public static File dir = new File(filepath);
	public static String frameworkPath = dir.getParent();
	public static String filePath = "DataVault/TestData.xlsx";
	public static String Driver_TestCase_LogicalName;
	public static String Driver_TestCase_Type;
	public static String Driver_Execution_Flag;
	public static String Driver_TestDataSource_ID;
	String className = this.getClass().getName();
	public static String arr[] = scenario_ClassName.split("\\.");
	public static String scenarioClassName = arr[arr.length - 1];

	/**
	* This method will generate test data from excel sheet and store it in HashMap
	* @return LinkedHashMap -  returns HashMap containing the test data
	* @see ExcelScanner#extractFilteredSheetData(String, String, String, String)
	* @see ExcelScanner#getCellValue(LinkedHashMap, String, String)
	*/
	public static LinkedHashMap<String, LinkedHashMap<String, String>> generateTestDataMap() {// extractArrayList(){
		// filter with logical name....
		System.out.println(frameworkPath);
		try {
			filtered_DriverDictionary = ExcelScanner.extractFilteredSheetData(filePath, "Scenario",
					"TestCase_LogicalName", scenarioClassName);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		// store all datasourceids for respective logical testcase...
		System.out.println("Ashith: " + filtered_DriverDictionary);
		System.out.println("Ashith: " + scenario_ClassName);
		List<String> listDataSourceID = new ArrayList<String>();
		for (String key : filtered_DriverDictionary.keySet()) {
			// Collect all imp column Values...

			Driver_TestDataSource_ID = ExcelScanner.getCellValue(filtered_DriverDictionary, key, "TestDataSource_ID");
			// Generate XML here...

			listDataSourceID.add(Driver_TestDataSource_ID);
		}

		// List<LinkedHashMap> executionEntry = new ArrayList<>();

		LinkedHashMap<String, LinkedHashMap<String, String>> Map_DataProvider = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		
		//This for loop is used to fetch Key from excel sheet for dataSource iD and extract row data and add it in excel sheet.
		for (String iDataSourceID : listDataSourceID) {
			try {
				filtered_DataSheetDictionary = ExcelScanner.extractFilteredSheetData(filePath, "Test_DataSource",
						"TestDataSource_ID", iDataSourceID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Safely add each data row on seperate level in map...
			filtered_DataSheetDictionary.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v));
			filtered_DataSheetDictionary.forEach((k, v) -> Map_DataProvider.put(k, v));
			System.out.println(
					"...........................................................................................................................");
			Map_DataProvider.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v));
			// System.out.println(Map_DataProvider.size());
		}
		return Map_DataProvider;

	}

}
