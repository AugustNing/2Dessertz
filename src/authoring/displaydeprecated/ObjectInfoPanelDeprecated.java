package authoring.displaydeprecated;

import java.util.ResourceBundle;

import authoring.AuthBehavior;
import authoring.Game;
import authoring.GameObject;
import data.ImageManager;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Maddie Wilkinson
 *
 */
public class ObjectInfoPanelDeprecated extends MainWindowComponentDeprecated {
	private static final String IMAGES_FILEPATH = "./";
	private static final double PANEL_MIN_WIDTH = 300;

	private VBox myVBox;

	public ObjectInfoPanelDeprecated(ResourceBundle resources, Game game, Node root) {
		super(resources, game, root);
		myVBox = new VBox(DEFAULT_SPACING);
		myVBox.setMinWidth(PANEL_MIN_WIDTH);
		initialize();
	}

	private void initialize() {
		myVBox.getChildren().add(makeBehaviorBar());

	}

	private Node makeBehaviorBar() {
		HBox hBox = new HBox(DEFAULT_SPACING);
		hBox.getChildren().add(new Label("Object Properties"));
		return hBox;
	}

	private void makeObjectInfo() {
		GameObject currObject = getGame().getSceneManager().getCurrentScene().getCurrentGameObject();
		if(currObject != null) {
			myVBox.getChildren().add(makeBasicInfo(currObject));
		}
	}
	
	private Node makeBasicInfo(GameObject currObject) {
		VBox vBox = new VBox(DEFAULT_SPACING);
		vBox.setAlignment(Pos.CENTER);
		if(currObject != null) {
			AuthBehavior mandatory = currObject.getMandatoryBehavior();
			String imagePath = (String) mandatory.getProperty("imagePath").getValue();
			ImageView imageView = new ImageView(getGame().getImageManager().getImage(imagePath + ".png"));
			imageView.setPreserveRatio(true);
			imageView.setFitHeight(100);
			
			vBox.getChildren().addAll(imageView, new Label("Name: " + currObject.getName()), new Label("Image: " + imagePath));
		}
		return vBox;
	}
	
	private Node makeInstance(GameObject currObject) {
		if(currObject != null) {
			
		}
		return null;
	}

	public void updatePanel() {
		myVBox.getChildren().clear();
		initialize();
		makeObjectInfo();
	}

	public VBox asVBox() {
		return myVBox;
	}

	@Override
	protected Node asNode() {
		return myVBox;
	}
}
