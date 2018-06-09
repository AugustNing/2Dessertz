package gamePlayer.buttons;

import java.util.Map;

import gamePlayer.keyBindings.KeyInputDictionary;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public interface ButtonData {
	public void playGame(String file, boolean isNewGame);

	public Stage getStage();

	public String getMostRecentFile();

	public void clearHighScores();

	public void addToRoot(Node node);

	public KeyInputDictionary getKeyBindings();

	public void toggleVolume();

	public void removeFromRoot(Node node);

	public void resumeGame();

	public void pauseGame();

	public Boolean getVolumeStatus();

	public void changeUsername(String newName);

	public void saveGame();

	public Map<KeyCode, String> getKeyAssignments();

	public void setMostRecentFile(String file);

	public boolean engineRunning();

	public void setEngineRunning(boolean running);
}
