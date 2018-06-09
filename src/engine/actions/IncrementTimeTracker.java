package engine.actions;

import engine.GameElement;
import engine.behaviors.TimeTracker;
import engine.events.elementevents.ElementEvent;
import engine.events.elementevents.TimeEvent;

public class IncrementTimeTracker implements Action{

	@Override
	public void act(ElementEvent e, GameElement ge) {
		//ArrayList<GameEvent> events = new ArrayList<GameEvent>();
		TimeEvent te = (TimeEvent) e;
		TimeTracker tt = (TimeTracker) ge.getBehavior(TimeTracker.class);
		tt.incrementTimePass(te.getTime());
	}
	
}
