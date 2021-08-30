package webcam_ascii;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageToAscii {
	// private static String grayRamp = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~i!lI;:,\"^`\\'. ";
	private static String grayRamp = "@%#*+=-:. ";

	public static void convertToAscii(String imgname) {
		try {
			BufferedImage img = ImageIO.read(new File(imgname));
			convertToAscii(img);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static String convertToAscii(BufferedImage img) {
		String ascii = "";
		for (int i = 0; i < img.getHeight(); i++) {
			String line = "";
			for (int j = 0; j < img.getWidth(); j++) {
				Color pixcol = new Color(img.getRGB(j, i));
				double pixelGray = (pixcol.getRed() * 0.30) +
						(pixcol.getBlue() * 0.59) +
						(pixcol.getGreen() * 0.11);
				line = line + pixelToChar(pixelGray);
			}
			ascii += line;
		}
		return ascii;
	}

	public static char pixelToChar(double gray) {
		return grayRamp.charAt((int) Math.ceil((grayRamp.length() - 1) * gray / 255));
	}
}
