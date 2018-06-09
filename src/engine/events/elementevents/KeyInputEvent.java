package engine.events.elementevents;

import javafx.scene.input.KeyCode;

public class KeyInputEvent extends ElementEvent {
	String code;
	
	public KeyInputEvent(KeyCode co) {
		this.code = co.getName();
	}
	
	public KeyCode getKeyCode() {
		return KeyCode.getKeyCode(code);
	}

	@Override
	public String toString() {
		return "Key Input Event: " + code.toString() + " key pressed";
	}

	@Override
	public boolean matchesEvent(ElementEvent other) {
		if (other instanceof KeyInputEvent) {
			KeyInputEvent oth = (KeyInputEvent) other;
			return getKeyCode().compareTo(oth.getKeyCode()) == 0;
		}
		return false;
	}
	
	@Override
	public String getTriggerString() {
		return code;
	}
	
	
}
