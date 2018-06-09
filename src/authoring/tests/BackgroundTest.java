package authoring.tests;

import java.util.HashSet;

import authoring.AuthBehavior;
import authoring.GameObject;
import authoring.Property;
import authoring.SceneBackground;
import data.ImageManager;
import display.SplashScreen;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

public class BackgroundTest extends Application {

	private SceneBackground background;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		background = new SceneBackground(500,500);
		Scene scene = new Scene(background.getPane(), 500, 500);
		ImageManager im = new ImageManager("default");
//		background.addImage(im.getImage("flappybird.png"));
		stage.setTitle("Background Test");
		stage.setScene(scene);

		stage.show();
	}
	
}
