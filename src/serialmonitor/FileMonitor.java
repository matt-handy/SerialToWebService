package serialmonitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import webservice.ImgPoster;

public class FileMonitor extends Thread {
	private String targetDirectory;
	private ImgPoster imgPoster = new ImgPoster("quiet-cliffs-1735.herokuapp.com", 80, "/postPicture");

	public FileMonitor(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}

	@Override
	public void run() {
		while (true) {
			File varTmpDir = new File(targetDirectory + "image.jpg");
			boolean exists = varTmpDir.exists();
			if (exists) {
				try {
					byte[] fileData = new byte[(int) varTmpDir.length()];
					FileInputStream in = new FileInputStream(varTmpDir);
					in.read(fileData);
					in.close();
					
					if(fileData.length == 0){
						continue;
					}
					
					System.out.println("Found file of bytes: " + fileData.length);
					
					Date date = new Date();
					SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss z");
					imgPoster.post("000005", fileData, dt.format(date));
					
					System.out.println("Deleting file");
					varTmpDir.delete();
					System.out.println("Waiting for new image");
					
					Thread.sleep(60000);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
}
