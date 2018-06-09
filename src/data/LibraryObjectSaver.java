package data;

import authoring.GameObject;

/**
 * @author Edward Zhuang
 * Interface implemented by GameObjectManager allowing for saving of library objects.
 */
public interface LibraryObjectSaver {

	void saveUpdatedLibraryObject(GameObject gameObject);
	
}
