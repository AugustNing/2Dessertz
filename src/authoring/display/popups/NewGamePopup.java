package authoring.display.popups;

import authoring.Game;
import authoring.display.LoadAuthoringInterface;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Edward Zhuang
 * Popup which allows the user to create a new game.
 */
public class NewGamePopup extends Popup {

	private static final int xSize = 300;
	private static final int ySize = 200;
	private static final String DESCRIPTION = "Description";
	private static final String NAME = "Name";
	private static final String CREATE_GAME = "CreateGame";
	private Button newGameButton;
	private TextField gameNameText;
	private TextField gameDescriptionText;
	private Game game;
	private LoadAuthoringInterface loadAuthorer;
	
	public NewGamePopup(LoadAuthoringInterface loadAuthoringInterface) {
		super();
		loadAuthorer = loadAuthoringInterface;
		open(xSize, ySize);
		generatePopup();
		mapButtons();
	}
	
	protected void generatePopup() {
		VBox vBox = new VBox();
		HBox gameName = new HBox();
		HBox gameDescription = new HBox();
		newGameButton = new Button(ResourceBundleManager.getAuthoring(CREATE_GAME));
		
		gameNameText = new TextField();
		gameName.getChildren().addAll(new Label(ResourceBundleManager.getAuthoring(NAME)), gameNameText);
		gameDescriptionText = new TextField();
		gameDescription.getChildren().addAll(new Label(ResourceBundleManager.getAuthoring(DESCRIPTION)), gameDescriptionText);
		vBox.getChildren().addAll(gameName, gameDescription, newGameButton);
		
		BorderPane borderPane = getPane();
		borderPane.setCenter(vBox);
		
	};

	protected void mapButtons() {
		newGameButton.setOnAction( e -> 
		{	
			game = new Game(gameNameText.getText());
			game.setGameDescription(gameDescriptionText.getText()); 
			System.out.println(game.getGameDescription());
			loadAuthorer.loadAuthoringEnvironment(game);
			close();
		});
	}
	
	
	
	
}
