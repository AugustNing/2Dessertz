package authoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

/** 
 * GameScene is the background image of each level
 * @author: Summer, Edward Zhuang
 **/
public class GameScene extends Observable implements AudioObservable, GameViewObservable, ObjectInfoObservable, GameSceneSerializableCreator {
	
	//has a list of objects
	private String myName;
	private String levelId;
	private String backgroundImageName;
	private List<SceneBackgroundImageSerializable> backgroundImageSerializables;
	private List<GameObject> myObjects;
	private Set<String> myObjectNames;
	private GameObject currentGameObject;
	private GameSceneToCareTaker memento;
	private String audioName;

	public GameScene(String name, String id) {
		myName = name;
		levelId = id;
		myObjects = new ArrayList<>();
		myObjectNames = new TreeSet<>();
		backgroundImageSerializables = new ArrayList<>();	
		backgroundImageName = myName.replaceAll("\\s", "") + "backgroundimage";
	}
	
	public GameScene(GameSceneSerializableCreator scene) {
		myName = scene.getName();
		myObjects = scene.getMyObjects();
		backgroundImageSerializables = scene.getBackgroundImageSerializables();
		currentGameObject = scene.getCurrentGameObject();
		myObjectNames = scene.getMyObjectNames();
		backgroundImageName = scene.getBackgroundImageName();
		audioName = scene.getAudioName();
		levelId = scene.getId();
	}
	
	/**
	 * returns set of names of GameObjects that are contained within the screen
	 */
	@Override
	public Set<String> getMyObjectNames(){
		return myObjectNames;
	}
	
	/**
	 * 
	 * @param toAdd is the object to add to the gamescene
	 */
	public void addObject(GameObject toAdd) {
		backupGameScene();
		myObjects.add(toAdd);
		notifyMyObservers();
	}
	
	/**
	 * returns list of gameobjects in a scene
	 */
	@Override
	public List<GameObject> getMyObjects(){
		return myObjects;
	}

	/**
	 * returns the currently selected game object
	 */
	@Override
	public GameObject getCurrentGameObject() {
		return currentGameObject;
	}

	/**
	 * 
	 * @param selectedGameObject sets the current game object to the slected one
	 */
	public void setCurrentGameObject(GameObject selectedGameObject) {
		currentGameObject = selectedGameObject;
		notifyMyObservers();
	}

	/**
	 * returns the name of the game scene
	 */
	@Override
	public String getName() {
		return myName;
	}

	/**
	 * 
	 * @param name sets the name of the scene
	 */
	public void setName(String name) {
		myName = name;
	}
	
	/**
	 * 
	 * @return the scene ID
	 */
	public String getId() {
		return levelId;
	}

	/**
	 * 
	 * @param id sets the scene ID
	 */
	public void setId(String id) {
		System.out.println("setId: ");
		levelId = id;
	}
	
	/**
	 * toString for game scene returns the name
	 */
	@Override
	public String toString() {
		return myName;
	}
	
	/**
	 * 
	 * @param s is the background image
	 * adds a background image to the scene
	 */
	public void addBackgroundImageSerializable(SceneBackgroundImageSerializable s) {
		backgroundImageSerializables.add(s);
		notifyMyObservers();
	}
	
	/**
	 * 
	 * @param name name of object to check. 
	 * checks if an object with the same name exists in the scene
	 * @return true or false
	 */
	public boolean checkUniqueObjectNames(String name) {
		boolean isUniqueName = true;
		for (GameObject go : myObjects){
			if (go.getName().equals(name)) {
				isUniqueName = false;
			}
		}
		return isUniqueName;
	}
	
	/**
	 * returns all the instances of the current game object
	 */
	@Override
	public List<GameObject> getInstances() {
		// TODO Auto-generated method stub
		List<GameObject> list = new ArrayList<>();
		GameObject gameObject = getCurrentGameObject();
		String name = gameObject.getName();
		for (GameObject go: myObjects) {
			if (name.equals(go.getName())) {
				list.add(go);
			}		
		}
		return list;
	}

	public void notifyMyObservers() {
		setChanged();
		notifyObservers();
	}

	/**
	 * returns image path of the current image
	 */
	@Override
	public List<SceneBackgroundImageSerializable> getBackgroundImageSerializables() {
		return backgroundImageSerializables;
	}
	
	public void backupGameScene() {
		memento = new GameSceneMemento(myObjects, backgroundImageSerializables);
	}
	
	public void restorePreviousGameScene() {
		myObjects = ((GameSceneToOriginator)memento).getGameObjects();
		backgroundImageSerializables = ((GameSceneToOriginator)memento).getSerializables();
		notifyMyObservers();
	}
	
	/**
	 * creates a new instance of the current game object 
	 */
	public void duplicateGameObject() {
		GameObject newGo = new GameObject(currentGameObject);
		myObjects.add(newGo);
	}

	/**
	 * returns audioName
	 */
	public String getAudioName() {
		return audioName;
	}

	/**
	 * @param audio 
	 * sets audioName to audio
	 */
	public void setAudioName(String audio) {
		this.audioName = audio;
		notifyMyObservers();
	}

	/**
	 * returns the background image name
	 */
	public String getBackgroundImageName() {
		return backgroundImageName;
	}

	/**
	 * sets the name of the background image
	 */
	public void setBackgroundImageName() {
		this.backgroundImageName = myName.replaceAll("\\s", "") + "backgroundimage";
		System.out.println(backgroundImageName);
	}
	
}
