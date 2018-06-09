package authoring.display.popups;

import java.util.List;

import authoring.GameObject;
import authoring.display.controllers.BehaviorPopupController;
import javafx.scene.layout.HBox;

/**
 * @author Maddie Wilkinson
 *
 */
public class BehaviorPopup extends Popup {

	private static final int xSize = 600;
	private static final int ySize = 600;
	private static final int DEFAULT_SPACING = 10;
	private BehaviorPopupController myController;
	private List<GameObject> myGameObjects;
	private HBox myHBox;
	
	/**
	 * @param currentObjects
	 */
	public BehaviorPopup(List<GameObject> currentObjects) {
		super();
		myHBox = new HBox(DEFAULT_SPACING);
		myGameObjects = currentObjects;
		myController = new BehaviorPopupController(myGameObjects);
		initializeRoot();
		generatePopup();
		open(xSize, ySize);
	}
	
	private void initializeRoot() {
		myHBox.getChildren().addAll(myController.getPanels());
	}

	@Override
	protected void generatePopup() {
		getPane().getChildren().add(myHBox);
	}

	@Override
	protected void mapButtons() {
		//no buttons being mapped
	}
}
