package engine.actions;

import engine.GameElement;
import engine.behaviors.Killable;
import engine.events.elementevents.ElementEvent;
import engine.events.elementevents.TimeEvent;

public class TimeKillable implements Action{

	@Override
	public void act(ElementEvent event, GameElement element) {
		TimeEvent te = (TimeEvent) event;
		Killable b = (Killable) element.getBehavior(Killable.class);
		b.decayHealth(te.getTime());
	}
}
