package authoring.displaydeprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import authoring.Game;
import authoring.GameObject;
import data.GameObjectManager;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/*
 * @author Edward Zhuang
 */
public class TemplateObjectPanelDeprecated extends MainWindowComponentDeprecated {

	ListView<String> myListView;
	GameObjectManager myGameObjectManager;
	TreeMap<String, GameObject> myMap;
	BorderPane myPane;
	
	public TemplateObjectPanelDeprecated(ResourceBundle resources, Game game, Node root) {
		super(resources, game, root);
		myListView = new ListView<String>();
		myGameObjectManager = new GameObjectManager();
		myPane = new BorderPane();
		myPane.setTop(myListView);
		update();
	}
		
	/**
	 * When new object is added, this will provide an updated list
	 */
	public void update() {
		myListView.getItems().removeAll();
		myMap = (TreeMap<String, GameObject>) myGameObjectManager.getSavedGameObjects();
		SortedSet<String> keys = new TreeSet<>(myMap.keySet());
		for (String key: keys) {
			myListView.getItems().add(key);
		}
	}
	
	public void addCustomObject(GameObject gameObject, String name) throws IOException {
		myGameObjectManager.saveCustomGameObject(gameObject, name);
		update();
	}
	
	public BorderPane asPane() {
		return myPane;
	}

	@Override
	protected Node asNode() {
		return myPane;
	}	
}
