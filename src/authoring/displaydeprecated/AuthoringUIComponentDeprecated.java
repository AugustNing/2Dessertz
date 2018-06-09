package authoring.displaydeprecated;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import authoring.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * @author Maddie Wilkinson
 *
 */
public abstract class AuthoringUIComponentDeprecated {
	protected static final double DEFAULT_SPACING = 5;
	
	private ResourceBundle myResources;
	private Game myGame;
	private Node myRoot;
	
	public AuthoringUIComponentDeprecated(ResourceBundle resources, Game game, Node root) {
		myResources = resources;
		myGame = game;
		myRoot = root;
	}
	
	protected Button makeButton (String property, EventHandler<ActionEvent> handler) {
		Button result = new Button();
		String label = "";
		try {
		label = myResources.getString(property);
		}
		catch(MissingResourceException e){
			label = property;
		}
		result.setText(label);
		result.setOnAction(handler);
		return result;
	}
	
	protected ResourceBundle getResources() { 
		return myResources;
	}
	
	protected Game getGame() {
		return myGame;
	}
	
	protected Node getRoot() {
		return myRoot;
	}
	
	//this will be a filler method for buttons and such, before their actual functions are implemented
	public void doNothing() {
		
	}
	
}
