package gamePlayer;

import java.io.IOException;

import com.dropbox.core.DbxException;

import data.propertiesFiles.ResourceBundleManager;
import engine.exceptions.ErrorBox;
import javafx.scene.control.Button;
import voogadropbox.VoogaDropbox;

public class UploadGameButton extends Button {

	private String gameName;
	private static final String buttonText = "Save and Upload";

	private final static String BASELOCATION = ResourceBundleManager.getPath("BASELOCATION");

	public UploadGameButton (String gameName/*, /*Whatever other Parameters you need*/) {
		this.gameName = gameName;
		setupButtonProperties(); //fill this in
		setupAction();
		
	}
	
	private void setupButtonProperties() {
		this.setText(buttonText);
	}

	private void setupAction() {
		this.setOnAction((event) -> {
			// must first save locally because saving online means going through local games
			// and uploading the one with the correct name
			saveGameLocally();
			saveGameOnline();

		});

	}
	
	private void saveGameLocally() {
		
	}

	private void saveGameOnline() {
		VoogaDropbox vd = new VoogaDropbox(BASELOCATION);
		try {
			vd.downloadGame(gameName);
		} catch (DbxException | IOException e) {
			// TODO Auto-generated catch block
			new ErrorBox("DropBox save failed!", "Couldn't save to DropBox");
		}
	}

}
