package authoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import engine.authouringconversion.Converter2;
import engine.tests.ModelGamePart1;

/** 
 * SceneManager keeps track of all the GameScenes
 * and all the levels
 * 
 * @author: Summer, Edward Zhuang
 **/
public class SceneManager extends Observable implements LevelsObservable {
	
	private GameScene currentScene;
	private List<GameScene> myScenes;

	public SceneManager() {
		myScenes = new ArrayList<>();
		GameScene newLevel = new GameScene("Scene 1", "Level 1");
		myScenes.add(newLevel);
		currentScene = newLevel;
		setChanged();
	}
	
	public GameScene makeScene(String name, String id) {
		GameScene newLevel = new GameScene(name, id);
		myScenes.add(newLevel);
		notifyMyObservers();
		return newLevel;
	}
	
	/**
	 * returns the current scene
	 * @return
	 */
	public GameScene getCurrentScene() {
		return currentScene;
	}
	
	/**
	 * 
	 * @param name sets name of the current scene
	 */
	public void setCurrentScene(String name) {
		for (GameScene scene: myScenes) {
			if (scene.getName().equals(name)) {
				currentScene = scene;
			}
		}
		notifyMyObservers();
	}
	
	/**
	 * removes the scene toRemove from the game
	 * @param toRemove
	 */
	public void removeScene(GameScene toRemove) {
		myScenes.remove(toRemove);
		notifyMyObservers();
	}
	
	/**
	 * returns all the scenes 
	 */
	@Override
	public List<GameScene> getScenes(){
		return myScenes;
	}
	
	/**
	 * restores a scene that was lost
	 * @param list
	 */
	public void restoreScenes(List<GameScene> list) {
		myScenes = list;
		currentScene = myScenes.get(0);
		notifyMyObservers();
	}
	
	public void notifyMyObservers() {
		setChanged();
		notifyObservers();
	}


	/**
	 * gets the name of the current scene
	 */
	@Override
	public String getCurrentSceneName() {
		// TODO Auto-generated method stub
		return getCurrentScene().getName();
	}
	
	/**
	 * gets the ID of the current scene
	 */
	@Override 
	public String getCurrentSceneId() {
		return getCurrentScene().getId();
	}
	
	/**
	 * sets the name of the current scene to name
	 */
	@Override
	public void setCurrentSceneName(String name) {
		getCurrentScene().setName(name);
	}
	
	/**
	 * sets the ID of the current scene to id
	 */
	@Override
	public void setCurrentLevelId(String id) {
		getCurrentScene().setId(id);
	}
	
	
}
