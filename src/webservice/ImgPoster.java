package webservice;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ImgPoster {
	private String host;
	private int port;
	private String route;
	
	public ImgPoster(String host, int port, String route){
		this.host = host;
		this.port = port;
		this.route = route;
	}
	
	public boolean post(String uid, byte[] value, String formattedDate) throws ClientProtocolException, IOException{
		String url = "http://" + host + ":" + port + "/" + route;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost uploadFile = new HttpPost(url);
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("uid", uid);
		builder.addTextBody("timestamp", formattedDate);
		builder.addBinaryBody("value", value, ContentType.APPLICATION_OCTET_STREAM, "img.jpg");
		HttpEntity multipart = builder.build();
		uploadFile.setEntity(multipart);
		
		HttpResponse response = httpClient.execute(uploadFile);
		System.out.println(response.getStatusLine().getStatusCode());
		if(response.getStatusLine().getStatusCode() == 200){
			return true;
		}else{
			return false;
		}
		
		
		//UID = 000001
		//Value - file
		//timestamp
		
	}
}
