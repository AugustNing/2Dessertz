package authoring.tests;

import java.util.ArrayList;

import authoring.GameObject;
import authoring.display.popups.EventsPopup;
import authoring.display.popups.eventspopup.TWKPopup;
import authoring.display.popups.eventspopup.TriggerWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author August Ning
 * 
 * testing main class for the EventPopupRefactored
 *
 */
public class EventsPopUpTest extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		ArrayList<GameObject> gos1 = new ArrayList<>();
		ArrayList<GameObject> gos2 = new ArrayList<>();
		GameObject go1 = new GameObject();
		go1.setName("CalvinBoi");
		gos1.add(go1);
		
		GameObject go2 = new GameObject();
		go2.setName("JeffreyBoi");
		GameObject go3 = new GameObject();
		go3.setName("EddieBoi");
		GameObject go4 = new GameObject();
		go4.setName("AugustoBoi");
		GameObject go5 = new GameObject();
		go5.setName("Mar10Boi");
		gos2.add(go1);
		gos2.add(go2);
		gos2.add(go3);
		gos2.add(go4);
		gos2.add(go5);
		
		new EventsPopup(gos1, gos2);
	}



	public static void main(String args[]) {
		launch(args);
	}
}
