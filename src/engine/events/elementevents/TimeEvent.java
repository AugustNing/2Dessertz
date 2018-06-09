package engine.events.elementevents;

public class TimeEvent extends ElementEvent {
	
	private Double timeElapsed;
	
	public TimeEvent(Double t) {
		timeElapsed = t;
	}
	
	public Double getTime() {
		return timeElapsed;
	}
	
	@Override
	public String toString() {
		return "Time Event: " + timeElapsed + " seconds elapsed";
	}

	@Override
	public boolean matchesEvent(ElementEvent other) {
		return other instanceof TimeEvent;
	}
	
	@Override
	public String getTriggerString() {
		return "";
	}

}
