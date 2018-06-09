package authoring.display.popups;

import java.io.File;
import java.io.IOException;

import data.GameObjectManager;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class NewLibraryObjectPopup extends Popup {

	private static final String CHOOSE_OBJECT_IMAGE = "Choose Object Image";
	private static final int xSize = 400;
	private static final int ySize = 600;
	private static final int DEFAULT_SPACING = 50;
	private static final String NAME = "Name: ";
	private static final String CHOOSE_IMAGE_BUTTON = "ChooseImageButton";
	private static final String SAVE = "Save";
	private static final String IMAGE_FILES = "Image Files";
	private static final String PNG = "*.png";
	private static final String JPG = "*.jpg";
	private static final String GIF = "*.gif";
	private GameObjectManager manager;
	private TextField nameText;
	private Button chooseImageButton;
	private Button saveButton;
	private Image image;

	public NewLibraryObjectPopup(GameObjectManager manager) {
		super();
		this.manager = manager;
		open(xSize, ySize);
		generatePopup();
		mapButtons();
	}
	
	@Override
	protected void generatePopup() {
		VBox myVBox = new VBox(DEFAULT_SPACING);
		image = null;
		HBox nameObject = new HBox(DEFAULT_SPACING);
		nameText = new TextField();
		nameObject.getChildren().addAll(new Label(NAME), nameText);
		chooseImageButton = new Button(ResourceBundleManager.getAuthoring(CHOOSE_IMAGE_BUTTON));
		saveButton = new Button(ResourceBundleManager.getAuthoring(SAVE));
		myVBox.getChildren().addAll(nameObject, chooseImageButton, saveButton);
		BorderPane borderPane = getPane();
		borderPane.setCenter(myVBox);
	}

	@Override
	protected void mapButtons() {
		chooseImageButton.setOnAction(e -> {
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle(CHOOSE_OBJECT_IMAGE);
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter(IMAGE_FILES, PNG, JPG, GIF));
				File imageFile = fileChooser.showOpenDialog(new Stage());
				image = new Image(imageFile.toURI().toString());
			} catch (Exception exception) {
				// do nothing
				// this just means the user didn't choose an image
				return;
			}
		});

		saveButton.setOnAction(e -> {
			if (manager.checkUniqueName(nameText.getText())) {
				try {
					manager.saveCustomGameObject(nameText.getText(), image);
				} catch (IOException e1) {
					// this will never happen since we are using a file chooser
					return;
				}
				close();
			} else {
				new ErrorBox("Duplicate Object Name", "Please Change the Name of Your Object");
			}
		});
	}
	
}
