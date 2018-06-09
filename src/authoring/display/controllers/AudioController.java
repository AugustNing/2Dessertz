package authoring.display.controllers;

import java.io.File;

import authoring.GameScene;
import authoring.display.authoringuicomponents.AudioPanel;
import data.AudioManager;
import data.ImageManager;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.layout.Pane;

/**
 * @author Edward Zhuang
 * Controller class for AudioPanel. Allows Audio Panel to access audioManager to store mp3 files.
 */
public class AudioController extends Controller {

	private static final String AUDIOPANEL_X = "AUDIOPANEL_X";
	private static final String AUDIOPANEL_Y = "AUDIOPANEL_Y";
	private GameScene gameScene;
	private AudioPanel audioPanel;
	private AudioManager audioManager;

	public AudioController(GameScene gameScene, ImageManager imageManager, AudioManager audioManager) {
		super(imageManager);
		this.gameScene = gameScene;
		this.audioManager = audioManager;
		initializeScreenComponents();
	}

	@Override
	protected void initializeScreenComponents() {
		audioPanel = new AudioPanel(this);
	}

	@Override
	protected void setUpConnections() {
		gameScene.addObserver(audioPanel);
	}

	@Override
	protected void addToGUI(Pane pane) {
		int x = ResourceBundleManager.getPosition(AUDIOPANEL_X);
		int y = ResourceBundleManager.getPosition(AUDIOPANEL_Y);
		audioPanel.attachToPane(pane, x, y);
	}

	@Override
	protected void refreshView() {
	}

	/**
	 * sets up observer/observable interactions between new GameScene. Used when the current GameScene switches.
	 * @param gameScene new gameScene
	 */
	public void setGameScene(GameScene gameScene) {
		this.gameScene = gameScene;
		setUpConnections();
		gameScene.notifyMyObservers();
	}

	/**
	 * stores audio chosen by audio panel into game through audio manager. Sets gamescenes audio path.
	 * @param audio audio file
	 */
	public void setAudio(File audio) {
		audioManager.storeAudio(audio);
		gameScene.setAudioName(audio.getName());
	}

}
