package display;

import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author August Ning and Edward Zhuang
 * Main driver class that is used for choosing between game authoring and game player
 */
public class Main extends Application {
	private static final String ICON_IMAGE = "./data/images/icon.png";
	private static final String VOOGA = "VOOGA";

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		SplashScreen splash = new SplashScreen(stage);
		stage.setTitle(VOOGA);
		stage.setScene(splash.getSplashScreen());
		stage.getIcons().add(createIcon());
		stage.show();
	}
	
	private Image createIcon() {
		File iconFile = new File(ICON_IMAGE);
		Image icon;
		try {
			icon = new Image(iconFile.toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException();
		}
		return icon;
	}

}
