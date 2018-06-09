package display.buttonevents;

// turn this into a functional interface
/**
 * @author August Ning
 * Interface that is used for Buttons when assigning setOnAction event handlers
 */
@FunctionalInterface
public interface ButtonEvent {
	/**
	 * When a button is pressed, execute the desired event response by the button
	 */
	public void pressed();
}
