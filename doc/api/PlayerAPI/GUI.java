public interface GUI{
	/*
	 1. Be able to select a game.
	 2. Be able to switch from one game to another.
	 3. Be able to save a current game state.
	 4. Be able to load a game state.
	 5. Be able to keep track of high scores.
	 6. Be able to switch to the authoring environment.
	 7. Be able to provide help information about a game.
	 8. Be able to replay a game.
	 9. Be able to interact with the game using input devices (ex. keyboard)
	10. Be able to throw exceptions and show some type of error box. 
	
	*/
	
	/*
	 * We need a HUD class that takes in values from its update method, which is given by the 
	 * game engine. 
	 * 
	 * We need GUI class to hold everything (go into slightly more depth - it needs to hold buttons, 
	 * comboboxes, HUD, game engine, etc)
	 */
	
	/**
	 * general setting up display for GUI
	 */
	void setScene();
	
	/**
	 * initialising buttons
	 */
	void initialiseButtons();
	
	/**
	 * initialising isplays such as HUD
	 */
	void initialiseDisplays();
	
	/**
	 * switches to authoring environment
	 */
	void goAuthoring();
}
