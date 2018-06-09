package authoring.display.authoringuicomponents;

import authoring.GameObject;
import authoring.display.popups.NewLibraryObjectPopup;
import data.GameObjectManager;
import data.propertiesFiles.ResourceBundleManager;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Edward Zhuang
 * Front end component which shows saved objects which can be used in any game
 */
public class ObjectLibrary extends AuthoringUIComponent {

	private static final String NPC = "npc";
	private static final String BLOCK = "block";
	private static final String PLAYER = "player";
	private static final String ADD_LEVEL = "AddLevel";
	private static final String ADD_LIBRARY = "AddLibrary";
	private static final String PROJECTILE = "Projectile";
	private Button addToGameButton;
	private Button addToLibrary;
	private ListView<GameObject> objectList;
	private ComboBox<String> objectType;
	private GameObjectManager manager;
	private VBox vBox;
	
	public ObjectLibrary(GameObjectManager manager) {
		this.manager = manager;
		generateComponent();
	}
	
	@Override
	protected void initializeFXComponents() {
		addToGameButton = new Button(ResourceBundleManager.getAuthoring(ADD_LEVEL));
		addToLibrary = new Button(ResourceBundleManager.getAuthoring(ADD_LIBRARY));
		HBox bottomHBox = new HBox();
		bottomHBox.getChildren().addAll(addToGameButton,addToLibrary);
		objectList = new ListView<>();
		objectType = new ComboBox<>();
		objectType.getItems().addAll(PLAYER,BLOCK,NPC,PROJECTILE);
		vBox = new VBox();
		vBox.getChildren().addAll(bottomHBox, objectType,objectList);
	}

	@Override
	protected void generateComponent() {
		BorderPane borderPane = getBorderPane();
		initializeFXComponents();
		mapFXActions();
		borderPane.setCenter(vBox);
	}

	/**
	 * Updates front end list of GameObjects for users to choose
	 * @param list list of GameObjects
	 */
	public void updateObjectList(List<GameObject> list) {
		objectList.getItems().clear();
		objectList.getItems().addAll(FXCollections.observableArrayList(list));
	}
	
	@Override
	protected void mapFXActions() {
		addToGameButton.setOnAction(e->{
			manager.addObjectToGame();
		});
		addToLibrary.setOnAction(e->{
			new NewLibraryObjectPopup(manager);
		});
		objectList.setOnMouseClicked(e->{
			manager.setCurrentObject(objectList.getSelectionModel().getSelectedItem());
			
		});
		objectType.valueProperty().addListener((o,old,neww)->{
			manager.changeObjectType(neww);
		});
	}
	

}

