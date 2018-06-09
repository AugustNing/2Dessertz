package engine.events.gameevents;

import engine.GameState;

public abstract class GameEvent {
	
	public abstract void execute(GameState gameState);
	
}
