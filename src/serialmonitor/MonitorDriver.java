package serialmonitor;

import java.util.ArrayList;
import java.util.List;

public class MonitorDriver {

	public static void main(String[] args) {
		
		List<Thread> monitors = new ArrayList<Thread>();
		
		int counter = 0;
		for (String port : args){
			MonitorThread mt = new MonitorThread(port, counter + "");
			mt.start();
			monitors.add(mt);
			counter++;
		}
		/*
		XMLTlmHandler handler = new XMLTlmHandler();
		handler.init();
		SerialPort serialPort = new SerialPort(args[0]);
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
            	
            	int lastIdx = 0;
            	while(matcher.find()){
            		String match = matcher.group();
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
		*/

	}

}

