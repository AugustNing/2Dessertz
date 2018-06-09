package authoring.tests;

import java.util.HashSet;
import java.util.Set;

import authoring.AuthBehavior;
import authoring.Event;
import authoring.EventResponse;
import authoring.GameObject;
import authoring.Property;
import engine.behaviors.MovableCharacter;

public class EventTranslationTest {
	
	public GameObject getCharacter() {
		//moveable character that will jump on the w key
		GameObject go = new GameObject();
		go.setName("myObject");
		Property x = new Property("xVel", Double.class);
		x.setValue(0.0);
		Property y = new Property("yVel", Double.class);
		y.setValue(1.0);
		Set<Property> moveProps = new HashSet<>();
		moveProps.add(y);
		moveProps.add(x);
 		AuthBehavior movableCharacter = new AuthBehavior(MovableCharacter.class.getCanonicalName(), moveProps);
		go.addBehavior(movableCharacter);
		Property xPos = new Property("xPos", Double.class);
		xPos.setValue(5.0);
		Property yPos = new Property("yPos", Double.class);
		yPos.setValue(5.0);
		Property name = new Property("name", String.class);
		name.setValue("myObject");
		Set<Property> mandatoryProps = new HashSet<>();
		mandatoryProps.add(xPos);
		mandatoryProps.add(yPos);
		mandatoryProps.add(name);
		AuthBehavior mandatory = new AuthBehavior("MandatoryBehavior", mandatoryProps);
		go.addBehavior(mandatory);
		Event keyInput = new Event();
		keyInput.setEventType("engine.events.elementevents.KeyInputEvent");
		keyInput.setTrigger("w");
		
		//this will be changed to GroovyAction 
		EventResponse response = new EventResponse();
		response.setMyContent("myObject.getBehaviors('MovableCharacter').jump()");
		keyInput.addResponse(response);
		go.addEvent(keyInput);
		return go;
	}
}
