package authoring.displaydeprecated;

import java.util.ResourceBundle;

import authoring.Game;
import javafx.scene.Node;

public abstract class MainWindowComponentDeprecated extends AuthoringUIComponentDeprecated {

	public MainWindowComponentDeprecated(ResourceBundle resources, Game game, Node root) {
		super(resources, game, root);
	}

	protected abstract Node asNode();
}
