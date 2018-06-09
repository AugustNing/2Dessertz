package display.buttons;

import display.buttonevents.ButtonEvent;
import javafx.scene.control.Button;

/**
 * @author August Ning
 * A GUI button that will be used in the splash screen and can be used in other GUI packages
 */
public class GUIButton extends Button {

	private String buttonLabel;
	private ButtonEvent eventResponse;
	private final int X_LOC;
	private final int Y_LOC;
	private static final int X_SIZE = 150;
	private static final int Y_SIZE = 40;
	
	/**
	 * @param xloc 		X location of the button on a stage
	 * @param yloc 		Y location of the button on a stage
	 * @param label 	String label of the button
	 * @param response 	ButtonEvent class that is assigned to the button's setOnAction method
	 */
	public GUIButton(int xloc, int yloc, String label, ButtonEvent response) {
		X_LOC = xloc;
		Y_LOC = yloc;
		buttonLabel = label;
		eventResponse = response;
		this.setText(buttonLabel);
		this.setLayoutX(X_LOC);
		this.setLayoutY(Y_LOC);
		this.setPrefSize(X_SIZE, Y_SIZE);
		this.setOnAction(e -> eventResponse.pressed());
	}
	
	/**
	 * Change the label of the button
	 * @param newLabel the new name
	 */
	public void setLabel(String newLabel) {
		buttonLabel = newLabel;
		this.setText(buttonLabel);
	}
	
	/**
	 * Change the response of the setOnAction of the button
	 * @param newEvent the new ButtonEvent that is desired to be called by the button
	 */
	public void setButtonEvent(ButtonEvent newEvent) {
		eventResponse = newEvent;
		this.setOnAction(e -> eventResponse.pressed());
	}
}
