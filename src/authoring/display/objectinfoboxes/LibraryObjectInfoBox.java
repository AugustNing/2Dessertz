package authoring.display.objectinfoboxes;

import java.util.ArrayList;
import java.util.List;

import authoring.GameObject;
import authoring.display.popups.BehaviorPopup;
import authoring.display.popups.EventsPopup;
import data.GameObjectManager;
import data.LibraryObjectSaver;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * @author Edward Zhuang
 * LibraryObjectInfoBox class that shows information about the current library GameObject.
 * The user is able to edit this library GameObject.
 */
public class LibraryObjectInfoBox extends ObjectInfoBox {

	private static final String LIBRARY_OBJECT_PROPERTIES = "LibraryObjectProperties";
	private static final String EDIT_BEHAVIORS = "EditBehaviors";
	private static final String EDIT_EVENTS = "EditEvents";
	private static final String SAVE = "Save";
	private HBox buttonsHBox;
	private Button editBehaviorsButton;
	private Button editEventsButton;
	private Button saveEditsButton;
	private ImageView imageView;
	private GameObject gameObject;
	private List<GameObject> dummyList;
	private List<GameObject> libraryObjects;
	private Label objectName;
	private Label properties;
	private LibraryObjectSaver saver;
	
	public LibraryObjectInfoBox(GameObject gameObject, List<GameObject> library, LibraryObjectSaver saver, Image image) {
		super();
		this.gameObject = gameObject;
		this.libraryObjects = library;
		this.saver = saver;
		this.imageView = new ImageView(image);
		dummyList = new ArrayList<>();
		dummyList.add(this.gameObject);
		properties = new Label(ResourceBundleManager.getAuthoring(LIBRARY_OBJECT_PROPERTIES));
		objectName = new Label(this.gameObject.getName());
		initializeFXComponents();
		mapFXActions();
	}
	
	@Override
	public void mapFXActions() {
		editBehaviorsButton.setOnAction(e -> {
			new BehaviorPopup(dummyList);
		});
		editEventsButton.setOnAction(e -> {
			new EventsPopup(dummyList, libraryObjects);
		});
		saveEditsButton.setOnAction(e->{
			saver.saveUpdatedLibraryObject(gameObject);
		});
	}

	@Override
	public void initializeVBox() {
		getVBox().getChildren().clear();
		getVBox().getChildren().addAll(properties, imageView, objectName, buttonsHBox);
	}

	@Override
	public void initializeFXComponents() {
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(200);
		buttonsHBox = new HBox();
		editBehaviorsButton = new Button(ResourceBundleManager.getAuthoring(EDIT_BEHAVIORS));
		editEventsButton = new Button(ResourceBundleManager.getAuthoring(EDIT_EVENTS));
		saveEditsButton = new Button(ResourceBundleManager.getAuthoring(SAVE));
		buttonsHBox.getChildren().addAll(editBehaviorsButton,editEventsButton,saveEditsButton);
	}

}
