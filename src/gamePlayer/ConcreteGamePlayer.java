package gamePlayer;

import java.util.ResourceBundle;

import data.GameDescriptionProvider;
import engine.Engine;
import engine.EngineInterface;
import gamePlayer.buttons.ChangeNameButton;
import gamePlayer.buttons.ConcreteButtonData;
import gamePlayer.buttons.LoadButton;
import gamePlayer.buttons.LoadOnlineButton;
import gamePlayer.buttons.SaveButton;
import gamePlayer.buttons.ToggleVolumeButton;
import gamePlayer.highScores.ClearHighScoresButton;
import gamePlayer.highScores.ConcreteHighScores;
import gamePlayer.keyBindings.KeyInputDictionary;
import gamePlayer.keyBindings.KeyboardBindingButton;
import gamePlayer.buttons.NewGameButton;
import gamePlayer.buttons.PauseButton;
import gamePlayer.buttons.ReplayButton;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * 
 * @author jeffreyli, calvinma Concrete game player, manages game selection,
 *         loading, saving, HUD and high scores.
 */
public class ConcreteGamePlayer implements GamePlayer {

	private Scene myScene;
	private Stage myStage;
	private Group root;
	private SubScene gameDisplay;

	private Button saveButton;
	private Button loadButton;
	private Button replayButton;
	private Button newGameButton;
	private Button clearHighScoresButton;
	private Button keyboardBindingButton;
	private Button toggleVolumeButton;
	private Button pauseButton;
	private Button loadOnlineButton;
	private Button changeNameButton;
	private ConcreteButtonData buttonData;

	private HUD hud;
	private ConcreteHighScores highScores;
	private VolumeSlider volumeSlider;

	private EngineInterface engine;
	private GameDescriptionProvider gameDescriptionProvider;
	private KeyInputDictionary keyInputDictionary;

	private Username username;

	private ResourceBundle resources;

	private static final double SCREEN_HEIGHT = 650;
	private static final double SCREEN_WIDTH = 1250;
	private static final Paint BACKGROUND = Color.GRAY;
	private static final int BUTTONXLOCATION = 970;
	private static final int BUTTONWIDTH = 235;
	private static final int BUTTONHEIGHT = 20;

	public ConcreteGamePlayer(Stage stage) {

		resources = ResourceBundle.getBundle("gamePlayer.resources/ConcreteGP");
		gameDescriptionProvider = new GameDescriptionProvider();

		root = new Group();
		myScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		myStage = stage;
		myScene.getStylesheets().add(getClass().getResource("styleSelector.css").toExternalForm());

		myStage.setScene(myScene);

		highScores = new ConcreteHighScores();

		keyInputDictionary = new KeyInputDictionary(engine);
		myScene.setOnKeyPressed(keyPress -> keyInputDictionary.handleAction(keyPress.getCode()));

		volumeSlider = new VolumeSlider(engine);
		username = new Username();
		buttonData = new ConcreteButtonData(stage, this, volumeSlider, root, keyInputDictionary, username);

		ImageView backgroundImage = new ImageView(new Image("file:background2.png"));
		backgroundImage.setFitWidth(1250);
		backgroundImage.setFitHeight(650);
		
		root.getChildren().add(backgroundImage);
		
		initialiseGUIElements();

		addGuiElementsToRoot();
	}

	/**
	 * initialises buttons on screen
	 */
	private void initialiseGUIElements() {

		clearHighScoresButton = new ClearHighScoresButton(BUTTONXLOCATION,
				Integer.parseInt(resources.getString("clearHighScoresButtonY")), BUTTONWIDTH, BUTTONHEIGHT, buttonData);

		newGameButton = new NewGameButton(BUTTONXLOCATION, Integer.parseInt(resources.getString("newGameButtonY")),
				Integer.parseInt(resources.getString("smallButtonWidth")), BUTTONHEIGHT, buttonData);

		loadButton = new LoadButton(1095, Integer.parseInt(resources.getString("loadButtonY")),
				Integer.parseInt(resources.getString("smallButtonWidth")), BUTTONHEIGHT, buttonData);

		toggleVolumeButton = new ToggleVolumeButton(BUTTONXLOCATION,
				Integer.parseInt(resources.getString("toggleVolumeButtonY")), BUTTONWIDTH, BUTTONHEIGHT, buttonData);

		saveButton = new SaveButton(BUTTONXLOCATION, Integer.parseInt(resources.getString("saveButtonY")),
				Integer.parseInt(resources.getString("smallButtonWidth")), BUTTONHEIGHT, buttonData);

		replayButton = new ReplayButton(1095, Integer.parseInt(resources.getString("replayButtonY")),
				Integer.parseInt(resources.getString("smallButtonWidth")), BUTTONHEIGHT, buttonData);

		keyboardBindingButton = new KeyboardBindingButton(BUTTONXLOCATION,
				Integer.parseInt(resources.getString("keybordBindingButtonY")), BUTTONWIDTH, BUTTONHEIGHT, buttonData);

		pauseButton = new PauseButton(BUTTONXLOCATION, Integer.parseInt(resources.getString("pauseButtonY")),
				BUTTONWIDTH, BUTTONHEIGHT, buttonData);

		loadOnlineButton = new LoadOnlineButton(BUTTONXLOCATION,
				Integer.parseInt(resources.getString("loadOnlineButtonY")), BUTTONWIDTH, BUTTONHEIGHT, buttonData);
		changeNameButton = new ChangeNameButton(Integer.parseInt(resources.getString("changeButtonX")),
				Integer.parseInt(resources.getString("changeButtonY")),
				Integer.parseInt(resources.getString("changeNameWidth")), BUTTONHEIGHT, buttonData);

	}

	private void addGuiElementsToRoot() {
		root.getChildren().add(clearHighScoresButton);
		root.getChildren().add(newGameButton);
		root.getChildren().add(loadButton);
		root.getChildren().add(toggleVolumeButton);
		root.getChildren().add(saveButton);
		root.getChildren().add(replayButton);
		root.getChildren().add(keyboardBindingButton);
		root.getChildren().add(loadOnlineButton);
		root.getChildren().add(pauseButton);
		root.getChildren().add(changeNameButton);
		root.getChildren().add(volumeSlider.getVolumeText());
		root.getChildren().add(volumeSlider.getSlider());
		root.getChildren().add(username.getNameText());
		root.getChildren().add(highScores.getScores());
	}

	@Override
	public void playGame(String gameName, boolean isNewGame) {
		cleanOldEngineGuiElements();
		if (engine != null) {
			engine.close();
		}
		hud = new ConcreteHUD(gameDescriptionProvider.getGameName(gameName));
		highScores = new ConcreteHighScores(gameName);
		buttonData.setHighScores(highScores);
		PlayerUpdater concretePlayerUpdater = new ConcretePlayerUpdater(hud, highScores, username.getName());

		engine = new Engine(gameName, isNewGame, concretePlayerUpdater);
		buttonData.setEngineRunning(true);
		updateEngines(engine);

		pauseButton = new PauseButton(BUTTONXLOCATION, Integer.parseInt(resources.getString("pauseButtonY")),
				BUTTONWIDTH, BUTTONHEIGHT, buttonData);
		setUpEngineGameDisplay();

		buttonData.setMostRecentFile(gameName);
		addEngineGUIToRoot();
	}

	/**
	 * removes all the old engine elements from the root, cleaning it before a new
	 * engine is made.
	 */
	private void cleanOldEngineGuiElements() {
		root.getChildren().remove(gameDisplay);
		root.getChildren().remove((Node) hud);
		root.getChildren().remove(highScores.getScores());
	}

	/**
	 * updates all the engine of all the different components that use engine.
	 * 
	 * @param newEngine
	 */
	private void updateEngines(EngineInterface newEngine) {
		buttonData.addEngine(newEngine);
		keyInputDictionary.setGame(newEngine);
		volumeSlider.setEngine(newEngine);
		keyInputDictionary.setGame(newEngine);
	}

	private void setUpEngineGameDisplay() {
		gameDisplay = engine.getDisplay();
		gameDisplay.setWidth(Integer.parseInt(resources.getString("gameDisplayWidth")));
		gameDisplay.setHeight(Integer.parseInt(resources.getString("gameDisplayHeight")));
		gameDisplay.setLayoutX(Integer.parseInt(resources.getString("gameDisplayX")));
		gameDisplay.setLayoutY(Integer.parseInt(resources.getString("gameDisplayY")));
	}

	private void addEngineGUIToRoot() {
		root.getChildren().add(gameDisplay);
		root.getChildren().add((Node) hud);
		root.getChildren().add(highScores.getScores());
		root.getChildren().remove(pauseButton);
		root.getChildren().add(pauseButton);
	}

	@Override
	public Scene getScene() {
		return myScene;
	}

	public void closeEngine() {
		if (engine == null) {
			myStage.close();
		} else {
			engine.close();
		}
	}
}
