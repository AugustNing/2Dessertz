package authoring.groovy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.AuthBehavior;
import authoring.GameObject;

/** 
 * 
 * @author: Summer
 **/
public class ObjectGroovyGenerator {

	private GroovyCommandFactory factory;
	
	public ObjectGroovyGenerator() {
		factory = new GroovyCommandFactory();
	}
	
	/*
	 * THESE METHODS SHOULD BE REFACTORED. DUPLICATED CODE
	 */
	public List<String> generateGroovyFields(GameObject object) {
//		Map<Class<?>, List<Object>> objectFields = new HashMap<>();
//		objectFields = factory.getBehaviorFields().keySet().stream().forEach(clazz -> clazz.toString().contains(object.getBehaviors().stream().forEach(curr -> curr.getName())));
//		System.out.println("This worked");
//		System.out.println("hey");
//		return objectFields;
		List<Object> objectFields = new ArrayList<>();
		for(AuthBehavior curr : object.getBehaviors()) {
			for(Class<?> clazz : factory.getBehaviorFields().keySet()) {
				if(clazz.toString().contains(curr.getName())) {
					objectFields.addAll(factory.getBehaviorFields().get(clazz));
				}
			}
		}
		return cleanList(objectFields);
	}
	
	public List<String> generateGroovyMethods(GameObject object) {
		List<Object> objectMethods = new ArrayList<>();
		for(AuthBehavior curr : object.getBehaviors()) {
			for(Class<?> clazz : factory.getBehaviorMethods().keySet()) {
				if(clazz.toString().contains(curr.getName())) {
					objectMethods.addAll(factory.getBehaviorMethods().get(clazz));
				}
			}
		}
		return cleanList(objectMethods);
	}
	
	private List<String> cleanList(List<Object> toSteralize) {
		List<String> toReturn = new ArrayList<>();
		for(Object curr : toSteralize) {
			String[] st = curr.toString().split(" ");
			toReturn.add(st[st.length-1]);
		}
		return toReturn;
	}
}
