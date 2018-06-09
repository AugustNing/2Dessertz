package authoring.display.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import authoring.AuthBehavior;
import authoring.BehaviorFactory;
import authoring.EngineClassRetriever;
import authoring.GameObject;
import authoring.display.popups.behaviorspopup.BehaviorPanel;
import authoring.display.popups.behaviorspopup.PropertyPanel;
import engine.behaviors.Behavior;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Maddie Wilkinson
 * 
 */
public class BehaviorPopupController extends PopupController {
	private final String PACKAGE_NAME = "engine.behaviors";
	private final String SUPERCLASS_NAME = "engine.behaviors.Behavior";
	private final String MANDATORY_NAME = "engine.behaviors.MandatoryBehavior";

	private List<GameObject> myGameObjects;
	private BehaviorPanel myBehaviorPanel;
	private PropertyPanel myPropertyPanel;

	/**
	 * @param gameObjects
	 */
	public BehaviorPopupController(List<GameObject> gameObjects) {
		myGameObjects = gameObjects;
		getAllBehaviors();
		initializeScreenComponents();
	}

	@Override
	protected void initializeScreenComponents() {
		myBehaviorPanel = new BehaviorPanel(this, myGameObjects, getAllBehaviors());
		myPropertyPanel = new PropertyPanel(this, myGameObjects);
	}

	protected void refreshView() {
		myBehaviorPanel.refresh();
		myPropertyPanel.refresh();
	}
	
	/**
	 * @return The BehaviorPanel and the PropertyPanel in the BehaviorPopup.
	 */
	public List<Node> getPanels() {
		List<Node> panels = new ArrayList<>();
		panels.add(myBehaviorPanel.asNode());
		panels.add(myPropertyPanel.asNode());
		return panels;
	}
	
	private Set<AuthBehavior> getAllBehaviors() {
		BehaviorFactory behaviorFactory = new BehaviorFactory();
		
		EngineClassRetriever retriever = new EngineClassRetriever();
		Set<Class<?>> classes = (Set<Class<?>>) retriever.getClasses(Behavior.class, "engine.behaviors");
		
		Set<AuthBehavior> allBehaviors = new HashSet<>();
		for(Class<?> c : classes) {
			String fullName = c.getCanonicalName();
			if(!fullName.equals(SUPERCLASS_NAME) && !fullName.equals(MANDATORY_NAME)) {
				allBehaviors.add(behaviorFactory.makeBehavior(fullName));
			}
		}
		return allBehaviors;
	}
	
	/**
	 * @return The currently selected AuthBehavior in the BehaviorPanel
	 */
	public AuthBehavior getCurrBehavior() {
		return myBehaviorPanel.getCurrBehavior();
	}

	/**
	 * Refreshes the displayed Properties in the PropertyPanel.
	 */
	public void refreshProperties() {
		myPropertyPanel.refresh();
	}
}
