package authoring.display.controllers;

import java.util.ArrayList;
import java.util.List;

import authoring.Game;
import data.AudioManager;
import data.GameObjectManager;
import data.ImageManager;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Edward Zhuang
 * Main game holding class in authoring which holds all front end components and controllers
 */
public class AuthoringEnvironment {

	private static final String ENVIRONMENTSIZE_X = "ENVIRONMENTSIZE_X";
	private static final String ENVIRONMENTSIZE_Y = "ENVIRONMENTSIZE_Y";
	private List<Controller> controllerList;
	private Game game;
	private LevelPanelController levelPanelController;
	private ImageManager imageManager;
	private AudioManager audioManager;
	private Pane pane;
	
	public AuthoringEnvironment(Game game) {
		this.game = game;
		imageManager = new ImageManager(game.getName());
		audioManager = new AudioManager(game.getName());
		pane = new Pane();
		pane.setPrefSize(ResourceBundleManager.getPosition(ENVIRONMENTSIZE_X), ResourceBundleManager.getPosition(ENVIRONMENTSIZE_Y));
		pane.setStyle("-fx-border-color: black");
		createControllers();
		setUpControllers();
		addLibrary();
	}
	
	private void createControllers() {
		controllerList = new ArrayList<>();
		levelPanelController = new LevelPanelController(game.getSceneManager(), imageManager, audioManager);
		GameViewWindowController gameViewWindowController = levelPanelController.getGameViewWindowController();
		ObjectInfoController objectInfoController = levelPanelController.getObjectInfoController();
		AudioController audioController = levelPanelController.getAudioController();
		controllerList.add(levelPanelController);
		controllerList.add(gameViewWindowController);
		controllerList.add(objectInfoController);
		controllerList.add(audioController);
	}

	private void setUpControllers() {
		for (Controller controller: controllerList) {
			controller.initializeScreenComponents();
			controller.setUpConnections();
			controller.addToGUI(pane);
		}
		game.getSceneManager().notifyMyObservers();
		game.getSceneManager().getCurrentScene().notifyMyObservers();
	}
	
	private void addLibrary() {
		GameObjectManager gameObjectManager = new GameObjectManager(levelPanelController, imageManager);
		gameObjectManager.addToGUI(pane);
		levelPanelController.getObjectInfoController().getObjectInfoPanelRefactored().setLibraryObservable(gameObjectManager);
		gameObjectManager.setObserver(levelPanelController.getObjectInfoController().getObjectInfoPanelRefactored());
	}

	/**
	 * Gets the pane and passes it to authoring display
	 * @return pane containing all front end components
	 */
	public Pane getGUI() {
		return pane;	
	}
	
}
