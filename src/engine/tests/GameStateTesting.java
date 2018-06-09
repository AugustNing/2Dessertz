package engine.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import engine.EventManager2;
import engine.GamePart;
import engine.events.elementevents.KeyInputEvent;
import engine.events.elementevents.TimeEvent;
import javafx.scene.input.KeyCode;

class GameStateTesting {
	
	private GamePart part;
	private EventManager2 manager;
	
	@Before
	public void setUp() {
		part = new ModelGameState().getPart();
		manager = new EventManager2(part, null);
	}
	
	@Test
	public void testMove() {
		List<String> propertiesToPrint = Arrays.asList("elementName", "xPos", "yPos", "xVel", "yVel");
		part = new ModelGameState().getPart();
		manager = new EventManager2(part, null);
		
		printState(propertiesToPrint);
		manager.processElementEvent(new TimeEvent(1.0));
		printState(propertiesToPrint);
		
//		manager.processElementEvent(new TimeEvent(1.0));
//		printState(propertiesToPrint);
		
		manager.processElementEvent(new KeyInputEvent(KeyCode.UP));
		printState(propertiesToPrint);
		
		manager.processElementEvent(new TimeEvent(.1));
		printState(propertiesToPrint);
		
		manager.processElementEvent(new TimeEvent(.1));
		printState(propertiesToPrint);
		
	}
	
	public void printState(List<String> propertiesToPrint) {
		part.getElements().stream()
			.forEach(e -> {
				Map<String, Object> props = e.reportProperties();
				propertiesToPrint.stream() 
				.forEach((String prop) -> {
					if (props.containsKey(prop)) {
						//System.out.print(prop +" = " + props.get(prop) + ", ");
					}
				});
				System.out.println();
			});
		System.out.println();
	}
	

	
}
