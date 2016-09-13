package serialmonitor;

import helper.StringHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import webservice.ImgPoster;
import webservice.TlmPoster;

public class XMLTlmHandler {
	
	//private TlmPoster poster = new TlmPoster("192.168.1.9", 3000, "postRecord");
	private TlmPoster poster = new TlmPoster("quiet-cliffs-1735.herokuapp.com", 80, "/postRecord");
	private ImgPoster imgPoster = new ImgPoster("quiet-cliffs-1735.herokuapp.com", 80, "/postPicture");
	private Map<String, String> deviceIdToWSId = new HashMap<String, String>();
	private Map<String, String> imgIdToWSId = new HashMap<String, String>();
	
	public void init(){
		deviceIdToWSId.put("DS18S20-A", "000001");
		deviceIdToWSId.put("DHT-22-HUM", "000002");
		deviceIdToWSId.put("DHT-22-TEMP", "000003");
		deviceIdToWSId.put("DHT-22-INT-HUM", "000004");
		deviceIdToWSId.put("DHT-22-INT-TEMP", "000005");
		deviceIdToWSId.put("DHT-11-HUM", "000006");
		deviceIdToWSId.put("DHT-11-TEMP", "000007");
		deviceIdToWSId.put("B1-TEMP", "000008");
		deviceIdToWSId.put("B2-TEMP", "000009");
		deviceIdToWSId.put("BASE-HUM", "000010");
		deviceIdToWSId.put("BASE-TEMP", "000011");
		deviceIdToWSId.put("B3-TEMP", "000012");
		deviceIdToWSId.put("SHED-TEMP", "000013");
		deviceIdToWSId.put("SHED-HUM", "000014");
		deviceIdToWSId.put("B1IR", "000015");
		deviceIdToWSId.put("B1VIS", "000016");
		deviceIdToWSId.put("B1LUX", "000017");
		deviceIdToWSId.put("B1FLOW", "000018");
		
		imgIdToWSId.put("G-PRIME", "000001");
		imgIdToWSId.put("B2-MON", "000002");
		imgIdToWSId.put("T3-MON", "000003");
		
	}
	
	public void post(String xmlSample) throws Exception{
		xmlSample = "<?xml version=\"1.0\"?>" + xmlSample;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(xmlSample)));

		NodeList nList = doc.getElementsByTagName("TLM");
		
		Date date = new Date(); 
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss z");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				String name = eElement.getAttribute("NAME");
				String value = eElement.getAttribute("VALUE");
				
				try{
					poster.post(deviceIdToWSId.get(name), value, dt.format(date));
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		NodeList photoList = doc.getElementsByTagName("PHOTO");
		
		for (int temp = 0; temp < photoList.getLength(); temp++) {

			Node nNode = photoList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				String name = eElement.getAttribute("NAME");
				String value = eElement.getAttribute("VALUE");
				
				
				byte[] fileDump = StringHelper.hexStringToByteArray(value);
				
				
				try{
					imgPoster.post(imgIdToWSId.get(name), fileDump, dt.format(date));
				}catch(IOException ex){
					ex.printStackTrace();
				}
				
			}
		}
	}
}
