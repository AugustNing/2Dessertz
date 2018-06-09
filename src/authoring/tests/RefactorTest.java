package authoring.tests;

import authoring.display.AuthoringDisplay;
import javafx.application.Application;
import javafx.stage.Stage;

public class RefactorTest extends Application {
	
	AuthoringDisplay authoringDisplayRefactored;
	
	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		authoringDisplayRefactored = new AuthoringDisplay(primaryStage);
		primaryStage.show();
	}
}
