package gamePlayer.buttons;

import java.util.Map;

import engine.EngineInterface;
import gamePlayer.GamePlayer;
import gamePlayer.Username;
import gamePlayer.VolumeSlider;
import gamePlayer.highScores.HighScores;
import gamePlayer.keyBindings.KeyInputDictionary;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConcreteButtonData implements ButtonData {
	private Stage stage;
	private GamePlayer gamePlayer;
	private Group root;

	private HighScores highScores;
	private String mostRecentFile;
	private KeyInputDictionary keyBindingMap;
	private EngineInterface engine;
	private VolumeSlider volumeSlider;
	private Username username;

	private Text title;
	private Rectangle background;
	private boolean engineRunning;

	public ConcreteButtonData(Stage stage, GamePlayer gamePlayer, VolumeSlider volumeSlider, Group root,
			KeyInputDictionary keyInputDictionary, Username username) {
		this.stage = stage;
		this.gamePlayer = gamePlayer;
		this.root = root;
		this.volumeSlider = volumeSlider;
		this.username = username;
		keyBindingMap = keyInputDictionary;
		engineRunning = false;
	}

	@Override
	public void playGame(String file, boolean isNewGame) {
		gamePlayer.playGame(file, isNewGame);
	}

	public void setHighScores(HighScores highScores) {
		this.highScores = highScores;
	}

	@Override
	public void clearHighScores() {
		highScores.clear();
	}

	@Override
	public void setMostRecentFile(String file) {
		mostRecentFile = file;
	}

	@Override
	public Stage getStage() {
		return stage;
	}

	@Override
	public String getMostRecentFile() {
		return mostRecentFile;
	}

	@Override
	public void addToRoot(Node node) {
		root.getChildren().add(node);
	}

	@Override
	public KeyInputDictionary getKeyBindings() {
		return keyBindingMap;
	}

	@Override
	public void removeFromRoot(Node node) {
		root.getChildren().remove(node);

	}

	@Override
	public void toggleVolume() {
		volumeSlider.toggleMusic();
	}

	@Override
	public Boolean getVolumeStatus() {
		return volumeSlider.getMusicOn();
	}

	public void addEngine(EngineInterface engine) {
		this.engine = engine;
	}

	@Override
	public void resumeGame() {
		if (engine != null) {
			engine.play();
			root.getChildren().remove(title);
			root.getChildren().remove(background);
		}
	}

	@Override
	public void pauseGame() {
		if (engine != null) {
			engine.pause();
			title = new Text();
			title.setText("Paused");
			title.setStyle("-fx-font: 24 Verdana;");
			title.setFill(Color.LIGHTSKYBLUE);
			title.setX(450);
			title.setY(325);

			background = new Rectangle(440, 300, 105, 40);
			background.setFill(Color.BLACK);
			background.setOpacity(0.3);
			root.getChildren().add(background);
			root.getChildren().add(title);
		}
	}

	@Override
	public void changeUsername(String newName) {
		username.changeName(newName);
	}

	@Override
	public void saveGame() {
		engine.save();
	}

	@Override
	public Map<KeyCode, String> getKeyAssignments() {
		return engine.getKeyAssignments();
	}

	@Override
	public boolean engineRunning() {
		return engineRunning;
	}

	@Override
	public void setEngineRunning(boolean running) {
		engineRunning = running;
	}

}
