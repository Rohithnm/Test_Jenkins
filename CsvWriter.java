/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;
import java.io.FileWriter;

/**
* This class contains methods to add and update data into CSV file.
* @author Rohith Mallikarjuna - Charter
*/
public class CsvWriter extends ReportInitializer {

	/**
	* This method will append the data to the given CSV file.
	* @param results Sting - result that needs to be updated in CSV file should be passed
	*/
	public void append_results(String results)  {
		try {
			FileWriter writer = new FileWriter(CSVFilePath, true);
			writer.append(results + ",");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

	/**
	* This method will add a new line to the CSV file
	*/
	public void append_newline()  {
		try {
			FileWriter writer = new FileWriter(CSVFilePath, true);
			writer.append("\n");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}
