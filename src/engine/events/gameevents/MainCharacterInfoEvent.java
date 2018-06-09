package engine.events.gameevents;

import java.util.Map;

import engine.GameState;

public class MainCharacterInfoEvent extends GameEvent {
	private Map<String, Object> properties;
	
	public MainCharacterInfoEvent(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public void execute(GameState gameState) {
		// gamePart.getMetaData().updateMainCharacterInfo(properties);
		
	}

}
