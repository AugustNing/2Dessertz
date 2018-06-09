public interface SceneManager {

	public GameScene makeGameObject(); //makes a GameScene
	
	public void deleteGameScene(GameScene gameScene); //deletes a GameScene
	
	public List<GameScene> getGameScenes(); //returns the list of all GameScenes currently in the game
	
}
