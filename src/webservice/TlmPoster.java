package webservice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TlmPoster {
	
	private final String USER_AGENT = "AlphaTlmPoster/5.0";
	
	private String host;
	private int port;
	private String route;
	
	public TlmPoster(String host, int port, String route){
		this.host = host;
		this.port = port;
		this.route = route;
	}
	
	public boolean post(String uid, String value, String formattedDate) throws IOException{
		String url = "http://" + host + ":" + port + "/" + route;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("UID", uid);
		con.setRequestProperty("VALUE", value);
		
		String urlParameters = "uid=" + uid + "&value=" + value + "&timestamp=" + formattedDate;
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
		return true;
	}
}

