package authoring;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Behavior is a characteristic that is held by a GameObject It includes
 * characteristics such as movable, shoots, can die, etc.
 * 
 * @author: Summer
 **/
public class AuthBehavior {

	private Set<Property> myProperties;
	private String myName;

	public AuthBehavior(String name, Set<Property> properties) {
		myName = name;
		myProperties = properties;
	}

	/**
	 * adds a property to the Behavior
	 */
	public void addProperty(Property property) {
		myProperties.add(property);
	}

	/**
	 * returns all the properties
	 */
	public Set<Property> getProperties() {
		return myProperties;
	}

	public String getDisplayName() {
		String[] splitName = myName.split("\\.");
		return splitName[splitName.length - 1];
	}

	/**
	 * returns all the properties
	 **/
	public Property getProperty(String propName) {
		for (Property p : myProperties) {

			if (p.getName().equals(propName)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Returns the full package name of the Behavior (e.g. engine.behaviors.Movable)
	 */
	public String getName() {
		return myName;
	}

	/**
	 * to string method for AuthBehavior
	 */
	public String toString() {
		return getDisplayName();
	}

	/**
	 * clones an AuthBehavior
	 * used for duplicating gameObjects
	 */
	public AuthBehavior clone() {
		AuthBehavior ab = new AuthBehavior(this.getName(), this.myProperties);
		return ab;
	}

	/**
	 * determines whether two AuthBehaviors are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!obj.getClass().equals(this.getClass()))
			return false;
		return ((AuthBehavior) obj).getName().equals(this.getName());
	}
}
