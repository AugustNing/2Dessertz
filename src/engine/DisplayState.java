package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.ImageManager;
import engine.behaviors.MainCharacter;
import javafx.scene.Group;

/**
 * @author Yashas Manjunatha, Gouttham Chandraekar, and Martin Muenster
 * Handles the Display of the Engine. Our design provides separation between the current game state
 * and the display state of the engine. The engine is constructed and updated from the current game state.
 * We use lists of elements to add and remove from the display to keep updating smart. Each element also update's
 * itself each time step. The DisplayState is a container of ImageElement objects which are a 1 to 1 mapping
 * of GameElement objects in the currently playing GamePart object.
 */
public class DisplayState {
	public static final double SUBSCENE_WIDTH =  900;
    public static final double SUBSCENE_HEIGHT = 590;
	
	private List<ImageElement> imageElements;
	private Group subSceneRoot = new Group();
	private ImageManager imageManager;
	
	/**
	 * Instantiates the DisplayState. Called when Engine in instantiated.
	 * @param gameState The GameState being played in the Engine.
	 * @param gameName Name of the Game (Folder Name)
	 */
	public DisplayState (GameState gameState, String gameName) {
		imageManager = new ImageManager(gameName);
		imageElements = new ArrayList<>();
		
		for(GameElement e : gameState.getCurrentGamePart().getElements()) {
			addElementToDisplay(e);
		}
		gameState.clearAddToDisplay();
	}
	
	/**
	 * Adds a GameElement to the display by creating the corresponding
	 * ImageElement object and adding it to the JavaFX Node for the display.
	 * @param element GameElement object to be added.
	 */
	private void addElementToDisplay(GameElement element) {
		System.out.println(element);
		ImageElement imageElement = new ImageElement(element, imageManager);
		imageElements.add(imageElement);
		subSceneRoot.getChildren().add(imageElement);
	}
	
	/**
	 * Removes a GameElement from the display by removing all ImageElement objects
	 * that have a matching GameElement reference from the JavaFX Node for the display.
	 * @param element GameElement object to be removed.
	 */
	private void removeElementFromDisplay(GameElement element) {
		List<ImageElement> toRemove = new ArrayList<>();
		for (ImageElement i : imageElements) {
			if (i.getReference() == element) {
				toRemove.add(i);
			}
		}
		for (ImageElement i : toRemove) {
			imageElements.remove(i);
			subSceneRoot.getChildren().remove(i);
		}
	}
	
	/**
	 * @return The Root object for the SubScene JavaFX Node that consolidated all
	 * display elements in the Engine.
	 */
	public Group getSubSceneRoot() {
		return subSceneRoot;
	}
	
	/**
	 * Updates the DisplayState using the current GameState.
	 * @param updatedGameState Reference to Current GameState Object
	 */
	public void update(GameState updatedGameState) {
		updateImageElements(scrollingAroundMainCharacter(updatedGameState.getCurrentGamePart()));
		
		for (GameElement e : updatedGameState.getAddToDisplay()) {
			addElementToDisplay(e);
		}
		updatedGameState.clearAddToDisplay();
		for (GameElement e : updatedGameState.getRemoveFromDisplay()) {
			removeElementFromDisplay(e);
		}
		updatedGameState.clearRemoveFromDisplay();
	}

	/**
	 * Updates the ImageElement objects parameters including offset to main character.
	 * @param mainCharacterLocation Vector of MainCharacter Location
	 */
	private void updateImageElements(List<Double> mainCharacterLocation) {
		for (ImageElement imageElement : imageElements) {
			imageElement.updateStateWithOffSet(mainCharacterLocation);
		}
	}
	
	/**
	 * Calculates the offset vector based on the MainCharacter position.
	 * @param gamePart Current GamePart that is Being Player (i.e. had main character)
	 * @return Offset Vector used to Update ImageElement Objects
	 */
	private List<Double> scrollingAroundMainCharacter(GamePart gamePart) {
		List<Double> offset = Arrays.asList(0.0,0.0);
		for (GameElement e: gamePart.getElements()) {
			if (e.hasBehavior(MainCharacter.class)) {
				MainCharacter mc_props = (MainCharacter) e.getBehavior(MainCharacter.class);
				offset = mc_props.getImageViewOffset(SUBSCENE_WIDTH, SUBSCENE_HEIGHT);
			}
		}
		return offset;
	}
}
