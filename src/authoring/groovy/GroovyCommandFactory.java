package authoring.groovy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.Scanner;

import authoring.AuthBehavior;

/** 
 * 
 * @author: Summer
 **/
public class GroovyCommandFactory {

	private final String PACKAGE_NAME = "engine.behaviors";
	private Map<Class<?>, List<Field>> behaviorFields;
	private Map<Class<?>, List<Method>> behaviorMethods;
	
	public static final Class<? extends Annotation> METHOD_ANNOTATION = GroovyMethod.class;
	public static final Class<? extends Annotation> FIELD_ANNOTATION = GroovyField.class;
	
	public GroovyCommandFactory() {
		behaviorMethods = makeMethodMap(new MethodAnnotationsScanner());
		behaviorFields = makeFieldMap(new FieldAnnotationsScanner());
	}
	
	private Map<Class<?>, List<Method>> makeMethodMap(Scanner scanner) {
		Reflections reflections = new Reflections(PACKAGE_NAME, scanner);
		return findMethods(reflections).stream().collect(Collectors.groupingBy( a -> a.getDeclaringClass()));
	}
	
    private Collection<Method> findMethods(Reflections reflections) {
    		return reflections.getMethodsAnnotatedWith(METHOD_ANNOTATION);
    }
    
    private Map<Class<?>, List<Field>> makeFieldMap(Scanner scanner) {
    		Reflections reflections = new Reflections(PACKAGE_NAME, scanner);
		return findFields(reflections).stream().collect(Collectors.groupingBy( a -> a.getDeclaringClass()));
    }
    
    private Collection<Field> findFields(Reflections reflections) {
		return reflections.getFieldsAnnotatedWith(FIELD_ANNOTATION);
    }
    
    public Map<Class<?>, List<Field>> getBehaviorFields(){
    		return behaviorFields;
    }
    
    public List<Field> getFields(AuthBehavior behavior) {
    		for (Class<?> c : behaviorFields.keySet()) {
    			if (navigateMap(behavior.getName(), c)) {
    				return behaviorFields.get(c);
    			}
    		}
    		return null;
    }
    
    public List<Method> getMethods(AuthBehavior behavior) {
		for (Class<?> c : behaviorMethods.keySet()) {
			if (navigateMap(behavior.getName(), c)) {
				return behaviorMethods.get(c);
			}
		}
		return null;
    }
    
    private boolean navigateMap(String s, Class<?> c) {
    		if (c.toString().contains(s)) {
    			return true;
    		}
    		return false;
    }
    
    public Map<Class<?>, List<Method>> getBehaviorMethods(){
    		return behaviorMethods;
    }
}
