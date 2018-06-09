package authoring.display.controllers;

import data.ImageManager;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Edward Zhuang
 * Abstract Controller class. Contains ImageManager to provide front end components with relevant imageviews.
 * Also responsible for setting up observer/observable interactions between game components and front end components,
 * passing commands from the front end to the game, and for attaching front end components to the AuthoringEnvironment.
 */
public abstract class Controller {

	private ImageManager imageManager;
	
	public Controller(ImageManager imageManager) {
		this.imageManager = imageManager;
	}
	
	/**
	 * Protected getter for ImageManager.
	 * @return imageManager
	 */
	protected ImageManager getImageManager() {
		return imageManager;
	};
	
	/**
	 * Initializes screen components
	 */
	protected abstract void initializeScreenComponents();
	
	/**
	 * Sets up observer/observable connections
	 */
	protected abstract void setUpConnections();
	
	/**
	 * Adds UIComponent to AuthoringEnvironment Pane
	 * @param pane pane
	 */
	protected abstract void addToGUI(Pane pane);
	
	/**
	 * Refreshes attached front end components
	 */
	protected abstract void refreshView();
	
	/**
	 * gets image with ImageManagr
	 * @param imageName name of image
	 * @return Image
	 */
	public Image getImage(String imageName) {
		return imageManager.getImage(imageName);
	}
	
}


