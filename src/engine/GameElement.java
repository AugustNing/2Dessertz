package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import authoring.GameObject;
import engine.actions.Action;
import engine.behaviors.Behavior;
import engine.behaviors.MainCharacter;
import engine.behaviors.MandatoryBehavior;
import engine.events.elementevents.ElementEvent;
import engine.events.gameevents.GameEvent;
import engine.exceptions.TooManyBehaviorsException;

/**
 * @author Trishul
 * 
 */


public class GameElement {
	private Set<Behavior> behaviors;
	private EventResponder responder;
	private List<GameEvent> returnedGameEvents;
	
	public GameElement() {
		behaviors = new HashSet<>();
		responder = new EventResponder(this);
		returnedGameEvents = new ArrayList<>();
	}
	
	public GameElement(String name) {
		this();
		behaviors.add(new MandatoryBehavior(this, name));
	}
	
	/**
	 * getter for behaviors
	 * @return Set<Behavior> all behaviors of this GameElement
	 */
	public Set<Behavior> getAllBehaviors() {
		return behaviors;
	}
	
	/**
	 * getter for event responder
	 * @return EventResponder the EventRespondedr for this GameElement
	 */
	public EventResponder getResponder() {
		return responder;
	}
	

	/** 
	 * Adds the Behavior object containing the fields and methods that correspond to a specific behavior.
	 * Checks if there already exists a behavior object for this type. If there is, it removes it and adds 
	 * the incoming one. Can be used to help subclasses replace super classes
	 * @param behave
	 */
	public void addBehavior(Behavior behave) {
		List<Behavior> existing = behaviors.stream()
				.filter(b -> b.getClass().isAssignableFrom(behave.getClass()))
				.collect(Collectors.toList());
		
		if (existing.size() > 0) {
			// If the existing behavior is a superclass of the one being added, it is okay. Remove the superclass
			// and allow the subclass to replace it. Otherwise, throw an exception to alert the user this addition
			// shouldn't be happening
			if (existing.get(0).getClass().isAssignableFrom(behave.getClass())) {
				behaviors.remove(existing.get(0));
			} else {
				throw new TooManyBehaviorsException("Trying to add " + behave.getClass() + " to a GameElement that already has this behavior: " + existing.get(0));
			}
		}
		behaviors.add(behave);
	}
	

	/** 
	 * Get the behavior object of this GameElement corresponding to the class type specified
	 * @param behavior_type: class of the behavior object desired
	 * @return Behavior: this GameElement's behavior instance object 
	 */
	public Behavior getBehavior (Class<?> behavior_type) {
		try {
			return behaviors.stream()
					.filter(behavior -> behavior_type.isAssignableFrom(behavior.getClass()))
					.collect(Collectors.toList())
					.get(0);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(behavior_type + " does not exist for GameElement " + getIdentifier());
		}
	}
	
	
	/**
	 * Get the behavior object corresponding to the String which is the name of the class type.
	 * @param className: String name of class
	 * @return Behavior: This GameElement's instance of the behavior object requested
	 */
	public Behavior getBehavior (String className) {
		String qualifiedName = Behavior.class.getPackage().getName() + "." + className;
		try {
			Class<?> clazz = Class.forName(qualifiedName);
			return getBehavior(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not convert " + qualifiedName + " to a behavior type");
		}
	}
	
	
	/**
	 * Checks if this GameElement has a Behavior object of the requested type. Returns true if any behavior is in 
	 * this GameElement that is a subclass of the one being requested or is the class itself.
	 * @param behaviorType: Class of behavior type we are searching for.
	 * @return boolean: indicating if element has the behavior
	 */
	public boolean hasBehavior(Class<?> behaviorType) {
		return behaviors.stream()
			.filter(behavior -> behaviorType.isAssignableFrom(behavior.getClass()) 
					|| behaviorType.equals(behavior.getClass()))
			.collect(Collectors.toList()).size() > 0;
	}
	
	
	/**
	 * Checks if this GameElement has a Behavior object of the requested type in String form
	 * @param className: String name of class for which we are checking if this GameElement has a a specific behavior
	 * @return boolean: indicating if element has the behavior
	 */
	public boolean hasBehavior(String className) {
		String qualifiedName = MandatoryBehavior.class.getPackage().getName() + className;
		try {
			return hasBehavior(Class.forName(qualifiedName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not find" + qualifiedName + " class");
		}
	}
	

	/**
	 * Adds the ability for this game element to respond to an ElementEvent in a certain way
	 * @param e: ElementEvent that serves as the trigger event
	 * @param a: Action interface object defining how a GameElement changes in response to an event
	 */
	public void addEventResponse(ElementEvent e, Action a) {
		responder.addResponse(e, a);
	}
	
	
	/** 
	 * Returns this object's mandatory behavior instance. Implemented for convenience sake since behaviors frequently 
	 * need to access properties of the mandatory behavior like position
	 * @return MandatoryBehavior: MandatoryBehavior instance of this object.
	 */
	public MandatoryBehavior getMandatoryBehavior() {
		return (MandatoryBehavior) getBehavior(MandatoryBehavior.class);
	}

	
	/** Orders this GameElement instance to respond to an incoming element event. Returns a list of GameEvents the 
	 * GameElement's behaviors created in response to this element processing an ElementEvent. Resets the list of GameEvents
	 * to which behaviors add after processing each ElementEvent.
	 * @param event: ElementEvent object containing information about the event this element is supposed to respond to
	 * @return List<GameEvent>: GameEvent objects that are meant to operate on the entire GamePart
	 */
	public List<GameEvent> processEvent(ElementEvent event) {
		responder.respondTo(event);
		List<GameEvent> returnableEvents = returnedGameEvents.stream()
				.collect(Collectors.toList()); 
		returnedGameEvents = new ArrayList<>();
		return returnableEvents;
	}
	
	/**Allows behaviors to add GameEvent objects to this GameElement that will 
	 * be returned at the completion of processEvent
	 * @param gameevent: GameEvent object this GameElement is supposed to report back to the EventManager
	 */
	public void addGameEvent(GameEvent gameevent) {
		returnedGameEvents.add(gameevent);
	}
	
	
	/** Defines the method we will use to identify this game element. Should be done according the 
	 *  BasicGameElement behavior since every element in the game will implement that
	 * @return String identifier
	 */
	public String getIdentifier() {
		MandatoryBehavior el = (MandatoryBehavior) behaviors.stream()
				.filter(b -> b.getClass() == MandatoryBehavior.class)
				.collect(Collectors.toList()).get(0);
		return el.getName();
	}
	
	
	/**
	 * Generates a map that can be used to see all information about this GameElement by passing on the responsibility
	 * to report information to the GameElement's behaviors.
	 * @return Map<String, Object>: map of behavior instance variable names to their values
	 */
	public Map<String, Object> reportProperties() {
		List<Map<String, Object>> behaviorResponses = behaviors.stream()
				.map(behavior -> behavior.reportProperties())
				.collect(Collectors.toList());
		Map<String, Object> returning = new HashMap<String, Object>();
		for (Map<String, Object> map: behaviorResponses) {
			returning.putAll(map);
		}
		return returning;
	}
	
	public String toString() {
		Double locX = ((MandatoryBehavior)(getBehavior(MandatoryBehavior.class))).getX();
		Double locY = ((MandatoryBehavior)(getBehavior(MandatoryBehavior.class))).getY();
		return getIdentifier() + " at (" + locX + ", " + locY + ")";
	}
	
}
