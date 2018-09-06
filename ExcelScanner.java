/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import SuiteUtils.ImportDoubleDictionary;

/**
* This class is used to fetch values from excel sheet by calling methods of ImportDOubleDictionary class
* @author Sagar Mitkari,Shruthi Nirvanappa - Charter
*/
public class ExcelScanner {

	/**
	* This method will create instance of ImportDoubleDictionary class and call methods present in that class for fetching data.
	* @param filePath String - the path of excel sheet with test data
	* @param sheetName String - the sheet name present in excel sheet
	* @return LinkedHashMap - HashMap containing test data
	* @throws Exception - Exception handling to be implemented
	* @see ImportDoubleDictionary#ImportHeader(String, String)
	* @see ImportDoubleDictionary#ImportDictionary(String, String, String[])
	*/
	public static LinkedHashMap<String, LinkedHashMap<String, String>> extractSheetData(String filePath,
			String sheetName) throws Exception {

		LinkedHashMap<String, LinkedHashMap<String, String>> main_Dictionary;

		ImportDoubleDictionary ID = new ImportDoubleDictionary();
		String[] arrHeader = ID.ImportHeader(filePath, sheetName);
		main_Dictionary = ID.ImportDictionary(filePath, sheetName, arrHeader);

		return main_Dictionary;
	}


	/**
	* This method takes the LinkedHashMap created from "extractSheetData" method and filters it
	  based on the filterValue passed and stores the result as LinkedHashMap.
	* @param filePath String - the path of excel sheet with test data
	* @param sheetName String - sheet name
	* @param filterColumnName String - column name which has to be filtered
	* @param filterValue String - The value on which data has to be filtered.
	* @return LinkedhashMap - returns HashMap containing filtered test data
	* @throws Exception - Exception handling to be implementeds
	* @see ImportDoubleDictionary#ImportDictionary(String, String, String[])
	* @see ImportDoubleDictionary#ImportHeader(String, String)
	* @see ImportDoubleDictionary#filterDoubleDictionary(LinkedHashMap, String, String)
	*/
	public static LinkedHashMap<String, LinkedHashMap<String, String>> extractFilteredSheetData(String filePath,
			String sheetName, String filterColumnName, String filterValue) throws Exception {

		LinkedHashMap<String, LinkedHashMap<String, String>> main_Dictionary;
		LinkedHashMap<String, LinkedHashMap<String, String>> filtered_Dictionary;

		ImportDoubleDictionary ID = new ImportDoubleDictionary();
		String[] arrHeader = ID.ImportHeader(filePath, sheetName);
		main_Dictionary = ID.ImportDictionary(filePath, sheetName, arrHeader);
		filtered_Dictionary = ImportDoubleDictionary.filterDoubleDictionary(main_Dictionary, filterColumnName,
				filterValue);

		return filtered_Dictionary;
	}

	/**
	* This method is used to store the filtered data from test data by calling "filterDoubleDictionary" method.
	* @param main_Dictionary LinkedHashMap - HashMap containing test data
	* @param filterColumnName String - column Name on which filter has to be done
	* @param filterValue String - The value on which data has to be filtered.
	* @return LinkedhashMap - test data which is not matching with filterValue
	* @throws Exception - Exception handling to be implementeds
	* @see ImportDoubleDictionary#filterDoubleDictionary(LinkedHashMap, String, String)
	*/
	public static LinkedHashMap<String, LinkedHashMap<String, String>> filterDictionary(
			LinkedHashMap<String, LinkedHashMap<String, String>> main_Dictionary, String filterColumnName,
			String filterValue) throws Exception {

		LinkedHashMap<String, LinkedHashMap<String, String>> filtered_Dictionary;
		filtered_Dictionary = ImportDoubleDictionary.filterDoubleDictionary(main_Dictionary, filterColumnName,
				filterValue);

		return filtered_Dictionary;
	}

	/**
	*This method is used to set the execution flag as Yes based on the Batch ID.
	* @param FilePath String- path of excel sheet
	* @param sheetName String - sheet name in which execution flag column is present.
	* @param BatchID String - batch ID for which execution flag is to be set.
	* @throws Exception - Exception handling to be implementeds.
	*/
	public static void AlterExcelColumnValue(String FilePath, String sheetName, String BatchID) throws Exception {

		File srcFilePath = new File(FilePath);
		FileInputStream fileipStr = new FileInputStream(srcFilePath);
		//Creating new workbook and work sheet objects
		XSSFWorkbook workBook = new XSSFWorkbook(fileipStr);
		XSSFSheet driverSheet = workBook.getSheet(sheetName);
		Cell cellVal = null;
		Cell TargetCell = null;
		Iterator<Row> rowIterator = driverSheet.iterator();
		
		/*It will loop through for each test case,check if Batch ID matches with given Batch ID,
		If it matches then it changes execution flag to "Yes" */
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			// System.out.println(row.getRowNum());
			cellVal = driverSheet.getRow(row.getRowNum()).getCell(7);
			// System.out.println(cellVal);
			String batchValue = cellVal.getStringCellValue();
			System.out.println(batchValue);
			if (batchValue.equals("BATCH ID")) {
				System.out.println("This is indeed Column");
			} else {
				if (batchValue.equals(BatchID)) {
					TargetCell = driverSheet.getRow(row.getRowNum()).getCell(6);
					TargetCell.setCellValue("Yes");
					System.out.println(TargetCell);
				} else {
					// do nothing
					System.out.println("Mismatch");
				}
			}

		}
		fileipStr.close();
		FileOutputStream outFile = new FileOutputStream(new File(FilePath));
		workBook.write(outFile);
		outFile.close();
		// workBook.close();
	}

	/**
	* This method will fetch the value present in the cell in the given page from the dictionary.
	* @param doubleDict LinkedHashMap - HashMap containing test data
	* @param key String - primary key
	* @param columnName String - column name from which data has to be fetched
	* @return String - test data present in the cell.
	*/
	public static String getCellValue(LinkedHashMap<String, LinkedHashMap<String, String>> doubleDict, String key,
			String columnName) {
		String cellValue = ImportDoubleDictionary.fetchItem(doubleDict, key, columnName);
		return cellValue;
	}

	/**
	* This method will fetch the headers present in the sheet.
	* @param filePath String - file path
	* @param sheetName String - sheet name
	* @return the string[] - returns array of String having headers in the given sheet
	* @throws Exception - Exception handling to be implemented
	*/
	public static String[] extractHeader(String filePath, String sheetName) throws Exception {

		ImportDoubleDictionary ID = new ImportDoubleDictionary();
		String[] arrHeader = ID.ImportHeader(filePath, sheetName);

		return arrHeader;
	}

}
