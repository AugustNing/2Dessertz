package authoring;

import java.util.ArrayList;
import java.util.List;

import engine.actions.GroovyAction;

/**
 * Event is owned by a GameObject
 * Event class holds an event trigger and a list of responses to that trigger
 * 
 * @author: Summer
 */
public class Event {
	
	private List<GroovyAction> myResponses;
	private String eventType;
	private String myTrigger;

	public Event() {
		myResponses = new ArrayList<>();
	}
	
	/**
	 * returns the list of event responses
	 */
	public List<GroovyAction> getResponses() {
		return myResponses;
	}
	
	/**
	 * 
	 * @param type sets event type
	 */
	public void setEventType(String type) {
		eventType = type;
	}
	
	/**
	 * returns event type
	 */
	public String getEventType() {
		return eventType;
	}
	
	/**
	 * returns the trigger
	 */
	public String getTrigger() {
		return myTrigger;
	}
	
	/**
	 * sets the trigger to trigger
	 */
	public void setTrigger(String trigger) {
		myTrigger = trigger;
	}
	
	/**
	 * adds an event response, toAdd, to the list 
	 */
	public void addResponse(GroovyAction toAdd) {
		myResponses.add(toAdd);
	}
	
	/**
	 * deletes event response, toDelete, from the list of responses
	 */
	public void deleteResponse(GroovyAction toDelete) {
		myResponses.remove(toDelete);
	}
	
	/**
	 * prints the name of the event
	 */
	public String toString() {
		String[] name = eventType.split("\\.");
		String use = name[name.length-1];
		return use;
	}
	
	/**
	 * method is used to clone the event when an object is cloned 
	 */
	public Event clone() {
		Event event = new Event();
		event.setEventType(this.getEventType());
		event.setTrigger(this.getTrigger());
		for (GroovyAction er : this.myResponses) {
			event.addResponse(er.clone());
		}
		
		return event;
	}
}
