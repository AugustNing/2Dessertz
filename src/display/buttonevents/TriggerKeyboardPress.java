package display.buttonevents;

import authoring.display.popups.eventspopup.TWKPopup;
import authoring.display.popups.eventspopup.TriggerWindow;

/**
 * @author August Ning
 * Opens up the keyboard window for keyboard event triggers
 *
 */
public class TriggerKeyboardPress implements ButtonEvent {
	
	private TriggerWindow tw;

	/**
	 * @param intw the trigger window where it is called from
	 */
	public TriggerKeyboardPress(TriggerWindow intw) {
		tw = intw;
	}

	@Override
	public void pressed() {
		new TWKPopup(tw);
	}

}
