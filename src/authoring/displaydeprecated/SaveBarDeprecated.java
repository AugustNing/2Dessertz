package authoring.displaydeprecated;

import java.util.ResourceBundle;

import authoring.Game;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author madelinewilkinson
 *
 */
public class SaveBarDeprecated extends MainWindowComponentDeprecated {
	
	private HBox myHBox;

	public SaveBarDeprecated(ResourceBundle resources, Game game, Node root) {
		super(resources, game, root);
		myHBox = new HBox();
		
		myHBox.getChildren().addAll(makeNewGameButton(), makeLoadGameButton(), makeSaveGameButton());
		for(Node child : myHBox.getChildren()) {
			HBox.setMargin(child, new Insets(4));
		}
	}
	
	private Button makeNewGameButton() {
		return makeButton("NewGameButton", event -> doNothing());
	}
	
	private Button makeLoadGameButton() {
		return makeButton("LoadGameButton", event -> doNothing());
	}
	
	private Button makeSaveGameButton() {
		return makeButton("SaveGameButton", event -> doNothing());
	}

	public HBox asHBox() {
		return myHBox;
	}
	
	@Override
	protected Node asNode() {
		return myHBox;
	}

}
