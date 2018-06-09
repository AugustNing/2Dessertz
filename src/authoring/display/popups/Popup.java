package authoring.display.popups;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
	
/**
 * 
 * @author Edward Zhuang
 *
 */
public abstract class Popup {
		
	private Stage stage;
	private BorderPane borderPane;
	
	public Popup() {
		stage = new Stage();
		borderPane = new BorderPane();
	}
	
	protected void open(int xSize, int ySize) {
		Scene scene = new Scene(borderPane, xSize, ySize);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	protected abstract void generatePopup();
	
	protected BorderPane getPane() {
		return borderPane;
	}
	
	protected abstract void mapButtons();
	
	protected void close() {
		stage.close();
	}
}
