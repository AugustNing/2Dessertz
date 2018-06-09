package authoring;

import data.GameInitializer;
import java.util.List;

/** 
 * Game keeps track of an entire game 
 * This includes the name, description, image, and managers
 * 
 * @author: Summer, Edward Zhuang
 **/
public class Game {

	private String gameName;
	private String gameDescription;
	private String gameImage;
	private SceneManager mySceneManager;
	
	public Game() {
		this("TestGame");
	}
	
	public Game(String name) {
		gameName = name;
		mySceneManager = new SceneManager();
		new GameInitializer(gameName);
		gameImage = "draw-more-few-cloud.png";
	}
	
	/**
	 * @param list is a list of scenes that represents one game
	 * restores a certain scene in a game
	 */
	public void restoreGame(List<GameScene> list) {
		mySceneManager.restoreScenes(list);
	}
	
	/**
	 * 
	 * @param name new game name
	 */
	public void setGameName(String name) {
		gameName = name;
	}
	
	/**
	 * 
	 * @return gameName
	 */
	public String getName() {
		return gameName;
	}
	
	/**
	 * 
	 * @param description is the new game description 
	 */
	public void setGameDescription(String description) {
		gameDescription = description;
	}
	
	/**
	 * 
	 * @return gameDescription
	 */
	public String getGameDescription() {
		return gameDescription;
	}

	/**
	 * 
	 * @return gameImage
	 */
	public String getGameImage() {
		return gameImage;
	}

	/** 
	 * 
	 * @return gameScenes in the game
	 */
	public List<GameScene> getScenes(){
		return mySceneManager.getScenes();
	}
	
	/**
	 * 
	 * @return sceneManager
	 */
	public SceneManager getSceneManager() {
		return mySceneManager;
	}

}
