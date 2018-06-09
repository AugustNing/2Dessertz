package authoring.displaydeprecated;

import java.util.ResourceBundle;

import authoring.AuthBehavior;
import authoring.Game;
import authoring.Property;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author madelinewilkinson
 *
 */
public class EditPropertiesWindowDeprecated extends PopupWindowDeprecated {

	private VBox myVBox;
	private AuthBehavior myBehavior;
	//don't think you need the GameObject as an instance var
	
	public EditPropertiesWindowDeprecated(ResourceBundle resources, Game game, Node root, AuthBehavior behavior) {
		super(resources, game, root);
		myBehavior = behavior;
		myVBox = new VBox(DEFAULT_SPACING);
		setStage(setUpScene());
	}
	
	@Override
	protected Scene setUpScene() {
		initialize();
		return new Scene(myVBox);
	}
	
	private void initialize() {
		HBox hBox = new HBox(DEFAULT_SPACING);
		hBox.getChildren().add(new Label(myBehavior.getName()));
		myVBox.getChildren().add(hBox);
		for(Property property : myBehavior.getProperties()) {
			myVBox.getChildren().add(makePropertyFields(property));
		}	
	}
	
	private Node makePropertyFields(Property property) {
		HBox hBox = new HBox(DEFAULT_SPACING);
		hBox.getChildren().add(new Label(property.getName()));
		TextField propField = new TextField();
		hBox.getChildren().add(propField);
		return hBox;
	}
}
