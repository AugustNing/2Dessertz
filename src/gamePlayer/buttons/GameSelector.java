package gamePlayer.buttons;

import java.io.File;
import java.nio.file.Paths;

import data.GameDescriptionProvider;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * front end for selecting new games
 * 
 * @author jeffreyli, calvinma
 *
 */
public class GameSelector extends ScrollPane {

	private ButtonData buttonData;
	private boolean isNew;
	private String playOrLoadGame;
	private VBox gameSelectorBox;
	private static final int boxWidth = 900;
	private static final int boxHeight = 210;
	private static final int paneHeight = 200;
	private static final String selectGameString = "Select Game";
	private static final String loadGameString = "Load Game";
	private static final String font = "Times New Roman";
	private static final Color color = Color.WHITE;

	public GameSelector(ButtonData buttonData, boolean isNew) {
		this.buttonData = buttonData;
		buttonData.setEngineRunning(false);
		buttonData.pauseGame();
		this.setLayoutX(30);
		this.setLayoutY(30);
		this.setPrefSize(900, 590);
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.isNew = isNew;
		if (isNew) {
			playOrLoadGame = selectGameString;
		} else {
			playOrLoadGame = loadGameString;
		}
		initializeGameSelections();
	}

	private void initializeGameSelections() {
		setUpGameSelectorBox();

		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/data/gamedata/games";
		File directory = new File(currentPath);
		GameDescriptionProvider gameDescriptionProvider = new GameDescriptionProvider();

		File[] gameList = directory.listFiles();
		for (File game : gameList) {
			String gameName = game.getName();
			try {
				String gameString = gameDescriptionProvider.getGameName(gameName);
				String gameDescription = gameDescriptionProvider.getGameDescription(gameName);
				Image gameImage = gameDescriptionProvider.getDescriptionImage(gameName);
				Pane gameDescriptionPane = setupNewGamePane(gameName, gameString, gameDescription, gameImage);
				gameSelectorBox.getChildren().add(gameDescriptionPane);
			} catch (Exception e) {
				System.out.println("Failed to load in game from folder " + gameName);
				continue;
			}
		}
		this.setContent(gameSelectorBox);
	}

	private void setUpGameSelectorBox() {
		gameSelectorBox = new VBox();
		gameSelectorBox.setSpacing(10);
		gameSelectorBox.setStyle("-fx-background-color: transparent;");

		gameSelectorBox.setMinWidth(boxWidth);
		gameSelectorBox.setMaxWidth(boxHeight);
	}

	private Pane setupNewGamePane(String gameName, String gameString, String gameDescription, Image gameImage) {
		Pane pane = new Pane();

		ImageView paneImage = new ImageView(new Image("file:background_image.jpg"));
		paneImage.setOpacity(0.4);
		paneImage.setFitWidth(boxWidth);
		paneImage.setFitHeight(boxHeight);
		pane.getChildren().add(paneImage);

		pane.setMinWidth(boxWidth);
		pane.setMaxWidth(boxWidth);
		pane.setMinHeight(paneHeight);
		pane.setMaxHeight(paneHeight);
		pane.setStyle("-fx-background-color: transparent;");

		ImageView imageView = setUpGameImage(gameImage);

		Text nameText = setUpGameText(gameString);
		Text descriptionText = setUpGameDescriptionText(gameDescription);
		Button playButton = makePlayButton(gameName);
		pane.getChildren().addAll(imageView, nameText, descriptionText, playButton);

		return pane;
	}

	private Button makePlayButton(String gameName) {
		Button playButton = new Button(playOrLoadGame);
		playButton.setLayoutX(750);
		playButton.setLayoutY(90);
		playButton.setOnAction((event) -> {
			buttonData.removeFromRoot(gameSelectorBox);
			buttonData.removeFromRoot(this);
			buttonData.playGame(gameName, isNew);
		});
		return playButton;
	}

	private Text setUpGameDescriptionText(String gameDescription) {
		Text descriptionText = new Text(gameDescription);
		descriptionText.setX(230);
		descriptionText.setY(90);
		descriptionText.setFont(new Font(font, 20));
		descriptionText.setFill(color);
		return descriptionText;
	}

	private ImageView setUpGameImage(Image gameImage) {
		ImageView imageView = new ImageView();
		imageView.setImage(gameImage);
		imageView.setX(20);
		imageView.setY(20);
		imageView.setFitHeight(160);
		imageView.setFitWidth(160);
		return imageView;
	}

	private Text setUpGameText(String gameString) {
		Text nameText = new Text(gameString);
		nameText.setX(225);
		nameText.setY(60);
		nameText.setStyle(" -fx-font: 50px Helvetica;\r\n"
				+ "    -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);\r\n"
				+ "    -fx-stroke: black;\r\n" + "    -fx-stroke-width: 1;");
		return nameText;
	}
}
