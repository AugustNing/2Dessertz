package authoring;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import engine.exceptions.ErrorBox;

/**
 * @author Maddie Wilkinson
 *
 */
public class BehaviorFactory {

	public BehaviorFactory() {

	}

	public AuthBehavior makeBehavior(String behaviorName) {
		try {
			Class<?> clazz = Class.forName(behaviorName);
			Set<Property> properties = makeProperties(clazz);
			return new AuthBehavior(behaviorName, properties);
		} catch (ClassNotFoundException e) {
			new ErrorBox("Class Not Found Exception", "The behavior couldn't be made!");
		}
		return null;
	}

	private Set<Property> makeProperties(Class<?> clazz){
		List<Field> fields = getValidFields(clazz);
		Set<Property> properties = new HashSet<>();
		for(Field f : fields) {
			Property prop = new Property(f.getName(), f.getType());
			properties.add(prop);
		}
		return properties;
	}

	private List<Field> getValidFields(Class<?> clazz) {
		Class<?> superClazz = clazz.getSuperclass();
		List<Field> superFields = Arrays.asList(superClazz.getDeclaredFields());
		List<Field> subFields = Arrays.asList(clazz.getDeclaredFields());
		List<Field> allFields = new ArrayList<>(subFields);
		if(!superClazz.getSimpleName().equals("Behavior")) {
			allFields.addAll(superFields);
		}
		List<Field> validFields = new ArrayList<>();
		for(Field f : allFields) {
			String[] modifiers = Modifier.toString(f.getModifiers()).split(" ");
			if(modifiers[0].equals("private")) {
				validFields.add(f);
			}
		}
		return validFields;
	}

}
