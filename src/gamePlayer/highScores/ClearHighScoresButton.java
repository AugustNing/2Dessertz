package gamePlayer.highScores;

import gamePlayer.buttons.ButtonData;
import gamePlayer.buttons.PlayerButtons;

public class ClearHighScoresButton extends PlayerButtons {
	private static final String buttonText = "Clear High Scores";
	
	public ClearHighScoresButton(double x, double y, double width, double height, ButtonData buttonData) {
		super(x, y, width, height, buttonData, buttonText);
	}

	protected void setAction() {
		this.setOnAction(event -> {
			buttonData.clearHighScores();
		});
	}

}
