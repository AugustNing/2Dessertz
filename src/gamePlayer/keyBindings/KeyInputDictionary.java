package gamePlayer.keyBindings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import engine.EngineInterface;
import javafx.scene.input.KeyCode;

/**
 * Keeps track of what keys are matched to which other keys
 * 
 * @author jeffreyli
 *
 */
public class KeyInputDictionary {
	private Map<KeyCode, KeyCode> dictionary;
	private EngineInterface engine;

	public KeyInputDictionary(EngineInterface engine) {
		dictionary = new HashMap<KeyCode, KeyCode>();
		this.engine = engine;
	}

	public void replaceKey(KeyCode input, KeyCode mapTo, KeyCode oldInput) {

		dictionary.remove(oldInput);
		dictionary.put(input, mapTo);
	}

	public boolean containsKey(KeyCode input) {
		return dictionary.containsKey(input);
	}

	public void handleAction(KeyCode input) {
		if (dictionary.containsKey(input)) {
			engine.handleKeyInput(dictionary.get(input));
		} else {
			engine.handleKeyInput(input);
		}
	}

	public void setGame(EngineInterface engine) {
		this.engine = engine;

	}

	public Set<KeyCode> getKeySet() {
		return dictionary.keySet();
	}

	public KeyCode getKey(KeyCode k) {
		return dictionary.get(k);
	}

	public void removeKey(KeyCode k) {
		dictionary.remove(k);

	}

	public KeyCode getKeyForValue(KeyCode keyCode) {
		for (KeyCode key : dictionary.keySet()) {
			if (dictionary.get(key) == keyCode) {
				return key;
			}
		}
		return null;
	}
	//
}
