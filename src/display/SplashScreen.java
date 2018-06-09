package display;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import data.propertiesFiles.ResourceBundleManager;
import display.buttonevents.GameAuthoringPress;
import display.buttonevents.GamePlayerPress;
import display.buttons.GUIButton;
import javafx.animation.RotateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author August Ning and Edward Zhuang
 * class that houses the SplashScreen
 *
 */
public class SplashScreen {

	private static final String PLAYERBUTTONSIZEX = ResourceBundleManager.getSplash("PLAYERBUTTONSIZEX");
	private static final String PLAYERBUTTONSIZEY = ResourceBundleManager.getSplash("PLAYERBUTTONSIZEY");
	private static final String AUTHBUTTONSIZEX = ResourceBundleManager.getSplash("AUTHBUTTONSIZEX");
	private static final String AUTHBUTTONSIZEY = ResourceBundleManager.getSplash("AUTHBUTTONSIZEY");
	private static final String PLAYERBUTTONID = ResourceBundleManager.getSplash("PLAYERBUTTONID");
	private static final String AUTHBUTTONID = ResourceBundleManager.getSplash("AUTHBUTTONID");
	private static final String PLAYFILE = ResourceBundleManager.getSplash("PLAYFILE");
	private static final String WRITEFILE = ResourceBundleManager.getSplash("WRITEFILE");
	private static final String FILEHEIGHT = ResourceBundleManager.getSplash("FILEHEIGHT");
	private static final String WINDOWSIZEX = ResourceBundleManager.getSplash("WINDOWSIZEX");
	private static final String WINDOWSIZEY = ResourceBundleManager.getSplash("WINDOWSIZEY");
	private static final String PANE = "pane";
	private static final String LOGO = ResourceBundleManager.getSplash("LOGO");
	private static final String LOGOSCALE = ResourceBundleManager.getSplash("LOGOSCALE");
	private static final String LOGOX = ResourceBundleManager.getSplash("LOGOX");
	private static final String LOGOY = ResourceBundleManager.getSplash("LOGOY");
	private static final String PLAY = ResourceBundleManager.getSplash("PLAY");
	private static final String PLAYSCALE = ResourceBundleManager.getSplash("PLAYSCALE");
	private static final String PLAYX = ResourceBundleManager.getSplash("PLAYX");
	private static final String PLAYY = ResourceBundleManager.getSplash("PLAYY");
	private static final String CREATE = ResourceBundleManager.getSplash("CREATE");
	private static final String CREATESCALE = ResourceBundleManager.getSplash("CREATESCALE");
	private static final String CREATEX = ResourceBundleManager.getSplash("CREATEX");
	private static final String CREATEY = ResourceBundleManager.getSplash("CREATEY");
	private static final String STYLE_CSS = "style.css";
	private static final int ROTATION_ANGLE = 360;
	private static final double ROTATION_DURATION = 0.25;
	private Stage currStage;
	private List<GUIButton> buttons;

	public SplashScreen(Stage stage) {
		this.currStage = stage;
		addButtons();
	}

	private void addButtons() {
		buttons = new ArrayList<>();
		GUIButton gamePlayerButton = new GUIButton(
				Integer.parseInt(PLAYERBUTTONSIZEX),
				Integer.parseInt(PLAYERBUTTONSIZEY), "",
				new GamePlayerPress(currStage));
		GUIButton gameAuthoringButton = new GUIButton(
				Integer.parseInt(AUTHBUTTONSIZEX),
				Integer.parseInt(AUTHBUTTONSIZEY), "",
				new GameAuthoringPress());
		gamePlayerButton.setId(PLAYERBUTTONID);
		gameAuthoringButton.setId(AUTHBUTTONID);

		File playImageFile = new File(PLAYFILE);
		setButtonGraphic(gamePlayerButton, playImageFile);

		File authorImageFile = new File(WRITEFILE);
		setButtonGraphic(gameAuthoringButton, authorImageFile);
		buttons.add(gamePlayerButton);
		buttons.add(gameAuthoringButton);

		rotateButtons(buttons);
	}

	private void setButtonGraphic(GUIButton gamePlayerButton, File playImageFile) {
		Image imagePlay;
		try {
			imagePlay = new Image(playImageFile.toURI().toURL().toExternalForm());
			ImageView im = new ImageView(imagePlay);
			im.setPreserveRatio(true);
			im.setFitHeight(Integer.parseInt(FILEHEIGHT));
			gamePlayerButton.setGraphic(im);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException();
		}
	}

	private void rotateButtons(List<GUIButton> buttons) {
		for (GUIButton b : buttons) {
			RotateTransition rotation = new RotateTransition(Duration.seconds(ROTATION_DURATION), b);
			rotation.setCycleCount(1);
			rotation.setByAngle(ROTATION_ANGLE);
			b.setOnMouseEntered(e -> rotation.play());
		}
	}

	private ImageView makeSplashImage(String imageName, double ratio, double x, double y) {
		try {
			File imageFile = new File(imageName);
			Image image = new Image(imageFile.toURI().toURL().toExternalForm());
			ImageView splashImage = new ImageView(image);
			splashImage.setPreserveRatio(true);

			splashImage.setFitHeight(ratio);
			splashImage.setLayoutX(x);
			splashImage.setLayoutY(y);

			return splashImage;
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @return a Scene that can be displayed on the splash screen
	 */
	public Scene getSplashScreen() {
		Pane myRoot = new Pane();
		myRoot.setId(PANE);
		Scene scene = new Scene(myRoot, Integer.parseInt(WINDOWSIZEX),
				Integer.parseInt(WINDOWSIZEY));
		scene.getStylesheets().add(getClass().getResource(STYLE_CSS).toExternalForm());
		myRoot.getChildren().addAll(buttons);
		myRoot.getChildren()
				.add(makeSplashImage(LOGO,
						Integer.parseInt(LOGOSCALE),
						Integer.parseInt(LOGOX),
						Integer.parseInt(LOGOY)));
		myRoot.getChildren()
				.add(makeSplashImage(PLAY,
						Integer.parseInt(PLAYSCALE),
						Integer.parseInt(PLAYX),
						Integer.parseInt(PLAYY)));
		myRoot.getChildren()
				.add(makeSplashImage(CREATE,
						Integer.parseInt(CREATESCALE),
						Integer.parseInt(CREATEX),
						Integer.parseInt(CREATEY)));
		return scene;
	}

}
