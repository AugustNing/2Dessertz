package authoring.displaydeprecated;

import java.io.File;
import java.util.ResourceBundle;

import authoring.Game;
import authoring.GameObject;
import authoring.GameScene;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Maddie Wilkinson
 *
 */
public class LevelPanelDeprecated extends MainWindowComponentDeprecated {

	private VBox myVBox;
	private HBox myHBox;
	private ComboBox<GameScene> myLevelDropdown;
	private ComboBox<String> myPanelSelectorComboBox; 
	private Button myAddLevelButton;
	private Button myAddGameObjectButton;
	private Button myAddSceneBackgroundImageButton;
	private ListView<GameObject> myLevelObjects;
	
	private GameViewWindowDeprecated myGameViewWindow;
	private ObjectInfoPanelDeprecated myObjectInfoPanel;

	public LevelPanelDeprecated(ResourceBundle resources, Game game, Node root, GameViewWindowDeprecated gameViewWindow, ObjectInfoPanelDeprecated objectInfoPanel) {
		super(resources, game, root); //pass resources to super constructor
		myGameViewWindow = gameViewWindow;
		myObjectInfoPanel = objectInfoPanel;

		myVBox = new VBox();
		myHBox = new HBox();
		myHBox.getChildren().addAll(makeAddGameObjectButton(), makeAddSceneBackgroundImageButton());
		myVBox.getChildren().addAll(makeLevelChooser(), makeObjectList(), myHBox);
	}

	private HBox makeLevelChooser() {
		HBox levelChooser = new HBox(DEFAULT_SPACING);
		makeLevelDropdown();
		makeAddLevelButton();
		makePanelSelectorComboBox();
		levelChooser.getChildren().addAll(myAddLevelButton, myLevelDropdown, myPanelSelectorComboBox); //TODO: maybe move myPanelSelectorComboBox 
		return levelChooser;
	}

	private Button makeAddLevelButton() {
		myAddLevelButton =  makeButton("AddSceneButton", event -> { 
			new NewLevelWindowDeprecated(getResources(), getGame(), getRoot(), myLevelDropdown);
		});
		return myAddLevelButton;
	}
	
	private Button makeAddGameObjectButton() {
		myAddGameObjectButton =  makeButton("AddGameObjectButton", event -> { 
			new NewGameObjectWindowDeprecated(getResources(), getGame(), getRoot(), myLevelObjects, myGameViewWindow);
		});
		return myAddGameObjectButton;
	}
	
	private Button makeAddSceneBackgroundImageButton() {
		myAddSceneBackgroundImageButton = makeButton("AddSceneBackgroundImageButton", event -> {
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose Object Image");
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
				File image = fileChooser.showOpenDialog(new Stage());
//				getGame().getSceneManager().getCurrentScene().getSceneBackground().addImage(new Image(image.toURI().toString()));
				//put image.getName into SceneBackground
			} catch (Exception e) {
				//do nothing
				//this just means the user didn't choose an image
				//which is a perfectly fine thing for them to do
			}
		});
				
		return myAddSceneBackgroundImageButton;
	}

	private ComboBox<GameScene> makeLevelDropdown() {
		myLevelDropdown = new ComboBox<>();
		myLevelDropdown.setPromptText(super.getResources().getString("SelectSceneDropDown")); //make super.getString method?
		myLevelDropdown.getItems().addAll(getGame().getSceneManager().getScenes());
		myLevelDropdown.valueProperty().addListener((o, old, neww) -> {
//			getGame().getSceneManager().setCurrentScene(neww);
			myLevelObjects.setItems(FXCollections.observableArrayList(getGame().getSceneManager().getCurrentScene().getMyObjects()));
			myGameViewWindow.updateWindow();
			System.out.println("Level Panel tried to call updateWindow");
		});
		return myLevelDropdown;
	}
	
	private ComboBox<String> makePanelSelectorComboBox(){
		myPanelSelectorComboBox = new ComboBox<>();
		myPanelSelectorComboBox.setPromptText(super.getResources().getString("ChoosePanel"));
		myPanelSelectorComboBox.getItems().add("Background");
		myPanelSelectorComboBox.getItems().add("Foreground");
		myPanelSelectorComboBox.valueProperty().addListener((o, old, neww) -> {
			myGameViewWindow.switchPanes(neww);
		});
		return myPanelSelectorComboBox; 
	}

	private ListView<GameObject> makeObjectList() {
		myLevelObjects = new ListView<GameObject>();
		myLevelObjects.setOnMouseClicked(event -> {
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
		        System.out.println("You double clicked!!!");
		    }
		});
		myLevelObjects.getSelectionModel().selectedItemProperty().addListener((o, old, neww) -> {
			getGame().getSceneManager().getCurrentScene().setCurrentGameObject(neww);
			myObjectInfoPanel.updatePanel();
		});
		return myLevelObjects;
	}

	public VBox asVBox() {
		return myVBox;
	}
	
	@Override
	protected Node asNode() {
		return myVBox;
	}

}
