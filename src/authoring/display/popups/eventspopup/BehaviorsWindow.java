package authoring.display.popups.eventspopup;

import java.util.List;

import authoring.AuthBehavior;
import authoring.GameObject;
import authoring.display.controllers.EventsPopupController;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author August Ning
 * VBox for listing behaviors of an game object
 */
public class BehaviorsWindow extends VBox {
	
	private List<GameObject> gos;
	private EventsPopupController epuc;
	private AuthBehavior currentBehavior;
	private ListView<AuthBehavior> behaviors;
	private GameObject currentGO;
	
	private static final String BEHAVIORS = "Behaviors";
	private static final String NOEVENT = "No Event selected";
	private static final int SPACING = 10;
	private static final int SIZE = 200;
	
	/**
	 * @param myEPUC 	The controller
	 * @param myGos 	The current game objects
	 */
	public BehaviorsWindow(EventsPopupController myEPUC, List<GameObject> myGos) {
		epuc = myEPUC;
		gos = myGos;
		behaviors = new ListView<>();
		behaviors.setMinHeight(SIZE);
		currentGO = gos.get(0);
		currentBehavior = null;
		createVBox();
	}
	
	/**
	 * Creates the layout for the VBox
	 */
	public void createVBox() {
		this.getChildren().clear();
		this.setPadding(new Insets(SPACING));
	    this.setSpacing(SPACING);
	    this.setMinWidth(SIZE);
	    Text title = new Text(BEHAVIORS);
	    this.getChildren().add(title);
	    createListView();
	}
	
	private void createListView() {
		if (!epuc.validEvent()) {
			this.getChildren().add(new Text(NOEVENT));
			return;
		}
		behaviors.getItems().clear();
		behaviors.getItems().addAll(currentGO.getBehaviors());
		behaviors.setOnMouseClicked(e -> mouseClicked(behaviors.getSelectionModel().getSelectedItem()));
		this.getChildren().add(behaviors);
	}
	
	private void mouseClicked(AuthBehavior selectedBehavior) {
		if (selectedBehavior == null) return;
		currentBehavior = selectedBehavior;
		epuc.updateFromBehavior();
		epuc.concatenateString(currentBehavior.getDisplayName(), this.getClass().getSimpleName());
	}
	
	/**
	 * @return the current AuthBehavior
	 */
	public AuthBehavior getCurrBehavior() {
		return currentBehavior;
	}
	/**
	 * Make the current object the passed in game object
	 * @param newGO 	The desired game object to be the current game object
	 */
	public void setCurrObject(GameObject newGO) {
		if (newGO == null) {
			return;
		}
		currentGO = newGO;
		createVBox();
	}
}
