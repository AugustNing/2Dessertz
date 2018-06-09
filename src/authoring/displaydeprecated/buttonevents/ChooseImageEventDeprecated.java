package authoring.displaydeprecated.buttonevents;

import java.io.File;

import display.buttonevents.ButtonEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ChooseImageEventDeprecated implements ButtonEvent {
	private TextField myTextField;
	private String myDirectory;
	private FileChooser myFileChooser;
	private File myImage;

	public ChooseImageEventDeprecated(TextField textField, String directory, FileChooser fileChooser) {
		myTextField = textField;
		myDirectory = directory;
		myFileChooser = fileChooser;
	}

	@Override
	public void pressed() {
		try {
			myFileChooser.setTitle("Choose Object Image");
			myFileChooser.setInitialDirectory(new File(myDirectory));
			myFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
			myImage = myFileChooser.showOpenDialog(new Stage());
			myTextField.setText(myImage.getName().substring(0, myImage.getName().indexOf(".")));
			
		} catch (Exception e) {
			//do nothing
			//this just means the user didn't choose an image
			//which is a perfectly fine thing for them to do
		}
	}
	
	public Image getImage() {
		return new Image(myImage.toURI().toString());
	}
}
