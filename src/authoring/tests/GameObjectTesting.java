package authoring.tests;

import java.util.HashSet;

import authoring.AuthBehavior;
import authoring.GameObject;
import authoring.Property;

public class GameObjectTesting {

	public GameObjectTesting() {
		
	}
	
	public static void main(String[] args) {
		AuthBehavior b = new AuthBehavior("Behavior!", new HashSet<Property>());

//		Property prop0 = new Property("awesomeness", new Double(1000000));
//		Property prop1 = new Property("coolness", new Double(10));
		GameObject go = new GameObject(b);
		System.out.println(go.getBehaviors());
//		go.getBehavior("Behavior!").addProperty(prop0);
//		go.getBehavior("Behavior!").addProperty(prop1);

		System.out.println(go.getBehaviors());
	}

}
