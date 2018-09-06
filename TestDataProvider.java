/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

/**
 * This class contains method to fetch the filtered data, update suite XML based
 * on given suiteID and execute test scripts.
 * 
 * @author Rohith Mallikarjuna - Charter
 */
public class TestDataProvider {

	public static Object[][] testData;
	public static String Driver_TestCase_LogicalName;
	public String[] HEADER;

	/**
	 * This method will generate test data and store it in an ArrayList
	 * 
	 * @return the object[][]
	 * @throws Exception
	 *             the exception
	 * @see ExcelDataExtracter#generateTestDataMap()
	 */
	@DataProvider(name = "DataProvider")
	public static Object[][] Global_DataProviderFactory() throws Exception {

		LinkedHashMap<String, LinkedHashMap<String, String>> Map_DataProvider = ExcelDataExtracter
				.generateTestDataMap();
		int rowCount = Map_DataProvider.size();
		List<LinkedHashMap<String, String>> arrayMapList = new ArrayList<LinkedHashMap<String, String>>();
		Object[][] obj = new Object[rowCount][1];
		Set<String> keys = Map_DataProvider.keySet();
		// This loop will fetch data for each key and update data in ArrayList
		for (String key : keys) {
			arrayMapList.add(Map_DataProvider.get(key));
		}
		// This loop will copy the test data from ArrayList and store it in an instance
		// of Object.
		for (int i = 0; i < arrayMapList.size(); i++) {
			obj[i][0] = arrayMapList.get(i);
			System.out.println("TestData on Obj 0,0 is : " + obj[i][0]);
		}
		return obj;

	}

	/**
	 * Read the data from excel file and store it in two dimensional object array
	 * @param xlFilePath
	 * @param sheetName
	 * @return Two dimensional Object array
	 */
	public Object[][] testData(String xlFilePath, String sheetName) {
		Object[][] inputData = null;
		FileInputStream file = null;
		Workbook workbook = null;
		int a, b;
		try {
			file = new FileInputStream(xlFilePath);
			workbook = new XSSFWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheet(sheetName);
		int startRow = 1;
		int startColumn = 0;
		int totalRows = sheet.getLastRowNum();
		int totalColumns = 2;

		inputData = new String[totalRows][totalColumns];
		a = 0;
		for (int i = startRow; i < totalRows + 1; i++, a++) {
			b = 0;
			for (int j = startColumn; j < totalColumns; j++, b++) {
				inputData[a][b] = sheet.getRow(i).getCell(j).getStringCellValue();
			}
		}
		return inputData;
	}
}
