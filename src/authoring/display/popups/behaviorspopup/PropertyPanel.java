package authoring.display.popups.behaviorspopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import authoring.AuthBehavior;
import authoring.GameObject;
import authoring.Property;
import engine.actions.Action;
import engine.actions.GroovyAction;
import authoring.display.controllers.BehaviorPopupController;
import authoring.display.popups.ErrorBox;
import data.GameObjectManager;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Maddie Wilkinson
 *
 */
public class PropertyPanel {

	private List<GameObject> myGameObjects;
	private VBox myVBox;
	private Map<Property, TextField> myPropTexts;
	private BehaviorPopupController myController;
	private AuthBehavior currBehavior;

	/**
	 * @param behaviorPopupController
	 * @param gameObjects
	 */
	public PropertyPanel(BehaviorPopupController behaviorPopupController, List<GameObject> gameObjects) {
		myGameObjects = gameObjects;
		myController = behaviorPopupController;
		myPropTexts = new HashMap<>();
		initialize();
	}

	private void initialize() {
		myVBox = new VBox();
		initializeRoot();
	}

	private void initializeRoot() {
		myVBox.setPadding(new Insets(10));
		myVBox.setSpacing(10);
		myVBox.setMinWidth(500);
		myVBox.setMinHeight(450);
		myVBox.getChildren().add(new Label("Properties"));
		myVBox.getChildren().add(makeSaveButton());
		currBehavior = myController.getCurrBehavior();
		if(currBehavior != null) {
			Set<Property> properties =  myController.getCurrBehavior().getProperties();
			if(properties.size() == 0) {
				myVBox.getChildren().add(new Label("This behavior has no properties"));
			}
			for(Property property : properties) {
				System.out.println(property.getValueType().getName());
				myVBox.getChildren().add(makePropertyFields(property));
			}
		}
	}

	private Node makePropertyFields(Property property) {
		HBox hBox = new HBox(10);
		hBox.getChildren().add(new Label(property.getName() + " (" + property.getValueType().getSimpleName() + ") "));
		TextField propField = null;
		if(myPropTexts.containsKey(property)) {
			System.out.println("has Key " + property); //a got damn print statement
			propField = myPropTexts.get(property);
		} else {
			propField = new TextField();
			myPropTexts.put(property, propField);
		}
		if(property.getValue() != null) {
			propField.setText(property.getValue().toString());
		}
		hBox.getChildren().add(propField);
		return hBox;
	}

	private Button makeSaveButton() {
		Button saveButton = new Button();
		saveButton.setText("Save");
		saveButton.setOnAction(e -> saveProperties());
		return saveButton;
	}

	private void saveProperties() {
		for(Property p : myPropTexts.keySet()) {
			for(GameObject g : myGameObjects) {
				Property prop = g.getBehavior(currBehavior.getName()).getProperty(p.getName());
				String value = myPropTexts.get(p).getText();
				if (value != null && prop != null) {
					try {
						checkTypeAndStore(value, prop);
					} catch (Exception e) {
						new ErrorBox("Invalid Input", "Invalid User Input");
					}
				}
			}
		}
	}

	private void checkTypeAndStore(String value, Property prop) {
		switch(prop.getValueType().getSimpleName()) {
		case "Double":
			prop.setValue(Double.parseDouble(value));
			break;
		case "String":
			prop.setValue(value);
			break;
		case "Integer":
			prop.setValue(Integer.parseInt(value));
			break;
		case "Boolean":
			prop.setValue(Boolean.parseBoolean(value));
			break;
		case "List":
			if(currBehavior.getDisplayName().equals("SpaceRoutine")) {
				prop.setValue(parseListList(value));
			} else if(currBehavior.getDisplayName().equals("EntrancePortal")) {
				prop.setValue(parseListString(value));
			}
			break;
		case "Map":
			if(prop.getName().equals("routineTimes")) {
				prop.setValue(parseMap(value));
			}
			break;
		case "GameObject":
			prop.setValue(storeBullet(value));
			break;
		}
	}

	private Object storeBullet(String value) {
		List<GameObject> projectiles = GameObjectManager.getSavedGameObjects("projectile");
		for(GameObject g : projectiles) {
			if(value.equals(g.getName())) {
				System.out.println("got the gameobject hell yih");
				System.out.println("MandatoryBehavior: " + g.getMandatoryBehavior());
				return g;
			}
		}
		return null;
	}

	private List<List<Double>> parseListList(String value) {
		List<List<Double>> listOfLists = new ArrayList<>();
		String[] pointArray = value.split(";");
		for(String point : pointArray) {
			String[] coords = point.split(",");
			List<Double> list = new ArrayList<>();
			list.add(Double.parseDouble(coords[0])); //x coordinate
			list.add(Double.parseDouble(coords[1])); //y coordinate
			listOfLists.add(list);
		}
		return listOfLists;
	}

	private List<String> parseListString(String value) {
		String[] pointArray = value.split(",");
		return Arrays.asList(pointArray);
	}

	/**
	 * This method parses the string in the Property's TextField and returns it as a Map<Action, Double>. 
	 * This parsing method should only be used when the behavior is TimeRoutine.
	 * 
	 * @param value A String gotten from the TextField
	 * @return
	 */
	private Map<Action, Double> parseMap(String value) {
		Map<Action, Double> map = new HashMap<>();
		String[] keyPairs = value.split(";");
		for(String keyPair : keyPairs) {
			System.out.println(keyPair);
			String[] keyPairArr = keyPair.split(",");
			System.out.println("" + keyPairArr[0]);
			System.out.println(keyPairArr[1]);
			Action newAction = new GroovyAction(keyPairArr[0]);
			Double newDouble = Double.parseDouble(keyPairArr[1]);
			map.put(newAction, newDouble);
		}
		return map;
	}

	/**
	 * 
	 */
	public void refresh() {
		myVBox.getChildren().clear();
		initializeRoot();
	}

	/**
	 * @return
	 */
	public Node asNode() {
		return myVBox;
	}
}
