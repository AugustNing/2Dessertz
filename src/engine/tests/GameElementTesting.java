package engine.tests;

import engine.GameElement;
import engine.actions.TimeMovable;
import engine.behaviors.Behavior;
import engine.behaviors.MandatoryBehavior;
import engine.behaviors.Movable;
import engine.events.elementevents.TimeEvent;

public class GameElementTesting {
	
	public static void main(String[] args) {
		testEventHandling();
	}
	
	
	public static void addBehaviorTest() {
		GameElement ge = new GameElement();
		ge.addBehavior(new MandatoryBehavior(ge, "Mario", 100.0, 100.0));
		//System.out.println(ge.reportProperties());
		Behavior b = new Movable(ge);
		ge.addBehavior(b);
		//System.out.println(ge.reportProperties());
	}
	
	public static void testEventHandling() {
		GameElement ge = new GameElement();
		
		ge.addBehavior(new MandatoryBehavior(ge, "Mario", 100.0, 100.0));
		//System.out.println(ge.reportProperties());
		
		ge.addBehavior(new Movable(ge));
		
		ge.addEventResponse(new TimeEvent(0.0), new TimeMovable());
		//System.out.println(ge.reportProperties());
		
		ge.processEvent(new TimeEvent(5.0));
		//System.out.println(ge.reportProperties());
		
		ge.processEvent(new TimeEvent(15.0));
		//System.out.println(ge.reportProperties());
	}
}
