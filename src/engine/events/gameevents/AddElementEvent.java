package engine.events.gameevents;

import engine.GameElement;
import engine.GameState;

public class AddElementEvent extends GameEvent {
	private GameElement toAdd;
	
	public AddElementEvent(GameElement gameElement) {
		toAdd = gameElement;
	}

	@Override
	public void execute(GameState gameState) {
		gameState.getCurrentGamePart().addGameElement(toAdd);
		gameState.addToDisplay(toAdd);
		//System.out.println("GameElement added to state");
	}

}
