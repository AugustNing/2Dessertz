package authoring.displaydeprecated;

import java.io.IOException;
import java.util.ResourceBundle;

import authoring.AuthBehavior;
import authoring.Game;
import authoring.GameObject;
import data.GameObjectManager;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * @author Maddie Wilkinson
 *
 */
public class NewGameObjectWindowDeprecated extends PopupWindowDeprecated {
	
	private static final String INITIAL_DIRECTORY = "./data/gamedata/games/";
	private static final String MANDATORY_BEHAVIOR_NAME = "engine.behaviors.MandatoryBehavior";
	
	private VBox myVBox;
	private ListView<GameObject> myLevelObjects;
	private GameViewWindowDeprecated myGameViewWindow;
	private String myInitialDirectory;

	public NewGameObjectWindowDeprecated(ResourceBundle resources, Game game, Node root, ListView<GameObject> levelObjects, GameViewWindowDeprecated gameViewWindow) {
		super(resources, game, root);
		myInitialDirectory = INITIAL_DIRECTORY + game.getName() + "/images";
		myLevelObjects = levelObjects;
		myGameViewWindow = gameViewWindow;
		setStage(setUpScene());
	}

	@Override
	protected Scene setUpScene() {
		myVBox = new VBox(DEFAULT_SPACING);

		HBox nameObject = new HBox(DEFAULT_SPACING);
		TextField nameText = new TextField();
		nameObject.getChildren().addAll(new Label("Name: "), nameText);

		HBox xPosValues = new HBox(DEFAULT_SPACING);
		TextField xText = new TextField();
		xPosValues.getChildren().addAll(new Label("X Position: "), xText);

		HBox yPosValues = new HBox(DEFAULT_SPACING);
		TextField yText = new TextField();
		yPosValues.getChildren().addAll(new Label("Y Position: "), yText);

		HBox imageInfo = new HBox(DEFAULT_SPACING);
		TextField imageText = new TextField();
		
		FileChooser fileChooser = new FileChooser();
		
		ChooseImageEventDeprecated buttonAction = new ChooseImageEventDeprecated(imageText, myInitialDirectory, fileChooser);
		Button chooseImageButton = makeButton("ChooseImageButton", event -> buttonAction.pressed()); 
		
		imageInfo.getChildren().addAll(chooseImageButton, new Label("Image name: "), imageText);

		Button saveButton = makeButton("Save", event -> {
			saveGameObject(nameText, xText, yText, imageText, buttonAction.getImage());
		});

		myVBox.getChildren().addAll(nameObject, xPosValues, yPosValues, imageInfo, saveButton);
		return new Scene(myVBox);
	}

	private void saveGameObject(TextField nameText, TextField xText, TextField yText, TextField imageText, Image image) {
		if(!nameText.getText().isEmpty() && !xText.getText().isEmpty() && !yText.getText().isEmpty() && !imageText.getText().isEmpty()) {
			try {
				String objectName = nameText.getText();
				Double xPos = Double.parseDouble(xText.getText());
				Double yPos = Double.parseDouble(yText.getText());
				String imageName = imageText.getText();
				getGame().getImageManager().storeImage(imageName, image);

				GameObject go = makeGameObject(objectName, xPos, yPos, imageName);
				getGame().getSceneManager().getCurrentScene().getMyObjects().add(go);
				myLevelObjects.getItems().add(0, go);
				myGameViewWindow.updateWindow();
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

	private GameObject makeGameObject(String name, Double xPos, Double yPos, String imageName) {
		GameObject newObject = new GameObject();
		newObject.setName(name);
		AuthBehavior mandatory = newObject.getMandatoryBehavior();
		mandatory.getProperty("elementName").setValue(name);
		mandatory.getProperty("xPos").setValue(xPos);
		mandatory.getProperty("yPos").setValue(yPos);
		mandatory.getProperty("imagePath").setValue(imageName);

		/**
		 * This is only temporary!! to see if we can add game objects to the template.
		 */
		//
		GameObjectManager manager = new GameObjectManager();
		try {
			manager.saveCustomGameObject(newObject, imageName);
		} catch (IOException e) {
			System.out.println("rip");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		return newObject;
	}
}
