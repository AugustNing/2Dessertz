package authoring.display.popups;

import authoring.GameObject;
import authoring.display.GameObjectImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author August Ning
 * Pop up that allows the user to edit the size of a game object
 */
public class GameObjectSizePopup extends Popup {
	
	private Button save;
	private TextField tfx;
	private TextField tfy;
	private GameObjectImageView goiv;
	private GameObject go;
	
	private static final int popupSizeX = 300;
	private static final int popupSizeY = 200;
	private static final String SAVE = "Save";
	private static final String xSizeText = "Enter the desired X size";
	private static final String ySizeText = "Enter the desired Y size";
	private static final String displayWidthProperty = "displayWidth";
	private static final String displayHeightProperty = "displayHeight";


	/**
	 * @param ingoiv 	The current GameObjectImageView to be edited
	 * @param ingo		The current/corresponding GameObject that is being edited
	 */
	public GameObjectSizePopup(GameObjectImageView ingoiv, GameObject ingo) {
		super();
		goiv = ingoiv;
		go = ingo;
		
		generatePopup();
		mapButtons();
		open(popupSizeX, popupSizeY);
	}

	@Override
	protected void generatePopup() {
		VBox box = new VBox();
		box.getChildren().add(new Text(xSizeText));
		
		tfx = new TextField();
		tfx.setMinWidth(popupSizeX);
		box.getChildren().add(tfx);
		
		box.getChildren().add(new Text(ySizeText));
		
		tfy = new TextField();
		tfy.setMinWidth(popupSizeX);
		box.getChildren().add(tfy);
		
		save = new Button(SAVE);
		box.getChildren().add(save);
		
		super.getPane().setCenter(box);
	}

	@Override
	protected void mapButtons() {
		save.setOnAction(e -> {
			double newWidth = Double.parseDouble(tfx.getText());
			double newHeight = Double.parseDouble(tfy.getText());
			
			goiv.getMyImage().setFitWidth(newWidth);
			goiv.getMyImage().setFitHeight(newHeight);
			
			go.getMandatoryBehavior().getProperty(displayWidthProperty).setValue(newWidth);
			go.getMandatoryBehavior().getProperty(displayHeightProperty).setValue(newHeight);
			super.close();
		});
	}

}
