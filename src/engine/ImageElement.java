package engine;

import java.util.List;

import data.ImageManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Yashas Manjunatha, Gouttham Chandraekar, Martin Muenster
 * Wrapper around the JavaFX ImageView object mapping to a GameElement. Constructed and used 
 * in the DisplayState of the game. All GameElement objects are translated to an ImageElement object
 * and then displayed in the DisplayState.
 */
public class ImageElement extends ImageView {
	private GameElement elementReference;
	
	ImageManager imageManager;
	String imageName;
	
	/**
	 * Constructs an ImageElement object from a GameElement object.
	 * @param elementReference Reference to the GameElement object being mapped to this ImageElement object.
	 * @param imageManager ImageManager from which the image asset for this ImageElement can be queried.
	 */
	public ImageElement(GameElement elementReference, ImageManager imageManager) {
		this.elementReference = elementReference;
		this.imageManager = imageManager;
		imageName = (String)elementReference.reportProperties().get("imagePath");
		System.out.println(elementReference.getIdentifier());
		System.out.println("ImageElement: " + elementReference.getIdentifier() + " " + imageName);
		Image image = imageManager.getImage(imageName + ".png");
		this.setImage(image);
		
		updateState();
		
	}

	/**
	 * Updated the properties of the ImageView object using corresponding properties
	 * from the GameElement object.
	 */
	protected void updateState() {
		if (!((String)elementReference.reportProperties().get("imagePath")).equals(imageName)){
			imageName = (String)elementReference.reportProperties().get("imagePath");
			Image image = imageManager.getImage(imageName);
			this.setImage(image);
		}
		this.setFitWidth((double)elementReference.reportProperties().get("displayWidth"));
		this.setFitHeight((double)elementReference.reportProperties().get("displayHeight"));
		this.setTranslateX((double) elementReference.reportProperties().get("xPos"));
		this.setTranslateY((double) elementReference.reportProperties().get("yPos"));
	}
	
	/**
	 * Updated the properties of the ImageView object using corresponding properties
	 * from the GameElement object. Also, updates the location with an offset from the main character.
	 * This is how the scrolling is implemented by offsetting all objects around the 
	 * main character, which is the center.
	 * @param mainCharacterLocation Vector with the Main Character's Location
	 */
	protected void updateStateWithOffSet(List<Double> mainCharacterLocation) {
		if (!((String)elementReference.reportProperties().get("imagePath")).equals(imageName)){
			imageName = (String)elementReference.reportProperties().get("imagePath");
			Image image = imageManager.getImage(imageName);
			this.setImage(image);
		}
		this.setFitWidth((double)elementReference.reportProperties().get("displayWidth"));
		this.setFitHeight((double)elementReference.reportProperties().get("displayHeight"));
		this.setTranslateX((double) elementReference.reportProperties().get("xPos") + mainCharacterLocation.get(0));
		this.setTranslateY((double) elementReference.reportProperties().get("yPos") + mainCharacterLocation.get(1));
	}
	
	/**
	 * @return The reference to the GameElement mapped to this ImageElement
	 */
	protected GameElement getReference() {
		return elementReference;
	}
}
