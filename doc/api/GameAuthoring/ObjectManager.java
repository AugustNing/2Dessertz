public interface ObjectManager {

	public GameObject makeGameObject(); //makes a game object with no properties

	public GameObject makeGameObject(Property prop) { //makes a game object with the given property
		gameObject = new GameObject();
		gameObject.addProperty(prop);
	}
	public void deleteGameObject(GameObject gameObject); //deletes a game object

	public List<GameObject> getPlacedObjects(); //returns the list of all game objects currently placed in the game

	public List<GameObject> getObjectTemplates(); //returns the list of all game object "templates" available to use

}
