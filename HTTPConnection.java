package Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
* This class is used to establish HTTP connection and send get/post request
* @author AshithRaj Shetty - Charter
*/
public class HTTPConnection {
	
	/**
	* This method will establish HTTP connection and send get request
	* @param url URL - URL to be loaded
	* @return string - Response after loading the URL
	* @throws ParserConfigurationException the parser configuration exception
	* @throws SAXException the SAX exception
	* @throws IOException Signals that an I/O exception has occurred.
	*/
	public String httpGETData(URL url) throws ParserConfigurationException, SAXException, IOException {
		StringBuilder response = new StringBuilder();
		try {
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader((InputStream) httpConnection.getContent()));
			String input;
			while ((input = bufferReader.readLine()) != null) {
				response.append(input.trim());
			}
			bufferReader.close();
		} catch (Exception e) {
			response.append("Reading App data failed.");
			e.printStackTrace();
		}
		return response.toString();
	}
	
	/**
	* This method will establish HTTP connect and send post requests.
	* @param url URL - URL to be loaded
	* @return Boolean - true if response is received successfully.
	* @throws IOException Signals that an I/O exception has occurred.
	*/
	public boolean httpPOSTData(URL url) throws IOException {
		try {
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setDoOutput(true);
			OutputStreamWriter outPutStream = new OutputStreamWriter(httpConnection.getOutputStream());
			outPutStream.flush();
			outPutStream.close();
			httpConnection.getResponseCode();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
