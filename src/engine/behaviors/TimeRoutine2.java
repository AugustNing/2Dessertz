package engine.behaviors;

import java.util.HashMap;
import java.util.Map;

import authoring.groovy.GroovyMethod;
import engine.GameElement;
import engine.actions.Action;
import engine.events.elementevents.TimeEvent;

public class TimeRoutine2 extends Behavior{

	private Map<Action, Double> routineTimes;
	private Map<Action, Double> timeRemaining;
	
	public TimeRoutine2(GameElement ge) {
		super(ge);
		routineTimes = new HashMap<>();
		timeRemaining = new HashMap<>();
	}
	
	public void addRoutine(Double time, Action routine) {
		routineTimes.put(routine, time);
		timeRemaining.put(routine, time);
	}
	
	@GroovyMethod
	@Override
	protected void addDefaultBehavior() {
		getParent().addEventResponse(new TimeEvent(0.0), (e, a) -> {
			TimeEvent te = (TimeEvent) e;
			for (Action routine:  routineTimes.keySet()) {
				if (!(timeRemaining.containsKey(routine))) {
					timeRemaining.put(routine, routineTimes.get(routine));
				}
				timeRemaining.put(routine, timeRemaining.get(routine) - te.getTime());
				if (timeRemaining.get(routine) < 0) {
					routine.act(new TimeEvent(0.0), getParent());
					timeRemaining.put(routine, routineTimes.get(routine));
				}
			}
		});
	}
	
}
