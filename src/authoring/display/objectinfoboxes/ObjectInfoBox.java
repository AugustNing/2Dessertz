package authoring.display.objectinfoboxes;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Edward Zhuang
 * Abstract class which provides a VBox to hold GameObject information.
 */
public abstract class ObjectInfoBox {
	
	private VBox vBox;

	private static final double DEFAULT_SPACING = 5;
	
	ObjectInfoBox() {
		vBox = new VBox(DEFAULT_SPACING);
		vBox.setAlignment(Pos.CENTER);
	}
	
	protected VBox getVBox() {
		return vBox;
	}

	/**
	 * Initializes JavaFX components in subclass.
	 */
	public abstract void initializeFXComponents();

	/**
	 * Sets actions of all JavaFX components in subclass.
	 */
	public abstract void mapFXActions();

	/**
	 * Adds all relevant components to the VBox.
	 */
	public abstract void initializeVBox();

	/**
	 * Adds the VBox to the center of a BorderPane.
	 * @param pane BorderPane
	 */
	public void addToBorderPane(BorderPane pane) {
		pane.setCenter(vBox);
	}
	
}
