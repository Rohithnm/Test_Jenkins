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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
* This Class is used to read data from excel sheet and store it in the LinkedHashMap.
* This class will import Apache POI and perform all actions related to excel sheet.
* @author Sagar Mitkari,Shruthi Nirvanappa - Charter
*/

public class ImportDoubleDictionary {
	
		public String rowID;
		
		/**
		* This method is used to loop through all the rows in a sheet and store data in a LinkedHashMap.
		* @param FilePath String - path of excel sheet
		* @param sheetName String - sheet name
		* @param arrHeader String[] - an array of column headers 
		* @return LinkedHashMap - HashMap containing test data
		* @throws Exception - Exception handling to be implemented
		*/
		public LinkedHashMap<String, LinkedHashMap<String, String>> ImportDictionary(String FilePath,String sheetName, String[] arrHeader)throws Exception{
			
					LinkedHashMap<String,LinkedHashMap<String,String>> parent_Dict = new LinkedHashMap<String,LinkedHashMap<String,String>>();
					
					File srcFilePath = new File(FilePath);
					FileInputStream fileipStr = new FileInputStream(srcFilePath);
					XSSFWorkbook workBook = new XSSFWorkbook(fileipStr);
					XSSFSheet driverSheet = workBook.getSheet(sheetName);
					int rowCount = driverSheet.getLastRowNum();
					int colCount = driverSheet.getRow(0).getPhysicalNumberOfCells();
					
					for(Row row : driverSheet){
						int i = 0;
						for(Cell cell : row){
								if(row.getRowNum()== 0){
									// Do nothing...
								}else{
									 if (cell.getColumnIndex()==0){
										 cell.setCellType(cell.CELL_TYPE_STRING);
										 rowID = cell.getStringCellValue();
										 
										 //cell.setCellValue("Sagar");
										 parent_Dict.put(rowID, new LinkedHashMap<String,String>());
									 }else{
										 cell.setCellType(cell.CELL_TYPE_STRING);
										 System.out.println("Cell Val: " + cell.getStringCellValue() + "Sheet Name: " + sheetName);
										 parent_Dict.get(rowID).put(arrHeader[i], cell.getStringCellValue());
									 }
									 i++;
								}
						}
					}
					//workBook.clo
					//workBook.close();
					return parent_Dict;
		}
		
		/**
		* This method will read the contents of the cells in the header for the given sheet.
		* @param FilePath String - path of excel sheet
		* @param sheetName String - sheet name
		* @return String[] - array of string containing headers value from the given sheet.
		* @throws Exception - Exception handling to be implemented
		*/
		public String[] ImportHeader(String FilePath,String sheetName)throws Exception{

					File srcFilePath = new File(FilePath);
					FileInputStream fileipStr = new FileInputStream(srcFilePath);
					XSSFWorkbook workBook = new XSSFWorkbook(fileipStr);
					XSSFSheet driverSheet = workBook.getSheet(sheetName);
					int rowCount = driverSheet.getLastRowNum();
					int colCount = driverSheet.getRow(0).getPhysicalNumberOfCells();
					String[] arrHeader = new String[colCount];
					
					for(Row row : driverSheet){
						int i = 0;
						for(Cell cell : row){
							 if (cell.getColumnIndex()==0){
							 	 // Do Nothing...
							 }else{
								 cell.setCellType(cell.CELL_TYPE_STRING);
								 arrHeader[i] = cell.getStringCellValue();
							 }
							 i++;
						}
						break;
					}
					//workBook.close();
					return  arrHeader;
         }
		
		/**
		* This method is used to filter the dictionary for the given filterValue and store the filtered data in a LinkedhashMap.
		* @param doubleDict LinkedhashMap - HashMap containing test data has to be passed.
		* @param filterColumn String - Column Name to apply filters.
		* @param filterColumnValue String - value to be filtered.
		* @return LinkedHashMap - returns HashMap containing filtered data.
		*/
		public static LinkedHashMap<String,LinkedHashMap<String,String>> filterDoubleDictionary(LinkedHashMap<String,LinkedHashMap<String,String>> doubleDict, String filterColumn, String filterColumnValue){
			            
						List<String> toRemove = new ArrayList<String>();
						for(String key : doubleDict.keySet()){
							if (!(doubleDict.get(key).get(filterColumn).equals(filterColumnValue))){
								toRemove.add(key);
							}
						}
						
						for (String key: toRemove){
							doubleDict.remove(key);
						}
						return doubleDict;
		}
		
		/**
		* This method is used to fetch the cell value from the given column and for the given key.
		* @param doubleDict LinkedHashMap - HashMap having test data
		* @param key String - primary key in the sheet
		* @param columnName String - Column Name in the sheet
		* @return String - data present in the cell
		*/
		public static String fetchItem(LinkedHashMap<String,LinkedHashMap<String,String>> doubleDict,String key, String columnName ){
					   String cellValue = null;
					   cellValue = doubleDict.get(key).get(columnName);
					   return cellValue;
		}	
		
		/**
		* This method is used to fetch test data from given Column.
		* @param doubleDict LinkedhashMap - HashMap containing test data has to be passed.
		* @param columnName String - Column Name in the sheet
		* @return String - data present in the cell
		*/
		public static String fetchTestData(LinkedHashMap<String,LinkedHashMap<String,String>> doubleDict, String columnName ){
						String cellValue = null;
						for(String key : doubleDict.keySet()){
							cellValue = doubleDict.get(key).get(columnName);
			            }
						return cellValue;
		}
}


