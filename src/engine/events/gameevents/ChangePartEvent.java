package engine.events.gameevents;

import engine.GameState;

public class ChangePartEvent extends GameEvent{

	private String partID;
	private Integer portalID;

	public ChangePartEvent(String partID, Integer portalID) {
		this.partID = partID;
		this.portalID = portalID;
	}

	@Override
	public void execute(GameState gameState) {
		gameState.changeCurrentGamePart(partID, portalID);
	}
}
