package engine.events.gameevents;

import engine.GameState;

/**
 * @author Yashas Manjunatha
 * Raises a GameEvent to Play Audio.
 *
 */
public class PlayAudioEvent extends GameEvent {
	private String musicPath;
	
	/**
	 * Creates a GameEvent to play a specific audio file.
	 * @param audioPath File Path to the Media/Audio File
	 */
	public PlayAudioEvent(String audioPath) {
		musicPath = audioPath;
	}

	/* (non-Javadoc)
	 * @see engine.events.gameevents.GameEvent#execute(engine.GameState)
	 */
	@Override
	public void execute(GameState gameState) {
		gameState.getAudioManager().newAudioPlayer(musicPath);
	}

}
