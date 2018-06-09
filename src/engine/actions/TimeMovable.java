package engine.actions;

import engine.GameElement;
import engine.behaviors.Movable;
import engine.events.elementevents.ElementEvent;
import engine.events.elementevents.TimeEvent;

public class TimeMovable implements Action{
	
	@Override
	public void act(ElementEvent event, GameElement element) {
		
		TimeEvent te = (TimeEvent) event;
		Movable b = (Movable) element.getBehavior(Movable.class);
		b.move(te.getTime());

	}
}
