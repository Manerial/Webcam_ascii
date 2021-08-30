package webcam_ascii;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class CaptureManager {
	private Webcam webcam;

	public CaptureManager() {
		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open();
		
		new VideoFeedTaker().start();
	}

	class VideoFeedTaker extends Thread {
		@Override
		public void run() {
			// QQVGA 176x144 / QVGA 320x240 / VGA 640x480
			while (true) {
				BufferedImage img = webcam.getImage();
				if(img == null) {
					continue;
				}
				img = contrast(img);
		        String str = ImageToAscii.convertToAscii(scale(img, 210, 60));
		        System.out.print("\033[H\033[2J");  
		        System.out.flush();
		        System.out.print(str);
		        try {
					Thread.sleep(80);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	
	private BufferedImage scale(BufferedImage originalImage, int targetWidth, int targetHeight) {
	    Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
	    BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
	    return outputImage;
	}
	
	private BufferedImage contrast(BufferedImage image) {	    
	    RescaleOp rescaleOp = new RescaleOp(1.2f, 15, null);
	    rescaleOp.filter(image, image);
	    return image;
	}
}
