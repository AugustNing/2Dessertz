package voogasalad_2dessertz;
/**
 * In this use case, an ObjectManager creates a new GameObject with the given properties. It creates a new GameObject, calls the add() method in the
 * GameObject class, and adds the GameObject to its internal data structure that keeps track of the GameObjects, objectTemplates.
 * 
 */
public class GameAuthoringUseCase {

	/**
	 * The ObjectManager class keeps track of all of the GameObjects within a GameScene.
	 */
	public class ObjectManager {
		private List<GameObject> objectTemplates;
		
		public GameObject makeGameObject(List<Property> properties) { //makes a game object with the given properties
			gameObject = new GameObject();
			for(Property prop : properties) {
				gameObject.addProperty(prop);
			}
			objectTemplates.add(gameObject);
		}	
	}

}
