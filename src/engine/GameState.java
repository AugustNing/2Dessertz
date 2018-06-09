package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import authoring.GameScene;
import data.GameLoader;
import data.GameSaver;
import engine.audio.AudioPlayerManager;
import engine.authouringconversion.Converter2;
import engine.behaviors.ExitPortal;
import engine.exceptions.ErrorBox;

/**
 * @author Yashas Manjunatha and Gouttham Chandraekar
 * The GameState objects contains all the information for a game. This is independent of the 
 * display (and any JavaFX objects) and thus a serializable object. This is a container for a
 * constructed hierarchy of GameLevel objects, which contain GamePart objects. At any time, one GamePart
 * object is active and playable, and the GameState keeps track of that via the current GameLevel. Also, keeps
 * track of elements to be added and removed from display, as well as audio management.
 */
public class GameState {
	private List<GameLevel> gameLevels;
	private GameLevel currentGameLevel;
	private final double gameSpeed = 2;
	private String gameName;
	
	private List<GameElement> addToDisplay;
	private List<GameElement> removeFromDisplay;
	
	private AudioPlayerManager audioPlayerManager;

	/**
	 * Instantiates a GameState for a game.
	 * @param gameName String Name of the Game (Folder Name)
	 * @param newGame Boolean whether loading a new game or saved game.
	 */
	public GameState(String gameName, boolean newGame) {
		this.gameName = gameName;
		gameLevels = new ArrayList<>();
		addToDisplay = new ArrayList<>();
		removeFromDisplay = new ArrayList<>();
		audioPlayerManager = new AudioPlayerManager(this.gameName, 1);
		
		constructGameState(loadGame(this.gameName, newGame));
		
		System.out.print("Hello"+this.getCurrentGamePart());
		audioPlayerManager.newAudioPlayer(this.getCurrentGamePart().getBackgroundAudio()).loop();
	}
	
	/**
	 * Loads a game from game data by deserializing all XML files and translating the GameScene objects
	 * to GamePart objects.
	 * @param gameName Name of the Game to Load
	 * @param newGame Boolean whether loading a new game or saved game.
	 * @return List of all the GamePart objects obtained from the game data of this game.
	 */
	private List<GamePart> loadGame(String gameName, boolean newGame) {
		GameLoader gameLoader = new GameLoader(gameName);
		Converter2 converter = new Converter2();
		List<GamePart> gameDataParts = new ArrayList<>();
		for (GameScene scene : gameLoader.getGameScenes(newGame)) {
			gameDataParts.add(converter.gameScene2GamePart(scene));
		}
		return gameDataParts;
	}
	
	/**
	 * Saves the current state of the game to game data. Translates all GamePart objects to GameScenes 
	 * and serializes them to XML and are saved in the folder with the game name under the saved games folder.
	 */
	protected void saveGame() {
		GameSaver gameSaver = new GameSaver(gameName);
		Converter2 converter = new Converter2();
		List<GameScene> gameSceneList = new ArrayList<GameScene>();
		for (GamePart part : this.getAllGameParts()) {
			gameSceneList.add(converter.gamePart2GameScene(part));
		}
		try {
			gameSaver.gameAuthorToXML(gameSceneList, false);
		} catch (IOException e) {
			new ErrorBox("Error in Game Engine", "Unable to Save the Game");
		}
	}
	
	/**
	 * Constructs the GameState Object from a List of all the GamePart objects obtained from
	 * the game data of this game. Constructs the GameLevel and GamePart hierarchy using Level IDs
	 * and locates the Main Character and sets the current GamePart reference accordingly.
	 * @param gameDataParts List of all the GamePart objects obtained from the game data of this game.
	 */
	private void constructGameState(List<GamePart> gameDataParts) {
		for (GamePart gp : gameDataParts) {
			if (!this.containsLevel(gp.getMyLevelID())) {
				this.addLevel(gp.getMyLevelID());
			}
			this.getLevel(gp.getMyLevelID()).addGamePart(gp);
			
			if (gp.hasMainCharacter()) {
				this.setCurrentGameLevel(this.getLevel(gp.getMyLevelID()));
				this.getCurrentGameLevel().setCurrentGamePart(gp);
			}
		}
	}
	
	/**
	 * @return Reference to the AudioManager of the Game
	 */
	public AudioPlayerManager getAudioManager() {
		return audioPlayerManager;
	}
	
	/**
	 * Adds a GameElement to be added to the display.
	 * Used in DisplayState.
	 * @param ge GameElement object to be added.
	 */
	public void addToDisplay (GameElement ge) {
		addToDisplay.add(ge);
	}
	
	/**
	 * Used in DisplayState.
	 * @return The List of GameElement objects to be added to the display.
	 */
	protected List<GameElement> getAddToDisplay() {
		return addToDisplay;
	}
	
	/**
	 * Clear elements to be added to the display. This call happens after the elements 
	 * are added to the display.
	 * Used in DisplayState.
	 */
	protected void clearAddToDisplay() {
		addToDisplay.clear();
	}
	
	/**
	 * Adds a GameElement to be removed from the display.
	 * Used in DisplayState.
	 * @param ge GameElement object to be removed.
	 */
	public void removeFromDisplay (GameElement ge) {
		removeFromDisplay.add(ge);
	}
	
	/**
	 * Used in DisplayState.
	 * @return The List of GameElement objects to be removed from the display.
	 */
	protected List<GameElement> getRemoveFromDisplay() {
		return removeFromDisplay;
	}
	
	/**
	 * Clear elements to be removed from the display. This call happens after the elements 
	 * are removed from the display.
	 * Used in DisplayState.
	 */
	protected void clearRemoveFromDisplay() {
		removeFromDisplay.clear();
	}
	
	/**
	 * @return Speed of the Game
	 */
	protected double getGameSpeed() {
		return gameSpeed;
	}
	
	/**
	 * The current playing GamePart object has the main character element.
	 * @return Reference to the Current Playing GamePart Object.
	 */
	public GamePart getCurrentGamePart() {
		return currentGameLevel.getCurrentGamePart();
	}
	
	/**
	 * Performs a context switch to change the current playing GamePart based on the portal used
	 * to change levels/parts. Transfers the Main Character to the new GamePart, updates references
	 * to the current GamePart and GameLebel, and readies the display updates.
	 * @param newPartID ID of the new GamePart
	 * @param portalID ID of the Portal object, used to place the Main Character in the new GamePart.
	 */
	public void changeCurrentGamePart(String newPartID, Integer portalID) {
		audioPlayerManager.stop();
		
		for (GameLevel gl : gameLevels) {
			for (GamePart newGamePart : gl.getGameParts()) {
				if(newGamePart.getGamePartID().equals(newPartID)) {
					
					GameElement mainCharacter = this.getCurrentGamePart().getMainCharacter();
					this.getCurrentGamePart().removeGameElement(mainCharacter);
					
					for (GameElement element : this.getCurrentGamePart().getElements()) {
						removeFromDisplay(element);
					}
					
					setCurrentGameLevel(gl);
					gl.setCurrentGamePart(newGamePart);
					for (GameElement element : this.getCurrentGamePart().getElements()) {
						if (element.hasBehavior(ExitPortal.class)) {
							ExitPortal exitP = (ExitPortal) element.getBehavior(ExitPortal.class);
							if (exitP.getPortalID().equals(portalID)) {
								mainCharacter.setPosition(element.getPosition());
								this.getCurrentGamePart().addGameElement(mainCharacter);
								break;	
							}
						}
						
					}
					
					for (GameElement element : this.getCurrentGamePart().getElements()) {
						addToDisplay(element);
					}
				}
			}
		}
		
		audioPlayerManager.newAudioPlayer(this.currentGameLevel.getCurrentGamePart().getBackgroundAudio()).loop();
	}
	
	/**
	 * Resets a specific level by re-loading the initial configurations from
	 * the game data and replacing the current GamePart objects in the level
	 * with the original GamePart objects as defined in Authoring.
	 * @param levelID ID of GameLevel to reset.
	 */
	public void resetLevel(String levelID) {
		if(this.getCurrentGameLevel().getGameLevelID().equals(levelID)) {
			for (GameElement element : this.getCurrentGamePart().getElements()) {
				removeFromDisplay(element);
			}
		}
		
		GameLevel toReset = this.getLevel(levelID);
		List<GamePart> gameDataParts = loadGame(this.gameName, true);
		for(GamePart gamePart : toReset.getGameParts()) {
			for (GamePart initialGamePart : gameDataParts) {
				if (initialGamePart.getGamePartID().equals(gamePart.getGamePartID())) {
					gamePart = initialGamePart;
				}				
				if (gamePart.hasMainCharacter()) {
					toReset.setCurrentGamePart(gamePart);
				}
			}
		}
		
		if(this.getCurrentGameLevel().getGameLevelID().equals(levelID)) {
			for (GameElement element : this.getCurrentGamePart().getElements()) {
				addToDisplay(element);
			}
		}
	}
	
	/**
	 * Sets the Current GameLevel
	 * @param gl Reference to the Current Playing GameLevel Object to Set
	 */
	protected void setCurrentGameLevel(GameLevel gl) {
		currentGameLevel = gl;
	}
	
	/**
	 * The Current GameLevel has the GamePart with the Main Character
	 * @return Reference to the Current Playing GameLevel Object
	 */
	protected GameLevel getCurrentGameLevel() {
		return currentGameLevel;
	}
	
	/**
	 * Checks if the game contains a GameLevel with the specific LevelID
	 * @param levelID String ID of the Level to Search For
	 * @return Boolean whether the game contains a GameLevel with the specified LevelID
	 */
	protected boolean containsLevel(String levelID) {
		return gameLevels.stream().anyMatch(gl -> gl.getGameLevelID().equals(levelID));
	}
	
	/**
	 * @param levelID String ID of the Level to Get
	 * @return The GameLevel Object matching the LevelID Queried
	 */
	protected GameLevel getLevel(String levelID) {
		for (GameLevel gl : gameLevels) {
			if (gl.getGameLevelID().equals(levelID))
				return gl;
		}
		new ErrorBox("Error in Game Engine", "Game Level Not Found");
		System.out.println("ERROR: Game Level Not Found");
		return null;
	}
	
	/**
	 * Instantiates and adds a new GameLevel object to the Game
	 * @param levelID String ID of the Level to be Added
	 */
	protected void addLevel(String levelID) {
		gameLevels.add(new GameLevel(levelID));
	}
	
	/**
	 * @return List of All GamePart objects in the Game
	 */
	protected List<GamePart> getAllGameParts() {
		List<GamePart> toReturn = new ArrayList<>();
		for (GameLevel gl : this.gameLevels) {
			toReturn.addAll(gl.getGameParts());
		}
		return toReturn;
	}

	public void resetAllLevels() {
		for (GameLevel l: gameLevels) {
			System.out.println("Reseting level: " + l.getGameLevelID());
			resetLevel(l.getGameLevelID());
		}		
	}
}
