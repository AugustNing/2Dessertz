package authoring;

import java.util.List;

/**
 * @author Edward Zhuang
 * Interface implemented by GameScene, letting ObjectInfo classes access some information.
 */
public interface ObjectInfoObservable {
	GameObject getCurrentGameObject();
	List<GameObject> getInstances();
	List<GameObject> getMyObjects();
}
