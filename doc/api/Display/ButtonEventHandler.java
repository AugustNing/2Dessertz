/**
 * interface that GUI buttons will use these classes to implement the setOnAction of the GUI Button.
 * Will only have one method so it can be used by lambdas by the GUI buttons
 */
public interface ButtonEventHandler {
	
	/**
	 * implement the desired action when the button using this event handler is pressed.
	 */
	public void execute();
	
}
