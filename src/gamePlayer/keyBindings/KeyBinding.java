package gamePlayer.keyBindings;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * one single key binding, front end and back end
 * 
 * @author jeffreyli
 *
 */
public class KeyBinding {
	private KeyCode defaultKeyCode;
	private KeyCode currentBinding;
	private String action;
	private ArrayList<Node> nodeList;
	private boolean keyIsChanged = false;
	private FadeTransition fadeTransition;
	private FadeTransition flashTransition;
	private Pane popUp;
	private KeyInputDictionary keyMap;
	private Label keyText;
	private Label repeatedKeyText;
	private static final String errorMessage = "KEY ALREADY BOUND";
	private static final Color ERROR_COLOR = Color.RED;
	private static final int REPEAT_KEY_X = 110;
	private static final int REPEAT_KEY_Y = 275;
	private static final String REPEAT_KEY_STYLE = "-fx-font: 18 Euphemia;";
	private static final Color WORDS_COLOR = Color.ALICEBLUE;
	private static final Duration FADE_TRANSITION_DURATION = Duration.millis(300);
	private static final String CHANGE_BUTTON_TEXT = "Change";
	private static final int CYCLECOUNT = 2;
	
	
	
	public KeyBinding(KeyCode keyCode, String theAction, KeyInputDictionary keyMap, Pane popUp) {
		defaultKeyCode = keyCode;
		action = theAction;
		this.keyMap = keyMap;
		currentBinding = keyMap.getKeyForValue(keyCode);
		this.popUp = popUp;
		nodeList = new ArrayList<>();
		addWords();
		setupRepeatedKeyText();
		setupFlashTransition();
		setUpFadeTransition();
		makeChangeButton();
	}
	
	
	private void setupFlashTransition() {
		flashTransition = new FadeTransition(FADE_TRANSITION_DURATION, repeatedKeyText);
		flashTransition.setFromValue(0);
		flashTransition.setToValue(1);
		flashTransition.setCycleCount(CYCLECOUNT);
		flashTransition.setAutoReverse(true);

	}
	
	private void setupRepeatedKeyText() {
		repeatedKeyText = new Label(errorMessage);
		repeatedKeyText.setLayoutX(REPEAT_KEY_X);
		repeatedKeyText.setLayoutY(REPEAT_KEY_Y);
		repeatedKeyText.setStyle(REPEAT_KEY_STYLE);
		repeatedKeyText.setTextFill(ERROR_COLOR);
		repeatedKeyText.setOpacity(0);
		popUp.getChildren().add(repeatedKeyText);
	}

	/**
	 * adds the text side of the keyBinding
	 */
	private void addWords() {
		Label actionText = new Label(action + ":");
		actionText.setStyle("-fx-font: 18 Euphemia;");
		actionText.setTextFill(WORDS_COLOR);
		nodeList.add(actionText);

		keyText = new Label(currentBinding.toString());
		keyText.setStyle("-fx-font: 20 Euphemia;");
		keyText.setTextFill(WORDS_COLOR);
		nodeList.add(keyText);
	}
	
	/**
	 * settings up the fade transition
	 */
	private void setUpFadeTransition() {
		fadeTransition = new FadeTransition(FADE_TRANSITION_DURATION, keyText);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.1);
		fadeTransition.setCycleCount(Timeline.INDEFINITE);
		fadeTransition.setAutoReverse(true);
	}

	/**
	 * makes a button to change the key binding for a command
	 */
	private void makeChangeButton() {

		Button change = new Button(CHANGE_BUTTON_TEXT);
		change.setOnAction(pushButtonEvent -> {
			fadeTransition.play();
			keyIsChanged = false;
			popUp.setOnKeyPressed(keyPressInput -> {
				KeyCode keyPressInputCode = keyPressInput.getCode();
				if (keyIsChanged == false) {
					if (!keyMap.containsKey(keyPressInputCode)) {
						keyMap.replaceKey(keyPressInputCode, defaultKeyCode, currentBinding);
						keyText.setText(keyPressInputCode.toString());
						fadeTransition.stop();
						keyText.setOpacity(1);
						keyIsChanged = true;
					} else if (keyMap.containsKey(keyPressInputCode)) {
						flashTransition.play();
					}

				}
			});
		});
		nodeList.add(change);
	}

	public ArrayList<Node> getNodeList() {
		return this.nodeList;
	}
}
