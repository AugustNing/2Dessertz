package data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.scene.media.Media;

/**
 * @author Edward Zhuang
 * Audio Manager class which allows user to save and retrieve audio files for games.
 */
public class AudioManager {

	private static final String BACKSLASH = "/";
    private static final String AUDIO = "audio/";
	private static final String MP3 = ".mp3";
	private String gameAudioLocation;
    private static final String baseLocation = "./data/gamedata/games/";
	
	public AudioManager(String gameName) {
		String gameLocation = baseLocation + gameName + BACKSLASH;
		gameAudioLocation = gameLocation + AUDIO;
	}

	/**
	 * Stores mp3 file into the current game's data.
	 * @param audioFile file to be saved
	 */
	public void storeAudio(File audioFile) {
		try {
			Files.copy(audioFile.toPath(), 
					(new File(gameAudioLocation + audioFile.getName())).toPath(), 
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			//this will always work due to file chooser
			return;
		}
	}

	/**
	 * Returns media file from audio file name
	 * @param audioName name of audio file
	 * @return Media file
	 */
	public Media getAudioMedia(String audioName) {
		return new Media(new File(gameAudioLocation+audioName).toURI().toString());
	}
	
}
