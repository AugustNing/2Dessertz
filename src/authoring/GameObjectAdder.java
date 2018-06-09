package authoring;

/**
 * @author Edward Zhuang
 * Interface which is implemented by LevelPanelController allowing the GameObjectManager to add GameObjects to current GameScene.
 */
public interface GameObjectAdder {

	void addToCurrentScene(GameObject gameObject);
	
}

