package authoring.displaydeprecated;

import java.io.File;

import authoring.display.controllers.ObjectInfoController;
import authoring.display.popups.ErrorBox;
import authoring.display.popups.Popup;
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

/**
 * 
 * @author Edward Zhuang
 *
 */
public class NewGameObjectPopupDeprecated extends Popup {

	private static final int xSize = 300;
	private static final int ySize = 200;
	private static final int DEFAULT_SPACING = 10;
	private ObjectInfoController controller;
	private TextField nameText;
	private TextField xText;
	TextField yText;
	TextField imageText;
	Button chooseImageButton;
	Button saveButton;
	Image image;

	public NewGameObjectPopupDeprecated(ObjectInfoController controller) {
		// TODO Auto-generated constructor stub
		super();
		this.controller = controller;
		open(xSize, ySize);
		generatePopup();
		mapButtons();
	}

	@Override
	protected void generatePopup() {
		// TODO Auto-generated method stub
		VBox myVBox = new VBox(DEFAULT_SPACING);
		image = null;
		HBox nameObject = new HBox(DEFAULT_SPACING);
		nameText = new TextField();
		nameObject.getChildren().addAll(new Label("Name: "), nameText);

		HBox xPosValues = new HBox(DEFAULT_SPACING);
		xText = new TextField();
		xPosValues.getChildren().addAll(new Label("X Position: "), xText);

		HBox yPosValues = new HBox(DEFAULT_SPACING);
		yText = new TextField();
		yPosValues.getChildren().addAll(new Label("Y Position: "), yText);

		chooseImageButton = new Button(ResourceBundleManager.getAuthoring("ChooseImageButton"));

		saveButton = new Button(ResourceBundleManager.getAuthoring("Save"));

		myVBox.getChildren().addAll(nameObject, xPosValues, yPosValues, chooseImageButton, saveButton);

		BorderPane borderPane = getPane();
		borderPane.setCenter(myVBox);

	}

	@Override
	protected void mapButtons() {
		// TODO Auto-generated method stub
//		chooseImageButton.setOnAction(e -> {
//			try {
//				FileChooser fileChooser = new FileChooser();
//				fileChooser.setTitle("Choose Object Image");
//				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
//				File imageFile = fileChooser.showOpenDialog(new Stage());
//				image = new Image(imageFile.toURI().toString());
//				// put image.getName into SceneBackground
//			} catch (Exception exception) {
//				// do nothing
//				// this just means the user didn't choose an image
//
//			} //
//		});

		saveButton.setOnAction(e -> {
			// System.out.println("ADDING TO GAMEOBJECT NAME: " + nameText.getText());
			if (controller.checkUniqueName(nameText.getText())) {
				controller.addGameObject(nameText.getText(), Double.parseDouble(xText.getText()),
						Double.parseDouble(yText.getText()), (nameText.getText() + "image"), image);
				close();

			} else {
				
				new ErrorBox("Duplicate Object Name", "Please Change the Name of Your Object");

			}
		});

	}

}
