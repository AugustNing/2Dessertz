package authoring.display.popups.eventspopup;

import java.util.List;

import authoring.Event;
import authoring.GameObject;
import authoring.display.controllers.EventsPopupController;
import display.buttonevents.TriggerCollisionPress;
import display.buttonevents.TriggerKeyboardPress;
import display.buttons.GUIButton;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author August 
 * Panel that takes care of trigger of events from game objects
 *
 */
public class TriggerWindow extends VBox {

	private List<GameObject> gos;
	private List<GameObject> allGos;
	private EventsPopupController epuc;
	private Event currentEvent;
	
	private String kc;
	private String collideObject;

	private static final String KEYBOARD = "KeyInputEvent";
	private static final String COLLISION = "CollisionEvent";
	private static final String NOEVENT = "No event selected";
	private static final String NOTRIGGER = "No trigger required";
	private static final String TRIGGER = "Trigger";
	private static final String KEYBINDLABEL = "Edit Keybind";
	private static final String COLLISIONLABEL = "Edit Collision";
	private static final String CURRKEYBIND = "Current Keybind: ";
	private static final String CURRCOLLISION = "Current Collide Object: ";
	private static final int SPACING = 10;
	private static final int SIZE = 200;


	public TriggerWindow(EventsPopupController myEPUC, List<GameObject> myGos, List<GameObject> allGO) {
		gos = myGos;
		allGos = allGO;
		epuc = myEPUC;
		kc = "";
		collideObject = "";
		createVBox();
	}

	/**
	 * Creates the VBox for the trigger window
	 */
	public void createVBox() {
		this.getChildren().clear();
		this.setPadding(new Insets(SPACING));
		this.setSpacing(SPACING);
		this.setMinWidth(SIZE);
		Text title = new Text(TRIGGER);
		this.getChildren().add(title);
		assignCurrentEvent();
	}

	private void assignCurrentEvent() {
		currentEvent = epuc.getCurrEvent();
		try {
			if (currentEvent.getEventType().equals(KEYBOARD)) {
				this.getChildren().add(new GUIButton(0, 0, KEYBINDLABEL, new TriggerKeyboardPress(this)));
				this.getChildren().add(new Text(CURRKEYBIND + currentEvent.getTrigger()));
			} else if (currentEvent.getEventType().equals(COLLISION)) {
				this.getChildren().add(new GUIButton(0, 0, COLLISIONLABEL, new TriggerCollisionPress(this, allGos)));
				this.getChildren().add(new Text(CURRCOLLISION + currentEvent.getTrigger()));
			} else {
				this.getChildren().add(new Text(NOTRIGGER));
			}
		} catch (Exception e) {
			this.getChildren().add(new Text(NOEVENT));
		}
	}
	
	/**
	 * Sets the keybind to newkc
	 * @param newkc 	The new Keybind
	 */
	public void setKeyCode(String newkc) {
		kc = newkc;
		currentEvent.setTrigger(kc);
	}
	
	/**
	 * Returns the current keybind
	 * @return 	The current keybind
	 */
	public String getKeyCode() {
		return kc;
	}
	
	/**
	 * @return the name of the current GameObject
	 */
	public String currentObjectName() {
		return gos.get(0).getName();
	}
	
	/**
	 * Sets the collide object for the current event
	 * @param goName 	The name of the collide object
	 */
	public void setCollideObject(String goName) {
		collideObject = goName;
		currentEvent.setTrigger(collideObject);
	}
	
	/**
	 * @return Returns the game object that is set to the collide object
	 * returns null if the game object is never found, this should never happen
	 */
	public GameObject getCollideObject() {
		for (GameObject go : allGos) {
			if (go.getName().equals(collideObject)) {
				return go;
			}
		}
		return null;
	}
	/**
	 * update the collide object when called
	 */
	public void updateCollideObject() {
		epuc.updateResponseWindow();
	}
}
