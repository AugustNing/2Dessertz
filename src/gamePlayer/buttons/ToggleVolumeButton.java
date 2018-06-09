package gamePlayer.buttons;

public class ToggleVolumeButton extends PlayerButtons {
	Boolean On;

	public ToggleVolumeButton(double x, double y, double width, double height, ButtonData buttonData) {
		super(x, y, width, height, buttonData, "Turn Volume Off");
		On = buttonData.getVolumeStatus();
	}

	@Override
	protected void setAction() {
		On = buttonData.getVolumeStatus();
		this.setOnAction(event -> {
			On = !On;
			buttonData.toggleVolume();
			String OnorOff = "";
			if (On) {
				OnorOff = "Off";
			} else {
				OnorOff = "On";
			}
			this.setText("Turn Volume " + OnorOff);
		});
	}

}
