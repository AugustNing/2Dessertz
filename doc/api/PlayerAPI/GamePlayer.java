package voogasalad_2dessertz;

public interface GamePlayer {
	
	/**
	 * called by the Display/Splash screen when user selects gamePlayer. 
	 */
	public void initializePlayer();
	
	/**
	 * called when a new game is started or old game loaded.
	 * initialises a new game engine with parameters appropriate to the specific game.
	 * throws an exception if the string passed in does not represent one of the games in the data files.
	 * @param game to be played
	 */
	 void playGame(String game);
	 
	 /**
	  * saves game, uses XML writer to write a file containing all current positions of game elements.
	  * 
	  */
	 void saveGame();
}
