package data;

import authoring.display.objectinfoboxes.LibraryObjectInfoBox;

/**
 * @author Edward Zhuang
 * Observable interface implemented by GameObjectManager allowing ObjectInfoPanel to retrieve LibraryObjectInfoBox.
 */
public interface LibraryObservable {
	LibraryObjectInfoBox getLibraryObjectInfoBox();
}
