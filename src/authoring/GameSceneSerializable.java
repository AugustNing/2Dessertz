package authoring;

import java.util.List;
import java.util.Set;

/**
 * @author Edward Zhuang
 * A serializable form of GameScene. Since GameScene implements Observable interfaces, it cannot be serialized.
 */
public class GameSceneSerializable implements GameSceneSerializableCreator {
	
	private String myName;
	private String levelId;
	private List<SceneBackgroundImageSerializable> backgroundImageSerializables;
	private List<GameObject> myObjects;
	private Set<String> myObjectNames;
	private GameObject currentGameObject;
	private String backgroundImageName;
	private String audioName;
	
	public GameSceneSerializable(GameSceneSerializableCreator scene) {
		myName = scene.getName();
		myObjects = scene.getMyObjects();
		levelId = scene.getId();
		backgroundImageSerializables = scene.getBackgroundImageSerializables();
		currentGameObject = scene.getCurrentGameObject();
		myObjectNames = scene.getMyObjectNames();
		backgroundImageName = scene.getBackgroundImageName();
		audioName = scene.getAudioName();
	}
	
	public Set<String> getMyObjectNames(){
		return myObjectNames;
	}
	
	public List<GameObject> getMyObjects(){
		return myObjects;
	}
	
	public GameObject getCurrentGameObject() {
		return currentGameObject;
	}
	
	public List<SceneBackgroundImageSerializable> getBackgroundImageSerializables() {
		return backgroundImageSerializables;
	}
	
	public String getName() {
		return myName;
	}

	public String getBackgroundImageName() {
		return backgroundImageName;
	}

	public String getAudioName() {
		return audioName;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return levelId;
	}
	
	
}
