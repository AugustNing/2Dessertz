package engine.audio;

import java.util.ArrayList;
import java.util.List;

import data.AudioManager;
import javafx.scene.media.Media;

/**
 * @author Yashas Manjunatha
 * Manages all the AudioPlayer objects in the Engine. Also provides public methods to allow
 * the Player to set the volume of the audio and close all audio players on the close of the Engine.
 */
public class AudioPlayerManager {
	List<AudioPlayer> audioPlayers;
	private AudioManager audioManager;
	private double volume;
	
	/**
	 * Instantiates the AudioManager. Called when the Engine is instantiated.
	 * @param volume Initial volume for all new AudioPlayer objects. (double between 0 and 1)
	 */
	public AudioPlayerManager(String gameName, double volume) {
		audioPlayers = new ArrayList<>();
		audioManager = new AudioManager(gameName);
		this.volume = volume;
	}
	
	/**
	 * Instantiates a new AudioPlayer and starts playing the audio.
	 * @param mediaFile Path to the Media File to Play
	 * @return The AudioPlayer object createds
	 */
	public AudioPlayer newAudioPlayer(String mediaFile) {
		Media media = audioManager.getAudioMedia(mediaFile);
		AudioPlayer audioPlayer = new AudioPlayer(media, volume);
		/*audioPlayer.addOnEndEvent(new Runnable(){
			@Override
			public void run(){
				audioPlayers.remove(audioPlayer);
			}
		});*/
		audioPlayers.add(audioPlayer);
		audioPlayer.play();
		return audioPlayer;
	}
	
	/**
	 * This method sets a new volume for all the AudioPlayer objects.
	 * @param newVolume New Volume to Set (double between 0 and 1)
	 */
	public void setVolume(double newVolume) {
		this.volume = newVolume;
		for (AudioPlayer a : audioPlayers) {
			a.setVolume(newVolume);
		}
	}
	
	/**
	 * This method stops all AudioPlayer objects.
	 */
	public void stop() {
		for (AudioPlayer a : audioPlayers) {
			a.stop();
		}
		audioPlayers.clear();
	}
	
	/**
	 * This method pauses all AudioPlayer objects.
	 */
	public void pause() {
		for (AudioPlayer a : audioPlayers) {
			a.pause();
		}
	}
	
	/**
	 * This method resumes playing all AudioPlayer objects.
	 */
	public void play() {
		for (AudioPlayer a : audioPlayers) {
			a.play();
		}
	}
	
	/**
	 * Method Used to See Active Audio Players
	 */
	public void printAudioPlayers() {
		System.out.println(audioPlayers.toString());
	}
}
