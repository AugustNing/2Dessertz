package engine.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * @author Yashas Manjunatha
 * Wrapper around Java's MediaPlayer object to provide audio playing capabilities to the Engine.
 */
public class AudioPlayer {
	private static MediaPlayer mediaPlayer;
	
	/**
	 * Instantiating an AudioPlayer object collects the Media asset using the audio file path
	 * and starts a MediaPlayer object using the Media to begin playing the sound track.
	 * @param audioFile The File Path for the Audio File
	 * @param volume Volume of the Audio File (double between 0 and 1)
	 */
	public AudioPlayer(Media media, double volume) {
		//Media media = new Media(new File(audioFile).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		setVolume(volume);
		//this.play();
	}
	
	protected void addOnEndEvent(Runnable value) {
		mediaPlayer.setOnEndOfMedia(value);
	}
	
	/**
	 * Calling this method pauses the audio player.
	 */
	protected void pause() {
		mediaPlayer.pause();
	}
	
	/**
	 * Calling this method resumes the audio player.
	 */
	public void play() {
		mediaPlayer.play();
	}
	
	/**
	 * Calling this method stops the audio player.
	 */
	protected void stop() {
		mediaPlayer.stop();
	}
	
	/**
	 * Use this method to set the volume of the audio player.
	 * @param volume Volume of the Audio File (double between 0 and 1)
	 */
	protected void setVolume(double volume) {
		mediaPlayer.setVolume(volume);
	}
	
	public void loop() {
		mediaPlayer.setStartTime(Duration.seconds(0));
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}
}
