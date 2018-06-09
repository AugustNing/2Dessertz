package gamePlayer;

import java.util.Map;
/**
 * interface containing the Player's external API for the engine
 * @author calvinma
 *
 */
public interface PlayerUpdater {
	
	public void addHighScore(int score);
	
	public void updateHUD(Map<String, Object> info);	
}
