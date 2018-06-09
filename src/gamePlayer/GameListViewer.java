package gamePlayer;

import java.util.List;
import gamePlayer.buttons.ButtonData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import voogadropbox.VoogaDropbox;

public class GameListViewer extends Pane {

	private List<String> gameList;
	private ButtonData buttonData;
	private VoogaDropbox voogaDropbox;

	private ListView<String> gameListView;
	private Button closeButton;
	private Button downloadButton;

	public GameListViewer(List<String> gameList, ButtonData buttonData, VoogaDropbox voogaDropbox) {
		this.gameList = gameList;
		this.buttonData = buttonData;
		this.voogaDropbox = voogaDropbox;

		setupBackground();
		setupListView();
		setupDownloadButton();
		setupCloseButton();

	}

	private void setupCloseButton() {
		closeButton = new Button("Close");
		closeButton.setLayoutX(140);
		closeButton.setLayoutY(315);
		closeButton.setMinWidth(80);
		closeButton.setOnAction((event) -> {
			buttonData.removeFromRoot(this);
		});
		this.getChildren().add(closeButton);

	}

	private void setupDownloadButton() {
		downloadButton = new Button("Download");
		downloadButton.setLayoutX(50);
		downloadButton.setLayoutY(315);
		downloadButton.setMinWidth(80);
		downloadButton.setOnAction((event) -> {
			try {
				if (!gameListView.getSelectionModel().isEmpty()) {
					voogaDropbox.downloadGame(gameListView.getSelectionModel().getSelectedItem());
					buttonData.removeFromRoot(this);

				}
			} catch (Exception e) {
				// selected item must be something from the dropbox so do nothing
			}
		});
		this.getChildren().add(downloadButton);

	}

	private void setupBackground() {
		this.setLayoutX(450);
		this.setLayoutY(125);
		this.setMinWidth(270);
		this.setMaxWidth(300);
		this.setMinHeight(350);
		this.setMaxHeight(350);
		this.setStyle("-fx-background-color:black;");

	}

	private void setupListView() {
		gameListView = new ListView<>();
		ObservableList<String> obvservableGameList = FXCollections.observableArrayList(gameList);
		gameListView.setMaxHeight(300);
		gameListView.setLayoutX(12);
		gameListView.setLayoutY(10);

		gameListView.setItems(obvservableGameList);
		this.getChildren().add(gameListView);

	}

}
