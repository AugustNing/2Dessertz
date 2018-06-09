//package authoring.display.eventspopupold;
//
//import java.util.ResourceBundle;
//
//import authoring.Game;
//import authoring.GameObject;
//import authoring.display.AuthoringUIComponent;
//import javafx.geometry.Insets;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.layout.HBox;
//import javafx.scene.paint.Color;
//
///**
// * 
// * all panels will be in a VBox. All VBoxes will be placed on an Hbox.
// * the hbox will be the scene
// * 
// * @author Summer and August
// *
// */
//public class EventsPopUp extends AuthoringUIComponent {
//
////	private final int SCREEN_WIDTH = 50;
//	private final int SCREEN_HEIGHT = 400;
//
//	private Scene eventsPUScene;
//	private HBox eventsPUBox;
//	private EventsPopUpController epuc;
//
//	public EventsPopUp(ResourceBundle resources, Game game, Node root, GameObject go) {
//		super(resources, game, root);
//		eventsPUBox = new HBox();
//		epuc = new EventsPopUpController(game, go);
//		eventsPUBox.setPrefHeight(SCREEN_HEIGHT);
//		eventsPUBox.setPadding(new Insets(10));
//	    eventsPUBox.setSpacing(8);
//		eventsPUBox.getChildren().addAll(epuc.getWindows());
//		eventsPUScene = new Scene((Group) root, (int) eventsPUBox.getWidth(), SCREEN_HEIGHT, Color.GRAY);
//		((Group) root).getChildren().add(eventsPUBox);
//	}
//	
//	public Scene getScene() {
//		return eventsPUScene;
//	}
//
//}
