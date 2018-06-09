package engine.actions;

import engine.GameElement;
import engine.behaviors.EntrancePortal;
import engine.events.elementevents.ElementEvent;
import engine.events.elementevents.TimeEvent;

public class TimeDisablePortal implements Action{

	@Override
	public void act(ElementEvent event, GameElement element) {
		TimeEvent te = (TimeEvent) event;
		EntrancePortal ep = (EntrancePortal) element.getBehavior(EntrancePortal.class);
		ep.reduceDisableTime(te.getTime());
	}
}
