package engine.tests;

import java.util.Map;

import engine.GameElement;
import engine.behaviors.Behavior;
import engine.behaviors.Movable;

public class BehaviorTesting {
	
	public static void main(String[] args) {
		getValuesTest();
	}
	
	public static void getValuesTest() {
		GameElement ge = new GameElement();
		Behavior b = new Movable(ge);
		Map<String, Object> bProperties = b.reportProperties();
		for (String s: bProperties.keySet()) {
			//System.out.println(s + " -> " + bProperties.get(s));
		}
	}

}
