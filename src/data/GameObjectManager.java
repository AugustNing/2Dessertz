package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import authoring.AuthBehavior;
import authoring.GameObject;
import authoring.GameObjectAdder;
import authoring.Property;
import authoring.display.authoringuicomponents.ObjectLibrary;
import authoring.display.objectinfoboxes.LibraryObjectInfoBox;
import data.propertiesFiles.ResourceBundleManager;
import engine.exceptions.ErrorBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * @author Edward Zhuang
 * This class is essentially a "controller" for the Object Library. However, it has its own default image manager
 * which allows it to save game objects outside of game files. This way, the user can implement previously implemented game objects.
 */
public class GameObjectManager implements LibraryObjectSaver, LibraryObservable {

	private static final String defaultObjectLocation = "./data/gamedata/customobjects/";
	private static final String DEFAULT = "default";
	private static final String IMAGE = "image";
	private static final String PNG = ".png";
	private static final String PLAYER = "player";
	private static final String BLOCK = "block";
	private static final String NPC = "npc";
	private static final String PROJECTILE = "projectile";
	private static final String IMAGE_PNG = "image.png";
	private static final String LIBRARY_Y = "LIBRARY_Y";
	private static final String LIBRARY_X = "LIBRARY_X";
	private String key = "block";
	private Serializer serializer;
	private ImageManager defaultManager;
	private ImageManager gameImageManager;
	private GameObjectAdder levelPanelController;
	private GameObject currentObject;
	private ObjectLibrary library;
	private LibraryObserver observer;
	
	public GameObjectManager(GameObjectAdder adder, ImageManager imageManager) {
		serializer = new Serializer();
		levelPanelController = adder;
		gameImageManager = imageManager;
		defaultManager = new ImageManager(DEFAULT);
		library = new ObjectLibrary(this);
	}

	/**
	 * Gets a list of Game Objects. Maybe should not be static,
	 * but this was made due to time constraints.
	 * @param key type of object
	 * @return List of game objects
	 */
	public static List<GameObject> getSavedGameObjects(String key){
		Deserializer deserializer = new Deserializer();
		List<GameObject> list = new ArrayList<>();
		File directory = new File(defaultObjectLocation + key);
        File[] directoryListing = directory.listFiles();
        if (directoryListing != null){
            for (File level : directoryListing){
                String path = level.getAbsolutePath();
                list.add(deserializer.getGameObject(path));
            }
        }
        return list;
	}
	
	public void setObserver(LibraryObserver observer) {
		this.observer = observer;
	}

	/**
	 * Updates object
	 * @param key String
	 */
	public void changeObjectType(String key) {
		this.key = key;
		library.updateObjectList(getSavedGameObjects(key));
	}

	/**
	 * Sets current object, lets ObjectInfoPanel know
	 * @param gameObject GameObject
	 */
	public void setCurrentObject(GameObject gameObject) {
		this.currentObject = gameObject;
		observer.notifyObserver();
	}

	/**
	 * Saves a game object to the library
	 * @param name name of object
	 * @param image object's image
	 * @throws IOException exception
	 */
	public void saveCustomGameObject(String name, Image image) throws IOException {
		defaultManager.storeImage(name+ IMAGE,image);
		GameObject gameObject = new GameObject();
		AuthBehavior mandatory = gameObject.getMandatoryBehavior();
		gameObject.setName(name);
		mandatory.getProperty("xPos").setValue(0.0);
		mandatory.getProperty("yPos").setValue(0.0);
		mandatory.getProperty("imagePath").setValue(name+ IMAGE);
		serializer.saveGameObject(defaultObjectLocation + key + "/", gameObject, name);
		library.updateObjectList(getSavedGameObjects(key));
	}
	
	private void transferImageToGame(String imageName) {
		Image libraryImage = defaultManager.getImage(imageName + PNG);
		gameImageManager.storeImage(imageName, libraryImage);
	}

	/**
	 * Adds a GameObject to the currently authored game.
	 */
	public void addObjectToGame() {
		GameObject go = new GameObject(currentObject);
		Property imagePathProperty = currentObject.getMandatoryBehavior().getProperty("imagePath");
		String string = ((String) imagePathProperty.getValue());
		transferImageToGame(string);
		levelPanelController.addToCurrentScene(go);
	}

	/**
	 * Checks if name of game object is unique to prevent duplicates.
	 * @param name name
	 * @return boolean
	 */
	public boolean checkUniqueName(String name) {
		boolean isUnique = true;
		String originalKey = key;
		String[] keys = {PLAYER, BLOCK, NPC, PROJECTILE};
		for (String key1 : keys) {
			key = key1;
			for (GameObject go : getSavedGameObjects(key)) {
				if (name.equals(go.getName())) {
					isUnique = false;
				}
			}
		}
		key = originalKey;
		return isUnique;
	}

	/**
	 * Attaches the ObjectLibrary to the AuthoringEnvironment.
	 * @param pane pane
	 */
	public void addToGUI(Pane pane) {
		library.attachToPane(pane, ResourceBundleManager.getPosition(LIBRARY_X), ResourceBundleManager.getPosition(LIBRARY_Y));
	}

	/**
	 * Saves changes made to GameObject.
	 * @param gameObject gameObject that was edited.
	 */
	@Override
	public void saveUpdatedLibraryObject(GameObject gameObject) {
		try {
			serializer.saveGameObject(defaultObjectLocation + key + "/", gameObject, gameObject.getName());
		} catch (IOException e) {
			new ErrorBox("Serialization Failed", gameObject.getName() + ": Object could not be serialized");
		}
		library.updateObjectList(getSavedGameObjects(key));
	}

	/**
	 * Produces a LibraryObjectInfoBox which provides information about the current GameObject.
	 * @return LibraryObjectInfoBox for ObjectInfoPanel to use
	 */
	@Override
	public LibraryObjectInfoBox getLibraryObjectInfoBox() {
		List<GameObject> allObjects = new ArrayList<>();
		String[] keys = {PLAYER,BLOCK,NPC,PROJECTILE};
		for (String key1 : keys) {
			key = key1;
			allObjects.addAll(getSavedGameObjects(key));
		}
		String imageName = currentObject.getName() + IMAGE_PNG;
		return new LibraryObjectInfoBox(currentObject, allObjects, this, defaultManager.getImage(imageName));
	}
		
	
}
