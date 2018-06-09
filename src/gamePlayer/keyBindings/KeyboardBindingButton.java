package gamePlayer.keyBindings;

import gamePlayer.buttons.ButtonData;
import gamePlayer.buttons.PlayerButtons;

/**
 * the button to press if you want to launch key bindings editing
 * 
 * @author jeffreyli
 *
 */
public class KeyboardBindingButton extends PlayerButtons {
	private static final String buttonText = "Change Key Bindings";

	public KeyboardBindingButton(double x, double y, double width, double height, ButtonData buttonData) {
		super(x, y, width, height, buttonData, buttonText);
	}

	@Override
	protected void setAction() {
		this.setOnAction(event -> {
			if (buttonData.engineRunning()) {
				buttonData.pauseGame();
				KeyBindingPopup keyPopup = new KeyBindingPopup(buttonData);
				keyPopup.show();
			}
		});
	}

}
