package gamePlayer.buttons;

/**
 * a button for saving the game
 * 
 * @author jeffreyli
 *
 */
public class SaveButton extends PlayerButtons {
	private static final String buttonString = "Save Current State";

	public SaveButton(double x, double y, double width, double height, ButtonData buttonData) {
		super(x, y, width, height, buttonData, buttonString);
	}

	protected void setAction() {
		this.setOnAction(event -> {
			if (buttonData.engineRunning()) {
				buttonData.saveGame();
			}
		});
	}

}
