package engine.behaviors;

import authoring.groovy.GroovyMethod;
import engine.GameElement;
import engine.actions.IncrementTimeTracker;
import engine.events.elementevents.TimeEvent;

public class TimeTracker extends Behavior{
	
	private Double timePassed;
	
	public TimeTracker(GameElement ge) {
		super(ge);
		timePassed = 0.0;
	}
	
	public double getTimePassed() {
		return timePassed;
	}
	
	@GroovyMethod
	public boolean isMultipleOf(int i) {
		return ((int) Math.ceil(timePassed) % i)==0 && timePassed>1;
	}
	
	@GroovyMethod
	public void incrementTimePass(Double time) {
		timePassed += time;
	}
	
	@Override
	protected void addDefaultBehavior() {
		getParent().addEventResponse(new TimeEvent(0.0), new IncrementTimeTracker());
	}
}
