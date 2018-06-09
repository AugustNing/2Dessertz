package engine.authouringconversion;

import authoring.Event;
import engine.GameElement;
import engine.behaviors.MandatoryBehavior;
import engine.events.elementevents.CollisionEvent;
import engine.events.elementevents.ElementEvent;
import engine.events.elementevents.KeyInputEvent;
import engine.events.elementevents.MouseInputEvent;
import engine.events.elementevents.TimeEvent;
import javafx.scene.input.KeyCode;

public class EventConverter {
	
	public ElementEvent authEvent2ElementEvent(GameElement ge, Event authEvent) {
		String eventTypeClass = authEvent.getEventType();
		if (eventTypeClass.contains("CollisionEvent")) {
			return toCollisionEvent(ge, authEvent);
		} else if (eventTypeClass.contains("TimeEvent")) {
			return toTimeEvent(authEvent);
		} else if (eventTypeClass.contains("KeyInputEvent")) {
			return toKeyInputEvent(authEvent);
		} else if (eventTypeClass.contains("MouseInputEvent")) {
			return toMouseInputEvent(authEvent);
		}
		return null;
	}
	
	private TimeEvent toTimeEvent(Event authEvent) {
		return new TimeEvent (0.0);
	}
	
	private CollisionEvent toCollisionEvent(GameElement ge, Event authEvent) {
		GameElement newGameElement = new GameElement();
		MandatoryBehavior mand = new MandatoryBehavior(newGameElement, authEvent.getTrigger());
		newGameElement.addBehavior(mand);
		return new CollisionEvent(ge, CollisionEvent.ALL_SIDES, newGameElement, CollisionEvent.ALL_SIDES);
	}
	
	private KeyInputEvent toKeyInputEvent(Event authEvent) {
		return new KeyInputEvent(KeyCode.getKeyCode(authEvent.getTrigger().toUpperCase()));
	}
	
	private MouseInputEvent toMouseInputEvent(Event authEvent) {
		return new MouseInputEvent(0.0, 0.0);
	}
}
