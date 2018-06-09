public interface GameScene {

	public Image getBackgroundImage(); //gets image set as scene background
	
	public void setBackgroundImage(); //sets image for scene background
	
	public ObjectManager getObjectManager() //each GameScene will have an ObjectManager that keeps track of all the GameObjects in that scene; this returns that ObjectManager
}
