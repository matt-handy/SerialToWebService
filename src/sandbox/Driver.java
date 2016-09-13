package sandbox;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import webservice.TlmPoster;

public class Driver {

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		TlmPoster poster = new TlmPoster("192.168.1.8", 3000, "postRecord");
		
		String blob = "<SAMPLE><TLM NAME=\"000001\" VALUE=\"76\" /><TLM NAME=\"000001\" VALUE=\"77\" /><TLM NAME=\"000001\" VALUE=\"30\" /></SAMPLE>";
		blob = "<?xml version=\"1.0\"?>" + blob;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(blob)));

		System.out.println("Root element :"
				+ doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("TLM");
		
		Date date = new Date(); 
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd_hh:mm:ss");

		System.out.println("----------------------------");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				String name = eElement.getAttribute("NAME");
				String value = eElement.getAttribute("VALUE");
				System.out.println("Name : " + name);
				System.out.println("Value : " + value);
				System.out.println("Date : " + dt.format(date));
				
				try{
					poster.post(name, value, dt.format(date));
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		
	}
}

