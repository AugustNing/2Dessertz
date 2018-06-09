package authoring.display.authoringuicomponents;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import authoring.GameScene;
import authoring.LevelsObservable;
import authoring.display.controllers.LevelPanelController;
import authoring.display.popups.NewLevelPopup;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Edward Zhuang
 * Front end component which allows user to add, select, and rename levels
 */
public class LevelPanel extends AuthoringUIComponent implements Observer{

	private ComboBox<String> myLevelDropdown;
	private Button myAddLevelButton, setSceneIdButton, setLevelIdButton, chooseLevelButton;
	private LevelPanelController controller;
	private TextField gameSceneId, levelId;
	
	public LevelPanel(LevelPanelController controller) {
		super();
		this.controller = controller;	
	}
	
	@Override
	protected void generateComponent() {
		BorderPane borderPane = getBorderPane();
		initializeFXComponents();
		mapFXActions();
		HBox levelChooser = new HBox();
		levelChooser.getChildren().addAll(myAddLevelButton, myLevelDropdown, chooseLevelButton,gameSceneId,setSceneIdButton,levelId,setLevelIdButton);
		borderPane.setCenter(levelChooser);
	}

	/**
	 * Called by Game when model is updated, updates the level panel
	 */
	@Override
	public void update(Observable o, Object arg) {
		LevelsObservable levelsObservable = (LevelsObservable) o;
		updateLevelDropdown(levelsObservable.getScenes());
		updateLevelName(levelsObservable.getCurrentSceneName());
		gameSceneId.setText(levelsObservable.getCurrentSceneName());
		levelId.setText(levelsObservable.getCurrentSceneId());
	}
	
	private void updateLevelName(String currentSceneName) {
		myLevelDropdown.setPromptText(currentSceneName);
		
	}
	
	private void updateLevelDropdown(List<GameScene> list) {
		myLevelDropdown.getItems().removeAll();
		myLevelDropdown.getItems().clear();
		for (GameScene scene: list) {
			myLevelDropdown.getItems().add(scene.getName());
		}
	}

	@Override
	protected void initializeFXComponents() {
		myAddLevelButton = new Button(ResourceBundleManager.getAuthoring("AddSceneButton"));
		setSceneIdButton = new Button(ResourceBundleManager.getAuthoring("SetSceneIdButton"));
		setLevelIdButton = new Button(ResourceBundleManager.getAuthoring("SetLevelIdButton"));
		chooseLevelButton = new Button(ResourceBundleManager.getAuthoring("SetLevelButton"));
		gameSceneId = new TextField();
		levelId = new TextField();
		myLevelDropdown = new ComboBox<>();
		myLevelDropdown.setPromptText(ResourceBundleManager.getAuthoring("SelectSceneDropDown"));
	}

	@Override
	protected void mapFXActions() {
		myAddLevelButton.setOnAction(e -> {
			new NewLevelPopup(controller);
		});
		setSceneIdButton.setOnAction(e->{
			controller.setCurrentSceneName(gameSceneId.getText());
		});
		setLevelIdButton.setOnAction(e->{
			controller.setCurrentLevelId(levelId.getText());
		});
		chooseLevelButton.setOnAction(e->{
			controller.setLevel(myLevelDropdown.getSelectionModel().getSelectedItem());
		});
	}

}
