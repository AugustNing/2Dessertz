package authoring.display;


import authoring.GameObject;
import authoring.ViewRefreshInterface;
import authoring.display.popups.GameObjectSizePopup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class GameObjectImageView {

	private ImageView myImage;
	private GameObject gameObject;
	private double currentX;
	private double currentY;
	private double positionX;
	private double positionY;

	private ViewRefreshInterface viewRefreshInterface;
	
	public GameObjectImageView(ImageView imageView, GameObject go, ViewRefreshInterface viewRefreshInterface) {

		myImage = imageView;
		gameObject = go;
		myImage.setOnMousePressed(this::onMousePressed);
		myImage.setOnMouseDragged(this::onMouseDragged);
		myImage.setOnMouseClicked(this::onMouseClicked);
		myImage.setOnMouseReleased(t -> onMouseReleased());
		this.viewRefreshInterface = viewRefreshInterface;
		
	}
	
	private void onMouseReleased() {
		gameObject.setxPos(myImage.localToParent(myImage.getBoundsInLocal()).getMinX());
		gameObject.setyPos(myImage.localToParent(myImage.getBoundsInLocal()).getMinY());		
		viewRefreshInterface.notifyObjectInfoObservers(gameObject);
	}

	private void onMouseClicked(MouseEvent t) {
		if (t.getButton().equals(MouseButton.SECONDARY)) {
			new GameObjectSizePopup(this, gameObject);
		}
	}
	
	private void onMousePressed(MouseEvent t) {
		if (t.getButton().equals(MouseButton.PRIMARY)) {
		viewRefreshInterface.backupGameScene();
		currentX = t.getSceneX();
		currentY = t.getSceneY();
		positionX = ((ImageView)(t.getSource())).getTranslateX();
		positionY = ((ImageView)(t.getSource())).getTranslateY();
		} else {
			new GameObjectSizePopup(this, gameObject);
		}
	}
	
	private void onMouseDragged(MouseEvent t) {
		dragImage(t);
	}
	
	private void dragImage(MouseEvent t) {
		double newX = t.getSceneX();
		double newY = t.getSceneY();
		double translateX = newX - currentX + positionX;
		double translateY = newY - currentY + positionY;
		myImage.setTranslateX(translateX);
		myImage.setTranslateY(translateY);
	}

	/**
	 * Returns ImageView component of GameObjectImageView
	 * @return myImage
	 */
	public ImageView getMyImage() {
		return myImage;
	}
}
