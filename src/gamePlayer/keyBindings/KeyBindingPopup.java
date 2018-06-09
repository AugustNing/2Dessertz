package gamePlayer.keyBindings;

import java.util.ArrayList;
import java.util.Map;

import gamePlayer.buttons.ButtonData;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * The pop up pane that appears for the user to change keybindings
 * 
 * @author jeffreyli, calvinma
 *
 */
public class KeyBindingPopup extends Pane {

	private ButtonData buttonData;
	private KeyInputDictionary keyMap;
	private Map<KeyCode, String> defaultBindings;

	private static final int BINDING_X_POSITION = 40;
	private static final int FIRST_BINDING_Y_POSITION = 60;
	private static final int SPACE_BETWEEN_BINDINGS = 60;
	private static final int SPACE_BETWEEN_NODES = 125;
	private static final String CLOSE_BUTTON_TEXT = "Close";
	private static final String TITLE_TEXT = "Key Bindings";

	public KeyBindingPopup(ButtonData buttonData) {
		this.setLayoutX(300);
		this.setLayoutY(100);
		keyMap = buttonData.getKeyBindings();
		defaultBindings = buttonData.getKeyAssignments();

		this.buttonData = buttonData;
		setupBackground();
		setupBindings();
		setupCloseButton();
		setUpTitle();
	}

	private void setupBindings() {
		int yPosition = FIRST_BINDING_Y_POSITION;
		for (KeyCode key : defaultBindings.keySet()) {
			buttonData.getKeyBindings().replaceKey(key, key, key);
			KeyBinding keyBinding = new KeyBinding(key, defaultBindings.get(key), keyMap, this);
			addNodeListToGui(yPosition, BINDING_X_POSITION, keyBinding.getNodeList());
			yPosition += SPACE_BETWEEN_BINDINGS;
		}
	}

	/**
	 * adds the nodeList (which represents one row in the key binding screen) to the
	 * gui
	 * 
	 * @param yPosition:
	 *            of row
	 * @param xPosition:
	 *            of first node
	 * @param nodeList:
	 *            nodes to add
	 */
	private void addNodeListToGui(int yPosition, int xPosition, ArrayList<Node> nodeList) {
		for (Node n : nodeList) {
			n.setLayoutY(yPosition);
			n.setLayoutX(xPosition);
			xPosition = xPosition + SPACE_BETWEEN_NODES;
		}
		this.getChildren().addAll(nodeList);
	}

	private void setupCloseButton() {
		Button close = new Button(CLOSE_BUTTON_TEXT);
		close.setLayoutX(175);
		close.setLayoutY(350);
		close.setOnAction(event -> {
			buttonData.resumeGame();
			buttonData.removeFromRoot(this);
		});
		this.getChildren().add(close);

	}

	private void setupBackground() {
		Rectangle background = new Rectangle(0, 0, 400, 400);
		background.setFill(Color.BLACK);
		background.setOpacity(0.3);
		this.getChildren().add(background);
	}

	private void setUpTitle() {
		Text title = new Text();
		title.setText(TITLE_TEXT);
		title.setStyle("-fx-font: 24 Verdana;");
		title.setFill(Color.LIGHTSKYBLUE);
		title.setX(40);
		title.setY(30);
		this.getChildren().add(title);
	}

	public void show() {
		buttonData.addToRoot(this);
	}

}