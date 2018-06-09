package authoring.display.popups.eventspopup;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import authoring.EngineClassRetriever;
import authoring.Event;
import authoring.GameObject;
import authoring.display.controllers.EventsPopupController;
import engine.events.elementevents.ElementEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class that is used to selected and add new events to the game object
 * @author Summer and August
 *
 */
public class EventsWindow extends VBox {

	private final Class<?> EVENTS_SUPERCLASS = ElementEvent.class;
	private final String EVENTS_PACKAGE = "engine.events.elementevents";
	
	private List<GameObject> gos;
	private ComboBox<String> possibleEvents;
	private EngineClassRetriever classRetriever;
	private ListView<Event> myEvents;
	private Event currentEvent;
	private EventsPopupController epuc;
	
	private static final String EVENTS = "Events";
	private static final String EVENTOPTIONS = "Events Options";
	private static final int SPACING = 10;
	private static final int SIZE = 200;
	
	/**
	 * @param myEPUC 	controller
	 * @param myGos 	the list of game objects to be edited 
	 */
	public EventsWindow(EventsPopupController myEPUC, List<GameObject> myGos) {
		gos = myGos;
		classRetriever = new EngineClassRetriever();
		epuc = myEPUC;
		myEvents = new ListView<>();
		myEvents.setMinHeight(SIZE);
		currentEvent = null;
		createVBox();
	}
	
	private void createVBox() {
		this.setPadding(new Insets(SPACING));
	    this.setSpacing(SPACING);
	    this.setMinWidth(SIZE);
	    Text title = new Text(EVENTS);
	    this.getChildren().add(title);
	    this.getChildren().addAll(makeEventDropdown(), makeEventList());
	}
	
	private ComboBox<String> makeEventDropdown(){
		possibleEvents = new ComboBox<>();
		possibleEvents.setPromptText(EVENTOPTIONS);
		Set<?> retrieved = new TreeSet<>();
		retrieved = classRetriever.getClasses(EVENTS_SUPERCLASS, EVENTS_PACKAGE);
		retrieved.forEach(c -> {
							String[] holder = c.toString().split(" ");
							String[] name = holder[holder.length - 1].split("\\.");
							String use = name[name.length-1];
							possibleEvents.getItems().add(use);
		});
		possibleEvents.setOnAction(e -> comboBoxAction(possibleEvents.getValue()));
		return possibleEvents;
	}
	
	private void comboBoxAction(String eventName) {
		currentEvent = new Event();
		currentEvent.setEventType(eventName);
		for (GameObject go : gos) {
			go.addEvent(currentEvent);
		}
		epuc.updateFromEvent();
	}

	private ListView<Event> makeEventList(){
		myEvents.getItems().clear();
		myEvents.getItems().setAll(gos.get(0).getEvents());
		myEvents.setOnMouseClicked(e -> eventListAction(e, myEvents.getSelectionModel().getSelectedItem()));
		return myEvents;
	}
	
	private void eventListAction(MouseEvent me, Event e) {
		if (me.getButton().equals(MouseButton.PRIMARY)) {
		currentEvent = e;
		} else if (me.getButton().equals(MouseButton.SECONDARY)) {
			for (GameObject go : gos) {
				go.deleteEvent(e);
			}
			currentEvent = null;
		}
		epuc.updateFromEvent();
	}
	
	public void updateEventList() {
		this.getChildren().remove(myEvents);
		this.getChildren().add(makeEventList());
	}
	
	public Event getCurrEvent() {
		return currentEvent;
	}
	
	public boolean validEvent() {
		return currentEvent != null;
	}
}
