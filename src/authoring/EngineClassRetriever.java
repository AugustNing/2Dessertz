package authoring;

import java.util.Set;

import org.reflections.Reflections;


/** 
 * @author: Summer
 **/
public class EngineClassRetriever {
	
	public EngineClassRetriever() {}
	
	/**
	 * @param superClass is the super class of the target classes
	 * @param packageName is the packageName to search for subclasses
	 * @returns a set of class names
	 */
	public Set<?> getClasses(Class<?> superClass, String packageName) {
		Reflections reflections = new Reflections(packageName);
		return reflections.getSubTypesOf(superClass);
	}
	
//    public static void main(String[] args){
//		EngineClassRetriever events = new EngineClassRetriever();
//		System.out.println(events.getClasses(ElementEvent.class, "engine.events.elementevents"));
//    }
	
}
