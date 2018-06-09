/**
 * interface that GUI buttons will implement, will take in a ButtonEventHandler class that will be used to execute the event handler
 *
 */
public interface GUIButton {
	
	/**
	 * Assign the ButtonEventHandler class to the setOnAction method of the button.
	 */
	public void assignAction();
	
}
