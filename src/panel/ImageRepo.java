package panel;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageRepo {
	public static Image mineImg;
	public static ImageIcon mineIcon;
	public static Image charmanderImg;
	public static Image charmanderImgBig;
	public static ImageIcon charmanderIcon;
	public static Image flagImg;
	public static ImageIcon flagIcon;
	
	public ImageRepo() {
		// TODO Auto-generated constructor stub
	}
	public static void genImage() {
		try {
			mineImg = ImageIO.read(new File("./mine.png"));
			charmanderImg = ImageIO.read(new File("./charmander_face.png"));
			charmanderImgBig = ImageIO.read(new File("./CharmanderFaceBig.png"));
			flagImg = ImageIO.read(new File("./flag.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mineIcon = new ImageIcon(mineImg);
		flagIcon = new ImageIcon(flagImg);
		charmanderIcon = new ImageIcon(charmanderImg);
	}
}
