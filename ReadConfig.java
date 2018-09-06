package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
* This class will read data from properties file
* @author Rohith Mallikarjuna - Charter
*/
public class ReadConfig {
	
	/**
	* This method will read the property file for the given key value
	* @param Key String - unique key
	* @return String - property for the given key
	* @throws IOException Signals that an I/O exception has occurred.
	*/
	public String readPropertiesFile(String Key) throws IOException{
		String result = "";
		File file = new File("src/main/java/Config/firestickconf.properties");
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		result = properties.getProperty(Key);		
		return result;
	}
	
	/**
	* This method will fetch property values from the given property file
	* @param propFileName String - property file name
	* @return HashMap - returns HashMap containing the property values from the given property file
	s*/
	public static HashMap<String,String> getPropValues(String propFileName) {
		HashMap<String,String> result = new HashMap<String, String>();
		try {
			Properties prop = new Properties();
			File filename = new File(System.getProperty("user.dir")+"\\src\\main\\java\\Config\\"+propFileName);
			InputStreamReader inputStremReader=new InputStreamReader(new FileInputStream(filename));
			if (inputStremReader != null) {
				prop.load(inputStremReader);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			Set propNames = prop.stringPropertyNames();
			Iterator<String> iterator = propNames.iterator();
			while (iterator.hasNext())
			{
				String key = iterator.next();
				result.put(key , prop.getProperty(key));
			}
			inputStremReader.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		return result;
	}
}
