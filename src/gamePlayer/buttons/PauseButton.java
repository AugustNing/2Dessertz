package gamePlayer.buttons;

import gamePlayer.buttons.ButtonData;
import gamePlayer.buttons.PlayerButtons;

/**
 * a button for restarting the current game
 * 
 * @author jeffreyli
 *
 */
public class PauseButton extends PlayerButtons {

	Boolean paused;
	private static final String PAUSE_GAME = "Pause Game";
	private static final String RESUME_GAME = "Resume Game";

	public PauseButton(double x, double y, double width, double height, ButtonData buttonData) {
		super(x, y, width, height, buttonData, PAUSE_GAME);
		paused = false;
	}

	protected void setAction() {

		this.setOnAction(event -> {
			if (buttonData.engineRunning()) {
				if (paused) {
					buttonData.resumeGame();
					this.setText(PAUSE_GAME);
				}

				else if (!paused) {
					buttonData.pauseGame();
					this.setText(RESUME_GAME);
				}
				paused = !paused;
			}
		});
	}

}
