package authoring.display.authoringuicomponents;

import java.util.Observable;
import java.util.Observer;

import authoring.ObjectInfoObservable;
import authoring.display.controllers.ObjectInfoController;
import authoring.display.objectinfoboxes.GameObjectInfoBox;
import authoring.display.objectinfoboxes.LibraryObjectInfoBox;
import data.LibraryObservable;
import data.LibraryObserver;

/**
 * 
 * @author Edward Zhuang
 * Front end component which holds two types of ObjectInformationBoxes.
 */
public class ObjectInfoPanel extends AuthoringUIComponent implements Observer, LibraryObserver {

	private ObjectInfoController controller;
	private LibraryObservable libraryObservable;


	public ObjectInfoPanel(ObjectInfoController controller) {
		this.controller = controller;
	}
	
	public void setLibraryObservable(LibraryObservable libraryObservable) {
		this.libraryObservable = libraryObservable;
	}

	@Override
	protected void generateComponent() {
	}

	/**
	 * updates the panel with a GameObjectInfoBox if a current object is set in the backend GameScene.
	 * @param arg0 Observable
	 * @param arg1 Object
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		ObjectInfoObservable observable = (ObjectInfoObservable) arg0;
		if (observable.getCurrentGameObject() != null) {
		GameObjectInfoBox gameObjectInfoBox = new GameObjectInfoBox(observable.getCurrentGameObject(), observable.getInstances(), controller);
		gameObjectInfoBox.initializeVBox();
		gameObjectInfoBox.addToBorderPane(getBorderPane());
		} else {
			getBorderPane().getChildren().clear();
		}
	}

	/**
	 * updates the panel with a LibraryObjectInfoBox if a library object is selected
	 */
	@Override
	public void notifyObserver() {
		LibraryObjectInfoBox libraryObjectInfoBox = libraryObservable.getLibraryObjectInfoBox();
		libraryObjectInfoBox.initializeVBox();
		libraryObjectInfoBox.addToBorderPane(getBorderPane());
	}

	@Override
	protected void initializeFXComponents() {
	}

	@Override
	protected void mapFXActions() {
	}

}
