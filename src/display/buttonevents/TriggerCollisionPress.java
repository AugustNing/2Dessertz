package display.buttonevents;

import java.util.List;

import authoring.GameObject;
import authoring.display.popups.eventspopup.TWCPopup;
import authoring.display.popups.eventspopup.TriggerWindow;

/**
 * @author August Ning
 * 
 * opens up the pop up window to edit collisions
 *
 */
public class TriggerCollisionPress implements ButtonEvent {
	
	private TriggerWindow tw;
	private List<GameObject> allGos;

	/**
	 * @param intw the trigger window that 
	 * @param inGos the a list of all the game objects in the current game scene
	 */
	public TriggerCollisionPress(TriggerWindow intw, List<GameObject> inGos) {
		tw = intw;
		allGos = inGos;
	}

	@Override
	public void pressed() {
		new TWCPopup(tw, allGos);
	}

}
