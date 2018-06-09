package data;

import java.io.File;
import java.io.IOException;

import data.propertiesFiles.ResourceBundleManager;

/**
 * Used when a game is first made. Creates all the necessary game files.
 * @author Edward Zhuang
 *
 */
public class GameInitializer {

    private static final String PLACEHOLDER = "This is a placeholder description.";
    private static final String BASE_LOCATION = ResourceBundleManager.getPath("BASELOCATION");
    private static final String DESCRIPTION = ResourceBundleManager.getPath("DESCRIPTION");
    private static final String SCENES = ResourceBundleManager.getPath("SCENES");
    private static final String IMAGES = ResourceBundleManager.getPath("IMAGES");
    private static final String BACKGROUNDIMAGES = ResourceBundleManager.getPath("BACKGROUNDIMAGES");
    private static final String SAVES = ResourceBundleManager.getPath("SAVES");
    private static final String AUDIO = ResourceBundleManager.getPath("AUDIO");
    private String gameLocation;
    private String gameDescriptionLocation;
    private String gameScenesLocation;
    private String gameImagesLocation;
	private String gameSavesLocation;
	private String gameBackgroundImagesLocation;
	private String gameAudioLocation;
    
	public GameInitializer(String gameName) {

        gameLocation = BASE_LOCATION + gameName + "/";
        gameDescriptionLocation = gameLocation + DESCRIPTION;
        gameScenesLocation = gameLocation + SCENES;
        gameImagesLocation = gameLocation + IMAGES;
        gameBackgroundImagesLocation = gameLocation + BACKGROUNDIMAGES;
        gameSavesLocation = gameLocation + SAVES;
        gameAudioLocation = gameLocation + AUDIO;
        
        if (! new File(gameLocation).exists()){
        	 makeDirectories();
        	 addDefaultDescription(gameName);
        }	
	}
	
    /**
     * makes directories for a game
     */
    private void makeDirectories(){
        new File(gameLocation).mkdirs();
        new File(gameDescriptionLocation).mkdirs();
        new File(gameScenesLocation).mkdirs();
        new File(gameImagesLocation).mkdirs();
        new File(gameSavesLocation).mkdirs();
        new File( gameBackgroundImagesLocation).mkdirs();
        new File(gameAudioLocation).mkdirs();
    }
    
    private void addDefaultDescription(String gameName) {
        try {
			GameSaver saver = new GameSaver(gameName);
	    	saver.addDescription(gameName, PLACEHOLDER);
		} catch (IOException ignored) {
			// File should be found as it is created in the GameSaver
		}
    	
    }
		
}
