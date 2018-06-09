package data;

import authoring.GameScene;
import authoring.GameSceneSerializable;
import data.propertiesFiles.ResourceBundleManager;
import engine.GamePart;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Edward Zhuang
 * The GameLoader class is used to retrieve a saved game for either the authoring environment or the engine.
 */
public class GameLoader {

    private static final String BASE_LOCATION = ResourceBundleManager.getPath("BASELOCATION");
    private static final String SCENES = ResourceBundleManager.getPath("SCENES");
    private static final String SAVES = ResourceBundleManager.getPath("SAVES");
    private Deserializer deserializer;
    private String gameScenesLocation;
    private String gameSavesLocation;


    /**
     * GameLoader constructor
     * @param fileName name of games root file
     */
    public GameLoader(String gameName){
        deserializer = new Deserializer();
        String gameLocation = BASE_LOCATION + gameName + "/";
        gameScenesLocation = gameLocation + SCENES;
        gameSavesLocation = gameLocation + SAVES;
    }

    /**
     * Gets list of GameScenes for game authoring/game engine to use to load game
     * @return list of GameScenes
     */
    public List<GameScene> getGameScenes(boolean isNew){
    	List<GameScene> list = new ArrayList<>();
    	
    	if (isNew) {
        for (GameSceneSerializable s : deserializer.getGameSceneSerializables(gameScenesLocation)) {
        	list.add(new GameScene(s));
        }
    	} else {
    		for (GameSceneSerializable s : deserializer.getGameSceneSerializables(gameSavesLocation)) {
            	list.add(new GameScene(s));
            }
    	}
        return list;
    }

    /**
     * Gets GamePart
      * @return GamePart
     */
    public GamePart getGamePart(){
    	return deserializer.getSavePart(gameSavesLocation);
    }

}
