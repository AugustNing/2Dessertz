package display;



import java.io.File;
import java.net.MalformedURLException;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * @author Edward Zhuang
 * Class which makes a button with a spinning animation
 */
public class AnimatedButton {

	private static final int IMAGE_HEIGHT = 20;
	private Button button;
	private HBox hBox;

	public AnimatedButton(String imagePath, String labelText) {
		hBox = new HBox();
		Label label = new Label(labelText);
		button = new Button();
		hBox.getChildren().addAll(button, label);
		File imageFile = new File(imagePath);
		
		Image image;
		try {
			image = new Image(imageFile.toURI().toURL().toExternalForm());
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(IMAGE_HEIGHT);
		button.setGraphic(imageView);
		setRotate(button);
		stylizeButton(button);
		
		} catch (MalformedURLException e) {
			// This shouldn't happen
			}
	}
	
	private void setRotate(Button button) {
		RotateTransition rotation = new RotateTransition(Duration.seconds(0.25), button);
		rotation.setCycleCount(1);
		rotation.setInterpolator(Interpolator.LINEAR);
		rotation.setByAngle(360);
		button.setOnMouseEntered(e -> rotation.play());
	}
	
	private void stylizeButton(Button button) {
		button.setStyle( "-fx-border-color: transparent; -fx-border-width: 0;  -fx-background-radius: 0; -fx-background-color: transparent;");
	}
	
	public HBox getHBox() {
		return hBox;
	}
	
	public Button getButton() {
		return button;
	}
}
