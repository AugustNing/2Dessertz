package authoring.display.popups.eventspopup;

import authoring.display.popups.Popup;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author August Ning
 * Pop up for editing the keybinding for keybinding events
 */
public class TWKPopup extends Popup {
	
	private TriggerWindow tw;
	private TextField tf;
	private String kc;
	private Button save;
	private static final int xSize = 300;
	private static final int ySize = 200;
	private static final String SAVE = "Save";
	private static final String PROMPT = "Configure a keybinding";

	public TWKPopup(TriggerWindow intw) {
		kc = "";
		tw = intw;
		tf = new TextField();
		save = new Button(SAVE);
		generatePopup();
		mapButtons();
		open(xSize, ySize);
	}

	@Override
	protected void generatePopup() {
		VBox box = new VBox();
		box.getChildren().add(new Text(PROMPT));
		tf.setOnKeyTyped(e -> getKey(e.getCharacter()));
		box.getChildren().add(tf);
		box.getChildren().add(save);
		super.getPane().setCenter(box);
	}
	
	private void getKey(String inkc) {
		kc = inkc;
		tf.clear();
		tf.setText(kc);

	}
	@Override
	protected void mapButtons() {
		save.setOnAction(e -> {
			tw.setKeyCode(kc);
			tw.createVBox();
			close();
		});
	}

}
