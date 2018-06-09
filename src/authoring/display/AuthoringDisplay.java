package authoring.display;

import java.io.File;
import java.io.IOException;


import authoring.Game;
import authoring.display.controllers.AuthoringEnvironment;
import authoring.display.popups.NewGamePopup;
import data.GameLoader;
import data.GameSaver;
import data.propertiesFiles.ResourceBundleManager;
import display.AnimatedButton;
import engine.exceptions.ErrorBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import voogadropbox.VoogaDropbox;

/**
 * @author Edward Zhuang
 * Main authoring class. Contains buttons which allow user to save, create, and load games.
 * Contains the pane on which all front end authoring components are ultimate attached to.
 */
public class AuthoringDisplay implements LoadAuthoringInterface {

	private static final int GUI_LAYOUT_X = 20;
	private static final int GUI_LAYOUT_Y = 40;
	private static final String SAVE_GAME = "Save Game";
	private static final String LOAD_GAME = "Load Game";
	private static final String NEW_GAME = "New Game";
	private static final String NAME = "Authoring Environment";
	private static final String baseLocation = "./data/gamedata/games/";
	
	private static final int WIDTH = 1500;
	private static final int HEIGHT = 1000;
	private static final String RECENT_GAMES = "RecentGames";
	private static final String NEW_GAME_BUTTON = "NewGame";
	private static final String SAVE_GAME_BUTTON = "SaveGame";
	private static final String LOAD_GAME_BUTTON = "LoadGame";
	private static final String SAVE_ONLINE_BUTTON = "SaveOnline";
	private static final int BUTTON_BOX_SPACING = 30;
	private Pane root;
	private HBox buttonBox;
	private Button newGameButton;
	private Button loadGameButton;
	private Button saveGameButton;
	private Button saveOnlineButton;
	private ComboBox<String> gameNames;
	private Game currentGame;
	private AuthoringEnvironment authoringEnvironmentRefactored;
	
	public AuthoringDisplay(Stage stage) {
		initialize(stage);
		initializeButtonBox();
	}

	private void initialize(Stage stage) {
		root = new Pane();
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		stage.setScene(scene);
		stage.setTitle(NAME);
		stage.show();
	}
	
	private void initializeButtonBox() {
		buttonBox = new HBox();
		buttonBox.setSpacing(BUTTON_BOX_SPACING);
		gameNames = new ComboBox<>();
		AnimatedButton newGame = new AnimatedButton(ResourceBundleManager.getButton(NEW_GAME_BUTTON), NEW_GAME);
		AnimatedButton loadGame = new AnimatedButton(ResourceBundleManager.getButton(SAVE_GAME_BUTTON), LOAD_GAME);
		AnimatedButton saveGame = new AnimatedButton(ResourceBundleManager.getButton(LOAD_GAME_BUTTON), SAVE_GAME);
		saveOnlineButton = new Button(ResourceBundleManager.getAuthoring(SAVE_ONLINE_BUTTON));
		
		newGameButton = newGame.getButton();
		loadGameButton = loadGame.getButton();
		saveGameButton = saveGame.getButton();
		buttonBox.getChildren().addAll(newGame.getHBox(),saveGame.getHBox(),loadGame.getHBox(),gameNames,saveOnlineButton);
		setButtonActions();
		initializeComboBoxes();
		root.getChildren().add(buttonBox);
	}

	private void initializeComboBoxes() {
		gameNames.getItems().clear();
		gameNames.setPromptText(ResourceBundleManager.getAuthoring(RECENT_GAMES));
		File directory = new File(baseLocation);
		File[] directoryListing = directory.listFiles();
	        
	        if (directoryListing != null){
	            for (File f : directoryListing){
	                String path = f.getName();
	                gameNames.getItems().add(path);
	            }
	        }
		
	}
	
	private void setButtonActions() {
		newGameButton.setOnAction(e -> {
			new NewGamePopup(this);
		});
		saveGameButton.setOnAction(e -> {
			try {
				GameSaver saver = new GameSaver(currentGame.getName());
				saver.gameAuthorToXML(currentGame.getScenes(), true);
				saver.addDescription(currentGame.getName(), currentGame.getGameDescription());
			} catch (Exception e1) {
				new ErrorBox("No Game Selected", "Please select/load a game before saving");
			}
			
			initializeComboBoxes();
		});
		saveOnlineButton.setOnAction(e->{
			try {
				GameSaver saver = new GameSaver(currentGame.getName());
				saver.gameAuthorToXML(currentGame.getScenes(), true);
				saver.addDescription(currentGame.getName(), currentGame.getGameDescription());
			} catch (Exception e1) {
				new ErrorBox("No Game Selected", "Please select/load a game before saving");
			}
			
			initializeComboBoxes();
			VoogaDropbox voogaDropbox = new VoogaDropbox(ResourceBundleManager.getPath("BASELOCATION"));
			try {
				voogaDropbox.uploadGame(currentGame.getName());
			} catch (Exception e1) {
				new ErrorBox("Could Not Upload Game", "Please try again");
			}
		});
		
		loadGameButton.setOnAction(e -> {
			String gameName = gameNames.getSelectionModel().getSelectedItem();
			GameLoader gameLoader = new GameLoader(gameNames.getSelectionModel().getSelectedItem());
			currentGame = new Game(gameName);
			root.getChildren().clear();
			root.getChildren().add(buttonBox);
			currentGame.restoreGame(gameLoader.getGameScenes(true));
			authoringEnvironmentRefactored = new AuthoringEnvironment(currentGame);
			Pane GUIPane = authoringEnvironmentRefactored.getGUI();
			GUIPane.setLayoutX(GUI_LAYOUT_X);
			GUIPane.setLayoutY(GUI_LAYOUT_Y);
			root.getChildren().add(GUIPane);
			
		});
		
	}

	/**
	 * Initializes the AuthoringEnvironment, which can be done from a new or saved game
	 * @param game game to be edited in AuthoringEnvironment.
	 */
	public void loadAuthoringEnvironment(Game game) {
		root.getChildren().clear();
		root.getChildren().add(buttonBox);
		currentGame = game;
		authoringEnvironmentRefactored = new AuthoringEnvironment(game);
		Pane GUIPane = authoringEnvironmentRefactored.getGUI();
		GUIPane.setLayoutX(GUI_LAYOUT_X);
		GUIPane.setLayoutY(GUI_LAYOUT_Y);
		root.getChildren().add(GUIPane);
	}

}
