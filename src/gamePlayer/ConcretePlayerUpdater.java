package gamePlayer;

import java.util.Map;

import gamePlayer.highScores.HighScores;

public class ConcretePlayerUpdater implements PlayerUpdater {

	private HUD hud;
	private HighScores highScores;
	private String userName;

	public ConcretePlayerUpdater(HUD hud, HighScores highScores, String userName) {
		this.hud = hud;
		this.highScores = highScores;
		this.userName = userName;
	}

	@Override
	public void addHighScore(int score) {
		highScores.addScore(userName, score);
	}

	@Override
	public void updateHUD(Map<String, Object> info) {
		hud.updateInfo(info);
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
