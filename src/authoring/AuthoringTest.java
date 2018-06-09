package authoring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

class AuthoringTest {

	@Test
	void testGameObjectCopy() {
		AuthBehavior behave = new AuthBehavior("Movable", new HashSet<Property>());
		GameObject go = new GameObject(behave);
		GameObject goCopy = new GameObject(go);
		assertEquals("Moveable", goCopy.getBehavior("Movable").getName());
	}
	
	@Test
	void testEventSetTrigger() {
		Event newEvent = new Event();
		newEvent.setTrigger("Collision with block");
		assertEquals("Collision with block", newEvent.getTrigger());
	}
	
	@Test
	void testEventsAddResponse() {
		Event myEvent = new Event();
		EventResponse myResponse = new EventResponse();
		myResponse.setMyContent("move right");
		myEvent.addResponse(myResponse);
		assertEquals("move right", myEvent.getResponses().get(0).getMyContent());
	}

}
