package engine.authouringconversion;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import authoring.AuthBehavior;
import authoring.Event;
import authoring.GameObject;
import authoring.GameScene;
import authoring.Property;
import engine.EventResponder;
import engine.GameElement;
import engine.GamePart;
import engine.actions.Action;
import engine.actions.GroovyAction;
import engine.behaviors.Behavior;
import engine.behaviors.MandatoryBehavior;
import engine.behaviors.Shooter;
import engine.events.elementevents.ElementEvent;


/** 
 * Converts between authoring environment objects and engine objects
 * including 2 way between
 * GameScene <-> GamePart
 * GameObject <-> GameElement
 * AuthBehavior <-> Behavior
 * 
 * @author: Trishul
 **/

public class Converter2 {
	
	/*
	 * converts an authoring environment GameObject to an
	 * engine environment GameElement
	 */
	Printer printer = new Printer();
	public static final String BG_IMAGE_NAME = "BackgroundImage";
	
	public GameElement gameObject2GameElement(GameObject go) {
		GameElement ge = new GameElement();
		ge.addBehavior(authBehavior2Behavior(go.getBehavior(MandatoryBehavior.class.getCanonicalName()), ge));
		setBehavior2AuthorValues(go.getBehavior(MandatoryBehavior.class.getCanonicalName()), ge);
		
		List<AuthBehavior> remainingBehaviors = go.getBehaviors().stream()
				.filter(b -> !(b.getName().contains("Mandatory")))
				.collect(Collectors.toList());
		
		for (AuthBehavior authB: remainingBehaviors) {
			ge.addBehavior(authBehavior2Behavior(authB, ge));
		}
		
//		if (go.getName().contains("lakitu")) {
//			printer.printGameObject(go);
//		}

		for (AuthBehavior authB: go.getBehaviors()) {
			setBehavior2AuthorValues(authB, ge);
		}
		addResponsesAuth2Engine(ge, go);	
		System.out.println(ge.getIdentifier());
		System.out.println(ge.getResponder().getResponses());
		return ge;
	}
	
	
	public GameObject gameElement2GameObject(GameElement ge) {
		GameObject go = new GameObject();
		// Remove the default Authoring MandatoryBehavior
		go.removeBehavior(go.getMandatoryBehavior());
		// Translate MandatoryBehavior to AuthoringBehavior and add it
		
		go.addBehavior(behavior2AuthBehavior(ge.getBehavior(MandatoryBehavior.class))); 
		//go.setName(ge.getIdentifier());
		for (Behavior engB: ge.getAllBehaviors()) {
			if (engB.getClass().equals(MandatoryBehavior.class)) { continue;}
			go.addBehavior(behavior2AuthBehavior(engB));
		}
		
		addResponsesEngine2Auth(ge, go);
		return go;
	}
	

	public GamePart gameScene2GamePart(GameScene scene) {
		printer.printScene(scene);
		GamePart part = new GamePart(scene.getName(), scene.getId());
		part.addGameElement(getBackgroundElement(scene));
		for (GameObject go: scene.getMyObjects()) {
			part.addGameElement(gameObject2GameElement(go));
		}
		part.addAudio(scene.getAudioName());
		printer.printPart(part);
//		throw new RuntimeException();
		return part;
	}
	
	public GameElement getBackgroundElement(GameScene scene) {
		GameElement ge = new GameElement();
		scene.setBackgroundImageName();
		System.out.println("Converter2   " + scene.getBackgroundImageName());
		MandatoryBehavior mand = new MandatoryBehavior(ge, BG_IMAGE_NAME, scene.getBackgroundImageName(), 0.0, 0.0);
		ge.addBehavior(mand);
		return ge;
	}
	
	
	public GameScene gamePart2GameScene(GamePart part) {
		GameScene scene = new GameScene(part.getGamePartID(), "0");
		scene.setBackgroundImageName();
		for (GameElement element: part.getElements().stream()
				.filter(el -> !el.getIdentifier().equals(BG_IMAGE_NAME)).collect(Collectors.toList())) {
			scene.addObject(gameElement2GameObject(element));
		}
		scene.setAudioName(part.getBackgroundAudio());
		return scene;
	}
	
	/*
	 * Constructs all behaviors defined in authoring in the game element
	 */
	public Behavior authBehavior2Behavior (AuthBehavior authB, GameElement ge) {
		Behavior newEngBehavior;
		
		try {
			Constructor<?> use = getConstructor(Class.forName(authB.getName()));
			newEngBehavior = (Behavior) use.newInstance(ge);
		} catch (ClassNotFoundException|InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
			throw (new RuntimeException("Failed to instantiate newEngBehavior from " + authB.getName()));
		}
		return newEngBehavior;
	}
	
	/*
	 * Sets the behavior properties of a game element to what the author specified
	 */
	
	private void setBehavior2AuthorValues (AuthBehavior authB, GameElement ge) {
		String[] parts = authB.getName().split("\\.");
		String className = parts[parts.length - 1];
		Behavior behavior = ge.getBehavior(className);
		Class<?> newEngBehaviorClass = behavior.getClass();
		if (newEngBehaviorClass.equals(Shooter.class)) {
			printer.printEngineBehavior(behavior);
		}
		for (Field f: newEngBehaviorClass.getDeclaredFields()) {
			if (Modifier.isPublic(f.getModifiers())) {continue;}
			f.setAccessible(true);
			try {
				if (authB.getProperty(f.getName()).getValue() != null) {
					f.set(behavior, authB.getProperty(f.getName()).getValue());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
//		if (newEngBehaviorClass.equals(Shooter.class)) {
//			System.out.println("Converter2: Post Property Setting ");
//			printer.printEngineBehavior(behavior);
//			printer.printGameObject((GameObject)(behavior.reportProperties().get("toShoot")));
//			System.out.println("Converter2: parent hash code:" + ge.hashCode());
//		}
		
	}

	/*
	 * Gets the single GameElement constructor for a BehaviorConstructor
	 */
	private Constructor<?> getConstructor(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getConstructors();
		return Arrays.stream(constructors)
		.filter(cons -> cons.getParameterCount() == 1)
		.filter(cons -> cons.getParameterTypes()[0].equals(GameElement.class))
		.collect(Collectors.toList())
		.get(0);
	}
	
	/*
	 * Method that converts a GameElement Behavior into an Authoring Behavior
	 */
	public AuthBehavior behavior2AuthBehavior(Behavior engB) {
		AuthBehavior authB = new AuthBehavior(engB.getClass().getCanonicalName(), new HashSet<>());
		Map<String, Object> engBproperties = engB.reportProperties();
		for (Entry<String,Object> entry: engBproperties.entrySet()) {
			Property newProp = new Property(entry.getKey(), entry.getKey().getClass());
			newProp.setValue(entry.getValue());
			authB.addProperty(newProp);
		}
		return authB;
	}
	

	public void addResponsesEngine2Auth(GameElement ge, GameObject go) {
		Map<ElementEvent, Action> responses = new HashMap<>();
		responses.putAll(ge.getResponder().getResponses());
		for (Entry<ElementEvent, Action> response: responses.entrySet()) {
			if (!(response.getValue() instanceof GroovyAction)) {
				continue;
			}
			GroovyAction groovyAction = (GroovyAction) response.getValue();
			Event authEvent = new Event();
			authEvent.setEventType(response.getKey().getClass().getCanonicalName());
			authEvent.setTrigger(response.getKey().getTriggerString());
			authEvent.addResponse(groovyAction);
			go.addEvent(authEvent);
		}
	}

	
	/*
	 * Moves GroovyAction from authoring environment to Engine
	 */
	public void addResponsesAuth2Engine(GameElement ge, GameObject go) {
		EventResponder responder = ge.getResponder();
		for (Event event: go.getEvents()) {
			ElementEvent ee = authEvent2ElementEvent(ge, event);
			for (GroovyAction response: event.getResponses()) {
				responder.addResponse(ee, response);
			}
		}
	}
	
	/*
	 * Private helper method to get the ElementEvent type from an Event
	 */
	private ElementEvent authEvent2ElementEvent(GameElement ge, Event authE) {
		EventConverter eventConverter = new EventConverter();
		ElementEvent retEvent = eventConverter.authEvent2ElementEvent(ge, authE);
		return retEvent;
	}
	
	
}
