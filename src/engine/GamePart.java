package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import engine.behaviors.MainCharacter;

/**
 * @author Yashas Manjunatha and Gouttham Chandraekar
 * The GamePart object represents the smallest playable part of the game. It is a container
 * for GameElement objects. At any time in the game, only one GamePart is active and is displayed
 * in the DisplayState. The active/playable GamePart contains the main character. The main
 * character is transfered among GamePart objects during the context switches between GamePart
 * objects when changing levels/parts.
 */
public class GamePart {
	private List<GameElement> gameElements;
	private final String gamePartID;
	private final String myLevelID;
	private String audioName = "WiiShopChannelMusic";
	
	/**
	 * Instantiates a GamePart with it's GamePart ID, Level ID, and empty list of GameElement objects.
	 * @param gamePartID The String ID of this GamePart object.
	 * @param levelID The String ID of the Level this GamePart object lives in.
	 */
	public GamePart(String gamePartID, String levelID) {
		gameElements = new ArrayList<>();
		this.gamePartID = gamePartID;
		this.myLevelID = levelID;
	}
	
	/**
	 * Checks if this GamePart contains the main character.
	 * @return Boolean whether this GamePart contains the Main Character GameElement.
	 */
	protected boolean hasMainCharacter() {
		for (GameElement ge : gameElements) {
			if (ge.hasBehavior(MainCharacter.class)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds background audio to play during this GamePart playthrough
	 * @param audioName Name of Audio File
	 */
	public void addAudio (String audioName) {
		this.audioName = audioName;
	}
	
	/**
	 * @return Name of Audio File Used for Background Music
	 */
	public String getBackgroundAudio() {
		return this.audioName;
	}
	
	/**
	 * Adds a GameElement object to the GamePart object.
	 * This adds an element to the game.
	 * @param ge GameElement object to be added.
	 */
	public void addGameElement (GameElement ge) {
		gameElements.add(ge);
	}
	
	/**
	 * Removes a GameElement object from the GamePart object.
	 * This removes an element from the game.
	 * @param ge  GameElement object to be removed.
	 */
	public void removeGameElement (GameElement ge) {
		gameElements.remove(ge);
	}
	
	/**
	 * @return The String ID of this GamePart object.
	 */
	public String getGamePartID () {
		return gamePartID;
	}
	
	/**
	 * @return The String ID of the Level this GamePart object lives in.
	 */
	public String getMyLevelID() {
		return myLevelID;
	}
	
	/**
	 * @return List of GameElement objects constituting the GamePart
	 */
	public List<GameElement> getElements() {
		return gameElements;
	}
	
	/**
	 * @return The Main Character GameElement object contained in the GamePart
	 */
	public GameElement getMainCharacter() {
		return gameElements.stream()
				.filter(e -> e.hasBehavior(MainCharacter.class))
				.collect(Collectors.toList()).get(0);
	}
	
	/**
	 * @return List of GameElements that have the desired identifier
	 * @param String identifier: what the .getIdentifier() method should be for the desired elements
	 */
	public List<GameElement> getElementsByIdentifier(String identifier) {
		List<GameElement> elements = new ArrayList<>();
		gameElements.stream()
		.filter(el -> el.getIdentifier().equals(identifier))
		.forEach(el -> elements.add(el));
		return gameElements;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "GamePart ID: " + gamePartID + ", Level ID: " + myLevelID;
	}
}
