package engine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yashas Manjunatha and Gouttham Chandraekar
 * Container for the GamePart objects in a level.
 *
 */
public class GameLevel {
	private List<GamePart> gameParts;
	private final String gameLevelID;
	private GamePart currentGamePart;
	//private int levelTime;
	
	/**
	 * Initiates a GameLevel object with the Level ID and empty list of GamePart objects.
	 * @param levelID String ID of Level
	 */
	public GameLevel(String levelID) {
		this.gameLevelID = levelID;
		gameParts = new ArrayList<>();
	}
	
	/**
	 * Gets the reference to the currently playing GamePart object.
	 * @return GamePart object that is currently being played.
	 */
	protected GamePart getCurrentGamePart() {
		return currentGamePart;
	}
	
	/**
	 * Sets the reference to the currently playing GamePart object.
	 * @param gp GamePart object that is currently being played.
	 */
	protected void setCurrentGamePart(GamePart gp) {
		currentGamePart = gp;
	}
	
	/**
	 * @return The list of GamePart objects in the GameLevel
	 */
	protected List<GamePart> getGameParts() {
		return gameParts;
	}
	
	/**
	 * Adds a GamePart object to the Level
	 * @param toAdd GamePart object to be added the the GameLevel
	 */
	protected void addGamePart(GamePart toAdd) {
		gameParts.add(toAdd);
	}

	/**
	 * @return The Level ID of this GameLevel object.
	 */
	protected String getGameLevelID() {
		return gameLevelID;
	}
}
