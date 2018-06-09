package authoring.display.authoringuicomponents;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Edward Zhuang
 *
 * Abstract class for all AuthoringUIComponents. Provides borderpane.
 */
public abstract class AuthoringUIComponent {

	private BorderPane borderPane;
	
	/**
	 * Constructor that creates borderpane and creates FX components of subclass
	 */
	public AuthoringUIComponent() {
		borderPane = new BorderPane();
		generateComponent();
	}
	
	protected abstract void generateComponent();
	
	protected abstract void initializeFXComponents();
	
	protected abstract void mapFXActions();
	
	/**
	 * Protected getter for borderPane which subclass can use to attach FX components onto
	 * @return borderPane
	 */
	protected BorderPane getBorderPane() {
		return borderPane;
	}
	
	/**
	 * Used by Authoring Environment to position this class' borderpane onto a larger overall GUI pane. This way, no public getter is needed.
	 * @param pane pane to attach to
	 * @param xPosition x coordinate
	 * @param yPosition y coordinate
	 */
	public void attachToPane(Pane pane, int xPosition, int yPosition) {
		borderPane.setLayoutX(xPosition);
		borderPane.setLayoutY(yPosition);
		pane.getChildren().add(borderPane);
	}
	
	
}
