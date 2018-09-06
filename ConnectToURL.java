package Utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

/**
* This class is used to connect to the URL.
* @author AshithRaj Shetty - Charter
*/
public class ConnectToURL {
	
	/** The Constant USER_AGENT. */
	private final static String USER_AGENT = "Mozilla/5.0";// Chrome/52.0.2743.116";

	/**
	 * This method will launch charter lab URL and store cookies information.
	 * @param keyStroke String - 
	 * @throws Exception the exception
	 */
	public static void sendGet(String keyStroke) throws Exception {
		
		//url = "https://glas.charterlab.com/#/device/8963";
		String url = "https://glas.charterlab.com/api/v1/device/8977/ir/"
				+ keyStroke + "?username=P2707952";
		String homeURL = "http://glas.charterlab.com";

		HttpClient client = new DefaultHttpClient();
		//HttpGet rq1 = new HttpGet(homeURL);	
		URLConnection connection = new URL("https://glas.charterlab.com/api/v1/device/8977/ir/ok?username=P2707952").openConnection();
		connection.connect();
		List<String> cookies = connection.getHeaderFields().get("Cookie");
		/*CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cookieManager);
		HttpResponse response1 = client.execute(rq1);
		response1.getEntity().getContent();*/
		/*System.out.println("Response Code1 : "
				+ response1.getStatusLine().getStatusCode() + "\n");
		BufferedReader rd1 = new BufferedReader(new InputStreamReader(response1
				.getEntity().getContent()));
		StringBuffer result1 = new StringBuffer();
		String line1 = "";
		while ((line1 = rd1.readLine()) != null) {
			result1.append(line1);
		}
		System.out.println(result1.toString());
		// returns the cookie store(bunch of cookies)
		CookieStore cookieStore = cookieManager.getCookieStore();

		// getting cookies which returns in the form of List of type HttpCookie
		List<HttpCookie> listOfcookies = cookieStore.getCookies();
		System.out.println("Length: " + listOfcookies.size());
		for (HttpCookie httpCookie : listOfcookies) {

			System.out.println("Cookie Name : " + httpCookie.getName()
					+ " Cookie Value : " + httpCookie.getValue());
		}*/
		/*for (String cookie:cookies)*/{
			System.out.println("Cookie Name : " + cookies);
		}
		//rq1.releaseConnection();
		String cookieValue = null;

	     CookieHandler handler = CookieHandler.getDefault();
	     if (handler != null)    {
	          Map<String, List<String>> headers = handler.get(new URL("https://glas.charterlab.com/api/v1/device/8998/ir/ok?username=P2707952").toURI(), new HashMap<String,List<String>>());
	          List<String> values = headers.get("Cookie");
	          for (Iterator<String> iter=values.iterator(); iter.hasNext();) {
	               String v = iter.next(); 

	               if (cookieValue == null) 
	                    cookieValue = v; 
	               else{
	                    cookieValue = cookieValue + ";" + v;
	                    System.out.println("CookieValue"+cookieValue);
	               }
	          } 
	     }
		HttpGet request = new HttpGet(url);
		request.addHeader("Cookie",
				"MRHSession=ff72f5577492666d91fd412973926aca");
		request.addHeader("Cookie",
				"LastMRH_Session=94b924e4");
		// add request header
		request.addHeader("User-Agent", USER_AGENT);

		HttpResponse response = client.execute(request);

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : "
				+ response.getStatusLine().getStatusCode() + "\n");

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());

	}
}
