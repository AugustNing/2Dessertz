package engine;

import java.util.List;

import authoring.GameScene;

/**
 * @author Yashas Manjunatha
 * Public interface dictating the queries the Player could make on the Engine,
 * and more specifically the current state of the game.
 */
public interface GameMetaDataInterface {
	/**
	 * @return Current level being played.
	 */
	public String getCurrentLevelID();
	
	/**
	 * @return Number of lives the main character has left.
	 */
	public int getMainCharacterLives();
	
	/**
	 * This method is intended to be used to save the current state of the game.
	 * @return List of all the game scenes in the game and their current state.
	 */
	public List<GameScene> getGameScenes();
}
