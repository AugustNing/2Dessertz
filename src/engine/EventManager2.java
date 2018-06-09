package engine;

import java.util.ArrayList;
import java.util.List;

import engine.collision.CollisionManager;
import engine.events.elementevents.ElementEvent;
import engine.events.gameevents.GameEvent;

/**
 * @author Martin
 * 
 * Construct a new EventManager, used to receive, process, and handle GameEvents
 * and ElementEvents
 *
 */
public class EventManager2 {
	
	private GameState gameSate;
	private CollisionManager collisionManager;
	
	/**
	 * Construct an EventManager with a GameState
	 * 
	 * @param state GameState of the current game
	 */
	public EventManager2 (GameState state) {
		gameSate = state;
		collisionManager = new CollisionManager();
	}
	
	/**
	 * Send an ElementEvent to the game elements of the current game part to be processed,
	 * and store the returned GameEvents.
	 * @param ee
	 */
	public void processElementEvent(ElementEvent ee) {
//		if (ee instanceof KeyInputEvent) {
//			System.out.println(ee);
//		}
		List<GameEvent> gameEvents = new ArrayList<>();
		for (GameElement ge: gameSate.getCurrentGamePart().getElements()) {
			gameEvents.addAll(ge.processEvent(ee));
		}
		gameEvents.addAll(collisionManager.handleCollisions(gameSate.getCurrentGamePart()));
		gameEvents.stream().forEach(event -> processGameEvent(event));
	}
	
	/**
	 * Execute a game event on the current GameState.
	 * @param gameEvent GameEvent to be processed
	 */
	private void processGameEvent(GameEvent gameEvent) {
		gameEvent.execute(gameSate);
	}
}