package authoring;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edward Zhuang
 * WORK IN PROGRESS - class designed to take in a previous state of GameScene to allow for undoing of user actions.
 */
public class GameSceneMemento implements GameSceneToCareTaker, GameSceneToOriginator {

	private List<GameObject> gameObjects;
	private List<SceneBackgroundImageSerializable> serializables;
	
	public GameSceneMemento(List<GameObject> gameObjects, List<SceneBackgroundImageSerializable> serializables) {
		gameObjects = new ArrayList<>();
		for (GameObject go: gameObjects) {
			GameObject copy = new GameObject(go);
			this.gameObjects.add(copy);
		}
		
		this.serializables = serializables;
	}
	
	@Override
	public List<GameObject> getGameObjects() {
		// TODO Auto-generated method stub
		return gameObjects;
	}

	@Override
	public List<SceneBackgroundImageSerializable> getSerializables() {
		// TODO Auto-generated method stub
		return serializables;
	}

}
