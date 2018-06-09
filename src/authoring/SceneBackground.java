package authoring;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Edward Zhuang
 * The SceneBackground holds all SceneBackgroundImages. Its pane is added to the StackPane of the GameViewWindow.
 */
public class SceneBackground {
	
	private Pane pane;
	private Rectangle borderRectangle;
	private List<SceneBackgroundImage> list;
	
	public SceneBackground(int xSize, int ySize) {
		pane = new Pane();
		list = new ArrayList<>();
		pane.setPrefSize(xSize, ySize);
		borderRectangle = new Rectangle(xSize, ySize);
		pane.getChildren().add(borderRectangle);
		borderRectangle.setFill(Color.TRANSPARENT);
		borderRectangle.setStroke(Color.BLACK);
		
	}
	
	public Pane getPane() {
		return pane;
	}

	/**
	 * Sets border rectangle with boundaries for SceneBackgroundImages.
	 * @param xSize width
	 * @param ySize height
	 */
	public void setRectangle(int xSize, int ySize) {
		pane.getChildren().remove(borderRectangle);
		borderRectangle = new Rectangle(xSize, ySize);
		borderRectangle.setFill(Color.TRANSPARENT);
		borderRectangle.setStroke(Color.BLACK);
		pane.getChildren().add(borderRectangle);
		for (SceneBackgroundImage s: list) {
			s.setRectangle(borderRectangle);
			
		}
	}

	/**
	 * Adds SceneBackgroundImage to the pane.
	 * @param sceneBackgroundImage SceneBackgroundImage
	 */
	public void addImage(SceneBackgroundImage sceneBackgroundImage) {
		sceneBackgroundImage.setRectangle(borderRectangle);
		Pane imagePane = sceneBackgroundImage.getPane();
		pane.getChildren().add(imagePane);
		list.add(sceneBackgroundImage);
		sceneBackgroundImage.translate();
	}

	/**
	 * Removes the SceneBackgroundImages
	 */
	public void clearPane() {
		list.clear();
		pane.getChildren().clear();
	}
	
	
}
