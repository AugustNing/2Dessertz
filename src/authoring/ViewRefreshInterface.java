package authoring;

/**
 * @author Edward Zhuang
 * Interface which allows GameObjectImageView to update GameScene.
 */
public interface ViewRefreshInterface {

	void notifyObjectInfoObservers(GameObject gameObject);
	
	void backupGameScene();
}
