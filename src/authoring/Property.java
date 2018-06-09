package authoring;

/** 
 * 
 * @author: Summer and Maddie
 **/
public class Property {

	private Object myValue;
	private String myName;
	private Class<?> myValueType;
	
	public Property(String name, Class<?> clazz) {
		myName = name;
		setValueType(clazz);
	}
	
	//sets the value of the property
	public void setValue(Object value) {
		try {
			myValue = value;
		}
		catch(Exception e) { 
			throw(new RuntimeException("value entered is not the correct type"));
		}
	}
	
	//returns the value of the property
	public Object getValue() {
		return myValue;
	}
	
	//returns the name of the property
	public String getName() {
		return myName;
	}
	
	private void setValueType(Class<?> clazz) {
		myValueType = clazz;
	}
	
	public Class<?> getValueType() {
		return myValueType;
	}
	
	//returns the name of the property
	public String toString() {
		return myValueType + " " + myName + " = " + myValue;
	}
	
}
