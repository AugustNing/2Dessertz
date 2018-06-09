package engine.events.gameevents;

import engine.GameState;

public class ResetLevelEvent extends GameEvent{
	private String levelToResetID;
	public ResetLevelEvent(String levelToResetID) {
		this.levelToResetID = levelToResetID;
	}
	
	@Override
	public void execute(GameState gameState) {
		gameState.resetLevel(levelToResetID);
	}
}
