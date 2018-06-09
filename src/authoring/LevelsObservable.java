package authoring;

import java.util.List;

/**
 * @author Edward Zhuang
 * Interface implemented by GameScene, letting LevelPanel access some information.
 */
public interface LevelsObservable {
	
	List<GameScene> getScenes();
	
	String getCurrentSceneName();

	String getCurrentSceneId();

	void setCurrentSceneName(String name);

	void setCurrentLevelId(String id);
	
}
