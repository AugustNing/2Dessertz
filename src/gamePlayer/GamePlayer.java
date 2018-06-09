package gamePlayer;

import javafx.scene.Scene;

public interface GamePlayer {

	/**
	 * called when a new game is started or old game loaded. initialises a new game
	 * engine with parameters appropriate to the specific game. throws an exception
	 * if the string passed in does not represent one of the games in the data
	 * files.
	 * 
	 * @param game
	 *            to be played
	 */
	void playGame(String game, boolean isNewGame);

	/**
	 * allows the display to retrieve the scene associated the game player
	 * 
	 * @return scene to be displayed
	 */
	public Scene getScene();

}
