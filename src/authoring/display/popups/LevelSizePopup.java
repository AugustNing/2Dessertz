package authoring.display.popups;

import authoring.display.authoringuicomponents.GameViewWindow;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author August Ning
 * Popup window used for editing the size of the level
 */
public class LevelSizePopup extends Popup {
	
	private GameViewWindow window;
	private TextField sizeX;
	private TextField sizeY;
	private Button save;
	
	private static final int popupSizeX = 300;
	private static final int popupSizeY = 200;
	private static final String levelSizeX = "Level Size X: ";
	private static final String levelSizeY = "Level Size Y: ";


	/**
	 * @param window 	The game view window that calls the pop up
	 */
	public LevelSizePopup(GameViewWindow window) {
		super();
		this.window = window;
		generatePopup();
		mapButtons();
		open(popupSizeX, popupSizeY);
	}
	

	@Override
	protected void generatePopup() {
		save = new Button(ResourceBundleManager.getAuthoring("Save"));
		sizeX = new TextField();
		sizeY = new TextField();
		
		VBox popupBox = new VBox();
		HBox sizeXBox = new HBox();
		HBox sizeYBox = new HBox();
		
		sizeXBox.getChildren().addAll(new Label(levelSizeX), sizeX);
		sizeYBox.getChildren().addAll(new Label(levelSizeY), sizeY);

		popupBox.getChildren().addAll(sizeXBox, sizeYBox, save);
		super.getPane().setCenter(popupBox);
	}

	@Override
	protected void mapButtons() {
		save.setOnAction(e -> {
			window.updatePaneSize(Integer.parseInt(sizeX.getText()), Integer.parseInt(sizeY.getText()));
			super.close();
		});
	}

}
