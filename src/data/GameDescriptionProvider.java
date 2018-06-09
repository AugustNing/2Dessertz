package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import data.propertiesFiles.ResourceBundleManager;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * @author Edward Zhuang
 * Provides description of game for the GameDescriptionProvider.
 */
public class GameDescriptionProvider {

	private static final String DESCRIPTION = ResourceBundleManager.getPath("DESCRIPTION");
	private static final String DESCRIPTION_TEXT = ResourceBundleManager.getPath("DESCRIPTIONTEXT");
	private static final String NAME = ResourceBundleManager.getPath("NAME");
	private static final String DESCRIPTION_IMAGE = ResourceBundleManager.getPath("DESCRIPTIONIMAGE");
	private String baseLocation = ResourceBundleManager.getPath("BASELOCATION");
	
	public GameDescriptionProvider() { }

	/**
	 * Gets name of game
	 * @param gameName game root file name
	 * @return Game name
	 */
	public String getGameName(String gameName) {
		String gameLocation = baseLocation + gameName + "/";
		String gameDescriptionLocation = gameLocation + DESCRIPTION;
		return retrieveStringFromTextFile(gameDescriptionLocation + NAME);
	}

	/**
	 * Gets description of game
	 * @param gameName game root file name
	 * @return Game description
	 */
	public String getGameDescription(String gameName) {
		String gameLocation = baseLocation + gameName + "/";
		String gameDescriptionLocation = gameLocation + DESCRIPTION;
		return retrieveStringFromTextFile(gameDescriptionLocation + DESCRIPTION_TEXT);
	}

	/**
	 * Gets description image of game
	 * @param gameName game root file name
	 * @return Game description image
	 */
	public Image getDescriptionImage(String gameName) {
		String gameLocation = baseLocation + gameName + "/";
		String gameDescriptionLocation = gameLocation + DESCRIPTION;
		
	    try {
	    	BufferedImage bufferedImage = ImageIO.read(new File(gameDescriptionLocation + DESCRIPTION_IMAGE));
	    	return SwingFXUtils.toFXImage(bufferedImage, null);
	    } catch (IOException ignored) {
	    	// image should always be found
        }
        return null;
	}
	
    /**
     * Converts a text file into a string
     * @param fileName text file
     * @return string version of text file
     */
    private String retrieveStringFromTextFile(String fileName){
    	Scanner scanner = null;
        try {
            scanner = new Scanner( new File(fileName));
        } catch (FileNotFoundException ignored) {
            // File should always be found
        }
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();

        return text;
    }

}
