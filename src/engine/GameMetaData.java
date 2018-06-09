package engine;

import java.util.ArrayList;
import java.util.List;

import authoring.GameScene;
import engine.authouringconversion.Converter2;
import engine.behaviors.MainCharacter;

/**
 * @author Yashas Manjunatha
 * Unused class in new design of Engine.
 * Before was used as a communication method with the Player. The GameMetaData object was intended to
 * allow the Player to query certain information from the current state of the game/Engine.
 */
public class GameMetaData implements GameMetaDataInterface {
	private GameState gameState;

	/**
	 * Instantiated a new GameMetaData object by storing a reference to the current state
	 * of the game. This state is then queried to return information to the Player in the implemented
	 * methods.
	 * @param currentGameState Reference to the current GameState.
	 */
	public GameMetaData(GameState currentGameState) {
		gameState = currentGameState;
	}

	/* (non-Javadoc)
	 * @see engine.GameMetaDataInterface#getCurrentLevelID()
	 */
	@Override
	public String getCurrentLevelID() {
		return gameState.getCurrentGameLevel().getGameLevelID();
	}

	/* (non-Javadoc)
	 * @see engine.GameMetaDataInterface#getMainCharacterLives()
	 */
	@Override
	public int getMainCharacterLives() {
		return ((MainCharacter) gameState.getCurrentGamePart().getMainCharacter().getBehavior(MainCharacter.class)).getLives();
	}

	/* (non-Javadoc)
	 * @see engine.GameMetaDataInterface#getGameScenes()
	 */
	@Override
	public List<GameScene> getGameScenes() {
		Converter2 converter = new Converter2();
		List<GameScene> toReturn = new ArrayList<GameScene>();
		for (GamePart part : gameState.getAllGameParts()) {
			toReturn.add(converter.gamePart2GameScene(part));
		}
		return toReturn;
	}
}
