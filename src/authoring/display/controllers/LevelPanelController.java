package authoring.display.controllers;

import authoring.GameObject;
import authoring.GameObjectAdder;
import authoring.GameScene;
import authoring.SceneManager;
import authoring.display.authoringuicomponents.LevelPanel;
import data.AudioManager;
import data.ImageManager;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Edward Zhuang
 * Controller class for LevelPanel. Allows LevelPanel to connect with the SceneManager.
 */
public class LevelPanelController extends Controller implements GameObjectAdder {

	private SceneManager sceneManager;
	private LevelPanel levelPanelRefactored;
	private ObjectInfoController objectInfoController;
	private GameViewWindowController gameViewWindowController;
	private AudioController audioController;

	public LevelPanelController(SceneManager sceneManager, ImageManager imageManager, AudioManager audioManager) {
		super(imageManager);
		this.sceneManager = sceneManager;
		objectInfoController = new ObjectInfoController(this.sceneManager.getCurrentScene(),getImageManager());
		gameViewWindowController = new GameViewWindowController(this.sceneManager.getCurrentScene(),getImageManager());
		audioController = new AudioController(this.sceneManager.getCurrentScene(), getImageManager(), audioManager);
	}
	
	public ObjectInfoController getObjectInfoController() {
		return objectInfoController;
	}
	
	public GameViewWindowController getGameViewWindowController() {
		return gameViewWindowController;
	}
	
	public AudioController getAudioController() {
		return audioController;
	}
	
	@Override
	protected void initializeScreenComponents() {
		levelPanelRefactored = new LevelPanel(this);
	}

	@Override
	protected void setUpConnections() {
		sceneManager.addObserver(levelPanelRefactored);
	}

	@Override
	protected void addToGUI(Pane pane) {
		int x = ResourceBundleManager.getPosition("LEVELPANEL_X");
		int y = ResourceBundleManager.getPosition("LEVELPANEL_Y");
		levelPanelRefactored.attachToPane(pane, x, y);	
	}
	
	@Override
	protected void refreshView() {
	}
	
	public void setCurrentSceneName(String name) {
		sceneManager.setCurrentSceneName(name);
	}
	
	public void setCurrentLevelId(String id) {
		sceneManager.setCurrentLevelId(id);
	}
	
	public void addLevel(String name, String id) {
		GameScene scene = sceneManager.makeScene(name, id);
		setLevel(scene.getName());
	}
	
	public void setLevel(String name) {
		sceneManager.setCurrentScene(name);
		updateMyControllers(sceneManager.getCurrentScene());
	}
	
	private void updateMyControllers(GameScene gameScene) {
		objectInfoController.setGameScene(gameScene);
		gameViewWindowController.setGameScene(gameScene);
		audioController.setGameScene(gameScene);
	}

	@Override
	public void addToCurrentScene(GameObject gameObject) {
		sceneManager.getCurrentScene().addObject(gameObject);
	}
}
