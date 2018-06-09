package authoring.display.authoringuicomponents;


import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import authoring.AuthBehavior;
import authoring.GameObject;
import authoring.GameViewObservable;
import authoring.Property;
import authoring.SceneBackground;
import authoring.SceneBackgroundImage;
import authoring.SceneBackgroundImageSerializable;
import authoring.display.GameObjectImageView;
import authoring.display.controllers.GameViewWindowController;
import authoring.display.popups.LevelSizePopup;
import data.propertiesFiles.ResourceBundleManager;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


/**
 * 
 * @author Edward Zhuang
 * GameViewWindow which provides main visual representation for created game objects and scene background.
 */
public class GameViewWindow extends AuthoringUIComponent implements Observer {
	
	private static final double TRANSPARENT = 0.25;
	private static final String FOREGROUND = "Foreground";
	private static final String BACKGROUND = "Background";
	private List<ImageView> objectImageViews;
	private ScrollPane scrollPane;
	private Pane backgroundPane;
	private SceneBackground sceneBackground;
	private List<SceneBackgroundImage> list;
	private ComboBox<String> myPanelSelector; 
	private Pane foregroundPane;
	private GameViewWindowController controller;
	private Button myLevelSizeButton;
	private HBox hBox;
	private Button saveBackground;
	private GameViewObservable gameViewObservable;
	
	private static final int DEFAULTSIZEX = 1000;
	private static final int DEFAULTSIZEY = 1000;
	
	public GameViewWindow(GameViewWindowController controller) {
		this.controller = controller;
		objectImageViews = new ArrayList<>();
		updatePaneSize(DEFAULTSIZEX, DEFAULTSIZEY);
	}
	
	@Override
	protected void generateComponent() {
		BorderPane borderPane = getBorderPane();
		initializeFXComponents();
		mapFXActions();
		borderPane.setCenter(scrollPane);
		borderPane.setTop(hBox);
	}

	private void initializeButtons() {
		myLevelSizeButton = new Button(ResourceBundleManager.getAuthoring("EditLevelSize"));
		saveBackground = new Button(ResourceBundleManager.getAuthoring("SaveBackground"));
	}

	private void initializeComboBoxes() {
		myPanelSelector.setPromptText(ResourceBundleManager.getAuthoring("ChoosePanel"));
		myPanelSelector.getItems().add(BACKGROUND);
		myPanelSelector.getItems().add(FOREGROUND);
		myPanelSelector.valueProperty().addListener((o, old, key) -> {
			switchPanes(key);
		});
	}

	/**
	 * Called by Game when model is updated, updates the game window view
	 */
	@Override
	public void update(Observable o, Object arg) {
		gameViewObservable = (GameViewObservable) o;
		updateForeground(gameViewObservable.getMyObjects());
		updateBackground(gameViewObservable.getBackgroundImageSerializables());
	}
	
	private void updateForeground(List<GameObject> gameObjects) {
		foregroundPane.getChildren().clear();
		
		List<ImageView> list = new ArrayList<>();
		
		for (GameObject go: gameObjects) {
			ImageView imageView = toImageView(go);
			GameObjectImageView draggableImageView = new GameObjectImageView(imageView, go, controller);
			list.add(draggableImageView.getMyImage());
		}
		
		foregroundPane.getChildren().addAll(list);
		objectImageViews = list;
	}
	
	private ImageView toImageView(GameObject go) {
		Double xPosition = go.getxPos();
		Double yPosition = go.getyPos();			
		String imagePath = go.getImagePath();
		ImageView imageView = new ImageView(controller.getImage(imagePath + ".png"));
		imageView.setLayoutX(xPosition);
		imageView.setLayoutY(yPosition);
		
		try {
		imageView.setFitWidth(go.getDisplayWidth());
		imageView.setFitHeight(go.getDisplayHeight());
		} catch (NullPointerException e) {
			imageView.setPreserveRatio(true);
			imageView.setFitWidth(200);		
			go.setDisplayWidth(imageView.getBoundsInLocal().getWidth());
			go.setDisplayHeight(imageView.getBoundsInLocal().getHeight());
			imageView.setPreserveRatio(false);
		}
		return imageView;
	}
	
	private void updateBackground(List<SceneBackgroundImageSerializable> serializables) {
		list = new ArrayList<>();
		for (SceneBackgroundImageSerializable s: serializables) {
			list.add(controller.getBackgroundImage(s));
		}
		
		sceneBackground.clearPane();
		for (SceneBackgroundImage s: list) {
			sceneBackground.addImage(s);
		}
	}
	
	/**
	 * Switches between foreground and background editing, based on which option is selected in Combobox
	 * @param key key = FOREGROUND or BACKGROUND
	 */
	private void switchPanes(String key) {
		if (key.equals(BACKGROUND)) {
			for (ImageView imageView: objectImageViews) {
				imageView.setOpacity(TRANSPARENT);
				imageView.setMouseTransparent(true);
			}
			for (SceneBackgroundImage image: list) {
				image.setOpacity(1.0);
			}
			foregroundPane.setMouseTransparent(true);
		}
		
		if (key.equals(FOREGROUND)) {
				for (ImageView imageView: objectImageViews) {
					imageView.setOpacity(1);
					imageView.setMouseTransparent(false);
				}
				foregroundPane.setMouseTransparent(false);
			}
		}
	
	/**
	 * Resizes the panes of the gameviewwindow, called by level size popup
	 * @param x_size new window size x
	 * @param y_size new window size y
	 */
	public void updatePaneSize(int x_size, int y_size) {
		backgroundPane.setMinSize(x_size, y_size);
		foregroundPane.setMinSize(x_size, y_size);
		sceneBackground.setRectangle(x_size, y_size);
	}

	@Override
	protected void initializeFXComponents() {
		StackPane stackPane = new StackPane();
		list = new ArrayList<>();
		hBox = new HBox();
		myPanelSelector = new ComboBox<>();
		initializeButtons();
		initializeComboBoxes();
		hBox.getChildren().addAll(myPanelSelector, myLevelSizeButton,saveBackground);
		sceneBackground = new SceneBackground(ResourceBundleManager.getPosition("GAMEVIEWSIZE_X"), ResourceBundleManager.getPosition("GAMEVIEWSIZE_Y"));
		backgroundPane = sceneBackground.getPane();
		foregroundPane = new Pane();
		stackPane.setStyle("-fx-border-color: black");
		stackPane.setPrefSize(ResourceBundleManager.getPosition("GAMEVIEWSIZE_X"), ResourceBundleManager.getPosition("GAMEVIEWSIZE_Y"));
		scrollPane = new ScrollPane(stackPane);
		stackPane.getChildren().add(backgroundPane);
		stackPane.getChildren().add(foregroundPane);
	}

	@Override
	protected void mapFXActions() {
		myLevelSizeButton.setOnAction(e-> {
			new LevelSizePopup(this);
		});
		saveBackground.setOnAction(e->{
			controller.storeBackgroundImage(backgroundPane);
		});
	}
}
