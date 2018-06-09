package authoring;

import authoring.display.popups.BackgroundSizePopup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Edward Zhuang
 * The SceneBackgroundImage is the front end component which holds a background image. It can be dragged around and resized.
 */
public class SceneBackgroundImage {

	private Rectangle myRectangle;
	private ImageView myImage;
	private static final int BORDER = 20;
	private double currentX;
	private double currentY;
	private double positionX;
	private double positionY;
	private BorderPane imageHolder;
	private SceneBackgroundImageSerializable serializable;
	
	
	public SceneBackgroundImage(Image image, SceneBackgroundImageSerializable serializable) {
		this.serializable = serializable; 
		myImage = new ImageView(image);
		myImage.setPreserveRatio(true);
		myImage.setFitWidth(serializable.getxSize());
		imageHolder = new BorderPane();
		imageHolder.setLayoutX(0);
		imageHolder.setLayoutY(0);
		updateDimensions();
		imageHolder.setOnMousePressed(this::onMousePressed);
		imageHolder.setOnMouseDragged(this::onMouseDragged);
		imageHolder.setOnMouseClicked(this::onMouseClicked);
		imageHolder.setCenter(myImage);
	}

	/**
	 * Moves the SceneBackgroundImage
	 */
	public void translate() {
		imageHolder.setTranslateX(serializable.getxPos());
		imageHolder.setTranslateY(serializable.getyPos());
	}
	
	public void setRectangle(Rectangle rectangle) {
		myRectangle = rectangle;
	}
	
	public Pane getPane() {
		return imageHolder;
	}
	
	private void onMouseClicked(MouseEvent t) {
		if (t.getButton().equals(MouseButton.SECONDARY)) {
			new BackgroundSizePopup(this, serializable);
		}
	}
	
	private void onMousePressed(MouseEvent t) {
		currentX = t.getSceneX();
		currentY = t.getSceneY();
		positionX = ((Pane)(t.getSource())).getTranslateX();
		positionY = ((Pane)(t.getSource())).getTranslateY();
	}
	
	private void onMouseDragged(MouseEvent t) {
		dragImage(t);
	}

	private void dragImage(MouseEvent t) {
		double newX = t.getSceneX();
		double newY = t.getSceneY();	
		double translateX = newX - currentX + positionX;
		double translateY = newY - currentY + positionY;
		if ((translateX) > (myRectangle.getBoundsInParent().getMaxX()- imageHolder.getBoundsInParent().getWidth())) {
			translateX = myRectangle.getBoundsInParent().getMaxX() - imageHolder.getBoundsInParent().getWidth();
		}
		if (translateX < myRectangle.getBoundsInParent().getMinX()) {
			translateX = myRectangle.getBoundsInParent().getMinX();
		}
		if ((translateY) > (myRectangle.getBoundsInParent().getMaxY()- imageHolder.getBoundsInParent().getHeight())) {
			translateY = myRectangle.getBoundsInParent().getMaxY() - imageHolder.getBoundsInParent().getHeight();
		}
		if (translateY < myRectangle.getBoundsInParent().getMinX()) {
			translateY = myRectangle.getBoundsInParent().getMinY();
		}
		imageHolder.setTranslateX(translateX);
		imageHolder.setTranslateY(translateY);
		serializable.setxPos(translateX);
		serializable.setyPos(translateY);
	}

	public void setOpacity(Double value) {
		myImage.setOpacity(value);
	}

	/**
	 * Updates size of the image.
	 */
	public void updateDimensions() {
		double height = myImage.getBoundsInLocal().getHeight();
		double width = myImage.getBoundsInLocal().getWidth();
		imageHolder.setMaxWidth(width + BORDER);
		imageHolder.setMaxHeight(height + BORDER);
	}
	
	public ImageView getImageView() {
		return myImage;
	}
	
}
