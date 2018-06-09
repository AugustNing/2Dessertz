package authoring;

import authoring.display.AuthoringDisplay;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Maddie Wilkinson
 *
 */
public class RunAuthoring extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// eventually this primaryStage will pull up a little window where the user decides whether they want to make a new game
		// or load an old game, then that window will bring up the AuthoringDisplay depending on the user choice
		// for now we'll just give it a new game cause we don't know how to load games haha
		new AuthoringDisplayDeprecated(primaryStage, new Game());
	}
	
	public static void main(String[] args){
		launch();
	}

}
