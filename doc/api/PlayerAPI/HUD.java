package voogasalad_2dessertz;

import java.util.Map;

public interface HUD {
	
	/**
	 * called by the gameEngine to update Heads up display information
	 * @param score
	 * @param lives
	 * @param OtherInfo
	 */
	public void updateInfo(int score, int lives, Map<?, ?> OtherInfo);
	
}
