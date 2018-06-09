package authoring;

import java.util.List;
import java.util.Set;

/**
 * @author Edward Zhuang
 * Interface which is implemented by GameScene and GameSceneSerializable, only allowing for transfer of instance variables for serializing purposes.
 */
public interface GameSceneSerializableCreator {

	List<SceneBackgroundImageSerializable> getBackgroundImageSerializables();
	List<GameObject> getMyObjects();
	String getName();
	GameObject getCurrentGameObject();
	Set<String> getMyObjectNames();
	String getBackgroundImageName();
	String getAudioName();
	String getId();
}
