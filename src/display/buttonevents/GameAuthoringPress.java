package display.buttonevents;

import authoring.display.AuthoringDisplay;
import javafx.stage.Stage;

/**
 * @author August Ning
 * Button event handler that closes the stage it came from, and then opens up the game authoring stage
 *
 */
public class GameAuthoringPress implements ButtonEvent {
	
	public GameAuthoringPress() {

	}

	/* (non-Javadoc)
	 * @see display.buttonevents.ButtonEvent#pressed()
	 */
	@Override
	public void pressed() {	
		new AuthoringDisplay(new Stage());
	}

}
