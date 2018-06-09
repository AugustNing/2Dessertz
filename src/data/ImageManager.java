package data;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.exceptions.ErrorBox;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * @author Edward Zhuang
 * Class responsible for storing and accessing images from game data.
 */
public class ImageManager {

    private static final String BACKSLASH = "/";
    private static final String BACKGROUND_IMAGES = "backgroundimages";
    private static final String IMAGES = "images";
    private static final String PNG = "png";
    private String gameImagesLocation;
    private String gameBackgroundImagesLocation;
    private static final String defaultImageLocation = "./data/gamedata/defaultimages/";
    private static final String baseLocation = "./data/gamedata/games/";
    private static final String DEFAULT = "default";


    public ImageManager(String gameName){
    	if (gameName.equals(DEFAULT)) {
    		gameImagesLocation = defaultImageLocation;
    	} else {
            String gameLocation = baseLocation + gameName + BACKSLASH;
	        gameImagesLocation = gameLocation + IMAGES + BACKSLASH;
	        gameBackgroundImagesLocation = gameLocation + BACKGROUND_IMAGES + BACKSLASH;
    	}
    }

    private BufferedImage getBufferedImage(String imageName, String location) {
        BufferedImage img;
        try {
        		System.out.println("help " + location + " " + imageName);
            img = ImageIO.read(new File(location + imageName));
            return img;
        } catch (IOException e) {

        }
        return null;
    }

    /**
     * Retrieves an FX Image from images folder of a game
     * @param imageName name of image desired
     * @return BufferedImage
     */
    public Image getImage(String imageName){
    	try {
    		return bufferedImagetoJavaFXImage(getBufferedImage(imageName, gameImagesLocation));
    	} catch (NullPointerException e) {
    		ImageManager defaultIM = new ImageManager("default");
    		return defaultIM.getImage(imageName);
//    		/*System.out.println(imageName);
//    		throw new NullPointerException();*/
    	}
    }

    private void storeBufferedImage(String imageName, BufferedImage image, String location){
        try {
        	ImageIO.write(image, "png", new File(location + imageName + ".png"));
        } catch (IOException e) {
        	// This would not happen, as the location is initialized in the constructor.
        	return;
        }
    }

    /**
     * Stores BackgroundImage
     * @param image Image to be saved
     * @return string of saved background image
     */
    public String storeBackgroundImage(Image image) {
        File directory = new File(gameBackgroundImagesLocation);
    	int number = directory.listFiles().length;
    	String background = "background" + number;
    	storeBufferedImage(background, javaFXToBufferedImage(image), gameBackgroundImagesLocation);
    	return background;
    }

    /**
     * Retrieves Background Image
     * @param imageName name of image
     * @return Image
     */
    public Image getBackgroundImage(String imageName) {
    	return bufferedImagetoJavaFXImage(getBufferedImage(imageName, gameBackgroundImagesLocation));
    }

    /**
     * Stores an FX Image as BufferedImage
     * @param imageName desired name of image
     * @param image to be stored
     */
    public void storeImage(String imageName, Image image){
        storeBufferedImage(imageName, javaFXToBufferedImage(image), gameImagesLocation);
    }

    /**
     * Stores the composite background image
     * @param imageName name of image
     * @param ri image to be stored
     */
    public void storeCompositeBackgroundImage(String imageName, RenderedImage ri) {
    	try {
			ImageIO.write(ri, PNG, new File(gameImagesLocation+imageName));
		} catch (IOException e) {
			// This would not happen, as the location is initialized in the constructor.
			return;
		}
    }
    
    /**
     * Converts JavaFX image to BufferedImage
     * @param image FX image
     * @return BufferedImage
     */
    private BufferedImage javaFXToBufferedImage(Image image){
        return SwingFXUtils.fromFXImage(image, null);
    }

    private Image bufferedImagetoJavaFXImage(BufferedImage bufferedImage){
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

}
