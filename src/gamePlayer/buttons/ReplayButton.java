package gamePlayer.buttons;

/**
 * a button for restarting the current game
 * 
 * @author jeffreyli
 *
 */
public class ReplayButton extends PlayerButtons {
	private static final String buttonString = "Restart Game";

	public ReplayButton(double x, double y, double width, double height, ButtonData buttonData) {
		super(x, y, width, height, buttonData, buttonString);
	}

	protected void setAction() {

		this.setOnAction(event -> {
			if (buttonData.engineRunning()) {
				buttonData.playGame(buttonData.getMostRecentFile(), true);
			}
		});
	}
}
