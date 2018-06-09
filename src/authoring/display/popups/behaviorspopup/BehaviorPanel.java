package authoring.display.popups.behaviorspopup;

import java.util.List;
import java.util.Set;

import authoring.AuthBehavior;
import authoring.GameObject;
import authoring.display.controllers.BehaviorPopupController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Maddie Wilkinson
 *
 */
public class BehaviorPanel {
	private List<GameObject> myGameObjects;
	private Set<AuthBehavior> myBehaviors;
	private Set<AuthBehavior> notMyBehaviors;
	
	private ListView<AuthBehavior> myBehaviorList;
	private ComboBox<AuthBehavior> myBehaviorDropdown;

	private VBox myVBox;
	private BehaviorPopupController myController;
	
	/**
	 * @param behaviorPopupController
	 * @param gameObjects
	 * @param everyBehavior
	 */
	public BehaviorPanel(BehaviorPopupController behaviorPopupController, List<GameObject> gameObjects, Set<AuthBehavior> everyBehavior) {
		myController = behaviorPopupController;
		myGameObjects = gameObjects;
		myBehaviors = myGameObjects.get(0).getBehaviors();
		notMyBehaviors = everyBehavior;
		System.out.println(myBehaviors);
		initialize();
	}
	
	private void initialize() {
		myVBox = new VBox();
		notMyBehaviors.removeAll(myBehaviors);
		initializeRoot();
	}
	
	private void initializeRoot() {
		myVBox.setPadding(new Insets(10));
		myVBox.setSpacing(10);
		myVBox.setMinWidth(250);
		myVBox.setMinHeight(450);
		myVBox.getChildren().add(new Label("Behaviors"));
		
		myBehaviorDropdown = makeBehaviorDropdown();
		myBehaviorList = makeBehaviorList();
		
		myVBox.getChildren().add(myBehaviorDropdown);
		HBox hBox = new HBox();
		hBox.getChildren().addAll(makeAddBehaviorButton(), makeRemoveBehaviorButton());
		myVBox.getChildren().add(hBox);
		myVBox.getChildren().add(myBehaviorList);
	}
	
	private ListView<AuthBehavior> makeBehaviorList() {
		ListView<AuthBehavior> behaviorList = new ListView<>();
		behaviorList.getItems().addAll(myBehaviors);
		behaviorList.getSelectionModel().selectedItemProperty().addListener((o, old, neww) -> {
			myController.refreshProperties();
		});
		return behaviorList;
	}
	
	private ComboBox<AuthBehavior> makeBehaviorDropdown() {
		ComboBox<AuthBehavior> behaviorDropdown = new ComboBox<>();
		behaviorDropdown.getItems().addAll(notMyBehaviors);
		return behaviorDropdown;
	}
	
	private Button makeAddBehaviorButton() {
		Button addBehaviorButton = new Button();
		addBehaviorButton.setText("Add Behavior");
		addBehaviorButton.setOnAction(e -> {
			addBehavior();
			refresh();
		});
		return addBehaviorButton;
	}
	
	private Button makeRemoveBehaviorButton() {
		Button removeBehaviorButton = new Button();
		removeBehaviorButton.setText("Remove Behavior");
		removeBehaviorButton.setOnAction(e -> {
			removeBehavior();
			refresh();
		});
		return removeBehaviorButton;
	}
	
	private void addBehavior() {
		AuthBehavior currBehavior = getBehaviorToAdd();
		if(currBehavior != null) {
			for(GameObject g : myGameObjects) {
				g.addBehavior(currBehavior);
			}
			System.out.println("behavior added");
			notMyBehaviors.remove(currBehavior);
		}
	}
	
	private void removeBehavior() {
		AuthBehavior currBehavior = getCurrBehavior();
		if(currBehavior != null) {
			for(GameObject g : myGameObjects) {
				if(!currBehavior.getDisplayName().equals("MandatoryBehavior")) {
					g.removeBehavior(currBehavior);
				}
			}
			System.out.println("behavior remove");
			notMyBehaviors.add(currBehavior);
		}
	}

	/**
	 * @return
	 */
	public AuthBehavior getBehaviorToAdd() {
		return myBehaviorDropdown.getSelectionModel().getSelectedItem();
	}	
	
	/**
	 * @return
	 */
	public AuthBehavior getCurrBehavior() {
		return myBehaviorList.getSelectionModel().getSelectedItem();
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
