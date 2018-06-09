package engine;

import java.util.HashMap;
import java.util.Map;

import engine.actions.Action;
import engine.events.elementevents.ElementEvent;

public class EventResponder {
	private Map <ElementEvent, Action> responses;
	private GameElement parent;
	
	public EventResponder(GameElement ge) {
		responses = new HashMap<>();
		parent = ge;
	}
	
	public void addResponse(ElementEvent e, Action a) {
		responses.put(e, a);
	}
	
	public void respondTo(ElementEvent other) {
		for (ElementEvent e: responses.keySet()) {
			if (e.matchesEvent(other)) {
				responses.get(e).act(other, parent);
			}
		}
	}
	
	public Map<ElementEvent, Action> getResponses() {
		return responses;
	}
}
