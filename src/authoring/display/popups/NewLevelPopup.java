package authoring.display.popups;

import authoring.display.controllers.LevelPanelController;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Edward Zhuang Popup which allows the user to create a new level.
 */
public class NewLevelPopup extends Popup {

	private static final int xSize = 300;
	private static final int ySize = 400;
	private static final String SCENE_ID = "SceneId";
	private static final String LEVEL_ID = "LevelId";
	public static final String SAVE = "Save";
	private Button saveButton;
	private TextField levelText;
	private TextField indexText;
	private LevelPanelController controller;

	public NewLevelPopup(LevelPanelController controller) {
		super();
		this.controller = controller;
		open(xSize, ySize);
		generatePopup();
		mapButtons();
	}

	@Override
	protected void generatePopup() {
		VBox root = new VBox();
		HBox nameLevel = new HBox();
		levelText = new TextField();
		nameLevel.getChildren().addAll(new Label(ResourceBundleManager.getAuthoring(SCENE_ID)), levelText);
		HBox levelIndex = new HBox();
		indexText = new TextField();
		levelIndex.getChildren().addAll(new Label(ResourceBundleManager.getAuthoring(LEVEL_ID)), indexText);
		saveButton = new Button(ResourceBundleManager.getAuthoring(SAVE));
		root.getChildren().addAll(nameLevel, levelIndex, saveButton);
		BorderPane borderPane = getPane();
		borderPane.setCenter(root);

	}

	@Override
	protected void mapButtons() {
		saveButton.setOnAction(event -> {
			if (!levelText.getText().isEmpty() && !indexText.getText().isEmpty()) {

				String levelName = levelText.getText();
				String levelId = indexText.getText();
				controller.addLevel(levelName, levelId);
				close();

			} else {
				close();
			}
		});

	}

}
