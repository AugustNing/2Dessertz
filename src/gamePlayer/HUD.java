package gamePlayer;

import java.util.Map;

public interface HUD {
	
	/**
	 * called by the gameEngine to update Heads up display information
	 * @param score
	 * @param lives
	 * @param OtherInfo
	 */

	public void updateInfo(Map<String, Object> info);
	
}
