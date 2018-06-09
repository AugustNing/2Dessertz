package authoring.display.controllers;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthBehavior;
import authoring.Event;
import authoring.GameObject;
import authoring.display.popups.eventspopup.BehaviorsWindow;
import authoring.display.popups.eventspopup.EventsWindow;
import authoring.display.popups.eventspopup.GroovyWindow;
import authoring.display.popups.eventspopup.MFWindow;
import authoring.display.popups.eventspopup.ResponseWindow;
import authoring.display.popups.eventspopup.TriggerWindow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Class used to control the EventsPopup, and creates the event pop up pane
 * @author August Ning 
 *
 */
public class EventsPopupController extends PopupController {
	
	private List<GameObject> gos;
	private List<GameObject> allGos;   
	private EventsWindow eventsWindow;
	private TriggerWindow triggerWindow;
	private BehaviorsWindow behaviorsWindow;
	private MFWindow mfWindow;
	private ResponseWindow responseWindow;
	private GroovyWindow groovyWindow;

	/**
	 * @param currentGos 	The list of objects to be edited
	 * @param allGO 		The list of all game objects in the game
	 */
	public EventsPopupController(List<GameObject> currentGos, List<GameObject> allGO){
		gos = currentGos;
		allGos = allGO;
		initializeScreenComponents();
	}

	@Override
	protected void initializeScreenComponents() {
		eventsWindow = new EventsWindow(this, gos);
		triggerWindow = new TriggerWindow(this, gos, allGos);
		behaviorsWindow = new BehaviorsWindow(this, gos);
		mfWindow = new MFWindow(this, gos);
		responseWindow = new ResponseWindow(this, gos);
		groovyWindow = new GroovyWindow(this, gos);
	}

	protected void refreshView() {
		responseWindow.createVBox();
		behaviorsWindow.createVBox();
		mfWindow.createVBox();
		eventsWindow.updateEventList();
	}
	
	/**
	 * @return the current selected event
	 */
	public Event getCurrEvent() {
		return eventsWindow.getCurrEvent();
	}
	/**
	 * @return the current selected AuthBehavior
	 */
	public AuthBehavior getCurrBehavior() {
		return behaviorsWindow.getCurrBehavior();
	}
	/**
	 * @return if a valid event is selected or not
	 */
	public boolean validEvent() {
		return eventsWindow.validEvent();
	}
	/**
	 * @param stringToAdd 	The desired string to be added to the groovy windwo
	 * @param caller 		The panel that called the command
	 */
	public void concatenateString(String stringToAdd, String caller) {
		groovyWindow.concatenateString(stringToAdd, caller);
	}
	/**
	 * @return Creates the windows in the Event Popup
	 */
	public List<VBox> getWindows() {
		List<VBox> windows = new ArrayList<>();
		windows.add(eventsWindow);
		windows.add(triggerWindow);
		windows.add(responseWindow);
		windows.add(behaviorsWindow);
		windows.add(mfWindow);
		windows.add(groovyWindow);
		return windows;
	}
	
	/**
	 * Refresh the windows
	 */
	public void updateFromEvent() {
		triggerWindow.createVBox();
		responseWindow.createVBox();
		behaviorsWindow.createVBox();
		mfWindow.createVBox();
		eventsWindow.updateEventList();
		groovyWindow.createVBox();
	}
	
	/**
	 * Refresh the MFWindow
	 */
	public void updateFromBehavior() {
		mfWindow.createVBox();
	}
	
	/**
	 * Refreshes the response window
	 */
	public void updateResponseWindow() {
		responseWindow.createVBox();
	}
	
	/**
	 * @return the current game object that it will collide with
	 */
	public GameObject getCurrCollisionObject() {
		return triggerWindow.getCollideObject();
	}
	
	/**
	 * @param name the name of the selected current object
	 */
	public void setCurrObject(String name) {
		for (GameObject go : allGos) {
			if (go.getName().equals(name)) {
				behaviorsWindow.setCurrObject(go);
				return;
			}
		}
	}
	
}
