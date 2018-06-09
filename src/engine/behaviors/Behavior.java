package engine.behaviors;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.GameElement;

public abstract class Behavior {

	private GameElement parent;
	
	public Behavior(GameElement ge) {
		parent = ge;
		addDefaultBehavior();
		addRequiredBehaviors();
	}
	
	
	public Map<String, Object> reportProperties() {
		Map <String, Object> returnValues = new HashMap<>();
		
		Class<?> objClass = this.getClass();
	    Field[] fields = objClass.getDeclaredFields();
	    for(Field field : fields) {
	    		field.setAccessible(true);
	        String name = field.getName();
	        Object value;
			try {
				value = field.get(this);
				returnValues.put(name,  value);
			} catch (IllegalArgumentException | IllegalAccessException  e) {
				e.printStackTrace();
			}
	    }
	    return returnValues;
	}
	
	protected GameElement getParent() {
		return parent;
	}
	
	protected void addDefaultBehavior() {
		// Do Nothing if no default behavior 
		// (in this case behavior implies adding things to the EventResponder
	}
	
	// Method to allow adding required behaviors if they do not already 
	// exist for the parent GameElement 
	protected void addBehaviorsIfNotExisting (List<Class<? extends Behavior>> list) {
		list.stream()
		.forEach(behavior -> {
			if (!(getParent().hasBehavior(behavior))) {
				try {
					Constructor<? extends Behavior> construct = behavior.getConstructor(GameElement.class);
//					System.out.println("Behavior: From addBehaviorsIfNotExisting");
					getParent().addBehavior(construct.newInstance(getParent()));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					throw new RuntimeException("Cannot add " + behavior + " to " + getParent().getIdentifier() + " which does not already have it");
				} 
			}
		});
	}
	
	protected void addRequiredBehaviors() {
		// Do Nothing in default case
		// Calls addBehaviorsIfNotExsiting in subclasses of Behavior with a list of required behaviors
	}
	
	
}
