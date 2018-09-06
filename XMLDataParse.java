package Utility;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
* The Class XMLDataParse.
* @author AshithRaj Shetty - Charter

*/
public class XMLDataParse {
	
	/**
	* This method will parse the xml string, builds document and returns Map
	* @param xmlString String - xml to be parsed
	* @param tagName String - elements tag name
	* @param attribute String - attribute to be retrieved
	* @return Map - map having tagName and text content for element 
	*/
	public Map<String, String> parseXMLString(String xmlString, String tagName, String attribute) {
		Map<String, String> appDataMap = new HashMap<String, String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		InputSource inputSource = new InputSource(new StringReader(xmlString));
		DocumentBuilder builder;
		Document doc;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(inputSource);
			NodeList node = doc.getDocumentElement().getElementsByTagName(tagName);
			for (int i=0; i< node.getLength(); i++) {
				appDataMap.put(node.item(i).getTextContent(),
						node.item(i).getAttributes().getNamedItem(attribute).getTextContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appDataMap;
	}
	
	/**
	* This method will write data from XML to file.
	* @param xmlString String - xml to be parsed
	* @throws ParserConfigurationException the parser configuration exception
	* @throws SAXException the SAX exception
	* @throws IOException Signals that an I/O exception has occurred.
	* @throws TransformerException the transformer exception
	*/
	public void writeToFile(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc;
		InputSource inputSource = new InputSource(new StringReader(xmlString));
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(inputSource);
			Node node = doc.getFirstChild();
			Node child = doc.getElementsByTagName("Title").item(0);
			child.setNodeValue("Test Automation");
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("Reports/test.html"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
