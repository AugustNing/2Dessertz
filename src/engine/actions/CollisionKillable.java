package engine.actions;

import engine.GameElement;
import engine.behaviors.Killable;
import engine.events.elementevents.ElementEvent;

public class CollisionKillable implements Action {

	@Override
	public void act(ElementEvent event, GameElement element) {
		Killable b = (Killable) element.getBehavior(Killable.class);
		b.reduceHealth(0.0);
	}

}