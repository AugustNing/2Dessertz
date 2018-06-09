package authoring.displaydeprecated;

import java.util.List;
import java.util.ResourceBundle;

import authoring.AuthBehavior;
import authoring.Game;
import authoring.GameObject;
import authoring.GameScene;
import authoring.Property;
import authoring.SceneBackground;
import authoring.SceneManager;
import data.ImageManager;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameViewWindowDeprecated extends MainWindowComponentDeprecated{

	
	
	List<GameObject> gameObjects;
	ScrollPane scrollPane;
	StackPane stackPane;
	Pane sceneBackgroundPane;
	Pane myPane;
	ImageManager imageManager;
	int xSize, ySize;

	public GameViewWindowDeprecated(ResourceBundle resources, Game game, Node root, int x_Size, int y_Size){
		super(resources, game, root);
		scrollPane = new ScrollPane();
		stackPane = new StackPane();
		sceneBackgroundPane = new Pane();
		myPane = new Pane();
		//		setGameObjectList(game);
		//		addObjectsToPane();
		imageManager = new ImageManager(getGame().getName());
		xSize = x_Size;
		ySize = y_Size;
//		makeBackgroundPane(getGame().getGameImage(), xSize, ySize);
		initBackground();
		stackPane.getChildren().add(sceneBackgroundPane);
		stackPane.getChildren().add(myPane);
	}

	private void setGameObjectList(){
		gameObjects = getGame().getSceneManager().getCurrentScene().getMyObjects();
	}

//	public void updateWindow(List<GameObject> gameObjectsList) {
	public void updateWindow() {
		//		gameObjects = gameObjectsList;
		stackPane.getChildren().clear();
//		sceneBackgroundPane.getChildren().clear();
		myPane.getChildren().clear();
		setGameObjectList();
		addObjectsToPane();
//		makeBackgroundPane(getGame().getGameImage(), xSize, ySize);
		makeBackgroundPane();
		stackPane.getChildren().add(sceneBackgroundPane);
		stackPane.getChildren().add(myPane);
	}

	private void addObjectsToPane() {
		if(gameObjects != null) {
			for (GameObject object: gameObjects) {
				AuthBehavior mandatoryBehavior = object.getMandatoryBehavior();
				Property xPositionProperty = mandatoryBehavior.getProperty("xPos");
				Property yPositionProperty = mandatoryBehavior.getProperty("yPos");
				Property imagePathProperty = mandatoryBehavior.getProperty("imagePath");
				Double xPosition = (Double) xPositionProperty.getValue();
				Double yPosition = (Double) yPositionProperty.getValue();
				String imagePath = (String) imagePathProperty.getValue();
				System.out.println(xPosition + " " + yPosition + " " + imagePath);
				placeObject(xPosition, yPosition, imagePath);
			}
		}
	}

	private void placeObject(Double x, Double y, String imagePath) {
		//Eddie I think this is where you'll put the dragging and dropping stuff, or put those in separate methods and call them here?
		System.out.println(imagePath);
		ImageView imageView = new ImageView(imageManager.getImage(imagePath + ".png"));
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(100);
		imageView.setLayoutX(x);
		imageView.setLayoutY(y);
		myPane.getChildren().add(imageView);
	}

	/**
	 * @param imageName desired background image that will be tiled if smaller than the game size
	 * @param xSize refers to total size of the game/platform
	 * @param ySize refers to the total size of the game/platform
	 */
	private void makeBackgroundPane() {
//		tilePane.setMaxSize(xSize, ySize);
//		Image image = imageManager.getImage(imageName);
//		ImageView bgImage = new ImageView(image);
////		int bgImageWidth = (int) bgImage.getFitWidth();
////		int bgImageHeight = (int) bgImage.getFitHeight();
//		int bgImageWidth = xSize;
//		int bgImageHeight = ySize;
//		int columnCount = xSize / bgImageWidth;
//		int rowCount = ySize / bgImageHeight;
//		bgImage.setFitHeight(bgImageHeight);
//		bgImage.setFitWidth(bgImageWidth);
//
//		for (int i = 0; i < columnCount * rowCount; i++) {
//			tilePane.getChildren().add(bgImage);
//		}
//		tilePane = getGame().getSceneManager().getCurrentScene().getScenceBackground().getPane();
//		SceneManager sm = getGame().getSceneManager();
//		GameScene gs = sm.getCurrentScene();
//		SceneBackground sb = gs.getSceneBackground();
//		sceneBackgroundPane = sb.getPane();
//		System.out.println("scene background pane added from game view window");
		
		
	}
	private void initBackground() {
		sceneBackgroundPane.setPrefSize(1000, 1000);
	}
	
	public void switchPanes(String key) {
		if (key.equals("Edit Background")) {
			stackPane.getChildren().clear();
			stackPane.getChildren().add(sceneBackgroundPane);
		} 
		if (key.equals("Edit Objects")) {
			stackPane.getChildren().clear();
			stackPane.getChildren().add(sceneBackgroundPane);
			stackPane.getChildren().add(myPane);
		}
	}

	public Pane asPane() {
		return stackPane;
	}

	@Override
	protected Node asNode() {
		return stackPane;
	}

}
