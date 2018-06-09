package display.buttonevents;

import gamePlayer.ConcreteGamePlayer;
import javafx.stage.Stage;

/**
 * @author August Ning
 * This class is used to start up the game player when a button is pressed.
 * It will close the current stage from where the button was called from, and then open up a new stage
 * that will run the game player
 */
public class GamePlayerPress implements ButtonEvent {

	private Stage gamePlayerStage;

	public GamePlayerPress(Stage stage) {
		gamePlayerStage = new Stage();
	}

	/* (non-Javadoc)
	 * @see display.buttonevents.ButtonEvent#pressed()
	 */
	@Override
	public void pressed() {
		
	ConcreteGamePlayer gamePlayer = new ConcreteGamePlayer(gamePlayerStage);
	gamePlayerStage.show();
	gamePlayerStage.setResizable(false);
	
	gamePlayerStage.setOnCloseRequest(event -> {
		gamePlayer.closeEngine();
	    // Save file
	});

	}

}
