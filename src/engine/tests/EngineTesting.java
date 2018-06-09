package engine.tests;


import engine.Engine;
import gamePlayer.ConcreteHUD;
import gamePlayer.ConcretePlayerUpdater;
import gamePlayer.HUD;
import gamePlayer.PlayerUpdater;
import gamePlayer.highScores.ConcreteHighScores;
import gamePlayer.highScores.HighScores;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * @author Yashas Manjunatha
 * Class to launch an application to test all parts of the Engine including display, 
 * audio, interactions, event raising and handling, collisions, etc.
 *
 */
public class EngineTesting extends Application {
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final Paint BACKGROUND = Color.WHITE;
	
	private Engine gameEngine;

	/**
	 * Starts the Engine Testing Application
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	} 

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		HUD hud = new ConcreteHUD("enginetestmario");
		HighScores highScores = new ConcreteHighScores("enginetestmario");
		PlayerUpdater concretePlayerUpdater = new ConcretePlayerUpdater(hud, highScores, "hello");
		gameEngine = new Engine("enginetestmario", true, concretePlayerUpdater);
		stage.setScene(setupLevel(900, 590, BACKGROUND));
		stage.show();
	}
	
	/**
	 * Sets up the JavaFX Scene
	 * @param width Width of the Scene
	 * @param height Height of the Scene
	 * @param background Background color of the Scene
	 * @return The Scene after Set Up
	 */
	private Scene setupLevel (int width, int height, Paint background) {
		Group root = new Group();
		Scene scene = new Scene(root, width, height, background);
		SubScene gameSubScene = gameEngine.getDisplay();
		root.getChildren().add(gameSubScene);
		scene.setOnKeyPressed(e -> gameEngine.handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> gameEngine.handleMouseInput(e.getX(), e.getY())); 
    	return scene;
    }
}
