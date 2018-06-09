package authoring;

import java.util.List;

/**
 * @author Edward Zhuang
 * Interface implemented by GameSceneMemento which passes information back to a GameScene to restore previous state.
 */
public interface GameSceneToOriginator {

	List<GameObject> getGameObjects();
	List<SceneBackgroundImageSerializable> getSerializables();
	
}
