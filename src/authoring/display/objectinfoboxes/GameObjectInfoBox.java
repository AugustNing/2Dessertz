package authoring.display.objectinfoboxes;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import authoring.GameObject;
import authoring.display.authoringuicomponents.ObjectCoordinatesInsertion;
import authoring.display.controllers.ObjectInfoSaver;
import authoring.display.popups.BehaviorPopup;
import authoring.display.popups.EventsPopup;
import data.propertiesFiles.ResourceBundleManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Edward Zhuang, Calvin Ma
 * GameObjectInfoBox class that shows information about the current GameObject in the GameScene.
 * The user is able to edit properties and behaviors of this GameObject and all other instances of it.
 */
public class GameObjectInfoBox extends ObjectInfoBox {

    private static final int TEXT_FIELD_PREF_WIDTH = 50;
    private static final int IMAGEVIEW_FIT_WIDTH = 200;
    private static final String INSTANCES = "Instances";
    private static final String GAME_OBJECT_PROPERTIES = "GameObjectProperties";
    private static final String EDIT_BEHAVIORS = "EditBehaviors";
    private static final String EDIT_EVENTS = "EditEvents";
    private static final String DUPLICATE = "Duplicate";
    private static final String CHANGE_NAME = "ChangeName";
    private static final String CHANGE_IMAGE = "ChangeImage";
    private static final int COLUMN_WIDTH = 125;
    private static final String CHOOSE_OBJECT_IMAGE = "Choose Object Image";
    private static final String IMAGE_FILES = "Image Files";
    private static final String PNG = "*.png";
    private static final String JPG = "*.jpg";
    private static final String GIF = "*.gif";
    private HBox buttonsHBox, nameHBox;
    private GameObject gameObject;
    private List<GameObject> objectInstances;
    private ObjectInfoSaver saver;
    private Button editBehaviorsButton;
    private Button editEventsButton;
    private Button duplicateButton;
    private Button changeNameButton;
    private Button changeImageButton;
    private TableView<ObjectCoordinatesInsertion> gameObjectCoordinates;
    private ImageView imageView;
    private TextField objectName;
    private Label instances;
    private Label properties;

    public GameObjectInfoBox(GameObject gameObject, List<GameObject> objectInstances, ObjectInfoSaver saver) {
        super();
        this.gameObject = gameObject;
        this.objectInstances = objectInstances;
        this.saver = saver;
        instances = new Label(ResourceBundleManager.getAuthoring(INSTANCES));
        properties = new Label(ResourceBundleManager.getAuthoring(GAME_OBJECT_PROPERTIES));
        objectName = new TextField();
        objectName.setPrefWidth(TEXT_FIELD_PREF_WIDTH);
        objectName.setText(this.gameObject.getName());
        imageView = new ImageView(saver.getImage(gameObject.getImagePath() + ".png"));
        initializeFXComponents();
        mapFXActions();
    }

    @Override
    public void initializeFXComponents() {
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(IMAGEVIEW_FIT_WIDTH);
        buttonsHBox = new HBox();
        nameHBox = new HBox();
        editBehaviorsButton = new Button(ResourceBundleManager.getAuthoring(EDIT_BEHAVIORS));
        editEventsButton = new Button(ResourceBundleManager.getAuthoring(EDIT_EVENTS));
        duplicateButton = new Button(ResourceBundleManager.getAuthoring(DUPLICATE));
        changeNameButton = new Button(ResourceBundleManager.getAuthoring(CHANGE_NAME));
        changeImageButton = new Button(ResourceBundleManager.getAuthoring(CHANGE_IMAGE));
        nameHBox.getChildren().addAll(changeNameButton, changeImageButton);
        buttonsHBox.getChildren().addAll(editBehaviorsButton, editEventsButton, duplicateButton);
        gameObjectCoordinates = new TableView<>();
        setupTableColumns();
        List<ObjectCoordinatesInsertion> insertions = new ArrayList<>();
        for (GameObject gameObject : objectInstances) {
            insertions.add(new ObjectCoordinatesInsertion(gameObject,
            		gameObject.getxPos(),
                    gameObject.getyPos()));
        }
        ObservableList<ObjectCoordinatesInsertion> observableInsertions = FXCollections.observableArrayList(insertions);
        gameObjectCoordinates.setItems(observableInsertions);
    }

    private void setupTableColumns() {
        gameObjectCoordinates.setEditable(true);
        TableColumn xposCol = new TableColumn("X");
        setupXposCol(xposCol);
        TableColumn yposCol = new TableColumn("Y");
        setupYposCol(yposCol);
        gameObjectCoordinates.getColumns().addAll(xposCol, yposCol);
    }

    private void setupXposCol(TableColumn<ObjectCoordinatesInsertion, String> xposCol) {
        xposCol.setCellValueFactory(f -> new ReadOnlyStringWrapper(f.getValue().getXpos()));
        xposCol.setMinWidth(COLUMN_WIDTH);
        xposCol.setMaxWidth(COLUMN_WIDTH);
        xposCol.setResizable(false);
        xposCol.setEditable(true);
        xposCol.setCellFactory(TextFieldTableCell.<ObjectCoordinatesInsertion>forTableColumn());
        xposCol.setOnEditCommit((CellEditEvent<ObjectCoordinatesInsertion, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow())
                    .setXpos(Double.parseDouble(t.getNewValue()));
            saver.updatePositions();
        });

    }

    private void setupYposCol(TableColumn<ObjectCoordinatesInsertion, String> yposCol) {
        yposCol.setCellValueFactory(f -> new ReadOnlyStringWrapper(f.getValue().getYpos()));
        yposCol.setMinWidth(COLUMN_WIDTH); 
        yposCol.setMaxWidth(COLUMN_WIDTH); 
        yposCol.setResizable(false);
        yposCol.setEditable(true);
        yposCol.setCellFactory(TextFieldTableCell.<ObjectCoordinatesInsertion>forTableColumn());
        yposCol.setOnEditCommit((CellEditEvent<ObjectCoordinatesInsertion, String> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow())
                    .setYpos(Double.parseDouble(t.getNewValue()));
            saver.updatePositions();
        });
    }

    @Override
    public void mapFXActions() {
        editBehaviorsButton.setOnAction(e -> {
            new BehaviorPopup(objectInstances);
        });
        editEventsButton.setOnAction(e -> {
            new EventsPopup(objectInstances, saver.getAllGameObjects());
        });

        duplicateButton.setOnAction(e -> {
            saver.duplicateGameObject();
        });
        changeNameButton.setOnAction(e -> {
            for (GameObject object : objectInstances) {
                object.setName(objectName.getText());
            }
            saver.updatePositions();
        });
        changeImageButton.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(CHOOSE_OBJECT_IMAGE);
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter(IMAGE_FILES, PNG, JPG, GIF));
                File image = fileChooser.showOpenDialog(new Stage());
                saver.setImage(new Image(image.toURI().toString()), gameObject.getImagePath());

            } catch (Exception exception) {
            }
            saver.updatePositions();
        });
    }

    @Override
    public void initializeVBox() {
        getVBox().getChildren().clear();
        getVBox().getChildren().addAll(properties, imageView, objectName, nameHBox, buttonsHBox, instances, gameObjectCoordinates);
    }

}
