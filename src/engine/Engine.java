
package engine;

import java.util.HashMap;
import java.util.Map;

import engine.behaviors.Killable;
import engine.actions.Action;
import engine.actions.GroovyAction;
import engine.behaviors.MainCharacter;
import engine.behaviors.TimeTracker;
import engine.events.elementevents.ElementEvent;
import engine.events.elementevents.KeyInputEvent;
import engine.events.elementevents.MouseInputEvent;
import engine.events.elementevents.TimeEvent;
import gamePlayer.PlayerUpdater;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * @author Yashas Manjunatha and Gouttham Chandraekar
 * The Engine provides a set of Java classes that serve as a shared framework to be able to play
 * scrolling platformer games. The tasks of the Engine include, but are not limited to,
 * instantiating a Game from the the Game Data, loading both new and saved games, displaying the game and
 * provided a singular JavaFX component to be displayed, raising and handling events when certain things
 * happen in the game, collision management, playing audio, and providing information about the current state
 * of the game to the Player.
 */
public class Engine implements EngineInterface{
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final double SUBSCENE_WIDTH =  900;
    public static final double SUBSCENE_HEIGHT = 590;
	
	private Timeline animation;
	private GameState currentGameState;
	private DisplayState displayState;
	private EventManager2 eventManager;
	private PlayerUpdater playerUpdater;
	
	/**
	 * Instantiates a new Engine object for a Game.
	 * @param gameName Name of the Game (Folder Name)
	 * @param newGame True for a new game with initial settings from Authoring or False for the saved game state.
	 * @param playerUpdater PlayerUpdater object from Player to populate and update information for the HUD.
	 */
	public Engine(String gameName, boolean newGame, PlayerUpdater playerUpdater) {
		currentGameState = new GameState(gameName, newGame);
		displayState = new DisplayState(currentGameState, gameName);
		eventManager = new EventManager2(currentGameState);
		this.playerUpdater = playerUpdater;
		startAnimation();
	}
	
	/**
	 * Starts the JavaFX animation.
	 */
	private void startAnimation() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> timeStep(SECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
	
	/**
	 * Time step function that fuels the JavaFX animation. Raises time passes events in the event manager,
	 * updates the display of the game, and updated the GamePlayer's HUD.
	 * @param elapsedTime Time Elapsed in the Animation (JavaFX Property)
	 */
	private void timeStep (double elapsedTime) {
		double gameSteps = elapsedTime * currentGameState.getGameSpeed();
    	eventManager.processElementEvent(new TimeEvent(gameSteps));
    	displayState.update(currentGameState);
    	playerUpdater.updateHUD(populateHUD());
    }
	
	/**
	 * Method to populate the Player HUD with information queried from the current state of the game.
	 * @return A Map of Label to Value of the HUD Information
	 */
	private Map<String, Object> populateHUD() {
		Map<String, Object> info = new HashMap<>();
		if (currentGameState.getCurrentGamePart().hasMainCharacter()) {
			GameElement mainCharacter = currentGameState.getCurrentGamePart().getMainCharacter();
			info.put("Name", mainCharacter.getIdentifier());
			info.put("Current Level", currentGameState.getCurrentGameLevel().getCurrentGamePart().getGamePartID());
			info.put("Game Time", (int)((TimeTracker)mainCharacter.getBehavior(TimeTracker.class)).getTimePassed());
			info.put("Lives", ((MainCharacter)mainCharacter.getBehavior(MainCharacter.class)).getLives());
			info.put("Health", ((Killable)mainCharacter.getBehavior(Killable.class)).getHealth());
			//info.put("Score", value);
		}
		return info;
	}
	
	/* (non-Javadoc)
	 * @see engine.EngineInterface#close()
	 */
	@Override
	public void close() {
		playerUpdater.addHighScore((int) ((TimeTracker)currentGameState.getCurrentGamePart().getMainCharacter().getBehavior(TimeTracker.class)).getTimePassed());
		currentGameState.getAudioManager().stop();
	}
	
	/* (non-Javadoc)
	 * @see engine.EngineInterface#getDisplay()
	 */
	@Override
	public SubScene getDisplay() {
		SubScene engineSubScene = new SubScene(displayState.getSubSceneRoot(), SUBSCENE_WIDTH, SUBSCENE_HEIGHT);
		return engineSubScene;
	}

	/* (non-Javadoc)
	 * @see engine.EngineInterface#handleKeyInput(javafx.scene.input.KeyCode)
	 */
	@Override
	public void handleKeyInput(KeyCode code) {
		eventManager.processElementEvent(new KeyInputEvent(code));
	}
	
	/* (non-Javadoc)
	 * @see engine.EngineInterface#handleMouseInput(double, double)
	 */
	@Override
	public void handleMouseInput(double x, double y) {
		eventManager.processElementEvent(new MouseInputEvent(x,y));
	}
	
	/* (non-Javadoc)
	 * @see engine.EngineInterface#setVolume(double)
	 */
	@Override
	public void setVolume(double newVolume) {
		currentGameState.getAudioManager().setVolume(newVolume);
	}

	/* (non-Javadoc)
	 * @see engine.EngineInterface#pause()
	 */
	@Override
	public void pause() {
		animation.pause();
		currentGameState.getAudioManager().pause();
	}

	/* (non-Javadoc)
	 * @see engine.EngineInterface#play()
	 */
	@Override
	public void play() {
		animation.play();
		currentGameState.getAudioManager().play();
	}

	/* (non-Javadoc)
	 * @see engine.EngineInterface#save()
	 */
	@Override
	public void save() {
		currentGameState.saveGame();
	}

	/* (non-Javadoc)
	 * @see engine.EngineInterface#getKeyAssignments()
	 */
	@Override
	public Map<KeyCode, String> getKeyAssignments() {
		Map<KeyCode, String> keyAssignments = new HashMap<>();
		Map<ElementEvent, Action> events = currentGameState.getCurrentGamePart().getMainCharacter().getResponder().getResponses();
		for (ElementEvent elementEvent : events.keySet()) {
			if ((elementEvent instanceof KeyInputEvent) && (events.get(elementEvent) instanceof GroovyAction)) {
				keyAssignments.put(((KeyInputEvent) elementEvent).getKeyCode(), ((GroovyAction) events.get(elementEvent)).getName());
			}
		}
		return keyAssignments;
	}
}
