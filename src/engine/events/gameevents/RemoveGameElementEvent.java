package engine.events.gameevents;

import engine.GameElement;
import engine.GameState;
import engine.behaviors.MainCharacter;

public class RemoveGameElementEvent extends GameEvent{
	
	private GameElement toRemove;
	
	public RemoveGameElementEvent(GameElement gameElement) {
		toRemove = gameElement;
		//System.out.println("Remove Event created");
	}

	@Override
	public void execute(GameState gameState) {
		if (!toRemove.hasBehavior(MainCharacter.class)) {
			gameState.getCurrentGamePart().removeGameElement(toRemove);
			gameState.removeFromDisplay(toRemove);
		}
		//System.out.println("RemoveElement event executed");
	}
}