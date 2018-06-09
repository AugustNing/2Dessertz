package engine.events.gameevents;

import engine.GameElement;
import engine.GameState;

public class GameOverEvent extends GameEvent{
	
	private GameElement toRemove;
	
	public GameOverEvent(GameElement gameElement) {
		
	}

	@Override
	public void execute(GameState gameState) {
		gameState.resetAllLevels();
	}
}
