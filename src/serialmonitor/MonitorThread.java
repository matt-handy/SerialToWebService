package serialmonitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jssc.SerialPort;
import jssc.SerialPortException;

public class MonitorThread extends Thread {
	
	private String port;
	private String threadId;
	
	public MonitorThread(String port, String threadId){
		this.port = port;
		this.threadId = threadId;
	}
	
	public void run(){
		XMLTlmHandler handler = new XMLTlmHandler();
		handler.init();
		SerialPort serialPort = new SerialPort(port);
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(9600, 8, 1, 0);//Set params.
            
            //byte[] buffer = serialPort.readBytes(10);
            //String content = new String(buffer);
            //System.out.println(content);
            String sample = "";
            while(true){
            	sample += serialPort.readString();
            	Pattern pattern = Pattern.compile("<SAMPLE>.*</SAMPLE>");
            	Matcher matcher = pattern.matcher(sample);
            	
            	//System.out.println(sample);
            	
            	
            	int lastIdx = 0;
            	while(matcher.find()){
            		String match = matcher.group();
            		System.out.println("Thread: " + threadId);
            		System.out.println(match);
            		try {
						handler.post(match);
					} catch (Exception e) {
						System.out.println("Unable to post tlm");
						e.printStackTrace();
					}
					lastIdx = matcher.end();
				}
            	
            	sample = sample.substring(lastIdx);
            	
            	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            //serialPort.closePort();//Close serial port
            
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
	}
}
