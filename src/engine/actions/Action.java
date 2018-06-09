package engine.actions;

import engine.GameElement;
import engine.events.elementevents.ElementEvent;

@FunctionalInterface
public interface Action {
	public void act(ElementEvent e, GameElement ge);
}
