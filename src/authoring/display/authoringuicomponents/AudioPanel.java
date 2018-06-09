package authoring.display.authoringuicomponents;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import authoring.AudioObservable;
import authoring.display.controllers.AudioController;
import data.propertiesFiles.ResourceBundleManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * 
 * @author Edward Zhuang
 * Front end component which allows user to add audio files to game scene
 */
public class AudioPanel extends AuthoringUIComponent implements Observer {

	private static final int SPACING = 30;
	private HBox hBox;
	private Button chooseAudioButton;
	private Label currentAudio;
	private AudioController audioController;
	
	public AudioPanel(AudioController audioController) {
		super();
		this.audioController = audioController;
	}
	
	@Override
	protected void generateComponent() {
		initializeFXComponents();
		mapFXActions();
		getBorderPane().setCenter(hBox);
	}

	@Override
	protected void initializeFXComponents() {
		hBox = new HBox(SPACING);
		chooseAudioButton = new Button(ResourceBundleManager.getAuthoring("ChooseAudio"));
		currentAudio = new Label();
		hBox.getChildren().addAll(chooseAudioButton, currentAudio);
	}

	@Override
	protected void mapFXActions() {
		chooseAudioButton.setOnAction(e->{
			chooseAudioFile();
		});
	}

	/**
	 * When gamescene song is set, front end updates to reflect this
	 */
	@Override
	public void update(Observable o, Object arg) {
		AudioObservable audioObservable = (AudioObservable) o;
		currentAudio.setText("Current Audio: " + audioObservable.getAudioName());
	}

	private void chooseAudioFile() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new ExtensionFilter( "MP3 Files", "*.mp3")); 
		File audioFile = chooser.showOpenDialog(new Stage());
		audioController.setAudio(audioFile);
	}

}
