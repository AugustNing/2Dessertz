package authoring.displaydeprecated;

import java.util.ResourceBundle;

import authoring.Game;
import authoring.GameScene;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Maddie Wilkinson
 *
 */
public class NewLevelWindowDeprecated extends PopupWindowDeprecated {

	private ComboBox<GameScene> myLevelDropdown;

	public NewLevelWindowDeprecated(ResourceBundle resources, Game game, Node root, ComboBox<GameScene> levelDropdown) {
		super(resources, game, root);
		myLevelDropdown = levelDropdown;
		setStage(setUpScene());
	}

	protected Scene setUpScene() {
		VBox root = new VBox();

		HBox nameLevel = new HBox();
		TextField levelText = new TextField();
		nameLevel.getChildren().addAll(new Label("Level name: "), levelText);

		HBox levelIndex = new HBox();
		TextField indexText = new TextField();
		levelIndex.getChildren().addAll(new Label("Level number: "), indexText);

		Button closeButton = makeButton("Save", event -> {
			saveLevel(levelText, indexText);
		});

		root.getChildren().addAll(nameLevel, levelIndex, closeButton);
		return new Scene(root);
	}

	private void saveLevel(TextField levelText, TextField indexText) {
		if(!levelText.getText().isEmpty() && !indexText.getText().isEmpty()) {
			try {
				String levelName = levelText.getText();
				Integer levelIndex = Integer.parseInt(indexText.getText());
				GameScene newScene = getGame().getSceneManager().makeScene(levelName, levelIndex);
				myLevelDropdown.getItems().add(levelIndex - 1, newScene);
				getStage().close();
				//after slider is implemented, only catch general exception
			} catch(NumberFormatException e) {
				System.out.println("Invalid index input numFormat");
				new Error("Invalid index input"); //this isn't being displayed yet
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Invalid index input OOB");
				//this isn't being displayed yet 
				new Error("Invalid index input"); //eventually to fix this you can get the size of the array and make a slider so they can choose where to put it
			}	
		} else {
			getStage().close();
		}
	}
}
