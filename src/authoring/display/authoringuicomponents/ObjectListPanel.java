package authoring.display.authoringuicomponents;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import authoring.GameObject;
import authoring.ObjectInfoObservable;
import authoring.display.controllers.ObjectInfoController;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


/**
 * 
 * @author Edward Zhuang
 * Front end component that shows list of all objects in the current GameScene
 */
public class ObjectListPanel extends AuthoringUIComponent implements Observer {

	private static final int MAX_HEIGHT = 300;
	private HBox upperHBox;
	private HBox lowerHBox;
	private VBox myVBox;
	private Button myAddSceneBackgroundImageButton;
	private ListView<GameObject> myLevelObjects;
	private Button myDeleteObjectButton;
	private Button undoActionButton;
	private ObjectInfoController controller;
	private ObjectInfoObservable observable = null;
	
	public ObjectListPanel(ObjectInfoController controller) {
		this.controller = controller;
	}
	
	@Override
	protected void generateComponent() {
		BorderPane borderPane = getBorderPane();
		initializeFXComponents();
		mapFXActions();
		upperHBox.getChildren().addAll(myAddSceneBackgroundImageButton);
		lowerHBox.getChildren().addAll(myDeleteObjectButton, undoActionButton);
		myVBox.getChildren().addAll(myLevelObjects, upperHBox, lowerHBox);
		borderPane.setCenter(myVBox);
	}

	/**
	 * Updates front end list of gameobjects. Usually called when GameScene is changed or object is added.
	 * @param arg0 Observable
	 * @param arg1 Object
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		observable = (ObjectInfoObservable) arg0;
		updateLevelObjects(observable.getMyObjects());
	}
	
	private void updateLevelObjects(List<GameObject> list) {
		Set<GameObject> set = new HashSet<>(list);
		myLevelObjects.getItems().clear();
		myLevelObjects.getItems().addAll(set);
		myLevelObjects.getSelectionModel().select(observable.getCurrentGameObject());
	}

	@Override
	protected void initializeFXComponents() {
		myAddSceneBackgroundImageButton = new Button(ResourceBundleManager.getAuthoring("AddSceneBackgroundImageButton"));
		myDeleteObjectButton = new Button(ResourceBundleManager.getAuthoring("AddDeleteObjectButton"));
		undoActionButton = new Button(ResourceBundleManager.getAuthoring("AddUndoActionButton"));
		myLevelObjects = new ListView<>();
		myLevelObjects.setMaxHeight(MAX_HEIGHT);
		myVBox = new VBox();
		upperHBox = new HBox();
		lowerHBox = new HBox();
	}

	@Override
	protected void mapFXActions() {
		myAddSceneBackgroundImageButton.setOnAction(e -> {
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose Object Image");
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
				File image = fileChooser.showOpenDialog(new Stage());
				controller.addBackgroundImage(new Image(image.toURI().toString()));
			} catch (Exception exception) {
				//do nothing
				//this just means the user didn't choose an image
				return;
			}
		});
		myDeleteObjectButton.setOnAction(e -> {
			int index = myLevelObjects.getSelectionModel().getSelectedIndex();
			controller.deleteGameObject(index);
		});
		myLevelObjects.setOnMouseClicked(e->{
			controller.setCurrentGameObject(myLevelObjects.getSelectionModel().getSelectedItem());
		});
		undoActionButton.setOnMouseClicked(e->{
			controller.restorePreviousGameScene();
		});
	}

}
